package me.limeglass.skellett.elements.bossbars;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyExpression;
import me.limeglass.skellett.utils.annotations.AllChangers;
import me.limeglass.skellett.utils.annotations.Multiple;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertiesAddition;
import me.limeglass.skellett.utils.annotations.RegisterEnum;
import me.limeglass.skellett.utils.annotations.Versions;

@Name("Bossbar flags")
@Description("Returns the flags of a bossbar(s).")
@Properties({"bossbars", "flag[s]", "{1}[(all [[of] the]|the)]"})
@PropertiesAddition("boss[ ]bar")
@RegisterEnum("barflag")
@Versions("1.9R1")
@AllChangers
@Multiple
public class ExprBossbarFlags extends SkellettPropertyExpression<BossBar, BarColor> {
	
	@Override
	protected BarColor[] get(Event event, BossBar[] bossbars) {
		for (BossBar bossbar : bossbars) {
			collection.add(bossbar.getColor());
		}
		return collection.toArray(new BarColor[collection.size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (isNull(event) || delta == null) return;
		BossBar[] bossbars = expressions.getAll(event, BossBar.class);
		BarFlag[] flags = (BarFlag[]) delta;
		switch (mode) {
			case ADD:
				for (BossBar bossbar : bossbars) {
					for (BarFlag flag : flags) {
						if (!bossbar.hasFlag(flag)) bossbar.addFlag(flag);
					}
				}
				break;
			case REMOVE:
				for (BossBar bossbar : bossbars) {
					for (BarFlag flag : flags) {
						if (bossbar.hasFlag(flag)) bossbar.removeFlag(flag);
					}
				}
				break;
			case DELETE:
			case REMOVE_ALL:
			case RESET:
				removeAll(bossbars);
				break;
			case SET:
				removeAll(bossbars);
				for (BossBar bossbar : bossbars) {
					for (BarFlag flag : flags) {
						bossbar.addFlag(flag);
					}
				}
				break;
		}
	}
	
	private void removeAll(BossBar... bossbars) {
		for (BossBar bossbar : bossbars) {
			for (BarFlag flag : BarFlag.values()) {
				if (bossbar.hasFlag(flag)) bossbar.removeFlag(flag);
			}
		}
	}

}