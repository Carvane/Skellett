package me.limeglass.skellett.elements;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptEvent;
import me.limeglass.skellett.Metrics;
import me.limeglass.skellett.Syntax;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.Skellett;
import me.limeglass.skellett.utils.EnumClassInfo;
import me.limeglass.skellett.utils.ReflectionUtil;
import me.limeglass.skellett.utils.TypeClassInfo;
import me.limeglass.skellett.utils.annotations.Disabled;
import me.limeglass.skellett.utils.annotations.ExpressionProperty;
import me.limeglass.skellett.utils.annotations.Patterns;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertiesAddition;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.RegisterEnum;
import me.limeglass.skellett.utils.annotations.RegisterType;
import me.limeglass.skellett.utils.annotations.Type;
import me.limeglass.skellett.utils.annotations.User;
import me.limeglass.skellett.utils.annotations.Versions;
import me.limeglass.skellett.utils.packets.PacketListener;

public class Register {
	
	private static Set<Class<?>> classes = new HashSet<>();
	private static JarFile addon;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void register() {
		PacketListener.register();
		try {
			Method method = JavaPlugin.class.getDeclaredMethod("getFile");
			method.setAccessible(true);
			File file = (File) method.invoke(Skellett.getInstance());
			addon = new JarFile(file);
			c : for (Enumeration<JarEntry> jarEntry = addon.entries(); jarEntry.hasMoreElements();) {
				String name = jarEntry.nextElement().getName().replace("/", ".");
				if (name.length() < 6) continue c;
				String className = name.substring(0, name.length() - 6);
				className = className.replace('/', '.');
				if (name.startsWith(Skellett.getInstance().getPackageName()) && name.endsWith(".class")) {
					classes.add(Class.forName(className));
				}
			}
			addon.close();
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		run : for (Class clazz : classes) {
			if (!clazz.isAnnotationPresent(Disabled.class)) {
				String[] syntax = null;
				ExpressionType type = ExpressionType.COMBINED;
				if (clazz.isAnnotationPresent(Versions.class)) {
					String server = ReflectionUtil.getVersion();
					boolean cancel = true;
					for (String input : ((Versions)clazz.getAnnotation(Versions.class)).value()) {
						if (server.contains(input)) {
							cancel = false;
						}
					}
					if (cancel) {
						List<String> versions = Skellett.getConfiguration("config").getStringList("versions");
						int spot = 0;
						for (String input : ((Versions)clazz.getAnnotation(Versions.class)).value()) {
							for (String version : versions) {
								if (input.equals(version) && spot + 1 <= versions.size() - 1) {
									for (int i = spot + 1; i <= versions.size() - 1; i++) {
										if (server.contains(versions.get(i))) {
											cancel = false;
										}
									}
								}
								spot++;
							}
						}
						if (cancel) continue run;
					}
				}
				if (clazz.isAnnotationPresent(Patterns.class)) {
					syntax = Syntax.register(clazz, ((Patterns)clazz.getAnnotation(Patterns.class)).value());
				} else if (PropertyExpression.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(Properties.class)) {
					type = ExpressionType.PROPERTY;
					String[] properties = ((Properties)clazz.getAnnotation(Properties.class)).value();
					String additions = (clazz.isAnnotationPresent(PropertiesAddition.class)) ? " " + ((PropertiesAddition) clazz.getAnnotation(PropertiesAddition.class)).value() + " " : " ";
					String input1 = "[the] ", input2 = "";
					if (properties.length > 2 && properties[2] != null) {
						int var = Integer.parseInt(properties[2].substring(1, 2));
						if (var == 1) input1 = properties[2].substring(3, properties[2].length());
						else input2 = properties[2].substring(3, properties[2].length());
					}
					String[] values = new String[]{Skellett.getInstance().getNameplate() + input1 + " " + properties[1] + " (of|from)" + additions + "%" + properties[0] + "%", Skellett.getInstance().getNameplate() + input2 + "%" + properties[0] + "%['s]"  + additions.replace("[the] ", "") + properties[1]};
					syntax = Syntax.register(clazz, values);
					if (syntax == null) Skellett.debugMessage("&cThere was an issue registering the syntax for " + clazz.getName() + ". Make sure that the SyntaxToggles.yml is set for this syntax.");
				} else {
					continue run;
				}
				if (clazz.isAnnotationPresent(RegisterEnum.class)) {
					try {
						String user = null;
						String enumType = ((RegisterEnum) clazz.getAnnotation(RegisterEnum.class)).value();
						Class returnType = ((RegisterEnum) clazz.getAnnotation(RegisterEnum.class)).ExprClass();
						if (returnType.equals(String.class)) returnType = ((Expression<?>) clazz.newInstance()).getReturnType();
						if (clazz.isAnnotationPresent(User.class)) user = ((User) clazz.getAnnotation(User.class)).value();
						EnumClassInfo.create(returnType, enumType, user).register();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				if (clazz.isAnnotationPresent(RegisterType.class)) {
					try {
						String typeName = ((RegisterType) clazz.getAnnotation(RegisterType.class)).value();
						Class<?> returnType = ((RegisterType) clazz.getAnnotation(RegisterType.class)).ExprClass();
						if (returnType.equals(String.class)) returnType = ((Expression<?>) clazz.newInstance()).getReturnType();
						TypeClassInfo.create(returnType, typeName).register();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				if (syntax != null) {
					if (Effect.class.isAssignableFrom(clazz)) {
						Skript.registerEffect(clazz, syntax);
						Skellett.debugMessage("&5Registered Effect " + clazz.getSimpleName() + " (" + clazz.getCanonicalName() + ") with syntax " + Arrays.toString(syntax));
					} else if (Condition.class.isAssignableFrom(clazz)) {
						if (SkellettPropertyCondition.class.isAssignableFrom(clazz)) {
							if (clazz.isAnnotationPresent(PropertyConditionType.class) && clazz.isAnnotationPresent(Type.class) && clazz.isAnnotationPresent(Properties.class)) {
								PropertyMode mode = ((PropertyConditionType)clazz.getAnnotation(PropertyConditionType.class)).value();
								String[] properties = ((Properties)clazz.getAnnotation(Properties.class)).value();
								String property = properties.length > 1 ? properties[1] : properties[0];
								String extraProperty = properties.length > 1 ? properties[0] : "";
								String propertyType = ((Type)clazz.getAnnotation(Type.class)).value();
								SkellettPropertyCondition.register(clazz, property, extraProperty + " " + propertyType, mode);
								Skellett.debugMessage("&5Registered PropertyCondition " + clazz.getSimpleName() + " (" + clazz.getCanonicalName() + ")");
							} else {
								Skellett.consoleMessage("There were no properties or types for the PropertyCondition " + " (" + clazz.getCanonicalName() + ")");
								continue run;
							}
						}
						Skript.registerCondition(clazz, syntax);
						Skellett.debugMessage("&5Registered Condition " + clazz.getSimpleName() + " (" + clazz.getCanonicalName() + ") with syntax " + Arrays.toString(syntax));
					} else if (SkriptEvent.class.isAssignableFrom(clazz)) {
						Events.registerEvent(clazz, ((Class<? extends Event>)((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0]), syntax);
					} else if (Expression.class.isAssignableFrom(clazz)) {
						if (clazz.isAnnotationPresent(ExpressionProperty.class)) type = ((ExpressionProperty) clazz.getAnnotation(ExpressionProperty.class)).value();
						try {
							Skript.registerExpression(clazz, ((Expression<?>) clazz.newInstance()).getReturnType(), type, syntax);
							Skellett.debugMessage("&5Registered Expression " + type.toString() + " " + clazz.getSimpleName() + " (" + clazz.getCanonicalName() + ") with syntax " + Arrays.toString(syntax));
						} catch (IllegalAccessException | IllegalArgumentException | InstantiationException e) {
							Skellett.consoleMessage("&cFailed to register expression " + clazz.getCanonicalName());
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public static void metrics(Metrics metrics) {
		metrics.addCustomChart(new Metrics.SimplePie("skript_version") {
			@Override
			public String getValue() {
				return Skript.getVersion().toString();
			}
		});
		Skellett.debugMessage("Metrics registered!");
	}

}
