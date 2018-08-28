package me.limeglass.skellett.elements.conditions;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.Utils;
import me.limeglass.skellett.utils.annotations.Patterns;
import me.limeglass.skellett.utils.annotations.Versions;

@Name("Item cooldown")
@Description("Test if a Material string has a custom cooldown on it. String being a Material name https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html")
@Patterns("%player% (1¦(has|does)|2¦(has|does)(n't| not)) [(have|got)] [a] cool[ ]down for [(item|material)] %string%")
@Versions("1.11.2")
public class CondHasCooldown extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return false;
		String material = expressions.getSingle(event, String.class);
		Player player = expressions.getSingle(event, Player.class);
		Material m = (Material) Utils.getEnum(Material.class, material);
		return ((HumanEntity)player).hasCooldown(m) ? isNegated() : !isNegated();
	}

}