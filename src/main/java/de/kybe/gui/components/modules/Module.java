/*
 * Copyright (c) 2024 kybe236
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.kybe.gui.components.modules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.kybe.gui.components.CategoryEnum;
import de.kybe.gui.components.settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class Module {
	private final String name;
	private final ArrayList<Setting> settings;
	private final CategoryEnum catagory;

	public Module(String name, CategoryEnum catagory) {
		this.name = name;
		this.settings = new ArrayList<>();
		this.catagory = catagory;
	}

	public String getName() {
		return name;
	}

	public void addSetting(Setting setting) {
		settings.add(setting);
	}

	public List<Setting> getSettings() {
		return settings;
	}

	public CategoryEnum getCategory() {
		return catagory;
	}

	/*
	 * Returns true if the key was handled
	 */
	public boolean handleKeyPress(int key) {
		return false;
	}

	public JsonObject serialize() {
		JsonObject obj = new JsonObject();
		obj.addProperty("catagory", this.getCategory().name());
		obj.addProperty("name", this.getName());

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

	public void deserialize(JsonObject obj) {
		if (!obj.get("catagory").getAsString().equals(this.catagory.name())) return;
		if (!obj.has("name") || !obj.get("name").getAsString().equals(this.getName())) return;

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
