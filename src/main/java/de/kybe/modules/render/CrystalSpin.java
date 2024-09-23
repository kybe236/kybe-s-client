package de.kybe.modules.render;

import de.kybe.baseModules.ToggleableModule;
import de.kybe.baseSettings.BindSetting;
import de.kybe.baseSettings.NumberSetting;
import de.kybe.eventBus.EventBus;
import de.kybe.gui.CategoryEnum;
import de.kybe.gui.Gui;

public class CrystalSpin {
	public static ToggleableModule crystalSpin = new ToggleableModule("CrystalSpin", CategoryEnum.RENDER);

	public CrystalSpin() {
		crystalSpin.addSetting(new NumberSetting<>("SidewaysSpeed", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("scaleX", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("scaleY", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("scaleZ", 1f, -20f, 20f, 0.1f));

		EventBus.register(this);
		Gui.addModule(crystalSpin);
	}
}
