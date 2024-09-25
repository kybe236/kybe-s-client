package de.kybe.client.core.module;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.kybe.Kybe;
import de.kybe.client.core.event.EventBus;
import de.kybe.client.core.setting.Setting;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

/*
 * @Author kybe236
 * @Author mopplkotze (Refactored)
 */
public class Module {

	private final String name;
	private final String description;
	private final ModuleCategory category;
	private final ArrayList<Setting> settings = new ArrayList<>();

	private boolean state;
	private boolean drawn;
	private int keybind;

	public Module(String name, String description, ModuleCategory category, int keybind) {
		this.description = description;
		this.category = category;
		this.name = name;
		this.keybind = keybind;
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("unused")
	public String getDescription() {
		return description;
	}

	public ModuleCategory getCategory() {
		return category;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		if (state) {
			enable();
		} else {
			disable();
		}
	}

	public void enable() {
		this.state = true;
		EventBus.register(this);
		onEnable();
	}

	public void disable() {
		this.state = false;
		EventBus.unregister(this);
		onDisable();
	}

	public void onEnable() {

	}

	public void onDisable() {

	}

	public void toggle() {
		if (state) {
			disable();
		} else {
			enable();
		}
	}

	public boolean isDrawn() {
		return drawn;
	}

	@SuppressWarnings("unused")
	public void setDrawn(boolean drawn) {
		this.drawn = drawn;
	}

	public int getKeybind() {
		return keybind;
	}

	public void setKeybind(int keybind) {
		this.keybind = keybind;
	}

	@SuppressWarnings("unused")
	public boolean handleKeyPress(int key) {
		if (key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) {
			this.toggle();
			return true;
		}
		return false;
	}

	public void addSetting(Setting setting) {
		if (!settings.contains(setting)) {
			settings.add(setting);
		} else {
			throw new IllegalArgumentException("Setting already exists");
		}
	}

	@SuppressWarnings("unused")
	public void addSettings(Setting... settings) {
		for (Setting setting : settings) {
			addSetting(setting);
		}
	}

	public ArrayList<Setting> getSettings() {
		return settings;
	}

	public Setting getSetting(String name) {
		for (Setting setting : settings) {
			if (setting.getName().equalsIgnoreCase(name)) {
				return setting;
			}
		}
		return null;
	}

	/*
	 * Creates an JsonObject with the module's data (category, name, settings)
	 */
	public JsonObject serialize() {
		JsonObject obj = new JsonObject();
		obj.addProperty("category", this.getCategory().name());
		obj.addProperty("name", this.getName());
		obj.addProperty("state", this.getState());
		obj.addProperty("drawn", this.isDrawn());
		obj.addProperty("keybind", this.getKeybind());

		/*
		 * If the module has settings, serialize them using there implementation of the serialize method
		 */
		if (!this.getSettings().isEmpty()) {
			JsonArray settings = new JsonArray();
			for (Setting setting : this.getSettings()) {
				Kybe.LOGGER.info("Saving setting: {}", setting.getName());
				settings.add(setting.serialize());
			}
			// Adding the array list to the module object as a json array called "settings"
			obj.add("settings", settings);
		}
		return obj;
	}
}
