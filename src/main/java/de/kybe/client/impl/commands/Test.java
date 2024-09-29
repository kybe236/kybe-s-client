package de.kybe.client.impl.commands;

import de.kybe.client.core.command.Command;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleManager;
import de.kybe.client.core.setting.Setting;
import de.kybe.client.core.util.ChatUtils;

public class Test extends Command {

	public Test() {
		super("Test", "Test output for Chatutils.", "test");
	}

	@Override
	public void execute(String name, String[] args) {

		for (Module module : ModuleManager.getModules()) {
			ChatUtils.clientMessage(module.getName());
			for (Setting setting : module.getSettings()) {
				ChatUtils.clientMessage(setting.toString());
			}
		}

	}
}
