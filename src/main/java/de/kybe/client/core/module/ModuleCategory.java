package de.kybe.client.core.module;

public enum ModuleCategory {
	CLIENT("Client", "textures/gui/client.png"),
	PLAYER("Player", "textures/gui/player.png"),
	COMBAT("Combat", "textures/gui/combat.png"),
	RENDER("Render", "textures/gui/render.png"),
	CHAT("Chat", "textures/gui/chat.png"),
	MOVEMENT("Movement", "textures/gui/movement.png"),
	WORLD("World", "textures/gui/world.png");

	private final String name;
	private final String icon;

	ModuleCategory(String name, String icon) {
		this.name = name;
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public String getIcon() {
		return icon;
	}
}
