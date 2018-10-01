package me.limeglass.skellett.elements.books;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import me.limeglass.skellett.lang.SkellettExpression;
import me.limeglass.skellett.utils.annotations.ExpressionProperty;
import me.limeglass.skellett.utils.annotations.Patterns;
import me.limeglass.skellett.utils.annotations.Single;

@Name("New book")
@Description("Returns a new written book itemstack. Skript makes doing this complicated and sometimes not possible. So I added this.")
@Patterns("[a] new [written] book")
@ExpressionProperty(ExpressionType.SIMPLE)
@Single
public class ExprNewBook extends SkellettExpression<ItemType> {
	
	@Override
	protected ItemType[] get(Event event) {
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
		return new ItemType[]{new ItemType(item)};
	}

}
