package me.limeglass.skellett.elements.bossbars;

import org.bukkit.boss.BarStyle;
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

@Name("Bossbar style")
@Description("Returns the style(s) of bossbar(s).")
@Properties({"bossbars", "style", "{1}[(all [[of] the]|the)]"})
@PropertiesAddition("boss[ ]bar")
@RegisterEnum("barstyle")
@Changers(ChangeMode.SET)
@Versions("1.9R1")
public class ExprBossbarStyle extends SkellettPropertyExpression<BossBar, BarStyle> {
	
	@Override
	protected BarStyle[] get(Event event, BossBar[] bossbars) {
		for (BossBar bossbar : bossbars) {
			collection.add(bossbar.getStyle());
		}
		return collection.toArray(new BarStyle[collection.size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (isNull(event) || delta == null) return;
		for (BossBar bossbar : getExpr().getAll(event)) {
			bossbar.setStyle((BarStyle)delta[0]);
		}
	}

}