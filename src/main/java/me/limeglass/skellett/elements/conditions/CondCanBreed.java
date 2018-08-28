package me.limeglass.skellett.elements.conditions;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.Type;

@Name("Entity can breed")
@Description("Check if the defined entities can breed.")
@PropertyConditionType(PropertyMode.CAN)
@Properties("[be] bre[e]d")
@Type("entities")
public class CondCanBreed extends SkellettPropertyCondition<Entity> {

	@Override
	public boolean check(Entity entity) {
		if (!(entity instanceof Ageable)) return !isNegated();
		return (((Ageable) entity).canBreed()) ? isNegated() : !isNegated();
	}

}
