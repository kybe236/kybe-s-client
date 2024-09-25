package de.kybe.client.core.event.events.ChatEvent;

import de.kybe.client.core.event.events.Cancelable;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.jetbrains.annotations.Nullable;

public class ChatAddEvent extends Cancelable {
	private final GuiMessageTag tag;
	Component component;
	MessageSignature messageSignature;

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
