package de.kybe.mixin;

import de.kybe.Kybe;
import de.kybe.client.core.command.CommandManager;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static de.kybe.Kybe.mc;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

	@Inject(
			method = "handleChatInput",
			at = @At("HEAD"),
			cancellable = true
	)
	private void onSendChatMessage(String s, boolean bl, CallbackInfoReturnable<Boolean> cir) {
		if (s.startsWith(Kybe.PREFIX) && s.length() > 1) {
			mc.gui.getChat().addRecentChat(s);
			CommandManager.executeCommand(s);
			cir.setReturnValue(true);
		}
	}
}