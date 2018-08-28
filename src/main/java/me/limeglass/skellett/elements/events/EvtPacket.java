package me.limeglass.skellett.elements.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.limeglass.skellett.lang.SkellettEvent;
import me.limeglass.skellett.objects.PacketEvent;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Skellett Packet")
@Patterns("packet [(send[ing]|reciev(e|ing))] [[of] %strings%]")
@Description("Called when a packet is sent or recieved to a Minecraft client.")
public class EvtPacket extends SkellettEvent<PacketEvent> {
	
	static {
		EventValues.registerEventValue(PacketEvent.class, Player.class, new Getter<Player, PacketEvent>() {
			@Override
			public Player get(PacketEvent event) {
				return event.getPlayer();
			}
		}, 0);
		EventValues.registerEventValue(PacketEvent.class, String.class, new Getter<String, PacketEvent>() {
			@Override
			public String get(PacketEvent event) {
				return event.getName();
			}
		}, 0);
	}

	@Override
	public boolean check(Event event) {
		if (literals == null) return true;
		for (String packet : literals.getAll(event, String.class)) {
			if (((PacketEvent)event).getName().equals(packet)) return true;
		}
		return false;
	}

}
