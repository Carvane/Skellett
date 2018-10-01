package me.limeglass.skellett.elements.books;

import org.bukkit.event.Event;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyExpression;
import me.limeglass.skellett.utils.annotations.*;

@Name("Title of book")
@Description("Grab/Set the title of a book.")
@Properties({"itemtypes", "title[s]", "{1}[(all [[of] the]|the)]"})
@Changers(ChangeMode.SET)
public class ExprBookTitle extends SkellettPropertyExpression<ItemType, String> {
	
	protected String[] get(ItemType book) {
		ItemMeta meta = (ItemMeta) book.getItemMeta();
		if (!(meta instanceof BookMeta)) return null;
		BookMeta bookmeta = (BookMeta) meta;
		return new String[]{bookmeta.getTitle()};
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		for (ItemType book : expressions.getAll(event, ItemType.class)) {
			ItemMeta meta = (ItemMeta) book.getItemMeta();
			if (delta == null || !(meta instanceof BookMeta)) return;
			BookMeta bookmeta = (BookMeta) meta;
			bookmeta.setTitle((String)delta[0]);
			book.setItemMeta(bookmeta);
		}
	}

}
