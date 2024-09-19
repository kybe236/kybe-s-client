package de.kybe.modules.misc;

import de.kybe.eventBus.Subscribe;
import de.kybe.eventBus.events.KeyboardEvent.KeyboardEvent;
import de.kybe.eventBus.events.typeCharEvent.TypeCharEvent;

import static de.kybe.constants.Globals.mc;

@SuppressWarnings("unused")
public class Gui {
	private static final String openCombo = "kybe";

	private static String input = "";

	@Subscribe
	public static void onCharTyped(TypeCharEvent event) {
		if (event.getType() != KeyboardEvent.Type.RELEASE) {
			return;
		}

		input += event.getKey();

		if (input.length() > openCombo.length()) {
			input = input.substring(1);
		}

		if (input.equals(openCombo)) {
			event.setCancel(true);
			mc.setScreen(new de.kybe.gui.Gui());
			input = "";
		}

	}
}
