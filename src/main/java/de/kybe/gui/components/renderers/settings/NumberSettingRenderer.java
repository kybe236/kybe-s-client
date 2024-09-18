package de.kybe.gui.components.renderers.settings;

import de.kybe.gui.components.settings.NumberSetting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class NumberSettingRenderer extends SettingRenderer {
	NumberSetting<?> setting;

	public NumberSettingRenderer(NumberSetting<?> setting) {
		this.setting = setting;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int yPosition, boolean selected, Font font) {
		int color = selected ? Color.BLUE.getRGB() : Color.GRAY.getRGB();
		color = setting.isInEditMode() ? Color.CYAN.getRGB() : color;
		guiGraphics.fill(SettingRenderer.SETTING_START_X, yPosition, SettingRenderer.SETTING_START_X + SettingRenderer.SETTING_WIDTH, yPosition + SettingRenderer.SETTING_HEIGHT, color);

		int textYPosition = yPosition + (SettingRenderer.SETTING_HEIGHT / 2) - (font.lineHeight / 2);
		guiGraphics.drawString(font, setting.getName() + ": " + setting.getValue(), SettingRenderer.SETTING_START_X + 5, textYPosition, Color.WHITE.getRGB());
	}
}
