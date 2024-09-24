package de.kybe.client.core.gui.gui.renderers.settings;

import de.kybe.client.impl.settings.BindSetting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class BindSettingRenderer {
	public static final int SETTING_START_X = 200;
	public static final int SETTING_WIDTH = 150;
	public static final int SETTING_HEIGHT = 15;


	public static void render(GuiGraphics guiGraphics, int yPosition, boolean selected, Font font, BindSetting setting) {
		int color = selected ? Color.BLUE.getRGB() : Color.GRAY.getRGB();
		color = setting.isInEditMode() ? Color.CYAN.getRGB() : color;
		guiGraphics.fill(SETTING_START_X, yPosition, SETTING_START_X + SETTING_WIDTH, yPosition + SETTING_HEIGHT, color);

		int textYPosition = yPosition + (SETTING_HEIGHT / 2) - (font.lineHeight / 2);
		guiGraphics.drawCenteredString(font, setting.getName() + ": " + GLFW.glfwGetKeyName(setting.getKeybind(), 0), SETTING_START_X + SETTING_WIDTH / 2, textYPosition, Color.WHITE.getRGB());
	}
}
