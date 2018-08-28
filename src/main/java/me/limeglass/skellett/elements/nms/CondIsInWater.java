package me.limeglass.skellett.elements.nms;

import org.bukkit.entity.Entity;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.utils.ReflectionUtil;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.Type;

@Name("In water")
@Description("Tests if an entity is in water or not.")
@PropertyConditionType(PropertyMode.IS_AND_WITHIN)
@Properties("in water")
@Type("entities")
public class CondIsInWater extends SkellettPropertyCondition<Entity> {
	
	public boolean check(Entity entity) {
		Object nmsEntity = ReflectionUtil.getHandle(entity);
		try {
			return nmsEntity.getClass().getField("inWater").getBoolean(nmsEntity) ? isNegated() : !isNegated();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			Skript.error("Failed to grab the inWater field from an Entity.");
		}
		return false;
	}

}
