package de.kybe.client.core.configManager;

import com.google.gson.JsonArray;
import de.kybe.Kybe;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleManager;

import java.io.File;
import java.io.FileWriter;

import static de.kybe.Kybe.mc;

public class ConfigManager {

	public static void saveSettings() {
		JsonArray modules = new JsonArray();
		for (Module module : ModuleManager.getModules()) {
			Kybe.LOGGER.info("Saving module: {}", module.getName());
			modules.add(module.serialize());
		}

		File settingsFile = new File(mc.gameDirectory, "settings.json");

		String json = modules.getAsString();

		Kybe.LOGGER.info("Saving settings to: {}", settingsFile.getAbsolutePath());
		Kybe.LOGGER.info(json);

		try (FileWriter writer = new FileWriter(settingsFile)) {
			writer.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
