package de.kybe.eventBus;

import de.kybe.Kybe;
import de.kybe.eventBus.events.BaseEvent;
import de.kybe.modules.Test;
import de.kybe.modules.misc.Gui;
import de.kybe.modules.movement.DoubleJump;
import de.kybe.modules.render.CrystalSpin;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EventBus {
	public static<T extends BaseEvent> void broadcast(T event, Execution execution) {
		String packetName = "de.kybe.modules";
		try {
			List<Class<?>> classes = new ArrayList<>();
			classes.add(Gui.class);
			classes.add(Test.class);
			classes.add(DoubleJump.class);
			classes.add(CrystalSpin.class);

			for (Class<?> clazz : classes) {
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(Subscribe.class)) {
						if (Execution.ALL != method.getAnnotation(Subscribe.class).execution()) {
							if (execution != method.getAnnotation(Subscribe.class).execution()) continue;
						}
						Class<?>[] paramTypes = method.getParameterTypes();

						if (paramTypes.length == 1 && paramTypes[0].isAssignableFrom(event.getClass()) && Modifier.isStatic(method.getModifiers())) {
							method.invoke(null, event);
						}
					}
				}
			}
		} catch (Exception e) {
			Kybe.LOGGER.info(e.getMessage());
			//noinspection CallToPrintStackTrace
			e.printStackTrace();
		}

	}

	private static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace(".", "/");
		URL resource = classLoader.getResource(path);
		if (resource == null) {
			throw new ClassNotFoundException("Package " + packageName + " not found");
		}

		File directory = new File(resource.getFile());
		if (!directory.exists()) {
			throw new ClassNotFoundException("Package " + packageName + " does not exist as a directory");
		}

		return findClasses(directory, packageName);
	}

	public static List<Class<?>> findClasses(File directory, String packetName) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<>();
		if (!directory.exists()) {
			return classes;
		}

		File[] files = directory.listFiles();
		if (files == null) {
			return classes;
		}

		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClasses(file, packetName + "." + file.getName()));
			} else if(file.getName().endsWith(".class")) {
				String className = packetName + "." + file.getName().substring(0, file.getName().length() - 6);
				classes.add(Class.forName(className));
			}
		}
		return classes;
	}
}
