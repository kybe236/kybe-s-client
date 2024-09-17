package de.kybe.modules;


import de.kybe.gui.components.CategoryEnum;
import de.kybe.gui.components.modules.Module;
import de.kybe.gui.components.settings.BooleanSetting;

import static de.kybe.constants.Globals.mc;

public class DoubleJump {
	static Module doubleJump = new Module("DoubleJump", CategoryEnum.MOVEMENT);

	public static Module load() {
		if (doubleJump.getSettings().isEmpty()) {
			doubleJump.addSetting(new BooleanSetting("Enabled"));
		}
		return doubleJump;
	}

	public static void onTick() {
		if (mc.player != null && !mc.player.onGround() && mc.options.keyJump.isDown() && !doubleJump.getSettings().isEmpty() && ((BooleanSetting) doubleJump.getSettings().get(0)).isToggled()) {
			mc.player.jumpFromGround();
		}
	}
}
