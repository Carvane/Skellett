package me.limeglass.skellett.elements.expressions;

import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyExpression;
import me.limeglass.skellett.utils.annotations.Properties;

@Name("Absolute value")
@Description("Returns the absolute value of a number.")
@Properties({"numbers", "absolute [value]"})
public class ExprAbsoluteValue extends SkellettPropertyExpression<Number, Number> {
	
	@Override
	protected Number[] get(Event event, Number[] numbers) {
		for (Number number : numbers) {
			collection.add(Math.abs(number.doubleValue()));
		}
		return collection.toArray(new Number[collection.size()]);
	}

}
