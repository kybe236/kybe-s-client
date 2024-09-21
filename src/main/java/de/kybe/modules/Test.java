package de.kybe.modules;

import de.kybe.baseModules.ToggleableModule;
import de.kybe.baseSettings.BooleanSetting;
import de.kybe.baseSettings.EnumSetting;
import de.kybe.baseSettings.NumberSetting;
import de.kybe.gui.CategoryEnum;
import de.kybe.gui.Gui;

public class Test {
	ToggleableModule test = new ToggleableModule("Test", CategoryEnum.MOVEMENT);
	ToggleableModule test2 = new ToggleableModule("Test2", CategoryEnum.MOVEMENT);
	ToggleableModule test3 = new ToggleableModule("Test3", CategoryEnum.MOVEMENT);

	BooleanSetting test4 = new BooleanSetting("Test3");

	NumberSetting<Integer> test5 = new NumberSetting<>("Test4", 0, 0, 10, 1);
	NumberSetting<Double> test6 = new NumberSetting<>("Test5", 0.0, 0.0, 10.0, 1.0);
	NumberSetting<Float> test7 = new NumberSetting<>("Test6", 0.0f, 0.0f, 10.0f, 0.1f);

	EnumSetting<TestEnum> test8 = new EnumSetting<>("Test7", TestEnum.TEST1);

	public Test() {
		this.test.addSetting(test4);

		this.test2.addSetting(test5);
		this.test2.addSetting(test6);
		this.test2.addSetting(test7);

		this.test3.addSetting(test8);

		Gui.addModule(test);
		Gui.addModule(test2);
		Gui.addModule(test3);
	}

	public enum TestEnum {
		TEST1,
		TEST2,
		TEST3
	}
}
