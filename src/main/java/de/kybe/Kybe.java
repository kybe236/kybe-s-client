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
import de.kybe.client.core.configManager.ConfigManager;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.core.module.ModuleManager;
import de.kybe.client.impl.commands.*;
import de.kybe.client.impl.modules.TestModule;
import de.kybe.client.impl.modules.client.ClickGUI;
import de.kybe.client.impl.modules.movement.DoubleJump;
import de.kybe.client.impl.modules.render.*;
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

import java.util.Timer;
import java.util.TimerTask;

public class Kybe implements ModInitializer {
	public static final String MOD_ID = "kybe";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final String PREFIX = "?";
	public static final String CLIENT_NAME = "Kybe";
	public static final String CLIENT_VERSION = "0.0.1";

	public static final int SAVE_DELAY = 100000;

	public static Minecraft mc;

	public static KeyMapping keyMapping;
	public static KeyMapping ClickGUIKey;

	public static ModuleManager moduleManager;
	public CommandManager commandManager;
	public Timer timer;

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
		ModuleManager.addModule(new NoPassiveEntityAdd());
		ModuleManager.addModule(new NoHostileEntityAdd());
		ModuleManager.addModule(new NoNeutralEntityAdd());
		ModuleManager.addModule(new NoProjectilesEntityAdd());
		ModuleManager.addModule(new NoMiscEntityAdd());

		//to test module scrolling in the gui
		char letter = 'A';
		for (int i = 0; i < 24; i++) {
			String moduleName = letter + "_module";
			String moduleDescription = "Description for " + moduleName;

			Module random = new Module(moduleName, moduleDescription, ModuleCategory.CHAT, GLFW.GLFW_KEY_UNKNOWN);
			ModuleManager.addModule(random);

			letter++;
		}

		CommandManager.addCommand(new Say());
		CommandManager.addCommand(new Test());
		CommandManager.addCommand(new Help());
		CommandManager.addCommand(new Set());
		CommandManager.addCommand(new Modules());
		CommandManager.addCommand(new Toggle());

		// initialize config
		ConfigManager.loadSettings();

		// save settings periodically
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				ConfigManager.saveSettings();
			}
		}, 0, SAVE_DELAY);
	}


}