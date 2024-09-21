package de.kybe.mixin;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyMapping.class)
public interface IKeyMapping {
	@Accessor("CATEGORY_SORT_ORDER")
	void setCategorySortOrder(Map<String, Integer> categorySortOrder);

	@Accessor("CATEGORY_SORT_ORDER")
	Map<String, Integer> getCategorySortOrder();
}
