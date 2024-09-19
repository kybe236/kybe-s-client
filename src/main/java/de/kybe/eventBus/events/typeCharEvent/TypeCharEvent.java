package de.kybe.eventBus.events.typeCharEvent;

import de.kybe.eventBus.events.BaseEvent;
import de.kybe.eventBus.events.KeyboardEvent.KeyboardEvent;

public class TypeCharEvent extends BaseEvent {
	private final char key;
	private boolean cancel = false;
	private final KeyboardEvent.Type type;

	public TypeCharEvent(char key, KeyboardEvent.Type type) {
		this.key = key;
		this.type = type;
	}

	public char getKey() {
		return key;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public KeyboardEvent.Type getType() {
		return type;
	}
}
