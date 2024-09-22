package de.kybe.mixin;

import de.kybe.Kybe;
import de.kybe.baseCommands.CommandManager;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static de.kybe.constants.Globals.mc;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    @Inject(at = @At("HEAD"), method = "handleChatInput", cancellable = true)
    private void onSendChatMessage(String s, boolean b, CallbackInfo ci) {
        if(s.startsWith(Kybe.PREFIX) && s.length() > 1) {
            mc.gui.getChat().addRecentChat(s);
            CommandManager.executeCommand(s);
            ci.cancel();
        }
    }
}