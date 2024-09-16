package de.kybe.gui.components;

import java.util.ArrayList;
import java.util.List;

public class Catagory {
	private String name;
	private List<Module> modules;

	public Catagory(String name) {
		this.name = name;
		this.modules = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void addModule(Module module) {
		modules.add(module);
	}

	public List<Module> getModules() {
		return modules;
	}
}
