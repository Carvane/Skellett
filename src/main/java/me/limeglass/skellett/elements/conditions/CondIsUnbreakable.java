package me.limeglass.skellett.elements.conditions;

import org.bukkit.inventory.meta.ItemMeta;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.Type;
import me.limeglass.skellett.utils.annotations.Versions;

@Name("Unbreakable condition")
@Description("Tests if an itemstack has the unbreakable state or not.")
@PropertyConditionType(PropertyMode.IS_AND_ARE)
@Properties("unbreakable")
@Type("itemtypes")
@Versions("1.11.2")
public class CondIsUnbreakable extends SkellettPropertyCondition<ItemType> {
	
	public boolean check(ItemType item) {
		ItemMeta meta = (ItemMeta) item.getItemMeta();
		return (meta.isUnbreakable()) ? isNegated() : !isNegated();
	}

}
