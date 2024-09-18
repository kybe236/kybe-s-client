package de.kybe.gui.components.renderers.settings;

import de.kybe.gui.components.settings.Setting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;


public abstract class SettingRenderer {
	public static final int SETTING_START_X = 200;
	public static final int SETTING_WIDTH = 100;
	public static final int SETTING_HEIGHT = 15;

	Setting setting;
	public SettingRenderer(Setting setting) {
		this.setting = setting;
	}

	protected SettingRenderer() {
	}

	public abstract void render(GuiGraphics guiGraphics, int yPosition, boolean selected, Font font);
}