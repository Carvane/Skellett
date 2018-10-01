package me.limeglass.skellett.elements.bossbars;

import java.util.List;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.ChangeablePropertyExpression;
import me.limeglass.skellett.utils.annotations.*;

@Name("Bossbar players")
@Description("Returns the players of a bossbar(s).")
@Properties({"bossbars", "player[s]", "{1}[(all [[of] the]|the)]"})
@PropertiesAddition("boss[ ]bar")
@Versions("1.9R1")
@AllChangers
@Multiple
public class ExprBossbarPlayers extends ChangeablePropertyExpression<BossBar, Player, Player[]> {
	
	@Override
	public Player[] get(BossBar bossbar) {
		List<Player> players = bossbar.getPlayers();
		return players.toArray(new Player[players.size()]);
	}

	@Override
	public void change(BossBar bossbar, Player[] players, ChangeMode mode) {
		switch (mode) {
			case ADD:
				for (Player player : players) {
					if (!bossbar.getPlayers().contains(player)) bossbar.addPlayer(player);
				}
				break;
			case REMOVE:
				for (Player player : players) {
					if (bossbar.getPlayers().contains(player)) bossbar.removePlayer(player);
				}
				break;
			case DELETE:
			case REMOVE_ALL:
			case RESET:
				bossbar.removeAll();
				break;
			case SET:
				bossbar.removeAll();
				for (Player player : players) {
					bossbar.addPlayer(player);
				}
				break;
		}
	}

}