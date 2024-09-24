package de.kybe.client.impl.modules.movement;

import de.kybe.client.core.event.Subscribe;
import de.kybe.client.core.event.events.EventTick;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import org.lwjgl.glfw.GLFW;

import static de.kybe.Kybe.mc;

public class DoubleJump extends Module {

    public DoubleJump() {
        super("DoubleJump", "Allows you to double jump when in air", ModuleCategory.MOVEMENT, GLFW.GLFW_KEY_UNKNOWN);
    }

    @Subscribe
    public static void onTick(EventTick ignore) {
        if (mc.player != null && !mc.player.onGround() && mc.options.keyJump.isDown()) {
            mc.player.jumpFromGround();
        }
    }
}
