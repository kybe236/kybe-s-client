package de.kybe.mixin;

import de.kybe.baseSettings.NumberSetting;
import de.kybe.modules.render.CrystalSpin;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

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
	@ModifyArgs(method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 0))
	public void modifyBaseScale(Args args) {
		if (CrystalSpin.crystalSpin.isToggled()) {
			args.set(0, (Float) args.get(0) * ((NumberSetting<Float>) CrystalSpin.crystalSpin.get("base")).getValue());
			args.set(1, (Float) args.get(0) * ((NumberSetting<Float>) CrystalSpin.crystalSpin.get("base")).getValue());
			args.set(2, (Float) args.get(0) * ((NumberSetting<Float>) CrystalSpin.crystalSpin.get("base")).getValue());
		}
	}

	@SuppressWarnings("unchecked")
	@ModifyArgs(method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 1))
	public void modifyGlassScale(Args args) {
		if (CrystalSpin.crystalSpin.isToggled()) {
			args.set(0, (Float) args.get(0) * ((NumberSetting<Float>) CrystalSpin.crystalSpin.get("glassF")).getValue());
			args.set(1, (Float) args.get(1) * ((NumberSetting<Float>) CrystalSpin.crystalSpin.get("glassG")).getValue());
			args.set(2, (Float) args.get(2) * ((NumberSetting<Float>) CrystalSpin.crystalSpin.get("glassH")).getValue());
		}
	}

	@SuppressWarnings("unchecked")
	@ModifyArgs(method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 2))
	public void modifyCubeScale(Args args) {
		if (CrystalSpin.crystalSpin.isToggled()) {
			args.set(0, (Float) args.get(0) * ((NumberSetting<Float>) CrystalSpin.crystalSpin.get("cubeF")).getValue());
			args.set(1, (Float) args.get(1) * ((NumberSetting<Float>) CrystalSpin.crystalSpin.get("cubeG")).getValue());
			args.set(2, (Float) args.get(2) * ((NumberSetting<Float>) CrystalSpin.crystalSpin.get("cubeH")).getValue());
		}
	}
}
