package de.kybe.eventBus.events.KeyboardEvent;

import de.kybe.eventBus.events.BaseEvent;

public class KeyboardEvent extends BaseEvent {
	private final int key;
	private final Type type;
	private boolean cancel = false;

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

	public boolean isCancel() {
		return cancel;
	}

	@SuppressWarnings("unused")
	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public enum Type {
		PRESS,
		RELEASE
	}
}
