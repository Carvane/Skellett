package me.limeglass.skellett.elements.nms;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import me.limeglass.skellett.lang.SkellettExpression;
import me.limeglass.skellett.utils.annotations.ExpressionProperty;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Tick")
@Description("Returns the current server tick.")
@Patterns({"[the] server tick[s]", "[the] server's tick[s]", "[the] tick[s] of [the] server"})
@ExpressionProperty(ExpressionType.SIMPLE)
public class ExprTick extends SkellettExpression<Number> {
	
	protected Number[] get(Event event) {
		try {
			Server server = Bukkit.getServer();
			Field consoleField = server.getClass().getDeclaredField("console");
			consoleField.setAccessible(true);
			Object nmsServer = consoleField.get(server);
			Field tps = nmsServer.getClass().getSuperclass().getDeclaredField("ticks");
			tps.setAccessible(true);
			return new Number[]{tps.getInt(nmsServer)};
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
