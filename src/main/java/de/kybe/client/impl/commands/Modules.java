package de.kybe.client.impl.commands;

import de.kybe.Kybe;
import de.kybe.client.core.command.Command;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleManager;
import de.kybe.client.core.setting.SettingManager;
import de.kybe.client.core.util.ChatUtils;
import de.kybe.client.impl.settings.BooleanSetting;
import de.kybe.client.impl.settings.EnumSetting;
import de.kybe.client.impl.settings.NumberSetting;
import de.kybe.client.core.setting.Setting;
import org.lwjgl.glfw.GLFW;

import java.util.Comparator;
import java.util.List;

public class Modules extends Command {

	public Modules() {
		super("Modules", "Lists all modules.", "modules", "mods");
	}

	@Override
	public void execute(String name, String[] args) {
		List<Module> modules = ModuleManager.getModules();

		if (args.length < 1) {
			ChatUtils.FAT_clientMessage("Modules:");

			int maxModuleNameLength = modules.stream().mapToInt(module -> module.getName().length()).max().orElse(0);
			modules.stream()
					.sorted(Comparator.comparing(Module::getName))
					.forEach(module -> {
						String moduleName = String.format("%-" + maxModuleNameLength + "s", module.getName());
						String state = module.getState() ? "Enabled" : "Disabled";
						String keybind;
						if (module.getKeybind() == 0) {
							keybind = "None";
						} else {
							keybind = GLFW.glfwGetKeyName(module.getKeybind(), 0);
						}
						if (keybind == null) {
							keybind = "Unknown";
						}

						ChatUtils.clientMessage(moduleName + " [" + state + "] (Key: " + keybind + ")");
					});
		} else if (args.length == 1) {
			String moduleName = args[0];
			Module module = modules.stream()
					.filter(mod -> mod.getName().equalsIgnoreCase(moduleName))
					.findFirst()
					.orElse(null);
			if (module != null) {
				List<Setting> settings = module.getSettings();
				ChatUtils.FAT_clientMessage(module.getName() + " has the following settings: ");
				settings.stream()
						.sorted(Comparator.comparing(Setting::getName))
						.forEach(setting -> ChatUtils.clientMessage(setting.getName() + " [" + getType(setting) + " | " + getSettingValue(setting) + "]"));
			} else {
				ChatUtils.clientWarningMessage("Module with name " + moduleName + " not found.");
			}
		} else {
			ChatUtils.clientWarningMessage("Usage: " + Kybe.PREFIX + " " + name + "<module>");
		}
	}

	private String getType(Setting setting) {
		if (setting instanceof NumberSetting) {
			return "Number";
		} else if (setting instanceof EnumSetting) {
			return "Enum";
		} else if (setting instanceof BooleanSetting) {
			return "Boolean";
		}
		return "Undefined";
	}

	private String getSettingValue(Setting setting) {
		if (setting instanceof NumberSetting) {
			return (String.valueOf(((NumberSetting<?>) setting).getValue()));
		} else if (setting instanceof EnumSetting) {
			return (String.valueOf(((EnumSetting<?>) setting).getValue()));
		} else if (setting instanceof BooleanSetting) {
			return (String.valueOf(((BooleanSetting) setting).isToggled()));
		}
		return "Undefined";
	}
}