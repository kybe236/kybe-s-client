package de.kybe.modules.misc;

import de.kybe.Kybe;
import de.kybe.baseModules.Module;
import de.kybe.eventBus.EventBus;
import de.kybe.eventBus.Subscribe;
import de.kybe.eventBus.events.KeyboardEvent.RawKeyboardEvent;
import de.kybe.gui.CategoryEnum;
import de.kybe.clientgui.GUI;

import static de.kybe.constants.Globals.mc;

public class ClickGUI {

    public Module module = new Module("ClickGUI", CategoryEnum.MISC);

    public ClickGUI() {
        de.kybe.gui.Gui.addModule(module);
        EventBus.register(this);
    }

    @Subscribe
    public static void onRawKeyboard(RawKeyboardEvent event) {
        if (Kybe.ClickGUIKey.matches(event.getI(), event.getJ()) && mc.screen == null) {
            event.setCancel(true);
            mc.setScreen(new GUI());
        }
    }

}
