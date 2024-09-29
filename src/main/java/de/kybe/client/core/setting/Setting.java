/*
 * Copyright (c) 2024 kybe236
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.kybe.client.core.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.kybe.client.core.module.Module;

/**
 * Base Settings class (Used for all settings)
 * Handles serialization and deserialization
 */
public abstract class Setting {
	private final String name;
	private final String description;
	private Module parent;

	protected Setting(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * Gets the description of the setting (Displayed once Hovered)
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the name of the setting
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the parent module of the setting (The module that the setting belongs to)
	 */
	public Module getParent() {
		return parent;
	}

	/**
	 * Sets the parent module of the setting
	 */
	public void setParent(Module module) {
		this.parent = module;
	}

	/**
	 * Returns true if the key was handled
	 */
	public boolean handleKeyPress(int key) {
		return false;
	}

	/**
	 * Clients implementation to serialize the setting value to an JsonElement
	 */
	public abstract JsonElement serializeValue();

	/**
	 * Serialize the setting to an JsonElement
	 */
	public JsonElement serialize() {
		JsonObject obj = new JsonObject();

		// save the name to identify the setting (not load it)
		obj.addProperty("name", name);
		obj.add("value", this.serializeValue());

		return obj;
	}

	/**
	 * Deserialize the setting from an JsonObject
	 */
	public void deserialize(JsonObject obj) {
		// only deserialize the value so name changes don't affect the setting
		if (obj.has("value")) {
			this.deserializeValue(obj.get("value"));
		}
	}

	/**
	 * Clients implementation to make sense of the JsonElement (NumberSettings handle numbers, StringSettings handle strings, etc.)
	 */
	public abstract void deserializeValue(JsonElement jsonElement);
}
