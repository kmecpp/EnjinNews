package me.kmecpp.enjinnews;

import me.kmecpp.enjinnews.util.FileUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class Commands implements CommandExecutor {
	
	public static Main plugin;
	
	public Commands(Main plugin){
		Commands.plugin = plugin;
	}
	
	public static void command(CommandSender sender, String commandLabel, String[] args){
		CommandSender out = sender;
		if(commandLabel.equalsIgnoreCase("news")){
			//Log Command
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < args.length; i++) {
				stringBuffer.append( args[i] );
			}
			String allArgs = stringBuffer.toString();
			Bukkit.getConsoleSender().sendMessage(out.getName() + " issued server command: /" + commandLabel + " " + allArgs);
			
			if(args.length == 0){
				out.sendMessage(ChatColor.AQUA + "Fetching RSS feed...");
				RSS.sendTitles(out);
			}else if(args.length == 1){
				if(args[0].equalsIgnoreCase("read")){
					out.sendMessage(ChatColor.AQUA + "Fetching RSS feed...");
					RSS.readRSS(out, 1);
					if(out instanceof Player){
						if(!(FileUtil.upToDate((Player) out))){
							FileUtil.writeToNews((Player) out, true, RSS.getCurrentArticle());
						}
					}
				}else if(args[0].equalsIgnoreCase("info")){
					PluginDescriptionFile pdfFile = plugin.getDescription();
					out.sendMessage(ChatColor.BLUE + ChatColor.BOLD.toString() + "EnjinNews Info");
					out.sendMessage(ChatColor.GOLD + "--------------------------------------------");
					out.sendMessage(ChatColor.GREEN + pdfFile.getName());
					out.sendMessage(ChatColor.GREEN + "Version: " + ChatColor.AQUA + pdfFile.getVersion());
					out.sendMessage(ChatColor.GREEN + "Author: " + ChatColor.AQUA + "kmecpp");
					out.sendMessage(ChatColor.GREEN + "Website: " + ChatColor.AQUA + pdfFile.getWebsite());
				}else if(args[0].equalsIgnoreCase("help")){
					displayHelp(out);
				}else if(args[0].equalsIgnoreCase("reload")){
					plugin.reloadConfig();
					out.sendMessage(ChatColor.GREEN + "Configuration file reloaded!");
				}else{
					try{
						int articleNumber = Integer.parseInt(args[0]);
						if(articleNumber == 0){
							out.sendMessage(ChatColor.RED + "Article numbers start at one!");
						}else if(articleNumber < 0){
							out.sendMessage(ChatColor.RED + "You can't look into the future!");
						}else if(articleNumber > 5){
							out.sendMessage(ChatColor.RED + "Only the last five articles are readable");
						}else{
							out.sendMessage(ChatColor.AQUA + "Fetching RSS feed...");
							RSS.readRSS(out, articleNumber);
							if(out instanceof Player && articleNumber == 1){
								if(!(FileUtil.upToDate((Player) out))){
									FileUtil.writeToNews((Player) out, true, RSS.getCurrentArticle());
								}
							}
							
						}						
					}catch(Exception e){
						out.sendMessage(ChatColor.RED + "This is not a valid EnjinNews command!");
					}
				}
			}
		}else if(commandLabel.equalsIgnoreCase("enjinnews")){
			displayHelp(out);
		}
	}
	
	public static void displayHelp(CommandSender out){
		out.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "EnjinNews Commands:");
		out.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "--------------------------------------");
		out.sendMessage(ChatColor.GREEN + "/news " + ChatColor.AQUA + "Returns the five most recent articles");
		out.sendMessage(ChatColor.GREEN + "/news <number> " + ChatColor.AQUA + "Downloads and displays the specified article");
		out.sendMessage(ChatColor.GREEN + "/news read " + ChatColor.AQUA + "Reads the most recent news article");
		out.sendMessage(ChatColor.GREEN + "/news info " + ChatColor.AQUA + "Displays plugin information");
		out.sendMessage(ChatColor.GREEN + "/news help " + ChatColor.AQUA + "Displays this message");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		command(sender, commandLabel, args);
		return false;
	}
}