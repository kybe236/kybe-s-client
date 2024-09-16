/*
 * Copyright (c) 2024 kybe236
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.kybe.gui.components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/*
 * Base Settings class (Used for all settings)
 */
public abstract class Setting {
	private String name;
	private boolean shouldSerialize = true;

	public Setting(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean shouldSerialize() {
		return shouldSerialize;
	}

	public void handleKeyPress(int key) {}

	public abstract JsonElement serializeValue();

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		obj.addProperty("name", name);
		obj.add("value", this.serializeValue());
		return obj;
	}

	public boolean deserialize(JsonElement jsonElement) {
		if (!jsonElement.isJsonObject() || jsonElement.isJsonNull()){
			return false;
		}

		final JsonObject obj = jsonElement.getAsJsonObject();

		boolean consumed = obj.has("value") && this.deserializeValue(obj.get("value"));

		return consumed;
	}

	public abstract boolean deserializeValue(JsonElement jsonElement);
}
