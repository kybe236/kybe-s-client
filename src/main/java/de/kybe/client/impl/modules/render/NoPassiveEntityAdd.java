package de.kybe.client.impl.modules.render;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.impl.settings.BooleanSetting;
import net.minecraft.world.entity.EntityType;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class NoPassiveEntityAdd extends Module {
	public static java.util.Set<EntityType<?>> PassiveEntityTypes = java.util.Set.of(
			EntityType.ALLAY,
			EntityType.AXOLOTL,
			EntityType.BAT,
			EntityType.CAMEL,
			EntityType.CAT,
			EntityType.CHICKEN,
			EntityType.COD,
			EntityType.COW,
			EntityType.DONKEY,
			EntityType.DOLPHIN,
			EntityType.FROG,
			EntityType.GLOW_SQUID,
			EntityType.HORSE,
			EntityType.MOOSHROOM,
			EntityType.MULE,
			EntityType.OCELOT,
			EntityType.PARROT,
			EntityType.PIG,
			EntityType.PUFFERFISH,
			EntityType.RABBIT,
			EntityType.SALMON,
			EntityType.SHEEP,
			EntityType.SNIFFER,
			EntityType.SNOW_GOLEM,
			EntityType.SQUID,
			EntityType.STRIDER,
			EntityType.TADPOLE,
			EntityType.TRADER_LLAMA,
			EntityType.TROPICAL_FISH,
			EntityType.TURTLE,
			EntityType.VILLAGER,
			EntityType.WANDERING_TRADER,
			EntityType.ZOMBIE_HORSE
	);

	private final Map<EntityType<?>, BooleanSetting> entitySettings = new HashMap<>();
	public NoPassiveEntityAdd() {
		super("NoPassiveEntityAdd", "Removes entity adding and with that rendering", ModuleCategory.RENDER, GLFW.GLFW_KEY_UNKNOWN);

		for (EntityType<?> entityType : PassiveEntityTypes) {
			entitySettings.put(entityType, new BooleanSetting(entityType.toString(), "Removes " + entityType.toString(), false));
			this.addSettings(entitySettings.get(entityType));
		}
	}

	public BooleanSetting getSettingForEntity(EntityType<?> entityType) {
		return entitySettings.get(entityType);
	}
}
