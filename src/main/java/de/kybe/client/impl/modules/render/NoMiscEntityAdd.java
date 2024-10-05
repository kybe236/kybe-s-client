package de.kybe.client.impl.modules.render;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.impl.settings.BooleanSetting;
import net.minecraft.world.entity.EntityType;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class NoMiscEntityAdd extends Module {
	public static java.util.Set<EntityType<?>> MiscEntityTypes = java.util.Set.of(
			EntityType.AREA_EFFECT_CLOUD,
			EntityType.ARMOR_STAND,
			EntityType.BLOCK_DISPLAY,
			EntityType.BOAT,
			EntityType.CHEST_BOAT,
			EntityType.COMMAND_BLOCK_MINECART,
			EntityType.END_CRYSTAL,
			EntityType.EVOKER_FANGS,
			EntityType.FALLING_BLOCK,
			EntityType.FURNACE_MINECART,
			EntityType.GLOW_ITEM_FRAME,
			EntityType.HOPPER_MINECART,
			EntityType.INTERACTION,
			EntityType.ITEM,
			EntityType.ITEM_DISPLAY,
			EntityType.ITEM_FRAME,
			EntityType.LEASH_KNOT,
			EntityType.LIGHTNING_BOLT,
			EntityType.MARKER,
			EntityType.MINECART,
			EntityType.PAINTING,
			EntityType.SPAWNER_MINECART,
			EntityType.TNT,
			EntityType.TNT_MINECART,
			EntityType.WITHER_SKULL,
			EntityType.PLAYER
	);

	private final Map<EntityType<?>, BooleanSetting> entitySettings = new HashMap<>();
	public NoMiscEntityAdd() {
		super("NoMiscEntityAdd", "Removes entity adding and with that rendering", ModuleCategory.RENDER, GLFW.GLFW_KEY_UNKNOWN);

		for (EntityType<?> entityType : MiscEntityTypes) {
			entitySettings.put(entityType, new BooleanSetting(entityType.toString(), "Removes " + entityType.toString(), false));
			this.addSettings(entitySettings.get(entityType));
		}
	}

	public BooleanSetting getSettingForEntity(EntityType<?> entityType) {
		return entitySettings.get(entityType);
	}
}
