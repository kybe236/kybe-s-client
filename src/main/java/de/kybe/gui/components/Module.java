package de.kybe.gui.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Module {
	private String name;
	private boolean toggled;
	private ArrayList<Setting> settings;

	public Module(String name) {
		this.name = name;
		this.toggled = false;
		this.settings = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void tooogle() {
		toggled = !toggled;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void addSetting(Setting setting) {
		settings.add(setting);
	}

	public List<Setting> getSettings() {
		return settings;
	}
}
