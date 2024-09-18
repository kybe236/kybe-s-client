package de.kybe.modules.movement;

import de.kybe.gui.Gui;
import de.kybe.gui.components.CategoryEnum;
import de.kybe.baseModules.ToggleableModule;

import static de.kybe.constants.Globals.mc;

public class DoubleJump {
	static ToggleableModule doubleJump = new ToggleableModule("DoubleJump", CategoryEnum.MOVEMENT);

	public DoubleJump() {
		Gui.addModule(doubleJump);
	}

	public static void onTick() {
		if (mc.player != null && !mc.player.onGround() && mc.options.keyJump.isDown() && doubleJump.isToggled()) {
			mc.player.setJumping(true);
		}
	}
}
