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

@Name("Book has author")
@Description("Test if a book has an author attached to it.")
@PropertyConditionType(PropertyMode.HAS_DOES_AND_CONTAINS)
@Properties({"[book]", "[a[n]] author"})
@Type("itemtypes")
public class CondHasAuthor extends SkellettPropertyCondition<ItemType> {

	@Override
	public boolean check(ItemType item) {
		ItemMeta meta = (ItemMeta) item.getItemMeta();
		if (!(meta instanceof BookMeta)) return !isNegated();
		return (((BookMeta) meta).hasAuthor()) ? isNegated() : !isNegated();
	}

}
