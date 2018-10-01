package me.limeglass.skellett.elements.books;

import java.util.ArrayList;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettExpression;
import me.limeglass.skellett.utils.annotations.Changers;
import me.limeglass.skellett.utils.annotations.Patterns;
import me.limeglass.skellett.utils.annotations.Single;

@Name("Page of book")
@Description("Grab/Set data of a page in a book.")
@Patterns({"[the] [book['s]] page %number% (of|in) [book] %itemtype%", "%itemtype%'s [book] page %number%"})
@Changers({ChangeMode.REMOVE, ChangeMode.SET, ChangeMode.DELETE, ChangeMode.RESET})
@Single
public class ExprBookPage extends SkellettExpression<String> {
	
	@Override
	protected String[] get(Event event) {
		if (areNull(event)) return null;
		BookMeta book = (BookMeta) expressions.getSingle(event, ItemType.class).getItemMeta();
		return new String[]{book.getPage(expressions.getInt(event))};
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (areNull(event)) return;
		ItemStack book = expressions.getSingle(event, ItemStack.class);
		if (!(book instanceof BookMeta)) return;
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		Integer num = expressions.getInt(event);
		ArrayList<String> pages = new ArrayList<String>(bookMeta.getPages());
		if (mode == ChangeMode.DELETE || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET) {
			if (pages.size() < num) return;
			pages.remove(num - 1);
			bookMeta.setPages(pages);
		} else {
			if (pages.size() < num) while (bookMeta.getPages().size() < num) bookMeta.addPage("");
			bookMeta.setPage(num, (String)delta[0]);
		}
		book.setItemMeta(bookMeta);
	}

}
