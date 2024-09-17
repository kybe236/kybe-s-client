package de.kybe.gui.components.modules;

import de.kybe.gui.components.CategoryEnum;
import org.lwjgl.glfw.GLFW;

public class ToggleableModule extends Module{
	private boolean toggled = false;

	public ToggleableModule(String name, CategoryEnum catagory) {
		super(name, catagory);
	}

	public void tooogle() {
		toggled = !toggled;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	@Override
	public boolean handleKeyPress(int key) {
		if (key == GLFW.GLFW_KEY_ENTER) {
			tooogle();
			return true;
		}
		return false;
	}
}
