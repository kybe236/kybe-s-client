package de.kybe.client.impl.commands;

import de.kybe.client.core.command.Command;
import de.kybe.client.core.util.ChatUtils;

public class Test extends Command {

	public Test() {
		super("Test", "Test output for Chatutils.", "test");
	}

	@Override
	public void execute(String name, String[] args) {

		ChatUtils.clientMessage("Client Message");
		ChatUtils.clientInfoMessage("Client Info");
		ChatUtils.clientWarningMessage("Client Warning");
		ChatUtils.clientErrorMessage("Client Error");
		ChatUtils.Message("");
		ChatUtils.Message("Message");
		ChatUtils.InfoMessage("Info");
		ChatUtils.WarningMessage("Warning");
		ChatUtils.ErrorMessage("Error");

	}
}
