package de.kybe.commands;

import de.kybe.Kybe;
import de.kybe.baseCommands.Command;
import de.kybe.utils.ChatUtils;

import static de.kybe.constants.Globals.mc;

public class Say extends Command {

	public Say() {
		super("Say", "Send a cool message in chat.", "say");
	}

	@Override
	public void execute(String name, String[] args) {
		if (mc.player == null) return;

		if (args.length < 1) {
			ChatUtils.clientWarningMessage("Usage: " + Kybe.PREFIX + name + " <message>");
			return;
		}

		String message = String.join(" ", args);
		mc.player.connection.sendChat(message);

	}
}
