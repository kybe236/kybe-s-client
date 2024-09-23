package de.kybe.client.impl.modules.misc;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.impl.settings.BooleanSetting;
import de.kybe.client.core.event.EventBus;
import de.kybe.client.core.event.Subscribe;
import de.kybe.client.core.event.events.KeyboardEvent.KeyboardEvent;
import de.kybe.client.core.event.events.KeyboardEvent.RawKeyboardEvent;
import de.kybe.client.core.event.events.typeCharEvent.TypeCharEvent;


import static de.kybe.Kybe.keyMapping;
import static de.kybe.Kybe.mc;


@SuppressWarnings("unused")
public class Gui {
	private static final String openCombo = "kybe";
	static BooleanSetting combo = new BooleanSetting("Open Combo");
	private static String input = "";
	public Module module = new Module("Gui", ModuleCategory.CLIENT);

	public Gui() {
		combo.setToggled(true);
		module.addSetting(combo);

		de.kybe.client.core.gui.gui.Gui.addModule(module);
		EventBus.register(this);
	}

	@Subscribe
	public static void onCharTyped(TypeCharEvent event) {
		if (event.getType() == KeyboardEvent.Type.RELEASE || !combo.isToggled()) {
			return;
		}

		input += event.getKey();

		if (input.length() > openCombo.length()) {
			input = input.substring(1);
		}

		if (input.equals(openCombo)) {
			event.setCancel(true);
			mc.setScreen(new de.kybe.client.core.gui.gui.Gui());
			input = "";
		}
	}

	@Subscribe
	public static void onRawKeyboard(RawKeyboardEvent event) {
		if (keyMapping.matches(event.getI(), event.getJ()) && mc.screen == null) {
			event.setCancel(true);
			mc.setScreen(new de.kybe.client.core.gui.gui.Gui());
		}
	}
}
