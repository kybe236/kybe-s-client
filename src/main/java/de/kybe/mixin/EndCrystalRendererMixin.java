package de.kybe.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.kybe.client.core.module.ModuleManager;
import de.kybe.client.impl.settings.NumberSetting;
import de.kybe.client.impl.modules.render.CrystalSpin;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndCrystalRenderer.class)
public abstract class EndCrystalRendererMixin {

	@ModifyArg(method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At(value = "INVOKE", target = "Lcom/mojang/math/Axis;rotationDegrees(F)Lorg/joml/Quaternionf;"))
	public float modifyRotation(float f) {
		CrystalSpin crystalSpin = (CrystalSpin) ModuleManager.getModule("CrystalSpin");

		if (crystalSpin != null && crystalSpin.getState()) {
			NumberSetting sidewaysSpeedSetting = (NumberSetting) crystalSpin.getSetting("SidewaysSpeed");
			if (sidewaysSpeedSetting != null) {
				return f * (Float) sidewaysSpeedSetting.getValue();
			}
		}
		return f;
	}

	@Inject(
			method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", ordinal = 0)
	)
	public void pushPose(EndCrystal endCrystal, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
		CrystalSpin crystalSpin = (CrystalSpin) ModuleManager.getModule("CrystalSpin");

		if (crystalSpin != null && crystalSpin.getState()) {
			NumberSetting scaleXSetting = (NumberSetting) crystalSpin.getSetting("scaleX");
			NumberSetting scaleYSetting = (NumberSetting) crystalSpin.getSetting("scaleY");
			NumberSetting scaleZSetting = (NumberSetting) crystalSpin.getSetting("scaleZ");

			if (scaleXSetting != null && scaleYSetting != null && scaleZSetting != null) {
				poseStack.scale(
						(Float) scaleXSetting.getValue(),
						(Float) scaleYSetting.getValue(),
						(Float) scaleZSetting.getValue()
				);
			}
		}
	}
}
