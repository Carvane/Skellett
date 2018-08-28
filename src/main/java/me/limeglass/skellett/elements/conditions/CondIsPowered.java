package me.limeglass.skellett.elements.conditions;

import org.bukkit.block.Block;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.Type;

@Name("Redstone power")
@Description("Tests if blocks are being actively updated by redstone.")
@PropertyConditionType(PropertyMode.IS_AND_HAS)
@Properties("[redstone] power[ed]")
@Type("blocks")
public class CondIsPowered extends SkellettPropertyCondition<Block> {
	
	public boolean check(Block block) {
		return (block.isBlockPowered()) ? isNegated() : !isNegated();
	}

}
