package de.kybe.client.impl.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.setting.Setting;
import org.lwjgl.glfw.GLFW;

public class BindSetting extends Setting {
	int keybind;
	boolean inEditMode = false;

	public BindSetting(String name, String description, int keybind) {
		super(name, description);
	}

	public boolean isInEditMode() {
		return inEditMode;
	}

	public void setInEditMode(boolean inEditMode) {
		this.inEditMode = inEditMode;
	}

	public int getKeybind() {
		return keybind;
	}

	public void setKeybind(int keybind) {
		this.keybind = keybind;
	}

	@Override
	public boolean handleKeyPress(int key) {
		if (key == GLFW.GLFW_KEY_ENTER) {
			setInEditMode(!isInEditMode());
			return true;
		}
		if (isInEditMode()) {
			if (key == GLFW.GLFW_KEY_ESCAPE) {
				setInEditMode(false);
				setKeybind(0);
				return true;
			}
			if (key == GLFW.GLFW_KEY_BACKSPACE) {
				setKeybind(0);
				return true;
			}
			this.keybind = key;
			setInEditMode(false);
			Module module = getParent();
			module.setKeybind(getKeybind());
			return true;
		}
		return false;
	}

	@Override
	public JsonElement serializeValue() {
		return new JsonPrimitive(keybind);
	}

	@Override
	public void deserializeValue(JsonElement jsonElement) {
		keybind = jsonElement.getAsInt();
	}
}
