package de.kybe.client.impl.modules.movement;

import de.kybe.client.core.event.Subscribe;
import de.kybe.client.core.event.events.EventTick;
import de.kybe.client.core.gui.gui.Gui;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.core.module.ToggleableModule;
import de.kybe.client.core.event.EventBus;

import static de.kybe.Kybe.mc;


public class DoubleJump {
	static ToggleableModule doubleJump = new ToggleableModule("DoubleJump", ModuleCategory.MOVEMENT);

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
