package me.limeglass.skellett.elements.conditions;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Adult condition")
@Description("Test if an entity is an Adult stage, if it's a Zombie it will test if the zombie is a baby or not.")
@Patterns("%entity% (1¦is|2¦is(n't| not)) [a[n]] adult")
public class CondIsAdult extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return !isNegated();
		Entity entity = expressions.getSingle(event, Entity.class);
		if (entity instanceof Ageable) {
			return (((Ageable)entity).isAdult()) ? isNegated() : !isNegated();
		} else if (entity instanceof Zombie) {
			return (((Zombie)entity).isBaby()) ? isNegated() : !isNegated();
		}
		return false;
	}
}