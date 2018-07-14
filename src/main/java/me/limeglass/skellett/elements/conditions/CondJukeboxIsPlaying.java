package me.limeglass.skellett.elements.conditions;

import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Jukebox is playing")
@Description("Tests if a jukebox block is playing a track.")
@Patterns("juke[ ]box %block% (1¦is|2¦is(n't| not)) playing [a] (record|track|song)")
public class CondJukeboxIsPlaying extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return !isNegated();
		Block block = expressions.getSingle(event, Block.class);
		if (!(block.getState() instanceof Jukebox)) return !isNegated();
		Jukebox jukebox = (Jukebox) block.getState();
		return jukebox.isPlaying() ? isNegated() : !isNegated();
	}
}