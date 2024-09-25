package de.kybe.client.impl.modules.client;

import de.kybe.client.core.gui.GUI;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import org.lwjgl.glfw.GLFW;

import static de.kybe.Kybe.mc;


public class ClickGUI extends Module {

    public ClickGUI() {
        super("ClickGUI", "Desc", ModuleCategory.CLIENT, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @Override
    public void onEnable() {
        mc.setScreen(new GUI());
        this.toggle();
    }
}
