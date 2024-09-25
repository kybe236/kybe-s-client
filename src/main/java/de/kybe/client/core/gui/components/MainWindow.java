package de.kybe.client.core.gui.components;

import de.kybe.client.core.util.renders.render2d.Rect;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class MainWindow {

	private final int min_width;
	private final int min_height;
	private final int max_width;
	private final int max_height;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean dragging = false;
	private boolean scaling = false;
	private int dragOffsetX, dragOffsetY;
	private int scaleOffsetX, scaleOffsetY;

	public MainWindow(int x, int y, int width, int height, int min_width, int min_height, int max_width, int max_height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.min_width = min_width;
		this.min_height = min_height;
		this.max_width = max_width;
		this.max_height = max_height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(int width, int height) {
		this.width = Math.max(min_width, Math.min(width, max_width));
		this.height = Math.max(min_height, Math.min(height, max_height));
	}

	// Method to draw the window (to be called in render)
	public void draw(GuiGraphics guiGraphics, Color backgroundColor, Color accentColor) {
		//background
		Rect.drawOutlinedRoundedSquare(x, y, width, height, 5, 50, 1, backgroundColor, accentColor);

		//corner
		Rect.drawOutlinedSquare(x + width - 10, y + height - 10, 10, 10, 1, backgroundColor, accentColor);
	}

	public boolean contains(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

	public boolean contains_dragarea(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 10;
	}

	public boolean containsResizableCorner(int mouseX, int mouseY) {
		return mouseX >= x + width - 10 && mouseX <= x + width && mouseY >= y + height - 10 && mouseY <= y + height;
	}

	public void snapToScreenEdges(int screenWidth, int screenHeight) {
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		if (x + width > screenWidth) {
			x = screenWidth - width;
		}
		if (y + height > screenHeight) {
			y = screenHeight - height;
		}
	}

	public void handleMouseClick(int mouseX, int mouseY, int button) {
		if (button == 0 && contains_dragarea(mouseX, mouseY)) {
			dragging = true;
			dragOffsetX = mouseX - x;
			dragOffsetY = mouseY - y;
		}

		if (button == 0 && containsResizableCorner(mouseX, mouseY)) {
			scaling = true;
			scaleOffsetX = mouseX - (x + width);
			scaleOffsetY = mouseY - (y + height);
		}
	}

	public void handleMouseDrag(int mouseX, int mouseY, int button, int screenWidth, int screenHeight) {
		if (dragging && button == 0) {
			setPosition(mouseX - dragOffsetX, mouseY - dragOffsetY);
			snapToScreenEdges(screenWidth, screenHeight);
		}

		if (scaling && button == 0) {
			setSize(mouseX - x - scaleOffsetX, mouseY - y - scaleOffsetY);
			snapToScreenEdges(screenWidth, screenHeight);
		}
	}

	public void handleMouseRelease(int mouseX, int mouseY, int button) {
		if (button == 0) {
			dragging = false;
			scaling = false;
		}
	}
}



