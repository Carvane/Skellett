package me.limeglass.skellett.elements.conditions;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Scoreboard existance")
@Description("Tests if a scoreboard has been registered or not. Keep in mind these are only spigot scoreboards not vanila registered ones.")
@Patterns("score[[ ]board] %string% (1¦(is set|[does] exist[s])|2¦(is(n't| not) set|does(n't| not) exist[s]))")
public class CondScoreboardExists extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return !isNegated();
		String scoreboard = expressions.getSingle(event, String.class);
		return (Bukkit.getScoreboardManager().getMainScoreboard().getTeam(scoreboard) != null) ? isNegated() : !isNegated();
	}

}
