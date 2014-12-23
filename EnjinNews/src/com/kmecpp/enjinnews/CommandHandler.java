package com.kmecpp.enjinnews;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandHandler implements Listener {
	
	public static Main plugin;
	
	public CommandHandler(Main plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		CommandHandler.plugin = plugin;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onCommandPreprocess(PlayerCommandPreprocessEvent e){
		if(e.getMessage().toLowerCase().startsWith("/news")){
			Player player = e.getPlayer();
			e.setCancelled(true);
			String[] command = e.getMessage().substring(1).split(" ");
			if(command.length > 1){
				Commands.runCommand(player, command[0], Arrays.copyOfRange(command, 1, command.length - 1));
			}else{
				Commands.runCommand(player, command[0], null);
			}
		}
	}
}
