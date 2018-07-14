package me.limeglass.skellett.utils.packets;

import org.bukkit.entity.Player;

import ch.njol.skript.Skript;
import me.limeglass.skellett.utils.ReflectionUtil;

import java.lang.reflect.Field;

public class PacketWrapper {

	protected Object packet;

	public PacketWrapper(Object packet) {
		this.packet = packet;
	}

	public void setValue(String name, Object value) {
		try {
			Field field = this.packet.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(packet, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			Skript.error(name + " is not a field name for packet " + getName());
		}
	}

	public Object getValue(String name) {
		try {
			Field field = packet.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(packet);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			Skript.error(name + " is not a field for packet " + getName());
		}
		return null;
	}

	public void send(Player player) {
		ReflectionUtil.sendPacket(this.packet, player);
	}

	public Object getNMSPacket() {
		return this.packet;
	}

	public String getName() {
		return this.packet.getClass().getSimpleName();
	}
}