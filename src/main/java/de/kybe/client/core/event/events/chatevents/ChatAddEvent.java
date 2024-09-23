package de.kybe.client.core.event.events.chatevents;

import net.minecraft.client.GuiMessageTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.jetbrains.annotations.Nullable;

public class ChatAddEvent extends Cancelable {
	Component component;
	MessageSignature messageSignature;
	private final GuiMessageTag tag;

	public ChatAddEvent(Component component, @Nullable MessageSignature messageSignature, @Nullable GuiMessageTag guiMessageTag) {
		this.component = component;
		this.tag = guiMessageTag;
		this.messageSignature = messageSignature;

	}

	@SuppressWarnings("unused")
	public GuiMessageTag getTag() {
		return tag;
	}

	@SuppressWarnings("unused")
	public Component getComponent() {
		return component;
	}

	@SuppressWarnings("unused")
	public MessageSignature getMessageSignature() {
		return messageSignature;
	}

}
