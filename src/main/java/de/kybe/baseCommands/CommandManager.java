package de.kybe.baseCommands;

import de.kybe.utils.ChatUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private static List<Command> commands = new ArrayList<>();

    public static void addCommand(Command command) {
        commands.add(command);
    }

    public static List<Command> getCommands() {
        return commands;
    }

    public static boolean executeCommand(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        String rawCommand = input.substring(1);
        String[] split = rawCommand.split(" ");

        if (split.length == 0) return false;

        String cmdName = split[0];

        Command command = commands.stream()
                .filter(cmd -> cmd.getAllNames().contains(cmdName))
                .findFirst()
                .orElse(null);

        if (command == null) {
            ChatUtils.clientWarningMessage("Command with name " + cmdName + " does not exist.");
            return false;
        }

        String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);

        command.execute(cmdName, args);
        return true;
    }
}

