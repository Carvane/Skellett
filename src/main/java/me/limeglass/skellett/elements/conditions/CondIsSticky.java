package me.limeglass.skellett.elements.conditions;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.material.PistonBaseMaterial;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Pistion is sticky")
@Description("Tests if a piston block is a sticky piston.")
@Patterns("%block% (1¦is|2¦is(n't| not)) [a] sticky [piston]")
public class CondIsSticky extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return !isNegated();
		Block block = expressions.getSingle(event, Block.class);
		if (!(block.getState().getData() instanceof PistonBaseMaterial)) return !isNegated();
		PistonBaseMaterial piston = (PistonBaseMaterial) block.getState().getData();
		return (piston.isSticky()) ? isNegated() : !isNegated();
	}
}