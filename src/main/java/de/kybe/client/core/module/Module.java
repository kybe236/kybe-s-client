package de.kybe.client.core.module;

import de.kybe.client.core.event.EventBus;
import org.lwjgl.glfw.GLFW;

public class Module {

    private final String name;
    private final String description;
    private final ModuleCategory category;

    private boolean state;
    private boolean drawn;
    private int keybind;

    public Module(String name, String description, ModuleCategory category, int keybind) {
        this.description = description;
        this.category = category;
        this.state = state;
        this.drawn = drawn;
        this.name = name;
        this.keybind = keybind;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        if (state) {
            enable();
        } else {
            disable();
        }
    }

    public void enable() {
        this.state = true;
        EventBus.register(this);
        onEnable();
    }

    public void disable() {
        this.state = false;
        EventBus.unregister(this);
        onDisable();
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void toggle() {
        if(state) {
            disable();
        } else {
            enable();
        }
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public boolean handleKeyPress(int key) {
        if (key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) {
            this.toggle();
            return true;
        }
        return false;
    }


}
