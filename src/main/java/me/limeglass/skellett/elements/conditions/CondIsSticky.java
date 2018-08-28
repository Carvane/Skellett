package me.limeglass.skellett.elements.conditions;

import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;
import org.bukkit.material.PistonBaseMaterial;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.Type;

@Name("Pistion is sticky")
@Description("Tests if a piston block is a sticky piston.")
@PropertyConditionType(PropertyMode.IS_AND_ARE)
@Properties("[a] sticky [piston]")
@Type("blocks")
public class CondIsSticky extends SkellettPropertyCondition<Block> {
	
	public boolean check(Block block) {
		MaterialData data = block.getState().getData();
		if (!(data instanceof PistonBaseMaterial)) return !isNegated();
		PistonBaseMaterial piston = (PistonBaseMaterial) data;
		return (piston.isSticky()) ? isNegated() : !isNegated();
	}

}
