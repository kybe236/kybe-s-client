package de.kybe.client.impl.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import de.kybe.client.core.setting.Setting;
import org.lwjgl.glfw.GLFW;

public class BooleanSetting extends Setting {
	private boolean toggled = false;

	public BooleanSetting(String name, String description, boolean toggled) {
		super(name, description);
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
