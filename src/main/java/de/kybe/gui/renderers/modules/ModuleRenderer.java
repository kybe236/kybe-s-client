package de.kybe.gui.renderers.modules;

import de.kybe.baseModules.Module;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class ModuleRenderer {
	public static final int MODULE_START_X = 100;
	public static final int MODULE_WIDTH = 100;
	public static final int MODULE_HEIGHT = 15;

	public static void render(GuiGraphics guiGraphics, int yPosition, boolean selected, Font font, Module module) {
		int color = (selected) ? Color.BLUE.getRGB() : Color.GRAY.getRGB();
		guiGraphics.fill(MODULE_START_X, yPosition, MODULE_START_X + MODULE_WIDTH, yPosition + MODULE_HEIGHT, color);

		int textYPosition = yPosition + (MODULE_HEIGHT / 2) - (font.lineHeight / 2);
		guiGraphics.drawCenteredString(font, module.getName(), MODULE_START_X + MODULE_WIDTH / 2, textYPosition, Color.WHITE.getRGB());
	}
}
