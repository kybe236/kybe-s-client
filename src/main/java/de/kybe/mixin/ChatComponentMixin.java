package de.kybe.mixin;

import de.kybe.client.core.event.EventBus;
import de.kybe.client.core.event.Execution;
import de.kybe.client.core.event.events.chatevents.ChatAddEvent;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {

	@Inject(
			method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V",
			at = @At("HEAD"),
			cancellable = true
	)
	public void onPreAddMessage(Component component, MessageSignature messageSignature, GuiMessageTag guiMessageTag, CallbackInfo ci) {
		ChatAddEvent event = new ChatAddEvent(component, messageSignature, guiMessageTag);
		EventBus.broadcast(event, Execution.PRE);
		if (event.isCancel()) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V",
			at = @At("HEAD"),
			cancellable = true
	)
	public void onPostAddMessage(Component component, MessageSignature messageSignature, GuiMessageTag guiMessageTag, CallbackInfo ci) {
		ChatAddEvent event = new ChatAddEvent(component, messageSignature, guiMessageTag);
		EventBus.broadcast(event, Execution.POST);
		if (event.isCancel()) {
			ci.cancel();
		}
	}
}
