package me.kmecpp.enjinnews;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been Disabled!");
	}
	
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been Enabled!");
		plugin = this;
		
		saveDefaultConfig();
		
		//Commands
		getCommand("news").setExecutor(new Commands(this));
		
		//Listeners
		new EventListener(this);
		new CommandHandler(this);
		
		//RSS
		new RSS(this);
	}
}
