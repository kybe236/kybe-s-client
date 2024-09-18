package de.kybe.gui.components.renderers.category;

import de.kybe.gui.components.CategoryEnum;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class CategoryRenderer {
	public static final int CATEGORY_START_X = 0;
	public static final int CATEGORY_WIDTH = 100;
	public static final int CATEGORY_HEIGHT = 15;

	CategoryEnum category;

	public CategoryRenderer(CategoryEnum category) {
		this.category = category;
	}

	public void render(GuiGraphics guiGraphics, int yPosition, boolean selected, Font font) {
		int color = selected ? Color.BLUE.getRGB() : Color.GRAY.getRGB();
		guiGraphics.fill(CATEGORY_START_X, yPosition, CATEGORY_START_X + CATEGORY_WIDTH, yPosition + CATEGORY_HEIGHT, color);

		int textYPosition = yPosition + (CATEGORY_HEIGHT / 2) - (font.lineHeight / 2);
		guiGraphics.drawCenteredString(font, category.name(), CATEGORY_START_X + CATEGORY_WIDTH / 2, textYPosition, Color.WHITE.getRGB());
	}
}
