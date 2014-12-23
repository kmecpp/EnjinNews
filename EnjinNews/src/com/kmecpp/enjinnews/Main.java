package com.kmecpp.enjinnews;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.kmecpp.enjinnews.metrics.Metrics;
import com.kmecpp.enjinnews.util.FileUtil;
import com.kmecpp.enjinnews.util.RSS;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin {

	public static Main plugin;

	public BukkitScheduler scheduler;
	public final Logger logger = Logger.getLogger("Minecraft");
	public static File newsFile;

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been Disabled!");
	}

	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been Enabled!");
		saveDefaultConfig();

		String pluginFolder = getDataFolder().getAbsolutePath();

		//SCHEDULER
		scheduler = Bukkit.getServer().getScheduler();

		// NEWS FILE
		newsFile = new File(pluginFolder + "userdata.yml");
		FileUtil.createNewFile(newsFile);

		// COMMANDS
		getCommand("news").setExecutor(new Commands(this));
		getCommand("enjinnews").setExecutor(new Commands(this));

		// LISTENERS
		new EventListener(this);
		new CommandHandler(this);

		// FILES
		(new File(pluginFolder + File.separator + "data")).mkdirs();
		newsFile = new File(pluginFolder + File.separator + "data" + File.separator + "userdata.yml");
		FileUtil.createNewFile(newsFile);

		// UTILS
		new RSS(this);
		new FileUtil(this);

		// Metrics
		if (getConfig().getBoolean("enable-metrics")) {
			try {
				Metrics metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e) {
				System.out.println("Error submitting EnjinNews plugin statistics!");
			}
		}
	}
}
