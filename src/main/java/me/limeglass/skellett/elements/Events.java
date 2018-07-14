package me.limeglass.skellett.elements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import me.limeglass.skellett.Skellett;
import me.limeglass.skellett.utils.ReflectionUtil;

public class Events {
	
	static {
		
	}
	
	public static void registerEvent(Class<? extends Event> event, String... patterns) {
		registerEvent(null, event, patterns);
	}
	
	public static void registerEvent(@Nullable Class<? extends SkriptEvent> skriptEvent, Class<? extends Event> event, String... patterns) {
		if (skriptEvent == null) skriptEvent = SimpleEvent.class;
		for (int i = 0; i < patterns.length; i++) {
			patterns[i] = Skellett.getInstance().getNameplate() + patterns[i];
		}
		if (!Skellett.getConfiguration("syntax").isSet("Syntax.Events." + skriptEvent.getSimpleName() + ".eventvalues")) {
			Skellett.getConfiguration("syntax").set("Syntax.Events." + skriptEvent.getSimpleName() + ".eventvalues", getEventValues(event));
		}
		Skellett.save("syntax");
		if (Skellett.getConfiguration("syntax").getBoolean("Syntax.Events." + skriptEvent.getSimpleName() + ".enabled", true)) {
			//TODO find a way to make the stupid Spigot Yaml read properly for user editing of event patterns.
			Skript.registerEvent(skriptEvent.getSimpleName(), skriptEvent, event, patterns);
			Skellett.debugMessage("&5Registered Event " + skriptEvent.getSimpleName() + " (" + skriptEvent.getCanonicalName() + ") with syntax " + Arrays.toString(patterns) +
					" for " + event.getSimpleName() + " ("+ event.getCanonicalName() + ")");
		}
	}
	
	@SafeVarargs
	private final static Set<String> getEventValues(Class<? extends Event>... events) {
		Set<String> classes = new HashSet<String>();
		try {
			Method method = EventValues.class.getDeclaredMethod("getEventValuesList", int.class);
			method.setAccessible(true);
			for (Class<? extends Event> event : events) {
				for (int i = -1; i < 2; i++) {
					List<?> eventValueInfos = (List<?>) method.invoke(EventValues.class, i);
					if (eventValueInfos != null) {
						for (Object eventValueInfo : eventValueInfos) {
							Class<?> e = ReflectionUtil.getField("event", eventValueInfo.getClass(), eventValueInfo);
							if (e != null && (e.isAssignableFrom(event) || event.isAssignableFrom(e))) {
								Class<?> clazz = ReflectionUtil.getField("c", eventValueInfo.getClass(), eventValueInfo);
								if (clazz != null) classes.add(clazz.getSimpleName());
							}
						}
					}
				}
			}
		} catch (SecurityException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException error) {
			Skript.exception(error, "Error grabbing the event values of event(s) " + Arrays.toString(events));
		}
		return classes;
	}
}