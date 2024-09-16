package de.kybe.gui.components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.lwjgl.glfw.GLFW;

public class BooleanSetting extends Setting{
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
	public void handleKeyPress(int key) {
		if (key == GLFW.GLFW_KEY_ENTER) {
			toggle();
		}
	}

	@Override
	public JsonElement serializeValue() {
		return new JsonPrimitive(toggled);
	}

	@Override
	public boolean deserializeValue(JsonElement jsonElement) {
		if (!jsonElement.isJsonObject() || jsonElement.getAsJsonPrimitive().isBoolean()) {
			return false;
		}

		this.setToggled(jsonElement.getAsBoolean());
		return true;
	}
}
