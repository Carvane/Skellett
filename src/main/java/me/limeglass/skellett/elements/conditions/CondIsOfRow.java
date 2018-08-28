package me.limeglass.skellett.elements.conditions;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Inventory row condition")
@Description("Tests if a number is within a row number between 1 and 5. Eg: slot number 12 would be in row 2. If you tested that it would return true.")
@Patterns("[slot] %number% (1¦(are|is)|2¦(are|is)(n't| not)) (within|of|in) row %number% [(of|in|from) [inventory] %-inventory%]")
public class CondIsOfRow extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return !isNegated();
		Number slot = expressions.getSingle(event, Number.class, 0);
		Number row = expressions.getSingle(event, Number.class, 1);
		Inventory inventory = expressions.getSingle(event, Inventory.class);
		if (slot != null && row != null) {
			Integer mod = 9;
			if (inventory != null) {
				if (inventory.getType() == InventoryType.DISPENSER ||inventory.getType() == InventoryType.WORKBENCH || inventory.getType() == InventoryType.DROPPER) {
					mod = 3;
				} else if (inventory.getType() == InventoryType.CHEST || inventory.getType() == InventoryType.SHULKER_BOX || inventory.getType() == InventoryType.ENDER_CHEST || inventory.getType() == InventoryType.PLAYER){
					mod = 9;
				} else {
					mod = inventory.getSize();
				}
			}
			Integer calculate = row.intValue() * mod;
			return slot.intValue() >= calculate - mod && slot.intValue() < calculate ? isNegated() : !isNegated();
		}
		return false;
	}

}
