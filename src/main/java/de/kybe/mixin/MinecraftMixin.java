package de.kybe.mixin;

import de.kybe.eventBus.EventBus;
import de.kybe.eventBus.Execution;
import de.kybe.eventBus.events.EventTick;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	public void onStartTick(CallbackInfo ci) {
		EventTick event = new EventTick();
		EventBus.broadcast(event, Execution.PRE);
	}

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	public void oneEndTick(CallbackInfo ci) {
		EventTick event = new EventTick();
		EventBus.broadcast(event, Execution.POST);
	}
}
