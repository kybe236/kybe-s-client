package de.kybe.gui.components;

public class Setting {
	private String name;
	private float value;
	private float min;
	private float max;

	public Setting(String name, float value, float min, float max) {
		this.name = name;
		this.value = value;
		this.min = min;
		this.max = max;
	}

	public String getName() {
		return name;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		if (value >= min && value <= max) {
			this.value = value;
		}
	}
}
