package de.kybe.client.core.gui.components;

import de.kybe.client.core.gui.GUI;
import de.kybe.client.core.gui.components.setting.SettingComponent;
import de.kybe.client.core.gui.components.setting.settings.BooleanComponent;
import de.kybe.client.core.gui.misc.TempColors;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.setting.Setting;
import de.kybe.client.core.util.renders.font.FontUtils;
import de.kybe.client.core.util.renders.render2d.Rect;
import de.kybe.client.impl.settings.BooleanSetting;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.ArrayList;

import static de.kybe.Kybe.mc;

public class ModuleComponent extends Component {

	private static final int setting_height = 15; //TODO make this load from config

	public static int firstVisibleSettingIndex = 0;
	public static int maxVisibleSettings;

	public ArrayList<SettingComponent> settings = new ArrayList<>();

	public Module module;

	public ModuleComponent(int x, int y, int width, int height, Color color, Module module) {
		super(x, y, width, height);
		this.module = module;
		addSettings();
		adjustSettings();
		firstVisibleSettingIndex = 0;
	}

	@Override
	public void mouseScrolled(double mouseX, double mouseY, double scrollAmount) {
		if (GUI.mainComponent.contains_setting_area(mouseX, mouseY)) {
			int totalSettings = settings.size();

			if (totalSettings > maxVisibleSettings) {
				if (scrollAmount > 0 && firstVisibleSettingIndex > 0) {
					firstVisibleSettingIndex = Math.max(0, firstVisibleSettingIndex - 1);
				} else if (scrollAmount < 0 && firstVisibleSettingIndex < totalSettings - maxVisibleSettings) {
					firstVisibleSettingIndex = Math.min(totalSettings - maxVisibleSettings, firstVisibleSettingIndex + 1);
				}

				adjustSettings();
			}
		}
	}

	@Override
	public void mouseReleased(double mouseX, double mouseY, int button) {
		adjustSettings();
	}

	@Override
	public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		adjustSettings();
	}

	public void addSettings() {
		settings.clear();
		for (Setting setting : this.module.getSettings()) {
			if (setting instanceof BooleanSetting b_setting) {
				String settingname = b_setting.getName() + ": " + b_setting.isToggled();
				BooleanComponent boolsetting = new BooleanComponent(0, 0, FontUtils.getStringWidth(settingname), setting_height, setting);
				settings.add(boolsetting);
			}
		}
		adjustSettings();
	}

	public void adjustSettings() {
		int padding_h = 2;
		int padding_v = 2;
		int y_pos = GUI.mainComponent.getY() + 15 + 6 + FontUtils.getStringHeight() + padding_v;

		maxVisibleSettings = Math.max(1, (GUI.mainComponent.height - 15) / (setting_height + padding_v)) - 1;

		for (int i = 0; i < maxVisibleSettings; i++) {
			int settingIndex = firstVisibleSettingIndex + i;

			if (settingIndex < 0 || settingIndex >= settings.size()) {
				break;
			}

			SettingComponent settingComponent = settings.get(settingIndex);
			int x_pos = GUI.mainComponent.getX() + MainComponent.categorySize + (MainComponent.workingSize / 3) + 4 + padding_h;
			settingComponent.setPosition(x_pos, y_pos);
			y_pos += settingComponent.height + padding_v;
		}
	}

	@Override
	public void drawScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

		if (module.getState()) {
			Rect.drawOutlinedSquare(x, y, width, height, 1, TempColors.color_module_enabled, TempColors.color_accent);
		} else {
			Rect.drawOutlinedSquare(x, y, width, height, 1, TempColors.color_background_darker, TempColors.color_accent);
		}

		guiGraphics.drawCenteredString(mc.font, module.getName(), x + (width / 2), y + (height / 2) - (mc.font.lineHeight / 2), Color.WHITE.getRGB());

		if (isCurrentMod()) {
			int x_pos = GUI.mainComponent.getX() + MainComponent.categorySize + MainComponent.workingSize / 3 * 2;
			int y_pos = GUI.mainComponent.getY() + 15 + 2;
			guiGraphics.drawCenteredString(mc.font, MainComponent.current_category.current_module.module.getName(), x_pos, y_pos, Color.WHITE.getRGB());
		}

		for (int i = 0; i < maxVisibleSettings; i++) {
			if (isCurrentMod()) {
				int settingIndex = firstVisibleSettingIndex + i;

				if (settingIndex < 0 || settingIndex >= settings.size()) {
					break;
				}

				SettingComponent settingComponent = settings.get(settingIndex);
				settingComponent.drawScreen(guiGraphics, mouseX, mouseY, partialTicks);
			}
		}


		super.drawScreen(guiGraphics, mouseX, mouseY, partialTicks);

	}

	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if (this.isHovered((int) mouseX, (int) mouseY)) {

			if (isHovered((int) mouseX, (int) mouseY) && MainComponent.current_category != null && MainComponent.current_category.category == this.module.getCategory()) {
				if (button == 0) {
					this.module.toggle();
				} else if (button == 1) {
					MainComponent.current_category.current_module = this;
					addSettings();
				}
			}
		}

		for (SettingComponent settingComponent : settings) {
			settingComponent.mouseClicked(mouseX, mouseY, button);
		}

		super.mouseClicked(mouseX, mouseY, button);
	}

	private boolean isCurrentMod() {
		return MainComponent.current_category != null && MainComponent.current_category.current_module == this;
	}
}