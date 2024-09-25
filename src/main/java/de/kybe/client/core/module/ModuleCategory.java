package de.kybe.client.core.module;

public enum ModuleCategory {
	PLAYER("Player"),
	COMBAT("Combat"),
	CHAT("Chat"),
	MOVEMENT("Movement"),
	CLIENT("Client"),
	WORLD("World"),
	RENDER("Render");

	private final String name;

	ModuleCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
