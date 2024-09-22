package de.kybe.eventBus;

import de.kybe.Kybe;
import de.kybe.eventBus.events.BaseEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
	private static final Map<Class<?>, List<Object>> moduleInstances = new HashMap<>();

	public static void register(Object module) {
		Class<?> clazz = module.getClass();

		moduleInstances.computeIfAbsent(clazz, k -> new ArrayList<>()).add(module);
		Kybe.LOGGER.info("Registered module: {}", clazz.getSimpleName());
	}

	public static <T extends BaseEvent> void broadcast(T event, Execution execution) {
		try {
			for (Map.Entry<Class<?>, List<Object>> entry : moduleInstances.entrySet()) {
				Class<?> clazz = entry.getKey();
				List<Object> instances = entry.getValue();

				for (Object instance : instances) {
					Method[] methods = clazz.getDeclaredMethods();
					for (Method method : methods) {
						if (method.isAnnotationPresent(Subscribe.class)) {
							if (Execution.ALL != method.getAnnotation(Subscribe.class).execution()) {
								if (execution != method.getAnnotation(Subscribe.class).execution()) continue;
							}

							Class<?>[] paramTypes = method.getParameterTypes();
							if (paramTypes.length == 1 && paramTypes[0].isAssignableFrom(event.getClass())) {
								if (Modifier.isStatic(method.getModifiers())) {
									method.invoke(null, event);
								} else {
									method.invoke(instance, event);
								}
							}
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
}