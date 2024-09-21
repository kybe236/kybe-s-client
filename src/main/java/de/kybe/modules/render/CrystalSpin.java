package de.kybe.modules.render;

import de.kybe.gui.Gui;
import de.kybe.gui.CategoryEnum;
import de.kybe.baseModules.ToggleableModule;
import de.kybe.baseSettings.NumberSetting;

public class CrystalSpin {
	public static ToggleableModule crystalSpin = new ToggleableModule("CrystalSpin", CategoryEnum.RENDER);

	public CrystalSpin() {
		crystalSpin.addSetting(new NumberSetting<>("SidewaysSpeed", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("f", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("g", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("h", 1f, -20f, 20f, 0.1f));

		Gui.addModule(crystalSpin);
	}
}
