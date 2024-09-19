package de.kybe.gui.renderers.settings;

import de.kybe.baseSettings.NumberSetting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class NumberSettingRenderer {
	public static final int SETTING_START_X = 200;
	public static final int SETTING_WIDTH = 150;
	public static final int SETTING_HEIGHT = 15;


	public static void render(GuiGraphics guiGraphics, int yPosition, boolean selected, Font font, NumberSetting<?> setting) {
		int color = selected ? Color.BLUE.getRGB() : Color.GRAY.getRGB();
		color = setting.isInEditMode() ? Color.CYAN.getRGB() : color;
		guiGraphics.fill(SETTING_START_X, yPosition, SETTING_START_X + SETTING_WIDTH, yPosition + SETTING_HEIGHT, color);

		int textYPosition = yPosition + (SETTING_HEIGHT / 2) - (font.lineHeight / 2);
		guiGraphics.drawString(font, setting.getName() + ": " + setting.getValue(), SETTING_START_X + 5, textYPosition, Color.WHITE.getRGB());
	}
}