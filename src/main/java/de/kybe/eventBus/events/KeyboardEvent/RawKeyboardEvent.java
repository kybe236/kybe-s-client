package de.kybe.eventBus.events.KeyboardEvent;

import de.kybe.eventBus.events.Cancelable;

public class RawKeyboardEvent extends Cancelable {
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

	public enum Type {
		PRESS,
		RELEASE
	}
}
