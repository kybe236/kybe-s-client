package de.kybe.client.core.gui.components.setting;

import de.kybe.client.core.setting.Setting;
import net.minecraft.client.gui.GuiGraphics;

public class SettingComponent {

	public int x, y, width, height;
	public Setting setting;

	public SettingComponent(int x, int y, int width, int height, Setting setting) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setting = setting;
	}

	public void drawScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
	}

	public void mouseClicked(double mouseX, double mouseY, int button) {

	}

	public void mouseReleased(double mouseX, double mouseY, int button) {

	}

	public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

	}

	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
