/*
 * Copyright (c) 2024 kybe236
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.kybe;

import com.mojang.blaze3d.platform.InputConstants;
import de.kybe.client.core.command.CommandManager;
import de.kybe.client.core.module.ModuleManager;
import de.kybe.client.impl.commands.*;
import de.kybe.client.impl.modules.TestModule;
import de.kybe.client.impl.modules.client.ClickGUI;
import de.kybe.client.impl.modules.movement.DoubleJump;
import de.kybe.client.impl.modules.render.CrystalSpin;
import de.kybe.client.impl.modules.render.NoSnowball;
import de.kybe.mixin.IKeyMapping;
import de.kybe.mixin.IOptions;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kybe implements ModInitializer {
	public static final String MOD_ID = "kybe";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final String PREFIX = "?";
	public static final String CLIENT_NAME = "Kybe";
	public static final String CLIENT_VERSION = "0.0.1";

	public static Minecraft mc;

	public static KeyMapping keyMapping;
	public static KeyMapping ClickGUIKey;

	public static ModuleManager moduleManager;
	public CommandManager commandManager;

	public static void afterConfigInit() {
		ClickGUIKey = new KeyMapping("ClickGUI", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.kybe");

		Options config = Minecraft.getInstance().options;

		((IOptions) config).setKeyMappings(ArrayUtils.add(config.keyMappings, ClickGUIKey));
		((IKeyMapping) ClickGUIKey).getCategorySortOrder().put("category.kybe", ((IKeyMapping) ClickGUIKey).getCategorySortOrder().size() + 1);
	}

	@Override
	public void onInitialize() {
		mc = Minecraft.getInstance();
		moduleManager = new ModuleManager();
		commandManager = new CommandManager();

		ModuleManager.addModule(new TestModule());
		ModuleManager.addModule(new ClickGUI());
		ModuleManager.addModule(new DoubleJump());
		ModuleManager.addModule(new CrystalSpin());
		ModuleManager.addModule(new NoSnowball());

		CommandManager.addCommand(new Say());
		CommandManager.addCommand(new de.kybe.client.impl.commands.Test());
		CommandManager.addCommand(new Help());
		CommandManager.addCommand(new Set());
		CommandManager.addCommand(new Modules());
		CommandManager.addCommand(new Toggle());
	}
}