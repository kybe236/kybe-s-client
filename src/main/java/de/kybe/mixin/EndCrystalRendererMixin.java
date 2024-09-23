package de.kybe.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
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

@SuppressWarnings("SpellCheckingInspection")
@Mixin(EndCrystalRenderer.class)
public abstract class EndCrystalRendererMixin {
	@ModifyArg(method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At(value = "INVOKE", target = "Lcom/mojang/math/Axis;rotationDegrees(F)Lorg/joml/Quaternionf;"))
	public float modifyRotation(float f) {
		if (CrystalSpin.crystalSpin.isToggled()) {
			//noinspection unchecked
			return f * ((NumberSetting<Float>) CrystalSpin.crystalSpin.get("SidewaysSpeed")).getValue();
		}
		return f;
	}

	@SuppressWarnings("unchecked")
	@Inject(
			method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", ordinal = 0)
	)
	public void pushPose(EndCrystal endCrystal, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
		if (CrystalSpin.crystalSpin.isToggled()) {
			poseStack.scale(
					((NumberSetting<Float>) CrystalSpin.crystalSpin.get("scaleX")).getValue(),
					((NumberSetting<Float>) CrystalSpin.crystalSpin.get("scaleY")).getValue(),
					((NumberSetting<Float>) CrystalSpin.crystalSpin.get("scaleZ")).getValue()
			);
		}
	}
}
