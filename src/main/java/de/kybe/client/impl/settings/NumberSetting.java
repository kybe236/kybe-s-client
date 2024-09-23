package de.kybe.client.impl.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import de.kybe.client.core.setting.Setting;
import org.lwjgl.glfw.GLFW;


public class NumberSetting<T extends Number> extends Setting {
	private final T min;
	private final T max;
	private final T increment;
	boolean inEditMode = false;
	private T value;

	public NumberSetting(String name, T value, T min, T max, T increment) {
		super(name);
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
	}

	public T getValue() {
		return value;
	}

	@SuppressWarnings("unchecked")
	public void setValue(Number value) {
		if (value instanceof Double && this.value instanceof Float) {
			// Convert Double to Float
			this.value = (T) Float.valueOf(value.floatValue());
		} else if (value instanceof Float && this.value instanceof Double) {
			// Convert Float to Double
			this.value = (T) Double.valueOf(value.doubleValue());
		} else if (this.value instanceof Integer && value instanceof Integer) {
			this.value = (T) Integer.valueOf(value.intValue());
		} else if (this.value instanceof Long && value instanceof Long) {
			this.value = (T) Long.valueOf(value.longValue());
		} else if (this.value instanceof Short && value instanceof Short) {
			this.value = (T) Short.valueOf(value.shortValue());
		} else if (this.value instanceof Byte && value instanceof Byte) {
			this.value = (T) Byte.valueOf(value.byteValue());
		} else if (this.value instanceof Double && value instanceof Double) {
			this.value = (T) Double.valueOf(value.doubleValue());
		} else if (this.value instanceof Float && value instanceof Float) {
			this.value = (T) Float.valueOf(value.floatValue());
		} else {
			throw new IllegalArgumentException("Incompatible type: " + value.getClass().getSimpleName());
		}
	}

	@SuppressWarnings("unused")
	public T getMin() {
		return min;
	}

	@SuppressWarnings("unused")
	public T getMax() {
		return max;
	}

	@SuppressWarnings("unused")
	public T getIncrement() {
		return increment;
	}

	public void setEditMode(boolean inEditMode) {
		this.inEditMode = inEditMode;
	}

	public boolean isInEditMode() {
		return inEditMode;
	}

	public void toggleEditMode() {
		inEditMode = !inEditMode;
	}

	@Override
	public boolean handleKeyPress(int key) {
		if (key == GLFW.GLFW_KEY_DOWN && isInEditMode()) {
			return true;
		}
		if (key == GLFW.GLFW_KEY_UP && isInEditMode()) {
			return true;
		}
		if (key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) {
			toggleEditMode();
			return true;
		}
		if (isInEditMode()) {
			if (key == GLFW.GLFW_KEY_LEFT) {
				handleDecrement();
				return true;
			} else if (key == GLFW.GLFW_KEY_RIGHT) {
				handleIncrement();
				return true;
			}
		}
		return false;
	}


	@SuppressWarnings("unchecked")
	public void handleIncrement() {
		if (value instanceof Double) {
			if (value.doubleValue() + increment.doubleValue() > max.doubleValue()) {
				value = (T) Double.valueOf(max.doubleValue());
				return;
			}
			value = (T) Double.valueOf(Math.round((value.doubleValue() + increment.doubleValue()) * 100.0) / 100.0);
		} else if (value instanceof Float) {
			if (value.floatValue() + increment.floatValue() > max.floatValue()) {
				value = (T) Float.valueOf(max.floatValue());
				return;
			}
			value = (T) Float.valueOf((float) (Math.round((value.floatValue() + increment.floatValue()) * 100.0) / 100.0));
		} else if (value instanceof Long) {
			if (value.longValue() + increment.longValue() > max.longValue()) {
				value = (T) Long.valueOf(max.longValue());
				return;
			}
			value = (T) Long.valueOf(value.longValue() + increment.longValue());
		} else if (value instanceof Integer) {
			if (value.intValue() + increment.intValue() > max.intValue()) {
				value = (T) Integer.valueOf(max.intValue());
				return;
			}
			value = (T) Integer.valueOf(value.intValue() + increment.intValue());
		} else if (value instanceof Short) {
			if (value.shortValue() + increment.shortValue() > max.shortValue()) {
				value = (T) Short.valueOf(max.shortValue());
				return;
			}
			value = (T) Short.valueOf((short) (value.shortValue() + increment.shortValue()));
		} else if (value instanceof Byte) {
			if (value.byteValue() + increment.byteValue() > max.byteValue()) {
				value = (T) Byte.valueOf(max.byteValue());
				return;
			}
			value = (T) Byte.valueOf((byte) (value.byteValue() + increment.byteValue()));
		}
	}

	@SuppressWarnings("unchecked")
	public void handleDecrement() {
		if (value instanceof Double) {
			if (value.doubleValue() - increment.doubleValue() < min.doubleValue()) {
				value = (T) Double.valueOf(min.doubleValue());
				return;
			}
			value = (T) Double.valueOf(Math.round((value.doubleValue() - increment.doubleValue()) * 100.0) / 100.0);
		} else if (value instanceof Float) {
			if (value.floatValue() - increment.floatValue() < min.floatValue()) {
				value = (T) Float.valueOf(min.floatValue());
				return;
			}
			value = (T) Float.valueOf((float) (Math.round((value.floatValue() - increment.floatValue()) * 100.0) / 100.0));
		} else if (value instanceof Long) {
			if (value.longValue() - increment.longValue() < min.longValue()) {
				value = (T) Long.valueOf(min.longValue());
				return;
			}
			value = (T) Long.valueOf(value.longValue() - increment.longValue());
		} else if (value instanceof Integer) {
			if (value.intValue() - increment.intValue() < min.intValue()) {
				value = (T) Integer.valueOf(min.intValue());
				return;
			}
			value = (T) Integer.valueOf(value.intValue() - increment.intValue());
		} else if (value instanceof Short) {
			if (value.shortValue() - increment.shortValue() < min.shortValue()) {
				value = (T) Short.valueOf(min.shortValue());
				return;
			}
			value = (T) Short.valueOf((short) (value.shortValue() - increment.shortValue()));
		} else if (value instanceof Byte) {
			if (value.byteValue() - increment.byteValue() < min.byteValue()) {
				value = (T) Byte.valueOf(min.byteValue());
				return;
			}
			value = (T) Byte.valueOf((byte) (value.byteValue() - increment.byteValue()));
		}
	}

	@SuppressWarnings("unused")
	public String getValueAsString() {
		return value.toString();
	}


	@Override
	public JsonElement serializeValue() {
		return new JsonPrimitive(value.toString());
	}

	@Override
	public void deserializeValue(JsonElement jsonElement) {
		if (this.getValue() instanceof Double) {
			this.setValue(jsonElement.getAsDouble());
		} else if (this.getValue() instanceof Float) {
			this.setValue(jsonElement.getAsFloat());
		} else if (this.getValue() instanceof Long) {
			this.setValue(jsonElement.getAsLong());
		} else if (this.getValue() instanceof Integer) {
			this.setValue(jsonElement.getAsInt());
		} else if (this.getValue() instanceof Short) {
			this.setValue(jsonElement.getAsShort());
		} else if (this.getValue() instanceof Byte) {
			this.setValue(jsonElement.getAsByte());
		}
	}
}
