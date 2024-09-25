package de.kybe.client.core.event;

import de.kybe.client.core.event.events.BaseEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
	private static final Map<Class<?>, List<Object>> moduleInstances = new ConcurrentHashMap<>();

	public static void register(Object module) {
		Class<?> clazz = module.getClass();

		moduleInstances.computeIfAbsent(clazz, k -> new CopyOnWriteArrayList<>()).add(module);
	}

	public static synchronized void unregister(Object module) {
		Class<?> clazz = module.getClass();
		List<Object> instances = moduleInstances.get(clazz);

		if (instances != null) {
			instances.remove(module);
		}
	}

	public static <T extends BaseEvent> void broadcast(T event, Execution execution) {
		Map<Class<?>, List<Object>> tempInstances = new HashMap<>(moduleInstances);

		try {
			for (Map.Entry<Class<?>, List<Object>> entry : tempInstances.entrySet()) {
				Class<?> clazz = entry.getKey();
				List<Object> instances = entry.getValue();

				if (instances == null) {
					continue;
				}

				for (Object instance : instances) {
					Method[] methods = clazz.getDeclaredMethods();

					for (Method method : methods) {
						if (method.isAnnotationPresent(Subscribe.class) && method.getParameterCount() == 1 && method.getParameterTypes()[0].isAssignableFrom(event.getClass()) && Modifier.isPublic(method.getModifiers())) {
							Subscribe sub = method.getAnnotation(Subscribe.class);

							if (sub.execution() == execution) {
								method.invoke(instance, event);
							}
						}
					}
				}
			}
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}