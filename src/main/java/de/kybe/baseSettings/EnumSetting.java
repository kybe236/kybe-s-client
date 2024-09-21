package de.kybe.baseSettings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.lwjgl.glfw.GLFW;

public class EnumSetting<T extends Enum<?>> extends Setting {
	T enumValue;
	boolean selected = false;

	public EnumSetting(String name, T passedEnum) {
		super(name);
		this.enumValue = passedEnum;
	}

	public void setEditMode(boolean editMode) {
		selected = editMode;
	}

	public T getValue() {
		return enumValue;
	}

	public boolean isSelected() {
		return selected;
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
