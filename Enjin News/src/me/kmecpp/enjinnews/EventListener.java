package me.kmecpp.enjinnews;

import org.bukkit.event.Listener;

public class EventListener implements Listener {
	
	public Main plugin;

	public EventListener(Main plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
}
