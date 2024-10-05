package de.kybe.mixin;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleManager;
import de.kybe.client.impl.modules.render.*;
import de.kybe.client.impl.settings.BooleanSetting;
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
		NoPassiveEntityAdd noPassiveEntityAdd = (NoPassiveEntityAdd) ModuleManager.getModule("NoPassiveEntityAdd");
		if (noPassiveEntityAdd != null && noPassiveEntityAdd.getState()) {
			EntityType<?> entityType = clientboundAddEntityPacket.getType();
			BooleanSetting setting = noPassiveEntityAdd.getSettingForEntity(entityType);

			if (setting != null && setting.isToggled()) {
				ci.cancel();
			}
		}

		NoHostileEntityAdd noHostileEntityAdd = (NoHostileEntityAdd) ModuleManager.getModule("NoHostileEntityAdd");
		if (noHostileEntityAdd != null && noHostileEntityAdd.getState()) {
			EntityType<?> entityType = clientboundAddEntityPacket.getType();
			BooleanSetting setting = noHostileEntityAdd.getSettingForEntity(entityType);

			if (setting != null && setting.isToggled()) {
				ci.cancel();
			}
		}

		NoNeutralEntityAddw noNeutralEntityAdd = (NoNeutralEntityAdd) ModuleManager.getModule("NoNeutralEntityAdd");
		if (noNeutralEntityAdd != null && noNeutralEntityAdd.getState()) {
			EntityType<?> entityType = clientboundAddEntityPacket.getType();
			BooleanSetting setting = noNeutralEntityAdd.getSettingForEntity(entityType);

			if (setting != null && setting.isToggled()) {
				ci.cancel();
			}
		}

		NoProjectilesEntityAdd noProjectilesEntityAdd = (NoProjectilesEntityAdd) ModuleManager.getModule("NoProjectilesEntityAdd");
		if (noProjectilesEntityAdd != null && noProjectilesEntityAdd.getState()) {
			EntityType<?> entityType = clientboundAddEntityPacket.getType();
			BooleanSetting setting = noProjectilesEntityAdd.getSettingForEntity(entityType);

			if (setting != null && setting.isToggled()) {
				ci.cancel();
			}
		}

		NoMiscEntityAdd noMiscEntityAdd = (NoMiscEntityAdd) ModuleManager.getModule("NoMiscEntityAdd");
		if (noMiscEntityAdd != null && noMiscEntityAdd.getState()) {
			EntityType<?> entityType = clientboundAddEntityPacket.getType();
			BooleanSetting setting = noMiscEntityAdd.getSettingForEntity(entityType);

			if (setting != null && setting.isToggled()) {
				ci.cancel();
			}
		}
	}
}
