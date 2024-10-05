package de.kybe.client.impl.modules.render;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.impl.settings.BooleanSetting;
import net.minecraft.world.entity.EntityType;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class NoProjectilesEntityAdd extends Module {
	public static java.util.Set<EntityType<?>> ProjectilesEntityTypes = java.util.Set.of(
			EntityType.ARROW,
			EntityType.DRAGON_FIREBALL,
			EntityType.EGG,
			EntityType.ENDER_PEARL,
			EntityType.EXPERIENCE_BOTTLE,
			EntityType.EXPERIENCE_ORB,
			EntityType.EYE_OF_ENDER,
			EntityType.FIREWORK_ROCKET,
			EntityType.FIREBALL,
			EntityType.LLAMA_SPIT,
			EntityType.POTION,
			EntityType.SHULKER_BULLET,
			EntityType.SMALL_FIREBALL,
			EntityType.SNOWBALL,
			EntityType.SPECTRAL_ARROW,
			EntityType.TRIDENT,
			EntityType.WIND_CHARGE

	);

	private final Map<EntityType<?>, BooleanSetting> entitySettings = new HashMap<>();
	public NoProjectilesEntityAdd() {
		super("NoProjectilesEntityAdd", "Prevents projectiles from being added to the world", ModuleCategory.RENDER, GLFW.GLFW_KEY_UNKNOWN);

		for (EntityType<?> entityType : ProjectilesEntityTypes) {
			entitySettings.put(entityType, new BooleanSetting(entityType.toString(), "Removes " + entityType.toString(), false));
			this.addSettings(entitySettings.get(entityType));
		}
	}

	public BooleanSetting getSettingForEntity(EntityType<?> entityType) {
		return entitySettings.get(entityType);
	}
}
