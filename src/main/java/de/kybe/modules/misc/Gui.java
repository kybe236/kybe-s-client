package de.kybe.modules.misc;

import static de.kybe.constants.Globals.mc;

public class Gui {
	private static final String openCombo = "kybe";

	private static String input = "";

	public static void onCharTyped(char key) {
		input += key;

		if (input.length() > openCombo.length()) {
			input = input.substring(1);
		}

		if (input.equals(openCombo)) {
			mc.setScreen(new de.kybe.gui.Gui());
			input = "";
		}

	}
}
