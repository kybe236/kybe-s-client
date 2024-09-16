package de.kybe.mixin;

import de.kybe.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static de.kybe.constants.Globals.mc;

@Mixin(LocalPlayer.class)
public abstract class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "drop")
	private void drop(boolean bl, CallbackInfoReturnable<Boolean> cir) {
		mc.execute(
				() -> {
					mc.setScreen(new Gui());
				}
		);
	}
}