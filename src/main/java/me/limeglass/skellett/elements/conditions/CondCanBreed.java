package me.limeglass.skellett.elements.conditions;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Entity Breed")
@Description("Test if an entity can breed, e.g: sheep, cows or pigs.")
@Patterns("%entity% (1¦can|2¦can([ ]no|')t) [be] breed")
public class CondCanBreed extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return !isNegated();
		Entity entity = expressions.getSingle(event, Entity.class);
		return (((Ageable)entity).canBreed()) ? isNegated() : !isNegated();
	}
}