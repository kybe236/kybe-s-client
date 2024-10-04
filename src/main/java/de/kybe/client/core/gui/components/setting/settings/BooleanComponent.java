package de.kybe.client.core.gui.components.setting.settings;

import de.kybe.client.core.gui.components.setting.SettingComponent;
import de.kybe.client.core.gui.misc.TempColors;
import de.kybe.client.core.setting.Setting;
import de.kybe.client.core.util.renders.font.FontUtils;
import de.kybe.client.core.util.renders.render2d.Rect;
import de.kybe.client.impl.settings.BooleanSetting;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

import static de.kybe.Kybe.mc;

public class BooleanComponent extends SettingComponent {

	public BooleanComponent(int x, int y, int width, int height, Setting setting) {
		super(x, y, width, height, setting);
	}

	@Override
	public void drawScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		BooleanSetting set = (BooleanSetting) setting;
		String settingname = set.getName() + ": " + set.isToggled();

		Rect.drawSquare(x, y, FontUtils.getStringWidth(settingname) + 4, height, TempColors.color_background_darker);
		guiGraphics.drawString(mc.font, settingname, x + 2, y + (height - FontUtils.getStringHeight()) / 2, Color.WHITE.getRGB());
	}

	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if (isHovered((int) mouseX, (int) mouseY) && button == 0) {
			BooleanSetting set = (BooleanSetting) setting;
			set.setToggled(!set.isToggled());
		}
	}
}