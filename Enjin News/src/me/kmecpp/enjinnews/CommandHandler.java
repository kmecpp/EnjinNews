package me.kmecpp.enjinnews;

import org.bukkit.event.Listener;

public class CommandHandler implements Listener {
	
	public Main plugin;
	
	public CommandHandler(Main plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

}
