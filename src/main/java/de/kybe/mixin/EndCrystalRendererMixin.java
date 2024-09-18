package de.kybe.mixin;

import de.kybe.gui.components.settings.NumberSetting;
import de.kybe.modules.CrystalSpin;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EndCrystalRenderer.class)
public abstract class EndCrystalRendererMixin {
	@ModifyArg(method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At(value = "INVOKE", target = "Lcom/mojang/math/Axis;rotationDegrees(F)Lorg/joml/Quaternionf;"))
	public float modifyRotation(float f) {
		if (CrystalSpin.crystalSpin.isToggled()) {
			return f * ((NumberSetting<Float>) CrystalSpin.crystalSpin.getSettings().get(0)).getValue();
		}
		return f;
	}
}
