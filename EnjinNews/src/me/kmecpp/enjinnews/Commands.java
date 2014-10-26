package me.kmecpp.enjinnews;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	
	public static Main plugin;
	
	public Commands(Main plugin){
		Commands.plugin = plugin;
	}
	
	public static void command(CommandSender sender, String commandLabel, String[] args){
		if(commandLabel.equalsIgnoreCase("news")){
			
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		command(sender, commandLabel, args);
		return false;
	}
}