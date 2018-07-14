package me.limeglass.skellett.elements.conditions;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Event cancelled")
@Description("Test if an event is cancelled or not.")
@Patterns("[(the|this)] event (1¦is|2¦is(n't| not)) cancelled")
public class CondEventCancelled extends SkellettCondition {
	
	public boolean check(Event event) {
		if (!(event instanceof Cancellable)) return !isNegated();
		return (((Cancellable) event).isCancelled()) ? isNegated() : !isNegated();
	}
}