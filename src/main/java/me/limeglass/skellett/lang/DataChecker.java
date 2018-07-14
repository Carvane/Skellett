package me.limeglass.skellett.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import me.limeglass.skellett.Skellett;

public interface DataChecker {

	public default Boolean areNull(Event event, ExpressionData expressionData) {
		Expression<?>[] expressions = expressionData.getExpressions();
		if (expressions == null) return true;
		for (Expression<?> expression : expressions) {
			if (expression == null) return true;
			if (expression.isSingle() && expression.getSingle(event) == null) {
				Skellett.debugMessage("An expression was null: " + expression.toString(event, true));
				return true;
			} else if (expression.getAll(event) == null || expression.getAll(event).length == 0) {
				ArrayList<String> nulledExpressions = new ArrayList<String>();
				Arrays.stream(expressions).filter(expr -> expr != null && expr.getAll(event) != null && expr.getAll(event).length == 0).forEach(expr -> nulledExpressions.add(expr.toString(event, true)));
				Skellett.debugMessage("Expressions were null: " + nulledExpressions.toString());
				return true;
			}
		}
		return false;
	}
	
	public default <T> Boolean isNull(Event event, ExpressionData expressions, @SuppressWarnings("unchecked") Class<T>... types) {
		Map<Expression<?>, T[]> map = expressions.getAllMapOf(event, types);
		if (map == null || map.isEmpty()) return true;
		for (Entry<Expression<?>, T[]> entry : map.entrySet()) {
			if (entry.getKey() != null && entry.getKey().isSingle() && entry.getKey().getSingle(event) == null) {
				Skellett.debugMessage("An expression was null: " + entry.getKey().toString(event, true));
				return true;
			} else if (entry.getKey() != null && entry.getKey().getAll(event).length == 0 || entry.getKey().getAll(event) == null) {
				ArrayList<String> nulledExpressions = new ArrayList<String>();
				Arrays.stream(expressions.getExpressions()).filter(expr -> expr != null && expr.getAll(event).length == 0 || expr.getAll(event) == null).forEach(expr -> nulledExpressions.add(expr.toString(event, true)));
				Skellett.debugMessage("Expressions were null: " + nulledExpressions.toString());
				return true;
			}
		}
		return false;
	}
	
	public default Boolean isNull(Event event, ExpressionData expressions, int index) {
		Expression<?> expression = expressions.get(index);
		if (expression == null) return true;
		if (expression.isSingle() && expression.getSingle(event) == null) {
			Skellett.debugMessage("The expression at index " + index + " was null: " + expression.toString(event, true));
			return true;
		} else if (expression != null && expression.getAll(event).length == 0 || expression.getAll(event) == null) {
			Skellett.debugMessage("The list expression at index " + index + " was null: " + expression.toString(event, true));
			return true;
		}
		return false;
	}
	
	public default Boolean areNull(Event event, LiteralData literalData) {
		Literal<?>[] literals = literalData.getLiterals();
		if (literals == null) return true;
		for (Literal<?> literal : literals) {
			if (literal == null) return true;
			if (literal.isSingle() && literal.getSingle(event) == null) {
				Skellett.debugMessage("A literal was null: " + literal.toString(event, true));
				return true;
			} else if (literal.getAll(event) == null || literal.getAll(event).length == 0) {
				ArrayList<String> nulledLiterals = new ArrayList<String>();
				Arrays.stream(literals).filter(lit -> lit != null && lit.getAll(event) != null && lit.getAll(event).length == 0).forEach(lit -> nulledLiterals.add(lit.toString(event, true)));
				Skellett.debugMessage("Literals were null: " + nulledLiterals.toString());
				return true;
			}
		}
		return false;
	}
	
	public default <T> Boolean isNull(Event event, LiteralData literals, @SuppressWarnings("unchecked") Class<T>... types) {
		Map<Literal<?>, T[]> map = literals.getAllMapOf(event, types);
		if (map == null || map.isEmpty()) return true;
		for (Entry<Literal<?>, T[]> entry : map.entrySet()) {
			if (entry.getKey() != null && entry.getKey().isSingle() && entry.getKey().getSingle(event) == null) {
				Skellett.debugMessage("A literal was null: " + entry.getKey().toString(event, true));
				return true;
			} else if (entry.getKey() != null && entry.getKey().getAll(event).length == 0 || entry.getKey().getAll(event) == null) {
				ArrayList<String> nulledLiterals = new ArrayList<String>();
				Arrays.stream(literals.getLiterals()).filter(lit -> lit != null && lit.getAll(event).length == 0 || lit.getAll(event) == null).forEach(lit -> nulledLiterals.add(lit.toString(event, true)));
				Skellett.debugMessage("Literals were null: " + nulledLiterals.toString());
				return true;
			}
		}
		return false;
	}
	
	public default Boolean isNull(Event event, LiteralData literals, int index) {
		Literal<?> literal = literals.get(index);
		if (literal == null) return true;
		if (literal.isSingle() && literal.getSingle(event) == null) {
			Skellett.debugMessage("The literal at index " + index + " was null: " + literal.toString(event, true));
			return true;
		} else if (literal != null && literal.getAll(event).length == 0 || literal.getAll(event) == null) {
			Skellett.debugMessage("The list literal at index " + index + " was null: " + literal.toString(event, true));
			return true;
		}
		return false;
	}
}