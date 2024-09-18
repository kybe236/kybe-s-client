package de.kybe.baseModules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.kybe.gui.components.CategoryEnum;
import de.kybe.settings.Setting;
import org.lwjgl.glfw.GLFW;

public class ToggleableModule extends Module {
	private boolean toggled = false;

	public ToggleableModule(String name, CategoryEnum category) {
		super(name, category);
	}

	public void toggle() {
		toggled = !toggled;
	}

	public boolean isToggled() {
		return toggled;
	}

	@SuppressWarnings("unused")
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
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
		if (!obj.get("category").getAsString().equals(this.getCategory().name())) return;
		if (!obj.has("name") || !obj.get("name").getAsString().equals(this.getName())) return;
		if (!obj.has("toggled")) return;


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
