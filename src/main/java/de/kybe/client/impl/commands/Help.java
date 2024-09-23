package de.kybe.client.impl.commands;

import de.kybe.Kybe;
import de.kybe.client.core.command.Command;
import de.kybe.client.core.command.CommandManager;
import de.kybe.utils.ChatUtils;

import java.util.Comparator;
import java.util.List;


public class Help extends Command {

	public Help() {
		super("Help", "Helps you out a bit.", "help");
	}

	@Override
	public void execute(String name, String[] args) {

		List<Command> commands = CommandManager.getCommands();

		if (args.length < 1) {
			ChatUtils.FAT_clientMessage("Commands:");
			commands.stream()
					.sorted(Comparator.comparing(Command::getName))
					.forEach(command -> ChatUtils.clientMessage(command.getName()));
		} else if (args.length == 1) {
			String commandName = args[0];
			Command command = commands.stream()
					.filter(cmd -> cmd.getAllNamesAndAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(commandName)))
					.findFirst()
					.orElse(null);
			if (command != null) {
				ChatUtils.clientMessage(command.getName() + ": " + command.getDescription());
			} else {
				ChatUtils.clientWarningMessage("Command with name " + commandName + " not found.");
			}
		} else {
			ChatUtils.clientWarningMessage("Usage: " + Kybe.PREFIX + name + " <command>");
		}
	}
}
