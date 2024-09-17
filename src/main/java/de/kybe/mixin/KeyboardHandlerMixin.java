package de.kybe.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import de.kybe.Kybe;
import de.kybe.handlers.KeyPress;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

	@Shadow public abstract void keyPress(long l, int i, int j, int k, int m);

	@Inject(at = @At("HEAD"), method = "keyPress")
	private void keyPress(long l, int i, int j, int k, int m, CallbackInfo ci) {
		switch (k) {
			case 0 -> KeyPress.onKeyPress(i);
			case 1 -> KeyPress.onKeyRelease(i);
			case 2 -> KeyPress.onKeyHold(i);
		}

		if (k == 0) {
			String character = InputConstants.getKey(i, j).getDisplayName().getString().toLowerCase();
			if (character.length() == 1) {
				KeyPress.onCharTyped(character.charAt(0));
			}
		}
	}
}