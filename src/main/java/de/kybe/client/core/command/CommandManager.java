package de.kybe.client.core.command;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleManager;
import de.kybe.client.core.util.ChatUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

	private static final List<Command> commands = new ArrayList<>();

	public CommandManager() {

	}

	public static void addCommand(Command command) {
		commands.add(command);
	}

	public static List<Command> getCommands() {
		return commands;
	}

	public static void executeCommand(String input) {
		if (input == null || input.isEmpty()) {
			return;
		}

		String rawCommand = input.substring(1);
		String[] split = rawCommand.split(" ");

		if (split.length == 0) return;

		String cmdName = split[0];

		// Check for matching module name first -> toggle if found
		Module module = ModuleManager.getModule(cmdName);

		if (module != null && split.length == 1) {
			module.setState(!module.getState());
			//Gui.saveSettings();
			ChatUtils.FAT_clientMessage("Toggled: " + module.getName() + " | " + module.getState());
			return;
		}

		Command command = commands.stream()
				.filter(cmd -> cmd.getAllNamesAndAliases().contains(cmdName))
				.findFirst()
				.orElse(null);

		if (command == null) {
			ChatUtils.clientWarningMessage("Command with name " + cmdName + " does not exist.");
			return;
		}

		String[] args = new String[split.length - 1];
		System.arraycopy(split, 1, args, 0, split.length - 1);

		command.execute(cmdName, args);
	}
}

