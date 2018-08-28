package me.limeglass.skellett.elements.conditions;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Whitelisted")
@Description("Tests if the server whitelist is toggled on or not.")
@Patterns("server (1¦is|2¦is(n't| not)) whitelisted")
public class CondIsWhitelisted extends SkellettCondition {
	
	public boolean check(Event event) {
		return (Bukkit.hasWhitelist()) ? isNegated() : !isNegated();
	}

}
