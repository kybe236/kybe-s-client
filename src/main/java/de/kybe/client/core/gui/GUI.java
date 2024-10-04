package de.kybe.client.core.gui;

import de.kybe.client.core.configManager.ConfigManager;
import de.kybe.client.core.gui.components.MainComponent;
import de.kybe.client.core.gui.components.ScreenSelector;
import de.kybe.client.core.gui.misc.AvailableScreens;
import de.kybe.client.core.gui.misc.TempColors;
import de.kybe.client.core.util.renders.render2d.Rect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.Arrays;

import static de.kybe.client.core.gui.components.MainComponent.current_category;

public class GUI extends Screen {

	public static MainComponent mainComponent;
	public static ScreenSelector screenSelector;
	//default dimensions
	public int x, y, width, height;

	public GUI() {
		super(Component.literal("ClickGUI"));

		//TODO grab these sizes from a the clickgui module itself
		this.x = 100;
		this.y = 100;
		this.width = 600;
		this.height = 400;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void init() {
		screenSelector = new ScreenSelector(Arrays.asList(AvailableScreens.values()));
		mainComponent = new MainComponent(x, y, width, height);
		mainComponent.init();
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		screenSelector.drawSelector(guiGraphics, this.font, 10, TempColors.color_accent, TempColors.color_background, TempColors.color_accent);

		if (screenSelector.getSelectedScreen() == AvailableScreens.CLICKGUI) {
			//debug
			if (current_category != null) {
				guiGraphics.drawString(this.font, "Current Category: " + current_category.category.getName(), 10, 10, Color.WHITE.getRGB());
			} else {
				guiGraphics.drawString(this.font, "Current Category: " + "null", 10, 10, Color.WHITE.getRGB());
			}

			if (current_category != null && current_category.current_module != null) {
				guiGraphics.drawString(this.font, "Current Module: " + current_category.current_module.module.getName(), 10, 20, Color.WHITE.getRGB());
			} else {
				guiGraphics.drawString(this.font, "Current Module: " + "null", 10, 20, Color.WHITE.getRGB());
			}
			//debug end

			mainComponent.drawScreen(guiGraphics, mouseX, mouseY, partialTick);
		} else if (screenSelector.getSelectedScreen() == AvailableScreens.HUD) {
			Rect.drawRoundedSquare(100, 100, 400, 400, 10, 50, new Color(164, 118, 214, 255));
		}

	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		screenSelector.handleMouseClick((int) mouseX, (int) mouseY, 10);

		if (screenSelector.getSelectedScreen() == AvailableScreens.CLICKGUI) {
			mainComponent.mouseClicked(mouseX, mouseY, button);
		}

		return false;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {

		if (screenSelector.getSelectedScreen() == AvailableScreens.CLICKGUI) {
			mainComponent.mouseReleased(mouseX, mouseY, button);
		}

		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

		if (screenSelector.getSelectedScreen() == AvailableScreens.CLICKGUI) {
			mainComponent.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}

		return false;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double g, double scrollAmount) {

		if (screenSelector.getSelectedScreen() == AvailableScreens.CLICKGUI) {
			mainComponent.mouseScrolled(mouseX, mouseY, scrollAmount);
		}

		return super.mouseScrolled(mouseX, mouseY, g, scrollAmount);
	}

}
