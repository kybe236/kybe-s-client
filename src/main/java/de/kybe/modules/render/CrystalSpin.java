package de.kybe.modules.render;

import de.kybe.gui.Gui;
import de.kybe.gui.CategoryEnum;
import de.kybe.baseModules.ToggleableModule;
import de.kybe.baseSettings.NumberSetting;

public class CrystalSpin {
	public static ToggleableModule crystalSpin = new ToggleableModule("CrystalSpin", CategoryEnum.RENDER);

	public CrystalSpin() {
		crystalSpin.addSetting(new NumberSetting<>("SidewaysSpeed", 5f, 0.1f, 50f, 0.5f));
		Gui.addModule(crystalSpin);
	}
}
