package de.kybe.gui;

import de.kybe.gui.components.Catagory;
import de.kybe.gui.components.Module;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.List;

public class Gui extends Screen {
	private List<Catagory> catagories;
	private int selectedCatagoryIndex = 0;
	private int selectedModuleIndex = 0;
	private int selectedSettingIndex = 0;

	public Gui() {
		super(Component.literal("Kybe Client"));
		catagories = loadCatagories();
	}

	private List<Catagory> loadCatagories() {
		Catagory combat = new Catagory("Combat");
		Module killAura = new Module("KillAura");
		combat.addModule(killAura);

		Catagory movement = new Catagory("Movement");
		Module speed = new Module("Speed");
		movement.addModule(speed);

		return List.of(combat, movement);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int i, int j, float f) {
		super.render(guiGraphics, i, j, f);

		drawCatagories(guiGraphics);
		drawModules(guiGraphics);
	}

	private void drawCatagories(GuiGraphics guiGraphics) {
		for (int i = 0; i < catagories.size(); i++) {
			Catagory catagory = catagories.get(i);
			if (i == selectedCatagoryIndex) {
				guiGraphics.fill(0, i * 10, 100, 10, Color.BLUE.getRGB());
			}

			guiGraphics.drawCenteredString(font, catagory.getName(), 50, i * 10, Color.WHITE.getRGB());
		}
	}

	private void drawModules(GuiGraphics guiGraphics) {
		List<Module> modules = catagories.get(selectedCatagoryIndex).getModules();
		for (int i = 0; i < modules.size(); i++) {
			Module module = modules.get(i);
			if (i == selectedModuleIndex) {
				guiGraphics.fill(100, i * 10, 100, 10, Color.BLUE.getRGB());
			}
			guiGraphics.drawCenteredString(font, module.getName(), 150, i * 10, Color.WHITE.getRGB());
		}
	}

	private void drawSetting(GuiGraphics guiGraphics) {
		return;
	}
}
