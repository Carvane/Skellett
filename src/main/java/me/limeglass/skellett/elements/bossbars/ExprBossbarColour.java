package me.limeglass.skellett.elements.bossbars;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyExpression;
import me.limeglass.skellett.utils.annotations.Changers;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertiesAddition;
import me.limeglass.skellett.utils.annotations.RegisterEnum;
import me.limeglass.skellett.utils.annotations.Versions;

@Name("Bossbar colour")
@Description("Returns the colo(u)r(s) of bossbar(s).")
@Properties({"bossbars", "colo[u]r", "{1}[(all [[of] the]|the)]"})
@PropertiesAddition("boss[ ]bar")
@RegisterEnum("barcolor")
@Changers(ChangeMode.SET)
@Versions("1.9R1")
public class ExprBossbarColour extends SkellettPropertyExpression<BossBar, BarColor> {
	
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
		for (BossBar bossbar : getExpr().getAll(event)) {
			bossbar.setColor((BarColor)delta[0]);
		}
	}

}