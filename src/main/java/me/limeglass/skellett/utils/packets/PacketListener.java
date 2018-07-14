package me.limeglass.skellett.utils.packets;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import me.limeglass.skellett.Skellett;
import me.limeglass.skellett.objects.PacketEvent;
import me.limeglass.skellett.utils.ReflectionUtil;

public class PacketListener implements Listener {
	
	public static void register() {
		if (isEnabled()) Bukkit.getPluginManager().registerEvents(new PacketListener(), Skellett.getInstance());
	}
	
	private static Boolean isEnabled() {
		FileConfiguration configuration = Skellett.getConfiguration("syntax");
		if (!configuration.isSet("Modules.Packets")) {
			configuration.set("Modules.Packets", true);
			Skellett.save("config");
			configuration = Skellett.getConfiguration("syntax");
		}
		return configuration.getBoolean("Modules.Packets");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (isEnabled()) {
			Player player = event.getPlayer();
			ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
				@Override
				public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
					PacketEvent event = new PacketEvent(player, new PacketWrapper(packet));
					Bukkit.getServer().getPluginManager().callEvent(event);
					if (!event.isCancelled()) super.channelRead(context, packet);
				}

				@Override
				public void write(ChannelHandlerContext context, Object packet, ChannelPromise channelPromise) throws Exception {
					PacketEvent event = new PacketEvent(player, new PacketWrapper(packet));
					Bukkit.getServer().getPluginManager().callEvent(event);
					if (!event.isCancelled()) super.write(context, packet, channelPromise);
				}
			};
			Channel channel = ReflectionUtil.getChannel(player);
			ChannelPipeline pipeline = channel.pipeline();
			pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (isEnabled()) {
			Player player = event.getPlayer();
			Channel channel = ReflectionUtil.getChannel(player);
			channel.eventLoop().submit(() -> {
				channel.pipeline().remove(player.getName());
				return null;
			});
		}
	}
}