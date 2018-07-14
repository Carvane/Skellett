package me.limeglass.skellett;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.SkriptEvent;
import me.limeglass.skellett.utils.Utils;
import me.limeglass.skellett.utils.annotations.AllChangers;
import me.limeglass.skellett.utils.annotations.Changers;
import me.limeglass.skellett.utils.annotations.Disabled;

public class Syntax {

	private static HashMap<String, String[]> modified = new HashMap<String, String[]>();
	private static HashMap<String, String[]> completeSyntax = new HashMap<String, String[]>();

	public static String[] register(Class<?> syntaxClass, String... syntax) {
		if (syntaxClass.isAnnotationPresent(Disabled.class)) return null;
		String type = "Expressions";
		if (Condition.class.isAssignableFrom(syntaxClass)) type = "Conditions";
		else if (Effect.class.isAssignableFrom(syntaxClass)) type = "Effects";
		else if (SkriptEvent.class.isAssignableFrom(syntaxClass)) type = "Events";
		else if (PropertyExpression.class.isAssignableFrom(syntaxClass)) type = "PropertyExpressions";
		String node = "Syntax." + type + "." + syntaxClass.getSimpleName() + ".";
		if (!Skellett.getConfiguration("syntax").isSet(node + "enabled")) {
			Skellett.getConfiguration("syntax").set(node + "enabled", true);
			Skellett.save("syntax");
		}
		if (syntaxClass.isAnnotationPresent(Changers.class) || syntaxClass.isAnnotationPresent(AllChangers.class)) {
			if (syntaxClass.isAnnotationPresent(AllChangers.class)) Skellett.getConfiguration("syntax").set(node + "changers", "All changers");
			else {
				ChangeMode[] changers = syntaxClass.getAnnotation(Changers.class).value();
				Skellett.getConfiguration("syntax").set(node + "changers", Arrays.toString(changers));
			}
			Skellett.save("syntax");
		}
		if (syntaxClass.isAnnotationPresent(Description.class)) {
			String[] descriptions = syntaxClass.getAnnotation(Description.class).value();
			Skellett.getConfiguration("syntax").set(node + "description", descriptions[0]);
			Skellett.save("syntax");
		}
		if (!Skellett.getConfiguration("syntax").getBoolean(node + "enabled")) {
			if (Skellett.getInstance().getConfig().getBoolean("NotRegisteredSyntax", false)) Skellett.consoleMessage(node.toString() + " didn't register!");
			return null;
		}
		if (!Skellett.getConfiguration("syntax").isSet(node + "syntax")) {
			Skellett.getConfiguration("syntax").set(node + "syntax", syntax);
			Skellett.save("syntax");
			return add(syntaxClass.getSimpleName(), syntax);
		}
		List<String> data = Skellett.getConfiguration("syntax").getStringList(node + "syntax");
		if (!Utils.compareArrays(data.toArray(new String[data.size()]), syntax)) modified.put(syntaxClass.getSimpleName(), syntax);
		if (Skellett.getConfiguration("syntax").isList(node + "syntax")) {
			List<String> syntaxes = Skellett.getConfiguration("syntax").getStringList(node + "syntax");
			return add(syntaxClass.getSimpleName(), syntaxes.toArray(new String[syntaxes.size()]));
		}
		return add(syntaxClass.getSimpleName(), new String[]{Skellett.getConfiguration("syntax").getString(node + "syntax")});
	}
	
	public static Boolean isModified(@SuppressWarnings("rawtypes") Class syntaxClass) {
		return modified.containsKey(syntaxClass.getSimpleName());
	}
	
	public static String[] get(String syntaxClass) {
		return completeSyntax.get(syntaxClass);
	}
	
	private static String[] add(String syntaxClass, String... syntax) {
		if (!completeSyntax.containsValue(syntax)) {
			completeSyntax.put(syntaxClass, syntax);
		}
		return syntax;
	}
}