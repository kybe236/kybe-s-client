package de.kybe.client.core.event.events.TypeCharEvent;


import de.kybe.client.core.event.events.Cancelable;
import de.kybe.client.core.event.events.KeyboardEvent.KeyboardEvent;

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
