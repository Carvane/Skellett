package me.limeglass.skellett.elements.conditions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Client Time Relative")
@Description("Test if a player's client side time is relative to the servers or not.")
@Patterns("[client] [relative] time of %player% (1¦is|2¦is(n't| not)) relative [to [the] server]")
public class CondClientTimeRelative extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return !isNegated();
		Player player = expressions.getSingle(event, Player.class);
		return (player.isPlayerTimeRelative()) ? isNegated() : !isNegated();
	}
}