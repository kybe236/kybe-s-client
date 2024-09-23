package de.kybe.client.core.module;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.kybe.Kybe;
import de.kybe.client.core.setting.Setting;
import de.kybe.client.core.event.EventBus;
import org.lwjgl.glfw.GLFW;

public class ToggleableModule extends Module {
	int keybind;
	private boolean toggled = false;

	public ToggleableModule(String name, ModuleCategory category) {
		super(name, category);
		EventBus.register(this);
	}

	public int getKeybind() {
		return this.keybind;
	}

	public void setKeybind(int keybind) {
		Kybe.LOGGER.info("Set keybind to {} for: {}", keybind, getName());
		this.keybind = keybind;
	}

	public void toggle() {
		this.toggled = !this.toggled;
	}


	public boolean isToggled() {
		return toggled;
	}

	@SuppressWarnings("unused")
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	public Setting get(String name) {
		for (Setting setting : this.getSettings()) {
			if (setting.getName().equals(name)) {
				return setting;
			}
		}

		return null;
	}

	@SuppressWarnings("unused")
	@Subscribe
	public void onKeyPress(KeyboardEvent event) {
		KeyboardEvent.Type type = event.getType();
		if (type == KeyboardEvent.Type.PRESS && event.getKey() == keybind) {
			toggle();
		}
	}

	@Override
	public boolean handleKeyPress(int key) {
		if (key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) {
			toggle();
			return true;
		}
		return false;
	}

	@Override
	public JsonObject serialize() {
		JsonObject obj = new JsonObject();
		obj.addProperty("category", this.getCategory().name());
		obj.addProperty("name", this.getName());
		obj.addProperty("toggled", this.isToggled());
		obj.addProperty("keybind", this.getKeybind());

		//noinspection DuplicatedCode
		if (!this.getSettings().isEmpty()) {
			JsonArray settings = new JsonArray();
			for (Setting setting : this.getSettings()) {
				settings.add(setting.serialize());
			}
			obj.add("settings", settings);
		}
		return obj;
	}

	@Override
	public void deserialize(JsonObject obj) {
		if (!obj.get("category").getAsString().equals(this.getCategory().name())) {
			Kybe.LOGGER.warn("Category mismatch in deserialization for " + this.getName());
			return;
		}
		if (!obj.has("name") || !obj.get("name").getAsString().equals(this.getName())) {
			Kybe.LOGGER.warn("Name mismatch in deserialization for " + this.getName());
			return;
		}
		if (!obj.has("toggled")) {
			Kybe.LOGGER.warn("Toggled not defined in deserialization for " + this.getName());
			return;
		}
		if (!obj.has("keybind")) {
			Kybe.LOGGER.warn("Keybind not defined in deserialization for " + this.getName());
			return;
		}

		this.setToggled(obj.get("toggled").getAsBoolean());
		this.keybind = obj.get("keybind").getAsInt();

		//noinspection DuplicatedCode
		if (obj.has("settings")) {
			JsonArray settings = obj.getAsJsonArray("settings");
			for (Setting setting : this.getSettings()) {
				for (JsonElement settingObj : settings.asList()) {
					if (settingObj.isJsonObject()) {
						JsonObject settingJson = settingObj.getAsJsonObject();
						if (settingJson.has("name") && settingJson.get("name").getAsString().equals(setting.getName())) {
							setting.deserialize(settingJson);
						}
					}
				}
			}
		}
	}
}
