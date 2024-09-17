package de.kybe.modules;


import de.kybe.gui.components.CategoryEnum;
import de.kybe.gui.components.modules.ToggleableModule;

import static de.kybe.constants.Globals.mc;

public class DoubleJump {
	static ToggleableModule doubleJump = new ToggleableModule("DoubleJump", CategoryEnum.MOVEMENT);

	public static ToggleableModule load() {
		return doubleJump;
	}

	public static void onTick() {
		if (mc.player != null && !mc.player.onGround() && mc.options.keyJump.isDown() && doubleJump.isToggled()) {
			mc.player.jumpFromGround();
		}
	}
}
