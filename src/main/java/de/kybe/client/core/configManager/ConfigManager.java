package de.kybe.client.core.configManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.kybe.Kybe;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static de.kybe.Kybe.mc;

public class ConfigManager {

	public static void saveSettings() {

		JsonArray modules = new JsonArray();

		for (Module module : ModuleManager.getModules()) {
			modules.add(module.serialize());
		}

		File settingsFile = new File(mc.gameDirectory, "settings.json");

		String json = modules.toString();

		Kybe.LOGGER.info("Saving settings to: {}", settingsFile.getAbsolutePath());
		Kybe.LOGGER.info(json);

		try (FileWriter writer = new FileWriter(settingsFile)) {
			writer.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadSettings() {
		try {

		File settingsFile = new File(mc.gameDirectory, "settings.json");
		if (!settingsFile.exists()) {
			Kybe.LOGGER.info("No settings file found.");
			return;
		}

		JsonArray jsonModules = JsonParser.parseReader(Files.newBufferedReader(settingsFile.toPath())).getAsJsonArray();
		List<JsonElement> jsonModulesList = jsonModules.asList();

		for (JsonElement jsonModule : jsonModulesList) {
			Module module = ModuleManager.getModule(jsonModule.getAsJsonObject().get("name").getAsString());
			if (module != null) {
				module.deserialize(jsonModule.getAsJsonObject());
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
