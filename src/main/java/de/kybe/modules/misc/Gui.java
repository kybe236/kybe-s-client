package de.kybe.modules.misc;

import static de.kybe.constants.Globals.mc;

public class Gui {
	private static final String openKombo = "kybe";

	private static String input = "";

	public static void onCharTyped(char key) {
		input += key;

		if (input.length() > openKombo.length()) {
			input = input.substring(1);
		}

		if (input.equals(openKombo)) {
			mc.setScreen(new de.kybe.gui.Gui());
			input = "";
		}

	}
}
