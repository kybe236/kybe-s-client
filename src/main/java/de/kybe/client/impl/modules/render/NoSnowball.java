package de.kybe.client.impl.modules.render;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import org.lwjgl.glfw.GLFW;

public class NoSnowball extends Module {
	public NoSnowball() {
		super("NoSnowball", "Removes snowball rendering", ModuleCategory.RENDER, GLFW.GLFW_KEY_UNKNOWN);
	}
}
