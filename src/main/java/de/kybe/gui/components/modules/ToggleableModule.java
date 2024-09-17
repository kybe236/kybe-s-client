package de.kybe.gui.components.modules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.kybe.gui.components.CategoryEnum;
import de.kybe.gui.components.settings.Setting;
import org.lwjgl.glfw.GLFW;

public class ToggleableModule extends Module{
	private boolean toggled = false;

	public ToggleableModule(String name, CategoryEnum catagory) {
		super(name, catagory);
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
		if (key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) {
			toggle();
			return true;
		}
		return false;
	}

	@Override
	public JsonObject serialize() {
		JsonObject obj = new JsonObject();
		obj.addProperty("catagory", this.getCategory().name());
		obj.addProperty("name", this.getName());
		obj.addProperty("toogled", this.isToggled());

		if (!this.getSettings().isEmpty()) {
			JsonArray settings = new JsonArray();
			for (Setting setting : this.getSettings()) {
				if (setting.shouldSerialize()) {
					settings.add(setting.serialize());
				}
			}
			obj.add("settings", settings);
		}
		return obj;
	}

	@Override
	public void deserialize(JsonObject obj) {
		if (!obj.get("catagory").getAsString().equals(this.getCategory().name())) return;
		if (!obj.has("name") || !obj.get("name").getAsString().equals(this.getName())) return;
		if (!obj.has("toogled")) return;

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
