package de.kybe.client.impl.modules.render;

import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.core.module.ToggleableModule;
import de.kybe.client.impl.settings.NumberSetting;
import de.kybe.client.core.event.EventBus;

public class CrystalSpin {
	public static ToggleableModule crystalSpin = new ToggleableModule("CrystalSpin", ModuleCategory.RENDER);

	public CrystalSpin() {
		crystalSpin.addSetting(new NumberSetting<>("SidewaysSpeed", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("scaleX", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("scaleY", 1f, -20f, 20f, 0.1f));
		crystalSpin.addSetting(new NumberSetting<>("scaleZ", 1f, -20f, 20f, 0.1f));

		EventBus.register(this);
		Gui.addModule(crystalSpin);
	}
}
