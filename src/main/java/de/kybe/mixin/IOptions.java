package de.kybe.mixin;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Options.class)
public interface IOptions {

	@Accessor("keyMappings")
	@Mutable
	void setKeyMappings(KeyMapping[] keyMappings);
}
