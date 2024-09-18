package de.kybe.gui.components.renderers.settings;

import de.kybe.settings.BooleanSetting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

import static de.kybe.gui.components.renderers.settings.NumberSettingRenderer.*;

public class BooleanSettingRenderer {
	public static void render(GuiGraphics guiGraphics, int yPosition, boolean selected, Font font, BooleanSetting setting) {
		int color = selected ? Color.BLUE.getRGB() : Color.GRAY.getRGB();
		color = setting.isToggled() ? Color.GREEN.getRGB() : color;

		guiGraphics.fill(SETTING_START_X, yPosition, SETTING_START_X + SETTING_WIDTH, yPosition + SETTING_HEIGHT, color);
		int textYPosition = yPosition + (SETTING_HEIGHT / 2) - (font.lineHeight / 2);
		guiGraphics.drawCenteredString(font, setting.getName(), SETTING_START_X + SETTING_WIDTH / 2, textYPosition, Color.WHITE.getRGB());
	}
}
