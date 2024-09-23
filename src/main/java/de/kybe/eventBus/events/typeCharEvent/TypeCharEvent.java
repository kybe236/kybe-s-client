package de.kybe.eventBus.events.typeCharEvent;

import de.kybe.eventBus.events.Cancelable;
import de.kybe.eventBus.events.KeyboardEvent.KeyboardEvent;

public class TypeCharEvent extends Cancelable {
	private final char key;
	private final KeyboardEvent.Type type;

	public TypeCharEvent(char key, KeyboardEvent.Type type) {
		this.key = key;
		this.type = type;
	}

	public char getKey() {
		return key;
	}

	public KeyboardEvent.Type getType() {
		return type;
	}
}
