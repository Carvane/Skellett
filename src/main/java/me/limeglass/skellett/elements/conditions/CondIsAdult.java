package me.limeglass.skellett.elements.conditions;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.Type;

@Name("Adult condition")
@Description("Test if an entity is an Adult stage, if it's a Zombie it will test if the zombie is a baby or not.")
@PropertyConditionType(PropertyMode.IS_AND_ARE)
@Properties("[a[n]] adult")
@Type("entities")
public class CondIsAdult extends SkellettPropertyCondition<Entity> {
	
	@Override
	public boolean check(Entity entity) {
		if (entity instanceof Ageable) {
			return (((Ageable)entity).isAdult()) ? isNegated() : !isNegated();
		} else if (entity instanceof Zombie) {
			return (((Zombie)entity).isBaby()) ? isNegated() : !isNegated();
		}
		return false;
	}

}
