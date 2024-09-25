package de.kybe.client.core.event.events;

public class Cancelable extends BaseEvent {
	boolean cancel = false;

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}
}
