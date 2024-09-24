package de.kybe.client.impl.commands;

import de.kybe.Kybe;
import de.kybe.client.core.command.Command;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleManager;
import de.kybe.client.core.setting.SettingManager;
import de.kybe.client.impl.settings.BooleanSetting;
import de.kybe.client.impl.settings.EnumSetting;
import de.kybe.client.impl.settings.NumberSetting;
import de.kybe.client.core.setting.Setting;
//import de.kybe.client.core.gui.gui.Gui;
import de.kybe.client.core.util.ChatUtils;

import java.util.Arrays;

public class Set extends Command {

	public Set() {
		super("Set", "Set the value of a modules setting.", "s", "set");
	}

	private static void sendUsage(String s) {
		ChatUtils.clientWarningMessage("Usage: " + Kybe.PREFIX + s + " <module>" + " <setting>" + " <value>");
	}

	private static void Success(String module, String setting, String value) {
		//Gui.saveSettings();
		ChatUtils.FAT_clientMessage("Set " + module + " - " + setting + " to " + value + ".");
	}

	@Override
	public void execute(String name, String[] args) {

		if (args.length < 3) {
			sendUsage(name);
			return;
		}

		Module module = ModuleManager.getModule(args[0]);
		if (module == null) {
			ChatUtils.clientWarningMessage("Module with name " + args[0] + " not found.");
			return;
		}

		Setting setting = SettingManager.getSettingByName(module, args[1]);
		switch (setting) {
			case null -> {
				ChatUtils.clientWarningMessage("Setting with name " + args[1] + " in module " + module.getName() + " not found.");
				//noinspection UnnecessaryReturnStatement
				return;
			}
			case BooleanSetting booleanSetting -> {
				String valueStr = args[2].toLowerCase();
				boolean value;

				if (valueStr.equalsIgnoreCase("toggle") || valueStr.equalsIgnoreCase("t")) {
					booleanSetting.setToggled(!booleanSetting.isToggled());
					Success(module.getName(), setting.getName(), String.valueOf(booleanSetting.isToggled()));
					return;
				}

				if (valueStr.equals("true") || valueStr.equals("on")) {
					value = true;
				} else if (valueStr.equals("false") || valueStr.equals("off")) {
					value = false;
				} else {
					ChatUtils.clientWarningMessage("Invalid value for boolean setting. Use true/false, on/off");
					return;
				}

				booleanSetting.setToggled(value);
				Success(module.getName(), setting.getName(), String.valueOf(booleanSetting.isToggled()));
			}
			case EnumSetting<?> enumSetting -> {
				String newValueStr = args[2].toUpperCase();
				Enum<?>[] allModes = enumSetting.getAllModes();

				boolean validValue = false;
				for (Enum<?> mode : allModes) {
					if (mode.name().equalsIgnoreCase(newValueStr)) {
						enumSetting.setValue(mode);
						validValue = true;
						break;
					}
				}

				if (validValue) {
					Success(module.getName(), setting.getName(), enumSetting.getValue().name());
				} else {
					ChatUtils.clientWarningMessage("Invalid value for enum setting. Available values: " +
							String.join(", ", Arrays.stream(allModes).map(Enum::name).toArray(String[]::new)));
				}
			}
			case NumberSetting<?> numberSetting -> {
				if (numberSetting.getIncrement().doubleValue() == 1) {
					int value;
					try {
						value = Integer.parseInt(args[2]);
						numberSetting.setValue(value);
						Success(module.getName(), setting.getName(), String.valueOf(value));
					} catch (NumberFormatException e) {
						ChatUtils.clientWarningMessage(args[2] + " is not a valid integer.");
					}
				} else {
					double value;
					try {
						value = Double.parseDouble(args[2]);
						numberSetting.setValue(value);
						Success(module.getName(), setting.getName(), String.valueOf(value));
					} catch (NumberFormatException e) {
						ChatUtils.clientWarningMessage(args[2] + " is not a valid number.");
					}
				}
			}
			default -> {
			}
		}

	}
}
