package de.kybe.baseSettings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import de.kybe.Kybe;
import de.kybe.baseModules.Module;
import de.kybe.baseModules.ToggleableModule;
import org.lwjgl.glfw.GLFW;

public class BindSetting extends Setting {
	int value;
	boolean inEditMode = false;

	public BindSetting(String name) {
		super(name);
	}

	public void setInEditMode(boolean inEditMode) {
		this.inEditMode = inEditMode;
	}

	public boolean isInEditMode() {
		return inEditMode;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
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
				setValue(0);
				return true;
			}
			if (key == GLFW.GLFW_KEY_BACKSPACE) {
				setValue(0);
				return true;
			}
			this.value = key;
			setInEditMode(false);
			Module module = getParent();
			if (module instanceof ToggleableModule) {
				((ToggleableModule) module).setKeybind(getValue());
			} else {
				Kybe.LOGGER.error("Trying to set keybind for non-toggleable module: {}", module.getName());
			}
			return true;
		}
		return false;
	}

	@Override
	public JsonElement serializeValue() {
		return new JsonPrimitive(value);
	}

	@Override
	public void deserializeValue(JsonElement jsonElement) {
		value = jsonElement.getAsInt();
	}
}
