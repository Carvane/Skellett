package me.limeglass.skellett.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyExpression;
import me.limeglass.skellett.utils.annotations.Changers;
import me.limeglass.skellett.utils.annotations.Properties;
import me.limeglass.skellett.utils.annotations.Versions;

@Name("Silent state")
@Description("Returns the silent state of the defined entities.")
@Properties({"entities", "(silent|quiet) [state]"})
@Changers(ChangeMode.SET)
@Versions("1.10")
public class ExprSilentState extends SkellettPropertyExpression<Entity, Boolean> {
	
	@Override
	protected Boolean[] get(Event event, Entity[] entities) {
		for (Entity entity : entities) {
			collection.add(entity.isSilent());
		}
		return collection.toArray(new Boolean[collection.size()]);
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		for (Entity entity : getExpr().getAll(event)) {
			entity.setSilent((boolean)delta[0]);
		}
	}

}
