package me.limeglass.skellett.elements.conditions;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Redstone power")
@Description("Tests if a block is being actively updated by redstone.")
@Patterns("%block% (1¦(is|has)|2¦(is|has)(n't| not)) [got] [redstone] powered")
public class CondIsPowered extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return !isNegated();
		Block block = expressions.getSingle(event, Block.class);
		return (block.isBlockPowered()) ? isNegated() : !isNegated();
	}
}