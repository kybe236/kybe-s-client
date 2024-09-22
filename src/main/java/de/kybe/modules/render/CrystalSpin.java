package de.kybe.modules.render;

import de.kybe.baseSettings.BindSetting;
import de.kybe.eventBus.EventBus;
import de.kybe.gui.Gui;
import de.kybe.gui.CategoryEnum;
import de.kybe.baseModules.ToggleableModule;
import de.kybe.baseSettings.NumberSetting;

public class CrystalSpin {
	public static ToggleableModule crystalSpin = new ToggleableModule("CrystalSpin", CategoryEnum.RENDER);

	public CrystalSpin() {
		crystalSpin.addSetting(new NumberSetting<>("SidewaysSpeed", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("base", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("glassF", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("glassG", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("glassH", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("cubeF", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("cubeG", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("cubeH", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new BindSetting("Bind"));

		EventBus.register(this);
		Gui.addModule(crystalSpin);
	}
}
