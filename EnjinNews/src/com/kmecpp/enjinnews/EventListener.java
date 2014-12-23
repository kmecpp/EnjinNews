package com.kmecpp.enjinnews;

import java.util.ArrayList;

import com.kmecpp.enjinnews.util.FileUtil;
import com.kmecpp.enjinnews.util.RSS;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

	public static Main plugin;
	public static ArrayList<Player> newsCheckFailed = new ArrayList<Player>();
	public static ArrayList<Player> newNews = new ArrayList<Player>();

	public EventListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		EventListener.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player player = e.getPlayer();
		plugin.scheduler.runTaskLaterAsynchronously(plugin, new Runnable() {
			public void run() {
				boolean newArticle = false;
				String currentArticle = RSS.getCurrentArticle();

				//CHECK FOR NEW UPDATES
				if (currentArticle != null) {
					YamlConfiguration yml = FileUtil.loadUserFile();
					if (!player.hasPlayedBefore()) {
						yml.set(player.getUniqueId() + ".Name", player.getName());
						yml.set(player.getUniqueId() + ".LastArticle", currentArticle);
					} else {
						if(!yml.contains(player.getUniqueId() + ".Name")){
							yml.set(player.getUniqueId() + ".Name", player.getName());
						}
						
						if (yml.contains(player.getUniqueId() + ".LastArticle")) {
							String lastRead = yml.getString(player.getUniqueId() + ".LastArticle");
							if (!lastRead.equalsIgnoreCase(currentArticle)) {
								newArticle = true;
							}
						} else {
							yml.set(player.getUniqueId() + ".LastArticle", currentArticle);
						}
						FileUtil.saveUserFile(yml);
					}
				}

				if (newArticle) {
					player.sendMessage(ChatColor.BLUE + ChatColor.STRIKETHROUGH.toString() + ChatColor.BOLD.toString() + "----------------------------------------");
					player.sendMessage("                " + ChatColor.GREEN + "You have an unread news update!");
					player.sendMessage("" + ChatColor.BLUE + "Type " + ChatColor.AQUA + "/news read" + ChatColor.BLUE + " to read this article and " + ChatColor.AQUA + "/news help" + ChatColor.BLUE + " for more!");
					player.sendMessage(ChatColor.BLUE + ChatColor.STRIKETHROUGH.toString() + ChatColor.BOLD.toString() + "----------------------------------------");
				}
			}
		}, 30L);
	}
}
