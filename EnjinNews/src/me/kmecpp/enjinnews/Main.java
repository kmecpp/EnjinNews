package me.kmecpp.enjinnews;

import java.io.File;
import java.util.logging.Logger;

import me.kmecpp.enjinnews.util.FileUtil;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public static FileUtil NewsFile;
	
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been Disabled!");
	}
	
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been Enabled!");
		plugin = this;
		
		String pluginFolder = getDataFolder().getAbsolutePath();
		
		saveDefaultConfig();
		
		// News Files
		(new File(pluginFolder + File.separator + "News Data")).mkdirs();

		NewsFile = new FileUtil(new File(pluginFolder + File.separator
				+ "News Data" + File.separator + "News.yml"));
		
		//Commands
		getCommand("news").setExecutor(new Commands(this));
		
		//Listeners
		new EventListener(this);
		new CommandHandler(this);
		
		//Files
		(new File(pluginFolder + File.separator + "News Data")).mkdirs();

		NewsFile = new FileUtil(new File(pluginFolder + File.separator
				+ "News Data" + File.separator + "News.yml"));
		
		//RSS
		new RSS(this);
	}
}
