package me.limeglass.skellett.elements.conditions;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Jukebox;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertiesAddition;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.Type;

@Name("Jukebox is playing")
@Description("Tests if a jukebox block is playing a track.")
@PropertyConditionType(PropertyMode.IS_AND_ARE)
@Properties("playing [a] (record|track|song)")
@PropertiesAddition("juke[ ]box")
@Type("blocks")
public class CondJukeboxIsPlaying extends SkellettPropertyCondition<Block> {
	
	public boolean check(Block block) {
		BlockState state = block.getState();
		if (!(block.getState() instanceof Jukebox)) return !isNegated();
		Jukebox jukebox = (Jukebox) state;
		return jukebox.isPlaying() ? isNegated() : !isNegated();
	}

}