package me.limeglass.skellett.elements.expressions;

import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettPropertyExpression;
import me.limeglass.skellett.utils.annotations.Properties;

@Name("World folder")
@Description("Returns the path folder of the world(s).")
@Properties({"worlds", "[world] folder[s]", "{1}[(all [[of] the]|the)]"})
public class ExprWorldFolder extends SkellettPropertyExpression<World, String> {

	@Override
	protected String[] get(Event event, World[] worlds) {
		for (World world : worlds) {
			collection.add(world.getWorldFolder().getPath());
		}
		return collection.toArray(new String[collection.size()]);
	}

}
