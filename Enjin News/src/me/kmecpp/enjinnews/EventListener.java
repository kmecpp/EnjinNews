package me.kmecpp.enjinnews;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EventListener implements Listener {
	
	public Main plugin;

	public EventListener(Main plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onCommandPreprocess(PlayerCommandPreprocessEvent e){
		if(e.getMessage().toLowerCase().startsWith("/news")){
			e.setCancelled(true);
			String[] args = Arrays.copyOfRange(e.getMessage().split(" "), 1, e.getMessage().split(" ").length);
			Commands.command((CommandSender) e.getPlayer(), e.getMessage().split(" ")[0].replace("/", ""), args);
		}
	}
}
