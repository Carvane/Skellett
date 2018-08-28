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
import me.limeglass.skellett.utils.annotations.Versions;

@Name("Book has generation")
@Description("Test if a book has a generation nbt attached to it. Generation is if the book is the original state or a copy of it.")
@PropertyConditionType(PropertyMode.HAS_DOES_AND_CONTAINS)
@Properties({"[book]", "[a] generation"})
@Versions("1.9R1")
@Type("itemtypes")
public class CondHasGeneration extends SkellettPropertyCondition<ItemType> {
	
	@Override
	public boolean check(ItemType item) {
		ItemMeta meta = (ItemMeta) item.getItemMeta();
		if (!(meta instanceof BookMeta)) return !isNegated();
		return (((BookMeta) meta).hasGeneration()) ? isNegated() : !isNegated();
	}

}
