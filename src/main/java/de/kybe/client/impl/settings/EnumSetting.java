package de.kybe.client.impl.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import de.kybe.client.core.setting.Setting;
import org.lwjgl.glfw.GLFW;

public class EnumSetting<T extends Enum<?>> extends Setting {
	T enumValue;
	boolean selected = false;

	public EnumSetting(String name, String description, T passedEnum) {
		super(name, description);
		this.enumValue = passedEnum;
	}

	public void setEditMode(boolean editMode) {
		selected = editMode;
	}

	public boolean isInEditMode() {
		return selected;
	}

	public T getValue() {
		return enumValue;
	}

	@SuppressWarnings("unchecked")
	public void setValue(Enum<?> newValue) {
		enumValue = (T) newValue;
	}

	public boolean isSelected() {
		return selected;
	}

	@SuppressWarnings("unchecked")
	public T[] getAllModes() {
		if (enumValue == null) {
			return (T[]) new Enum<?>[0];
		}
		return (T[]) enumValue.getDeclaringClass().getEnumConstants();
	}

	public void next() {
		if (enumValue == null) {
			return;
		}
		@SuppressWarnings("unchecked")
		T[] values = (T[]) enumValue.getDeclaringClass().getEnumConstants();
		int index = (enumValue.ordinal() + 1) % values.length;
		enumValue = values[index];
	}

	public void previous() {
		if (enumValue == null) {
			return;
		}
		@SuppressWarnings("unchecked")
		T[] values = (T[]) enumValue.getDeclaringClass().getEnumConstants();
		int index = (enumValue.ordinal() - 1 + values.length) % values.length;
		enumValue = values[index];
	}

	@Override
	public boolean handleKeyPress(int key) {
		if (key == GLFW.GLFW_KEY_DOWN && isInEditMode()) {
			return true;
		}
		if (key == GLFW.GLFW_KEY_UP && isInEditMode()) {
			return true;
		}

		if (key == GLFW.GLFW_KEY_ENTER) {
			selected = !selected;
			return true;
		}
		if (key == GLFW.GLFW_KEY_LEFT && selected) {
			previous();
			return true;
		}
		if (key == GLFW.GLFW_KEY_RIGHT && selected) {
			next();
			return true;
		}
		return false;
	}

	@Override
	public JsonElement serializeValue() {
		if (enumValue == null) {
			return new JsonPrimitive("");
		}
		return new JsonPrimitive(enumValue.name());
	}

	@Override
	public void deserializeValue(JsonElement jsonElement) {
		if (!jsonElement.getAsJsonPrimitive().isString()) {
			return;
		}

		String enumName = jsonElement.getAsString();
		if (enumName.isEmpty()) {
			return;
		}

		try {
			//noinspection unchecked
			enumValue = (T) Enum.valueOf(enumValue.getClass(), enumName);
		} catch (IllegalArgumentException e) {
			//noinspection CallToPrintStackTrace
			e.printStackTrace();
		}
	}
}
