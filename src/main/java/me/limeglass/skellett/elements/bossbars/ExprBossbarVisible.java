package me.limeglass.skellett.elements.bossbars;

import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyExpression;
import me.limeglass.skellett.utils.annotations.Changers;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.PropertiesAddition;
import me.limeglass.skellett.utils.annotations.Versions;

@Name("Bossbar visiblity")
@Description("Returns the visiblity of bossbar(s).")
@Properties({"bossbars", "visib(le|ility)", "{1}[(all [[of] the]|the)]"})
@PropertiesAddition("boss[ ]bar")
@Changers(ChangeMode.SET)
@Versions("1.9R1")
public class ExprBossbarVisible extends SkellettPropertyExpression<BossBar, Boolean> {
	
	@Override
	protected Boolean[] get(Event event, BossBar[] bossbars) {
		for (BossBar bossbar : bossbars) {
			collection.add(bossbar.isVisible());
		}
		return collection.toArray(new Boolean[collection.size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (isNull(event) || delta == null) return;
		for (BossBar bossbar : getExpr().getAll(event)) {
			bossbar.setVisible((Boolean)delta[0]);
		}
	}

}