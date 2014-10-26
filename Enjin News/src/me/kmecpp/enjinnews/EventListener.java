package me.kmecpp.enjinnews;

import java.util.ArrayList;
import me.kmecpp.enjinnews.util.FileUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EventListener implements Listener {
	
	public static Main plugin;
	public static ArrayList<Player> newsCheckFailed = new ArrayList<Player>();
	public static ArrayList<Player> newNews = new ArrayList<Player>();

	public EventListener(Main plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		EventListener.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		final Player player = e.getPlayer();
		if(!player.hasPlayedBefore()){
			FileUtil.writeToNews(player, true, RSS.getCurrentArticle());
		}else{
			if(FileUtil.NewsDataContains(player)){
				try{
					if(!FileUtil.lastArticleRead(player).equals(RSS.getCurrentArticle()) && !RSS.getCurrentArticle().equalsIgnoreCase("null")){
						FileUtil.writeToNews(player, false, RSS.getCurrentArticle());
					}else if(RSS.getCurrentArticle().equalsIgnoreCase("null")){
						newsCheckFailed.add(player);
					}
				}catch(Exception ex){
					newsCheckFailed.add(player);
				}
			}else{
				FileUtil.writeToNews(player, false, RSS.getCurrentArticle());
			}
			if(!FileUtil.upToDate(player)){
				newNews.add(player);
			}
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				if(!newsCheckFailed.contains(player)){
					if(newNews.contains(player)){
						player.sendMessage(ChatColor.BLUE + ChatColor.STRIKETHROUGH.toString() + ChatColor.BOLD.toString() + "----------------------------------------");
						player.sendMessage("                " + ChatColor.GREEN + "You have an unread news update!");
						player.sendMessage("" + ChatColor.BLUE + "Type " + ChatColor.AQUA + "/news read" + ChatColor.BLUE + " to read this article and " + ChatColor.AQUA + "/news help" + ChatColor.BLUE + " for more!");
						player.sendMessage(ChatColor.BLUE + ChatColor.STRIKETHROUGH.toString() + ChatColor.BOLD.toString() + "----------------------------------------");
					}
				}
			}
		}.runTaskLater(plugin, 50L);
	}
}
