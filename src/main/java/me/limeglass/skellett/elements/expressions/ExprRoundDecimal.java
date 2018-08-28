package me.limeglass.skellett.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettExpression;
import me.limeglass.skellett.utils.annotations.DetermineSingle;
import me.limeglass.skellett.utils.annotations.Patterns;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Round decimal")
@Description("Returns a decimal that has been rounded by a specified number of decimal places.")
@Patterns("%numbers% round[ed] [to [the]] [nearest] %number% decimal (digit|place)[s]")
@DetermineSingle
public class ExprRoundDecimal extends SkellettExpression<Number> {

	@Override
	@Nullable
	protected Number[] get(Event event) {
		if (areNull(event)) return null;
		Number[] output = new Number[expressions.getSize(event, Number.class, 0)];
		int decimal = expressions.getInt(event, 1);
		double pow = Math.pow(10.0, decimal);
		int i = 0;
		for (Number input : expressions.getAll(event, Number.class, 0)) {
			output[i] = Math.round(input.doubleValue() * pow) / pow;
			i++;
		}
		return output;
	}

}
