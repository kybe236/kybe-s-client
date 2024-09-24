package de.kybe.client.impl.modules;

import de.kybe.client.core.event.Subscribe;
import de.kybe.client.core.event.events.EventTick;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.core.util.ChatUtils;
import de.kybe.client.impl.settings.BindSetting;
import de.kybe.client.impl.settings.BooleanSetting;
import de.kybe.client.impl.settings.EnumSetting;
import de.kybe.client.impl.settings.NumberSetting;
import org.lwjgl.glfw.GLFW;

public class TestModule extends Module {

    NumberSetting numberSetting = new NumberSetting("Name", "hi", 3, 1, 5, 1);
    BooleanSetting booleanSetting = new BooleanSetting("Name", "hi", true);
    EnumSetting enumSetting = new EnumSetting("Enum", "hi",ModuleCategory.CLIENT);
    BindSetting bindSetting = new BindSetting("Bind","hi", GLFW.GLFW_KEY_ENTER);

    public TestModule() {
        super("TestModule", "Desc", ModuleCategory.COMBAT, GLFW.GLFW_KEY_P);
    }

    @Override
    public void onEnable() {
        ChatUtils.clientMessage("ENABLE");
    }

    @Override
    public void onDisable() {
        ChatUtils.clientMessage("DISABLE");
    }

    @Subscribe
    public static void onTick(EventTick ignore) {
        ChatUtils.clientMessage("TICKING");
    }
}
