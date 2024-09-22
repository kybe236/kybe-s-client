package de.kybe.commands;

import de.kybe.Kybe;
import de.kybe.baseCommands.Command;
import de.kybe.baseModules.Module;
import de.kybe.baseModules.ToggleableModule;
import de.kybe.gui.Gui;
import de.kybe.utils.ChatUtils;

import static de.kybe.constants.Globals.mc;

public class Toggle extends Command {

    public Toggle() {
        super("Toggle", "Toggle a module.", "toggle", "t");
    }

    @Override
    public void execute(String name, String[] args) {

        if(args.length < 1) {
            ChatUtils.clientWarningMessage("Usage: " + Kybe.PREFIX + name + " <module>");
            return;
        }

        Module module = Gui.getModuleByName(args[0]);
        if(module == null) {
            ChatUtils.clientWarningMessage("Module with name " + args[0] + " not found.");
            return;
        } else {
            if(module instanceof ToggleableModule toggleableModule) {
                toggleableModule.toggle();
                ChatUtils.FAT_clientMessage("Toggled: " + module.getName() + " | " + ((ToggleableModule) module).isToggled());
            }
        }

    }
}
