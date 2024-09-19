package de.kybe.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import de.kybe.eventBus.EventBus;
import de.kybe.eventBus.Execution;
import de.kybe.eventBus.events.KeyboardEvent.KeyboardEvent;
import de.kybe.eventBus.events.typeCharEvent.TypeCharEvent;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
	@Inject(at = @At("HEAD"), method = "keyPress", cancellable = true)
	private void keyPressStart(long l, int i, int j, int k, int m, CallbackInfo ci) {
		if (i == 2) {
			return;
		}

		KeyboardEvent kpe = new KeyboardEvent(i, k == 0 ? KeyboardEvent.Type.PRESS : KeyboardEvent.Type.RELEASE);
		EventBus.broadcast(kpe, Execution.PRE);

		String character = InputConstants.getKey(i, j).getDisplayName().getString().toLowerCase();
		if (character.length() == 1) {
			TypeCharEvent cpe = new TypeCharEvent(character.charAt(0), k == 0 ? KeyboardEvent.Type.PRESS : KeyboardEvent.Type.RELEASE);
			EventBus.broadcast(cpe, Execution.PRE);
			if (cpe.isCancel()) {
				ci.cancel();
			}
		}
		if (kpe.isCancel()) {
			ci.cancel();
		}
	}

	@Inject(at = @At("TAIL"), method = "keyPress", cancellable = true)
	private void keyPressEnd(long l, int i, int j, int k, int m, CallbackInfo ci) {
		if (i == 2) {
			return;
		}
		KeyboardEvent kpe = new KeyboardEvent(i, k == 0 ? KeyboardEvent.Type.PRESS : KeyboardEvent.Type.RELEASE);
		EventBus.broadcast(kpe, Execution.POST);

		String character = InputConstants.getKey(i, j).getDisplayName().getString().toLowerCase();
		if (character.length() == 1) {
			TypeCharEvent cpe = new TypeCharEvent(character.charAt(0), k == 0 ? KeyboardEvent.Type.PRESS : KeyboardEvent.Type.RELEASE);
			EventBus.broadcast(cpe, Execution.POST);
			if (cpe.isCancel()) {
				ci.cancel();
			}
		}
		if (kpe.isCancel()) {
			ci.cancel();
		}
	}
}