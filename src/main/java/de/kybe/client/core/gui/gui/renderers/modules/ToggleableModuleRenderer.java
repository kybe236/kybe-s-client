package de.kybe.client.core.gui.gui.renderers.modules;

import de.kybe.client.core.module.ToggleableModule;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

import static de.kybe.client.core.gui.gui.renderers.modules.ModuleRenderer.*;

public class ToggleableModuleRenderer {
	public static void render(GuiGraphics guiGraphics, int yPosition, boolean selected, Font font, ToggleableModule module) {
		int color = (selected) ? Color.BLUE.getRGB() : Color.GRAY.getRGB();
		//noinspection DuplicatedCode
		color = module.isToggled() ? Color.GREEN.getRGB() : color;

		guiGraphics.fill(MODULE_START_X, yPosition, MODULE_START_X + MODULE_WIDTH, yPosition + MODULE_HEIGHT, color);
		int textYPosition = yPosition + (MODULE_HEIGHT / 2) - (font.lineHeight / 2);
		guiGraphics.drawCenteredString(font, module.getName(), MODULE_START_X + MODULE_WIDTH / 2, textYPosition, Color.WHITE.getRGB());
	}
}
