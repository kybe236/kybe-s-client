package de.kybe.client.impl.modules.render;

import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.impl.settings.BooleanSetting;
import net.minecraft.world.entity.EntityType;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class NoNeutralEntityAdd extends Module {
	public static java.util.Set<EntityType<?>> NeutralEntityTypes = java.util.Set.of(
			EntityType.BEE,
			EntityType.CAVE_SPIDER,
			EntityType.DOLPHIN,
			EntityType.DROWNED,
			EntityType.ENDERMAN,
			EntityType.FOX,
			EntityType.FISHING_BOBBER,
			EntityType.GOAT,
			EntityType.IRON_GOLEM,
			EntityType.LLAMA,
			EntityType.PANDA,
			EntityType.PIGLIN,
			EntityType.POLAR_BEAR,
			EntityType.SKELETON_HORSE,
			EntityType.SPIDER,
			EntityType.WOLF,
			EntityType.ZOMBIFIED_PIGLIN
	);

	private final Map<EntityType<?>, BooleanSetting> entitySettings = new HashMap<>();
	public NoNeutralEntityAdd() {
		super("NoNeutralEntityAdd", "Removes entity adding and with that rendering", ModuleCategory.RENDER, GLFW.GLFW_KEY_UNKNOWN);

		for (EntityType<?> entityType : NeutralEntityTypes) {
			entitySettings.put(entityType, new BooleanSetting(entityType.toString(), "Removes " + entityType.toString(), false));
			this.addSettings(entitySettings.get(entityType));
		}
	}

	public BooleanSetting getSettingForEntity(EntityType<?> entityType) {
		return entitySettings.get(entityType);
	}
}
