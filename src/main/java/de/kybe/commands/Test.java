package de.kybe.commands;

import de.kybe.baseCommands.Command;
import de.kybe.utils.ChatUtils;

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
