package me.limeglass.skellett.elements.nms;

import org.bukkit.entity.Player;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyCondition;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;
import me.limeglass.skellett.utils.ReflectionUtil;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertyConditionType;
import me.limeglass.skellett.utils.annotations.Type;

@Name("Viewing credits")
@Description("Tests if a player is viewing the credits.")
@PropertyConditionType(PropertyMode.IS_AND_ARE)
@Properties("viewing [the] credits")
@Type("players")
public class CondViewingCredits extends SkellettPropertyCondition<Player> {
	
	public boolean check(Player player) {
		Object nmsPlayer = ReflectionUtil.getHandle(player);
		try {
			return nmsPlayer.getClass().getField("viewingCredits").getBoolean(nmsPlayer) ? isNegated() : !isNegated();
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException e1) {
			e1.printStackTrace();
		}
		return false;
	}

}
