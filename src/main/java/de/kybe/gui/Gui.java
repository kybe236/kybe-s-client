/*
 * Copyright (c) 2024 kybe236
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.kybe.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.kybe.Kybe;
import de.kybe.baseModules.Module;
import de.kybe.baseModules.ToggleableModule;
import de.kybe.baseSettings.EnumSetting;
import de.kybe.gui.renderers.category.CategoryRenderer;
import de.kybe.gui.renderers.modules.ModuleRenderer;
import de.kybe.gui.renderers.modules.ToggleableModuleRenderer;
import de.kybe.gui.renderers.settings.BooleanSettingRenderer;
import de.kybe.gui.renderers.settings.EnumSettingRenderer;
import de.kybe.gui.renderers.settings.NumberSettingRenderer;
import de.kybe.baseSettings.BooleanSetting;
import de.kybe.baseSettings.NumberSetting;
import de.kybe.baseSettings.Setting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static de.kybe.constants.Globals.mc;

public class Gui extends Screen {
	public static final int CATEGORY_START_Y = 0;
	public static final int CATEGORY_SPACING = 15;
	public static final int MODULE_START_Y = 0;
	public static final int MODULE_SPACING = 15;
	private static final ArrayList<Module> modules = new ArrayList<>();
	private static final int SETTING_START_Y = 0;
	private static final int SETTING_SPACING = 15;
	private int selectedCategoryIndex = 0;
	private int selectedModuleIndex = 0;
	private int selectedSettingIndex = 0;
	private Selection selection = Selection.CATEGORY;

	public Gui() {
		super(Component.literal("Kybe Client"));
		loadSettings();
	}

	public static void addModule(Module module) {
		modules.add(module);
	}

	@SuppressWarnings("unused")
	public static List<Module> getModules() {
		return modules;
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	public void render(GuiGraphics guiGraphics, int i, int j, float f) {
		super.render(guiGraphics, i, j, f);

		drawCategories(guiGraphics);
		drawModules(guiGraphics);
		if (selection == Selection.MODULE || selection == Selection.SETTING) {
			drawSettings(guiGraphics);
		}
	}

	private void drawCategories(GuiGraphics guiGraphics) {
		CategoryEnum[] categories = CategoryEnum.values();
		for (int i = 0; i < categories.length; i++) {
			CategoryEnum category = categories[i];
			int yPosition = CATEGORY_START_Y + i * CATEGORY_SPACING;

			new CategoryRenderer(category).render(guiGraphics, yPosition, i == selectedCategoryIndex && selection == Selection.CATEGORY, this.font);
		}
	}

	private void drawModules(GuiGraphics guiGraphics) {
		CategoryEnum selectedCategory = CategoryEnum.values()[selectedCategoryIndex];
		List<Module> categoryModules = getModulesForCategory(selectedCategory);

		for (int i = 0; i < categoryModules.size(); i++) {
			Module module = categoryModules.get(i);
			int yPosition = MODULE_START_Y + i * MODULE_SPACING;

			if (module instanceof ToggleableModule toggleableModule) {
				ToggleableModuleRenderer.render(guiGraphics, yPosition, i == selectedModuleIndex && selection == Selection.MODULE, this.font, toggleableModule);
			} else if (module instanceof Module md) {
				ModuleRenderer.render(guiGraphics, yPosition, i == selectedModuleIndex && selection == Selection.MODULE, this.font, md);
			}
		}
	}

	private void drawSettings(GuiGraphics guiGraphics) {
		CategoryEnum selectedCategory = CategoryEnum.values()[selectedCategoryIndex];
		List<Module> categoryModules = getModulesForCategory(selectedCategory);

		if (categoryModules.isEmpty()) return;
		Module selectedModule = categoryModules.get(selectedModuleIndex);
		List<Setting> settings = selectedModule.getSettings();

		for (int i = 0; i < settings.size(); i++) {
			Setting setting = settings.get(i);
			int yPosition = SETTING_START_Y + i * SETTING_SPACING;
			switch (setting) {
				case NumberSetting<?> numberSetting ->
						NumberSettingRenderer.render(guiGraphics, yPosition, i == selectedSettingIndex && selection == Selection.SETTING, this.font, numberSetting);
				case BooleanSetting booleanSetting ->
						BooleanSettingRenderer.render(guiGraphics, yPosition, i == selectedSettingIndex && selection == Selection.SETTING, this.font, booleanSetting);
				case EnumSetting<?> enumSetting ->
						EnumSettingRenderer.render(guiGraphics, yPosition, i == selectedSettingIndex && selection == Selection.SETTING, this.font, enumSetting);
				case null, default ->
						guiGraphics.drawString(this.font, "NO RENDERER FOUND", 200, yPosition, 0xFFFFFFFF);
			}
		}
	}

	/*
	 * Save the settings
	 */
	@Override
	public void onClose() {
		super.onClose();
		resetEditMode();
		saveSettings();
	}


	public void resetEditMode() {
		CategoryEnum selectedCategory = CategoryEnum.values()[selectedCategoryIndex];
		List<Module> categoryModules = getModulesForCategory(selectedCategory);

		if (categoryModules.isEmpty()) return;
		Module selectedModule = categoryModules.get(selectedModuleIndex);
		List<Setting> settings = selectedModule.getSettings();

		for (Setting setting : settings) {
			if (setting instanceof NumberSetting<?> numberSetting) {
				numberSetting.setEditMode(false);
			} else if (setting instanceof EnumSetting<?> enumSetting) {
				enumSetting.setEditMode(false);
			}
		}
	}

	public void saveSettings() {
		try {
			JsonArray jsonModules = new JsonArray();

			// Loop through each module and serialize it
			for (Module module : modules) {
				jsonModules.add(module.serialize());
			}

			// Save to a file
			File settingsFile = new File(mc.gameDirectory, "settings.json");

			String json = jsonModules.toString();

			// TODO maybe remove this
			Kybe.LOGGER.info(json);

			try (FileWriter writer = new FileWriter(settingsFile)) {
				writer.write(json);
			}

		} catch (Exception e) {
			Kybe.LOGGER.error("Failed to save settings", e);
		}
	}


	public void loadSettings() {
		try {
			File settingsFile = new File(mc.gameDirectory, "settings.json");

			JsonArray jsonModules = JsonParser.parseReader(Files.newBufferedReader(settingsFile.toPath())).getAsJsonArray();

			for (JsonElement obj : jsonModules.asList()) {
				if (!obj.isJsonObject()) {
					continue;
				}

				JsonObject moduleObj = obj.getAsJsonObject();
				for (Module module : modules) {
					if (moduleObj.get("name").getAsString().equals(module.getName())) {
						module.deserialize(moduleObj);
					}
				}
			}
		} catch (Exception e) {
			Kybe.LOGGER.error("Failed to load settings", e);
		}
	}

	/*
	 * Handle key presses
	 */
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		switch (selection) {
			case Selection.SETTING -> {
				/*
				 * Handle keypress inside selected setting
				 */
				List<Module> selectedCategoryModules = getModulesForCategory(CategoryEnum.values()[selectedCategoryIndex]);
				List<Setting> moduleSettings = selectedCategoryModules.get(selectedModuleIndex).getSettings();
				if (!moduleSettings.isEmpty()) {
					/*
					 * If the setting handled the keypress, return true
					 * So integer settings can increment/decrement without moving the selection
					 */
					Setting setting = moduleSettings.get(selectedSettingIndex);
					if (setting.handleKeyPress(keyCode)) {
						return true;
					}
				}
			}
			case Selection.MODULE -> {
				/*
				 * Handle keypress inside selected module
				 */
				List<Module> selectedCategoryModules = getModulesForCategory(CategoryEnum.values()[selectedCategoryIndex]);
				if (!selectedCategoryModules.isEmpty()) {
					/*
					 * Return true if the module handled the keypress
					 *
					 *
					 * TODO: maybe remove boolean value for handleKeyPress on an Module
					 */
					Module module = selectedCategoryModules.get(selectedModuleIndex);
					if (module instanceof ToggleableModule toggleableModule) {
						if (toggleableModule.handleKeyPress(keyCode)) {
							return true;
						}
					} else {
						if (module.handleKeyPress(keyCode)) {
							return true;
						}
					}
				}
			}
		}
		switch (keyCode) {
			case GLFW.GLFW_KEY_UP -> {
				moveSelectionUp();
				return true;
			}
			case GLFW.GLFW_KEY_DOWN -> {
				moveSelectionDown();
				return true;
			}
			case GLFW.GLFW_KEY_RIGHT -> {
				moveSelectionRight();
				return true;
			}
			case GLFW.GLFW_KEY_LEFT -> {
				moveSelectionLeft();
				return true;
			}
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	private void moveSelectionRight() {
		switch (selection) {
			case CATEGORY -> {
				List<Module> selectedCategoryModules = getModulesForCategory(CategoryEnum.values()[selectedCategoryIndex]);
				if (!selectedCategoryModules.isEmpty()) {
					selection = Selection.MODULE;
					selectedModuleIndex = 0;
				}
			}
			case MODULE -> {
				List<Setting> selectedModuleSettings = getModulesForCategory(CategoryEnum.values()[selectedCategoryIndex])
						.get(selectedModuleIndex).getSettings();
				if (!selectedModuleSettings.isEmpty()) {
					selection = Selection.SETTING;
					selectedSettingIndex = 0;
				}
			}
		}
	}

	private void moveSelectionLeft() {
		switch (selection) {
			case MODULE -> {
				selection = Selection.CATEGORY;
				selectedModuleIndex = 0;
			}
			case SETTING -> {
				selection = Selection.MODULE;
				List<Module> selectedCategoryModules = getModulesForCategory(CategoryEnum.values()[selectedCategoryIndex]);
				selectedModuleIndex = Math.min(selectedModuleIndex, selectedCategoryModules.size() - 1);
				selectedSettingIndex = 0;
			}
		}
	}

	private void moveSelectionDown() {
		switch (selection) {
			case CATEGORY -> {
				if (selectedCategoryIndex < CategoryEnum.values().length - 1) {
					selectedCategoryIndex++;
				} else {
					List<Module> selectedCategoryModules = getModulesForCategory(CategoryEnum.values()[selectedCategoryIndex]);
					if (!selectedCategoryModules.isEmpty()) {
						selection = Selection.MODULE;
						selectedModuleIndex = 0;
					}
				}
			}
			case MODULE -> {
				List<Module> selectedCategoryModules = getModulesForCategory(CategoryEnum.values()[selectedCategoryIndex]);
				if (selectedModuleIndex < selectedCategoryModules.size() - 1) {
					selectedModuleIndex++;
				} else {
					List<Setting> moduleSettings = selectedCategoryModules.get(selectedModuleIndex).getSettings();
					if (!moduleSettings.isEmpty()) {
						selection = Selection.SETTING;
						selectedSettingIndex = 0;
					}
				}
			}
			case SETTING -> {
				List<Module> selectedCategoryModules = getModulesForCategory(CategoryEnum.values()[selectedCategoryIndex]);
				List<Setting> moduleSettings = selectedCategoryModules.get(selectedModuleIndex).getSettings();
				if (selectedSettingIndex < moduleSettings.size() - 1) {
					selectedSettingIndex++;
				}
			}
		}
	}

	private void moveSelectionUp() {
		switch (selection) {
			case CATEGORY -> {
				if (selectedCategoryIndex > 0) {
					selectedCategoryIndex--;
				}
			}
			case MODULE -> {
				if (selectedModuleIndex > 0) {
					selectedModuleIndex--;
				} else {
					selection = Selection.CATEGORY;
					selectedCategoryIndex = Math.max(selectedCategoryIndex, 0);
				}
			}
			case SETTING -> {
				if (selectedSettingIndex > 0) {
					selectedSettingIndex--;
				} else {
					selection = Selection.MODULE;
					selectedModuleIndex = Math.max(selectedModuleIndex, 0);
				}
			}
		}
	}

	private List<Module> getModulesForCategory(CategoryEnum category) {
		return modules.stream()
				.filter(module -> module.getCategory() == category)
				.toList();
	}

	enum Selection {
		CATEGORY,
		MODULE,
		SETTING
	}

}
