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
import com.google.gson.JsonObject;
import de.kybe.Kybe;
import de.kybe.gui.components.BooleanSetting;
import de.kybe.gui.components.Catagory;
import de.kybe.gui.components.Module;
import de.kybe.gui.components.Setting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static de.kybe.constants.Globals.mc;

public class Gui extends Screen {
	public static final int CATEGORY_START_X = 20;
	public static final int CATEGORY_START_Y = 50;
	public static final int CATEGORY_SPACING = 20;
	public static final int CATEGORY_WIDTH = 100;
	public static final int CATEGORY_HEIGHT = 15;

	public static final int MODULE_START_X = 140;
	public static final int MODULE_START_Y = 50;
	public static final int MODULE_SPACING = 20;
	public static final int MODULE_WIDTH = 100;
	public static final int MODULE_HEIGHT = 15;

	public static final int SETTING_START_X = 260;
	public static final int SETTING_START_Y = 50;
	public static final int SETTING_SPACING = 20;
	public static final int SETTING_WIDTH = 100;
	public static final int SETTING_HEIGHT = 15;

	private static final float FONT_SCALE = 0.75f;

	private List<Catagory> categories;
	private int selectedCategoryIndex = 0;
	private int selectedModuleIndex = 0;
	private int selectedSettingIndex = 0;
	private Selection selection = Selection.CATEGORY;

	enum Selection {
		CATEGORY,
		MODULE,
		SETTING
	}

	public Gui() {
		super(Component.literal("Kybe Client"));

		categories = loadCategories();
	}

	private List<Catagory> loadCategories() {
		Catagory combat = new Catagory("Combat");
		Module killAura = new Module("KillAura");
		Module autoClicker = new Module("AutoClicker");
		killAura.addSetting(new BooleanSetting("Range"));
		killAura.addSetting(new BooleanSetting("Speed"));
		combat.addModule(killAura);
		combat.addModule(autoClicker);

		Catagory fun = new Catagory("Fun");
		Module chatSpammer = new Module("ChatSpammer");
		fun.addModule(chatSpammer);
		return List.of(combat, fun);
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
		for (int i = 0; i < categories.size(); i++) {
			Catagory category = categories.get(i);
			int yPosition = CATEGORY_START_Y + i * CATEGORY_SPACING;

			int color = (i == selectedCategoryIndex && selection == Selection.CATEGORY) ? Color.BLUE.getRGB() : Color.GRAY.getRGB();
			guiGraphics.fill(CATEGORY_START_X, yPosition, CATEGORY_START_X + CATEGORY_WIDTH, yPosition + CATEGORY_HEIGHT, color);

			int textYPosition = yPosition + (CATEGORY_HEIGHT / 2) - (this.font.lineHeight / 2);
			guiGraphics.drawCenteredString(this.font, category.getName(), CATEGORY_START_X + CATEGORY_WIDTH / 2, textYPosition, Color.WHITE.getRGB());
		}
	}

	private void drawModules(GuiGraphics guiGraphics) {
		List<Module> modules = categories.get(selectedCategoryIndex).getModules();
		for (int i = 0; i < modules.size(); i++) {
			Module module = modules.get(i);
			int yPosition = MODULE_START_Y + i * MODULE_SPACING;

			int color = (i == selectedModuleIndex && selection == Selection.MODULE) ? Color.BLUE.getRGB() : Color.GRAY.getRGB();
			guiGraphics.fill(MODULE_START_X, yPosition, MODULE_START_X + MODULE_WIDTH, yPosition + MODULE_HEIGHT, color);

			int textYPosition = yPosition + (MODULE_HEIGHT / 2) - (this.font.lineHeight / 2);
			guiGraphics.drawCenteredString(this.font, module.getName(), MODULE_START_X + MODULE_WIDTH / 2, textYPosition, Color.WHITE.getRGB());
		}
	}

	private void drawSettings(GuiGraphics guiGraphics) {
		List<Setting> settings = categories.get(selectedCategoryIndex).getModules().get(selectedModuleIndex).getSettings();
		for (int i = 0; i < settings.size(); i++) {
			Setting setting = settings.get(i);
			int yPosition = SETTING_START_Y + i * SETTING_SPACING;

			int color = (i == selectedSettingIndex && selection == Selection.SETTING) ? Color.BLUE.getRGB() : Color.GRAY.getRGB();
			guiGraphics.fill(SETTING_START_X, yPosition, SETTING_START_X + SETTING_WIDTH, yPosition + SETTING_HEIGHT, color);

			int textYPosition = yPosition + (SETTING_HEIGHT / 2) - (this.font.lineHeight / 2);
			guiGraphics.drawCenteredString(this.font, setting.getName(), SETTING_START_X + SETTING_WIDTH / 2, textYPosition, Color.WHITE.getRGB());
		}
	}

	/*
	 * Save the settings
	 */
	@Override
	public void onClose() {
		super.onClose();
		saveSettings();
	}

	public void saveSettings() {
		try {
			JsonArray obj = new JsonArray();
			for (Catagory category : categories) {
				obj.add(category.serialize());
			}
			//save to an file
			File settingsFile = new File(mc.gameDirectory, "settings.json");

			String json = obj.toString();

			Kybe.LOGGER.info("Saving settings to: " + settingsFile.getAbsolutePath());
			Kybe.LOGGER.info(json);

			try (FileWriter writer = new FileWriter(settingsFile)) {
				writer.write(json);
			}

		} catch (Exception e) {
			Kybe.LOGGER.error("Failed to save settings", e);
		}
	}

	/*
	 * Handle key presses
	 */
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
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
				if (!categories.get(selectedCategoryIndex).getModules().isEmpty()) {
					selection = Selection.MODULE;
					selectedModuleIndex = 0;
				}
			}
			case MODULE -> {
				List<Module> modules = categories.get(selectedCategoryIndex).getModules();
				if (!modules.isEmpty()) {
					if (!modules.get(selectedModuleIndex).getSettings().isEmpty()) {
						selection = Selection.SETTING;
						selectedSettingIndex = 0;
					}
				}
			}
		}
	}

	private void moveSelectionLeft() {
		switch (selection) {
			case MODULE -> {
				selection = Selection.CATEGORY;
				selectedCategoryIndex = Math.min(selectedCategoryIndex, categories.size() - 1);
				selectedModuleIndex = 0;
			}
			case SETTING -> {
				selection = Selection.MODULE;
				selectedModuleIndex = Math.min(selectedModuleIndex, categories.get(selectedCategoryIndex).getModules().size() - 1);
				selectedSettingIndex = 0;
			}
		}
	}

	private void moveSelectionDown() {
		switch (selection) {
			case CATEGORY -> {
				if (selectedCategoryIndex < categories.size() - 1) {
					selectedCategoryIndex++;
				} else {
					if (!categories.get(selectedCategoryIndex).getModules().isEmpty()) {
						selection = Selection.MODULE;
						selectedModuleIndex = 0;
					}
				}
			}
			case MODULE -> {
				List<Module> modules = categories.get(selectedCategoryIndex).getModules();
				if (selectedModuleIndex < modules.size() - 1) {
					selectedModuleIndex++;
				} else {
					if (!modules.get(selectedModuleIndex).getSettings().isEmpty()) {
						selection = Selection.SETTING;
						selectedSettingIndex = 0;
					}
				}
			}
			case SETTING -> {
				List<Setting> settings = categories.get(selectedCategoryIndex).getModules().get(selectedModuleIndex).getSettings();
				if (selectedSettingIndex < settings.size() - 1) {
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

}
