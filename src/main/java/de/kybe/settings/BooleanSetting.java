package de.kybe.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.lwjgl.glfw.GLFW;

public class BooleanSetting extends Setting {
	private boolean toggled = false;

	public BooleanSetting(String name) {
		super(name);
	}

	public void toggle() {
		toggled = !toggled;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	@Override
	public boolean handleKeyPress(int key) {
		if (key == GLFW.GLFW_KEY_ENTER) {
			toggle();
			return true;
		}
		return false;
	}

	@Override
	public JsonElement serializeValue() {
		return new JsonPrimitive(toggled);
	}

	@Override
	public void deserializeValue(JsonElement jsonElement) {
		if (!jsonElement.getAsJsonPrimitive().isBoolean()) {
			return;
		}

		this.setToggled(jsonElement.getAsBoolean());
	}
}
