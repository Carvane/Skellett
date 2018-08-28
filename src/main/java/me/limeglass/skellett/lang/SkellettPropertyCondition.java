package me.limeglass.skellett.lang;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Checker;
import ch.njol.util.Kleenean;

public abstract class SkellettPropertyCondition<T> extends Condition implements Checker<T> {

	public enum PropertyMode {
		CAN(1),
		IS_AND_ARE(2),
		IS_AND_HAS(3),
		IS_AND_WITHIN(4),
		HAS_AND_DOES_HAVE(5),
		HAS_DOES_AND_CONTAINS(6);
		
		int mode;
		
		private PropertyMode(int mode) {
			this.mode = mode;
		}
		
		public int getMode() {
			return mode;
		}
	
	}
	
	private Expression<? extends T> expression;
	private static String propertyName;
	
	public static void register(Class<? extends Condition> condition, String property, String type, PropertyMode mode) {
		String[] patterns = {"%" + type + "% (is|are) " + property, "%" + type + "% (is|are)(n't| not) " + property};
		switch (mode) {
			case CAN:
				patterns = new String[]{"%" + type + "% can " + property, "%" + type + "% can([ ]no|')t " + property};
				break;
			case IS_AND_ARE:
				break;
			case IS_AND_HAS:
				patterns = new String[]{"%" + type + "% (is|are|has) " + property, "%" + type + "% (is|are|has)(n't| not) " + property};
				break;
			case IS_AND_WITHIN:
				patterns = new String[]{"%" + type + "% (is|are) [with]in" + property, "%" + type + "% (is|are)(n't| not) [with]in" + property};
				break;
			case HAS_AND_DOES_HAVE:
				patterns = new String[]{"%" + type + "% (ha(s [had]|d|ve)|do[es] [have]) " + property, "%" + type + "% (ha(s|v)([n't]| not [got [any]])|do[es](n't| not) have) " + property};
				break;
			case HAS_DOES_AND_CONTAINS:
				patterns = new String[]{"%" + type + "% (ha(s [had]|d|ve)|do[es] [have]|contains) " + property, "%" + type + "% (ha(s|v)([n't]| not [got [any]])|do[es](n't| not) (contain|have)) " + property};
				break;
		}
		propertyName = property;
		Skript.registerCondition(condition, patterns);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		expression = (Expression<? extends T>) exprs[0];
		setNegated(matchedPattern == 1);
		return true;
	}
	
	@Override
	public final boolean check(final Event event) {
		return expression.check(event, this, isNegated());
	}
	
	@Override
	public abstract boolean check(T t);
	
	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return expression.toString(e, debug) + (expression.isSingle() ? " is " : " are ") + (isNegated() ? "not " : "") + propertyName;
	}

}
