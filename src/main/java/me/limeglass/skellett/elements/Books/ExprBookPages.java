package me.limeglass.skellett.elements.Books;

import org.bukkit.event.Event;
import org.bukkit.inventory.meta.BookMeta;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import me.limeglass.skellett.lang.SkellettExpression;
import me.limeglass.skellett.utils.annotations.Changers;
import me.limeglass.skellett.utils.annotations.ExpressionProperty;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Pages of book")
@Description("Grab/Set all data from the pages in a book.")
@Patterns("[(the|all)] [of] [the] [book['s]] pages [(from|of)] [book] %itemtype%")
@ExpressionProperty(ExpressionType.SIMPLE)
@Changers(ChangeMode.SET)
public class ExprBookPages extends SkellettExpression<String> {
	
	@Override
	protected String[] get(Event event) {
		if (areNull(event)) return null;
		BookMeta book = (BookMeta) expressions.getSingle(event, ItemType.class).getItemMeta();
		return book.getPages().toArray(new String[book.getPages().size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (areNull(event)) return;
		ItemType book = expressions.getSingle(event, ItemType.class);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		bookMeta.setPages((String)delta[0]);
		book.setItemMeta(bookMeta);
	}

}
