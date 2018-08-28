package me.limeglass.skellett.elements.expressions;

import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyExpression;
import me.limeglass.skellett.utils.annotations.Properties;

@Name("Next empty slot")
@Description("Returns the next empty slot of an inventory.")
@Properties({"inventories", "(next|first) empty slot"})
public class ExprNextEmptySlot extends SkellettPropertyExpression<Inventory, Number> {
	
	@Override
	protected Number[] get(Event event, Inventory[] inventories) {
		for (Inventory inventory : inventories) {
			collection.add(inventory.firstEmpty());
		}
		return collection.toArray(new Number[collection.size()]);
	}

}
