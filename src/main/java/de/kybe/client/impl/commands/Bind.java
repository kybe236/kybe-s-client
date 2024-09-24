package de.kybe.client.impl.commands;

import de.kybe.Kybe;
import de.kybe.client.core.command.Command;
import de.kybe.client.core.util.ChatUtils;

import static de.kybe.Kybe.mc;

public class Bind extends Command {

    public Bind() {
        super("Bind", "Bind a module to a key.", "b", "bind");
    }

    @Override
    public void execute(String name, String[] args) {
        if (mc.player == null) return;

        if (args.length < 1) {
            ChatUtils.clientWarningMessage("Usage: " + Kybe.PREFIX + name + " <module>" + " <key>");
            return;
        }

        String message = String.join(" ", args);
        mc.player.connection.sendChat(message);

    }
}