package de.kybe.modules.movement;

import de.kybe.baseModules.ToggleableModule;
import de.kybe.eventBus.EventBus;
import de.kybe.eventBus.Subscribe;
import de.kybe.eventBus.events.EventTick;
import de.kybe.gui.CategoryEnum;
import de.kybe.gui.Gui;

import static de.kybe.constants.Globals.mc;

public class DoubleJump {
	static ToggleableModule doubleJump = new ToggleableModule("DoubleJump", CategoryEnum.MOVEMENT);

	public DoubleJump() {
		Gui.addModule(doubleJump);
		EventBus.register(this);
	}

	@SuppressWarnings("unused")
	@Subscribe
	public static void onTick(EventTick ignore) {
		if (mc.player != null && !mc.player.onGround() && mc.options.keyJump.isDown() && doubleJump.isToggled()) {
			mc.player.jumpFromGround();
		}
	}
}
