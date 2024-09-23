package de.kybe.utils;

import de.kybe.Kybe;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import static de.kybe.constants.Globals.mc;

public final class ChatUtils {

	private static final TextColor MAIN_COLOR = TextColor.fromRgb(0x42BFF5);
	private static final TextColor INFO_COLOR = TextColor.fromRgb(0x79B4E4);
	private static final TextColor WARN_COLOR = TextColor.fromRgb(0xEED202);
	private static final TextColor ERROR_COLOR = TextColor.fromRgb(0xFF0F0F);

	private static Component createLabel(String label, TextColor color) {
		return Component.literal("[").withStyle(Style.EMPTY)
				.append(Component.literal(label).withStyle(style -> style.withColor(color)))
				.append(Component.literal("]").withStyle(Style.EMPTY));
	}

	public static void clientMessage(String message) {
		sendMessage(Component.literal("").append(createLabel(Kybe.MOD_ID, MAIN_COLOR))
				.append(Component.literal(" " + message)));
	}

	public static void FAT_clientMessage(String message) {
		sendMessage(Component.literal("")
				.append(createLabel(Kybe.CLIENT_NAME, MAIN_COLOR))
				.append(Component.literal(" " + message).withStyle(ChatFormatting.BOLD)));
	}


	public static void clientInfoMessage(String message) {
		sendMessage(Component.literal("").append(createLabel(Kybe.MOD_ID, MAIN_COLOR))
				.append(Component.literal(" ").append(createLabel("Info", INFO_COLOR)).append(" " + message)));
	}

	public static void clientWarningMessage(String message) {
		sendMessage(Component.literal("").append(createLabel(Kybe.MOD_ID, MAIN_COLOR))
				.append(Component.literal(" ").append(createLabel("Warning", WARN_COLOR)).append(" " + message)));
	}

	public static void clientErrorMessage(String message) {
		sendMessage(Component.literal("").append(createLabel(Kybe.MOD_ID, MAIN_COLOR))
				.append(Component.literal(" ").append(createLabel("Error", ERROR_COLOR)).append(" " + message)));
	}

	public static void Message(String message) {
		sendMessage(Component.literal(message));
	}

	@SuppressWarnings("unused")
	public static void FAT_Message(String message) {
		sendMessage(Component.literal(message).withStyle(ChatFormatting.BOLD));
	}

	public static void InfoMessage(String message) {
		sendMessage(createLabel("Info", INFO_COLOR).copy().append(" ").append(Component.literal(message)));
	}

	public static void WarningMessage(String message) {
		sendMessage(createLabel("Warning", WARN_COLOR).copy().append(" ").append(Component.literal(message)));
	}

	public static void ErrorMessage(String message) {
		sendMessage(createLabel("Error", ERROR_COLOR).copy().append(" ").append(Component.literal(message)));
	}

	private static void sendMessage(Component message) {
        if (mc.player == null) return;
		mc.player.sendSystemMessage(message);
	}
}
