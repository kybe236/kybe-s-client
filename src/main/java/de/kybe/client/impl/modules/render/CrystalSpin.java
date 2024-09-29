package de.kybe.client.impl.modules.render;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.impl.settings.NumberSetting;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CrystalSpin extends Module {
	NumberSetting sidewaysSpeed = new NumberSetting("SidewaysSpeed", "Speed of sideways spin", 1f, -20f, 20f, 0.1f);
	NumberSetting scaleX = new NumberSetting("scaleX", "Scaling on X-axis", 1f, -20f, 20f, 0.1f);
	NumberSetting scaleY = new NumberSetting("scaleY", "Scaling on Y-axis", 1f, -20f, 20f, 0.1f);
	NumberSetting scaleZ = new NumberSetting("scaleZ", "Scaling on Z-axis", 1f, -20f, 20f, 0.1f);

	public CrystalSpin() {
		super("CrystalSpin", "Rotates crystals in the render", ModuleCategory.RENDER, GLFW.GLFW_KEY_UNKNOWN);
	    this.addSettings(
                sidewaysSpeed,
                scaleX,
                scaleY,
                scaleZ
        );
    }
}