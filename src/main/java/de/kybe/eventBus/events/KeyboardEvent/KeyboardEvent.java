package de.kybe.eventBus.events.KeyboardEvent;

import de.kybe.eventBus.events.Cancelable;

public class KeyboardEvent extends Cancelable {
	private final int key;
	private final Type type;

	public KeyboardEvent(int key, Type type) {
		this.key = key;
		this.type = type;
	}

	@SuppressWarnings("unused")
	public int getKey() {
		return key;
	}

	@SuppressWarnings("unused")
	public Type getType() {
		return type;
	}

	public enum Type {
		PRESS,
		RELEASE
	}
}
