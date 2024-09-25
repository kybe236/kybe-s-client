package de.kybe.client.core.setting;

import de.kybe.client.core.module.Module;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SettingManager {

	private static final List<Setting> settings = new ArrayList<>();

	public SettingManager() {

	}

	public static void registerSetting(Setting setting) {
		settings.add(setting);
	}

	public static List<Setting> getAllSettings() {
		return settings;
	}

	public static List<Setting> getSettingsForModule(Module module) {
		List<Setting> moduleSettings = new ArrayList<>();
		for (Setting setting : settings) {
			if (setting.getParent() == module) {
				moduleSettings.add(setting);
			}
		}
		return moduleSettings;
	}

	public static Setting getSettingByName(Module module, String settingName) {
		for (Setting setting : settings) {
			if (setting.getParent() == module && setting.getName().equalsIgnoreCase(settingName)) {
				return setting;
			}
		}
		return null;
	}
}
