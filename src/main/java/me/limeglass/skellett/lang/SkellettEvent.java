package me.limeglass.skellett.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.event.Event;
import java.lang.reflect.ParameterizedType;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import me.limeglass.skellett.Skellett;
import me.limeglass.skellett.Syntax;

public abstract class SkellettEvent<T extends Event> extends SkriptEvent implements DataChecker {

	private final List<Object> values = new ArrayList<Object>();
	protected final Set<T> collection = new HashSet<T>();
	protected Class<? extends Event> event;
	protected LiteralData literals;
	protected ParseResult parser;
	protected int patternMark;
	
	protected String[] getSyntax() {
		return Syntax.get(getClass().getSimpleName());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Literal<?>[] arguments, int matchedPattern, ParseResult parser) {
		if (arguments == null || arguments.length == 0) return true;
		event = (Class<? extends Event>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.literals = new LiteralData(arguments, getSyntax()[0]);
		this.patternMark = parser.mark;
		this.parser = parser;
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		String modSyntax = Syntax.isModified(getClass()) ? "Modified syntax: " + Arrays.toString(getSyntax()) : Arrays.toString(getSyntax());
		if (literals != null && event != null) for (Literal<?> literal : literals.getLiterals()) values.add(literal.getSingle(event));
		if (event != null) Skellett.debugMessage(getClass().getSimpleName() + " - " + modSyntax + " (" + event.getEventName() + ")" + " Data: " + Arrays.toString(values.toArray()));
		return getClass().getSimpleName() + " - " + Arrays.toString(getSyntax());
	}
	
	protected Boolean isNull(Event event, @SuppressWarnings("unchecked") Class<T>... types) {
		return isNull(event, literals, types);
	}
	
	protected Boolean isNull(Event event, int... index) {
		for (int i : index) {
			if (isNull(event, literals, i)) return true;
		}
		return false;
	}

	protected Boolean areNull(Event event) {
		return areNull(event, literals);
	}
}