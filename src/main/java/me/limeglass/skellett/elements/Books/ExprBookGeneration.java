package me.limeglass.skellett.elements.Books;

import org.bukkit.event.Event;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyExpression;
import me.limeglass.skellett.utils.annotations.Changers;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.RegisterEnum;
import me.limeglass.skellett.utils.annotations.Versions;

@Name("Generation of book")
@Description("Grab/Set the generation of a book. Generation is basically how many times the book has been copied.")
@Properties({"itemtypes", "generation[s]", "{1}[(all [[of] the]|the)]"})
@RegisterEnum("bookgeneration")
@Changers(ChangeMode.SET)
@Versions("1.9R1")
public class ExprBookGeneration extends SkellettPropertyExpression<ItemType, Generation> {
	
	protected Generation[] get(ItemType book) {
		ItemMeta meta = (ItemMeta) book.getItemMeta();
		if (!(meta instanceof BookMeta)) return null;
		BookMeta bookmeta = (BookMeta) meta;
		return new Generation[]{bookmeta.getGeneration()};
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		for (ItemType book : expressions.getAll(event, ItemType.class)) {
			ItemMeta meta = (ItemMeta) book.getItemMeta();
			if (delta == null || !(meta instanceof BookMeta)) return;
			BookMeta bookmeta = (BookMeta) meta;
			bookmeta.setGeneration((Generation)delta[0]);
			book.setItemMeta(bookmeta);
		}
	}

}
