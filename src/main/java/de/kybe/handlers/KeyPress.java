package de.kybe.handlers;

import de.kybe.modules.misc.Gui;

public class KeyPress {
	public static void onKeyPress(int key) {
	}

	public static void onKeyRelease(int key) {

	}

	public static void onKeyHold(int key) {

	}

	public static void onCharTyped(char character) {
		Gui.onCharTyped(character);
	}
}
