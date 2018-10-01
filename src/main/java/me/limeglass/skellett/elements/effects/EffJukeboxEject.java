package me.limeglass.skellett.elements.effects;

import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettEffect;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Eject music")
@Description("Make a jukebox eject it's disc if it's playing.")
@Patterns({"(force|make) [the] juke[ ]box[es] %blocks% [to] eject [[its] (record|track|song|disk)]", "eject [the] juke[ ]box (record|track|song|disk) from %blocks%"})
public class EffJukeboxEject extends SkellettEffect {

	@Override
	protected void execute(Event event) {
		if (areNull(event)) return;
		for (Block block : expressions.getAll(event, Block.class)) {
			if (block.getState() instanceof Jukebox) {
				Jukebox jukebox = (Jukebox) block.getState();
				jukebox.eject();
			}
		}
	}

}