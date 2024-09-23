package de.kybe.client.impl.modules.misc;

import de.kybe.Kybe;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.event.EventBus;
import de.kybe.client.core.module.ModuleCategory;

import static de.kybe.Kybe.mc;

public class ClickGUI {

    public Module module = new Module("ClickGUI", ModuleCategory.CLIENT);

    public ClickGUI() {
        Gui.addModule(module);
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
