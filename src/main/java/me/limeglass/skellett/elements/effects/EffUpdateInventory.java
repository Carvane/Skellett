package me.limeglass.skellett.elements.effects;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettEffect;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Update inventory")
@Description("Updates an inventory. Used if the inventory has been modified alot.")
@Patterns("update [the] inventor(y|ies) %inventories%")
public class EffUpdateInventory extends SkellettEffect {

	@Override
	protected void execute(Event event) {
		if (areNull(event)) return;
		for (final Inventory inventory : expressions.getAll(event, Inventory.class)) {
			for (HumanEntity player : inventory.getViewers()) {
				((Player)player).updateInventory();
			}
		}
	}

}