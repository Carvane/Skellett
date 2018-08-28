package me.limeglass.skellett.elements.expressions;

import org.bukkit.event.Event;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettExpression;
import me.limeglass.skellett.objects.PacketEvent;
import me.limeglass.skellett.utils.annotations.Changers;
import me.limeglass.skellett.utils.annotations.Events;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Packet value")
@Description("Returns or gets the value of the defined string fields.")
@Patterns("packet (value|field)[s] %strings%")
@Events(PacketEvent.class)
@Changers(ChangeMode.SET)
public class ExprPacketValue extends SkellettExpression<Object> {
	
	@Override
	protected Object[] get(Event event) {
		for (String field : (String[])expressions.getAllOfFirst(event)) {
			collection.add(((PacketEvent)event).getPacket().getValue(field));
		}
		return collection.toArray(new Object[collection.size()]);
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		for (String field : (String[])expressions.getAllOfFirst(event)) {
			((PacketEvent)event).getPacket().setValue(field, (Object)delta[0]);
		}
	}

}
