package de.kybe.client.impl.modules.render;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import org.lwjgl.glfw.GLFW;

public class NoItems extends Module {
	public NoItems() {
		super("NoItem", "Removes items rendering", ModuleCategory.RENDER, GLFW.GLFW_KEY_UNKNOWN);
	}
}
