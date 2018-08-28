package me.limeglass.skellett.elements.Books;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettEffect;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Add book page")
@Description("Add a string page to a book.")
@Patterns("add [a] page [with] [(text|data)] [%-string%] to [book[s]] %itemtypes%")
public class EffAddPage extends SkellettEffect {

	@Override
	protected void execute(Event event) {
		String page = expressions.getSingle(event, String.class);
		for (ItemType book : expressions.getAll(event, ItemType.class)) {
			ItemMeta meta = (ItemMeta) book.getItemMeta();
			Material material = book.getRandom().getType();
			if (book.getItemMeta() instanceof BookMeta && material == Material.BOOK_AND_QUILL || material == Material.WRITTEN_BOOK) {
				BookMeta bookMeta = (BookMeta) meta;
				if (page != null) {
					bookMeta.addPage();
				} else {
					bookMeta.addPage(page);
				}
				book.setItemMeta(bookMeta);
			}
		}
	}

}
