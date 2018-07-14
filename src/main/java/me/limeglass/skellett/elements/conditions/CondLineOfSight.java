package me.limeglass.skellett.elements.conditions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Line of sight")
@Description("Tests if a an entity can see another entity, this is from Spigot and it's not always consistent for some reason.")
@Patterns("%entity% (1¦can|2¦can([ ]no|')t) [visibly] see %entity%")
public class CondLineOfSight extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return !isNegated();
		LivingEntity viewer = expressions.getSingle(event, LivingEntity.class, 0);
		LivingEntity entity = expressions.getSingle(event, LivingEntity.class, 1);
		return (viewer.hasLineOfSight(entity)) ? isNegated() : !isNegated();
	}
}