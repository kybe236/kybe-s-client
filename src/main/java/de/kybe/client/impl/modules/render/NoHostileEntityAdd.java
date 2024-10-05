package de.kybe.client.impl.modules.render;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.impl.settings.BooleanSetting;
import net.minecraft.world.entity.EntityType;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class NoHostileEntityAdd extends Module {
	public static java.util.Set<EntityType<?>> HostileEntityTypes = java.util.Set.of(
			EntityType.BLAZE,
			EntityType.BOGGED,
			EntityType.BREEZE,
			EntityType.CREEPER,
			EntityType.ELDER_GUARDIAN,
			EntityType.ENDER_DRAGON,
			EntityType.ENDERMITE,
			EntityType.EVOKER,
			EntityType.GHAST,
			EntityType.GIANT,
			EntityType.GUARDIAN,
			EntityType.HOGLIN,
			EntityType.HUSK,
			EntityType.ILLUSIONER,
			EntityType.MAGMA_CUBE,
			EntityType.PHANTOM,
			EntityType.PIGLIN_BRUTE,
			EntityType.PILLAGER,
			EntityType.RAVAGER,
			EntityType.SHULKER,
			EntityType.SILVERFISH,
			EntityType.SKELETON,
			EntityType.SLIME,
			EntityType.STRAY,
			EntityType.WARDEN,
			EntityType.WITCH,
			EntityType.WITHER,
			EntityType.WITHER_SKELETON,
			EntityType.ZOGLIN,
			EntityType.ZOMBIE,
			EntityType.ZOMBIE_VILLAGER,
			EntityType.VEX,
			EntityType.VINDICATOR
	);

	private final Map<EntityType<?>, BooleanSetting> entitySettings = new HashMap<>();
	public NoHostileEntityAdd() {
		super("NoHostileEntityAdd", "Removes entity adding and with that rendering", ModuleCategory.RENDER, GLFW.GLFW_KEY_UNKNOWN);

		for (EntityType<?> entityType : HostileEntityTypes) {
			entitySettings.put(entityType, new BooleanSetting(entityType.toString(), "Removes " + entityType.toString(), false));
			this.addSettings(entitySettings.get(entityType));
		}
	}

	public BooleanSetting getSettingForEntity(EntityType<?> entityType) {
		return entitySettings.get(entityType);
	}
}
