package de.kybe.eventBus.events.KeyboardEvent;

import de.kybe.eventBus.events.BaseEvent;

import java.util.ArrayList;

public class RawKeyboardEvent extends BaseEvent {
	boolean cancel = false;
	Type type;
	long l;
	int i, j, k, m;

	public RawKeyboardEvent(long l, int i, int j, int k, int m, Type type) {
		this.l = l;
		this.i = i;
		this.j = j;
		this.k = k;
		this.m = m;
		this.type = type;
	}

	@SuppressWarnings("unused")
	public long getL() {
		return l;
	}

	@SuppressWarnings("unused")
	public int getI() {
		return i;
	}

	@SuppressWarnings("unused")
	public int getJ() {
		return j;
	}

	@SuppressWarnings("unused")
	public int getK() {
		return k;
	}

	@SuppressWarnings("unused")
	public int getM() {
		return m;
	}

	@SuppressWarnings("unused")
	public Type getType() {
		return type;
	}

	@SuppressWarnings("unused")
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
