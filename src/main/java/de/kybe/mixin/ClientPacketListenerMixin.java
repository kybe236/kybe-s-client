package de.kybe.mixin;

import de.kybe.client.core.module.ModuleManager;
import de.kybe.client.impl.modules.render.CrystalSpin;
import de.kybe.client.impl.modules.render.NoItems;
import de.kybe.client.impl.modules.render.NoSnowball;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

	@Inject(
			method = "handleAddEntity",
			at = @At(
					value = "HEAD"
			),
			cancellable = true
	)
	private void handleAddEntity(ClientboundAddEntityPacket clientboundAddEntityPacket, CallbackInfo ci) {
		NoSnowball noSnowball = (NoSnowball) ModuleManager.getModule("NoSnowball");
		NoItems noItems = (NoItems) ModuleManager.getModule("NoItem");

		if (clientboundAddEntityPacket.getType() == EntityType.ITEM && noItems.getState()) {
			ci.cancel();
		}

		if (clientboundAddEntityPacket.getType() == EntityType.SNOWBALL && noSnowball.getState()) {
			ci.cancel();
		}
	}
}
