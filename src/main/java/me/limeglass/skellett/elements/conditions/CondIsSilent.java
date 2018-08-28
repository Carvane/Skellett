package me.limeglass.skellett.elements.conditions;

import org.bukkit.entity.Entity;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.Type;

@Name("Silent condition")
@Description("Test if an entity is silent.")
@PropertyConditionType(PropertyMode.IS_AND_ARE)
@Properties("(silent|quiet)")
@Type("entities")
public class CondIsSilent extends SkellettPropertyCondition<Entity> {
	
	@Override
	public boolean check(Entity entity) {
		return entity.isSilent() ? isNegated() : !isNegated();
	}

}
