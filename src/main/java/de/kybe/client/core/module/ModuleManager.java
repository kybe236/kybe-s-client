package de.kybe.client.core.module;

import de.kybe.client.core.event.EventBus;
import de.kybe.client.core.event.Subscribe;
import de.kybe.client.core.event.events.KeyboardEvent.KeyboardEvent;

import java.util.ArrayList;
import java.util.List;

import static de.kybe.Kybe.mc;

public class ModuleManager {

    public ModuleManager() {
        EventBus.register(this);
    }

    private static final List<Module> modules = new ArrayList<>();

    public static void addModule(Module module) {
        modules.add(module);
    }

    public static List<Module> getModules() {
        return modules;
    }

    public static Module getModule(String name) {
        return modules.stream().filter(module -> name.equalsIgnoreCase(module.getName())).findFirst().orElse(null);
    }

    public static List<Module> getModulesFromCategory(ModuleCategory category) {
        List<Module> returnmodules = new ArrayList<>();

        for (Module module : modules) {
            if (module.getCategory() == category) {
                returnmodules.add(module);
            }
        }

        return returnmodules;
    }

    @Subscribe
    public void onKeyPress(KeyboardEvent event) {
        if(mc.screen != null) return;
        KeyboardEvent.Type type = event.getType();
        for (Module module : getModules()) {
            if(module.getKeybind() == event.getKey() && type == KeyboardEvent.Type.PRESS) {
                module.toggle();
            }
        }
    }
}
