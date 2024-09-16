package de.kybe.mixin;

import de.kybe.gui.Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "drop")
	private void drop(boolean bl, CallbackInfoReturnable<Boolean> cir) {
		Minecraft.getInstance().execute(
				() -> {
					Minecraft.getInstance().setScreen(new Gui());
				}
		);
	}
}