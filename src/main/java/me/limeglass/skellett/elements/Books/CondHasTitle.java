package me.limeglass.skellett.elements.Books;

import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.Type;

@Name("Book has title")
@Description("Test if a book has a title attached to it.")
@PropertyConditionType(PropertyMode.HAS_DOES_AND_CONTAINS)
@Properties({"[book]", "[a] title"})
@Type("itemtypes")
public class CondHasTitle extends SkellettPropertyCondition<ItemType> {
	
	public boolean check(ItemType item) {
		ItemMeta meta = (ItemMeta) item.getItemMeta();
		if (!(meta instanceof BookMeta)) return !isNegated();
		return (((BookMeta) meta).hasTitle()) ? isNegated() : !isNegated();
	}

}
