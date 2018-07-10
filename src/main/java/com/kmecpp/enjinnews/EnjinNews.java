package com.kmecpp.enjinnews;

import com.kmecpp.osmium.Osmium;
import com.kmecpp.osmium.api.plugin.OsmiumPlugin;
import com.kmecpp.osmium.api.plugin.Plugin;
import com.kmecpp.osmium.api.plugin.PluginInstance;
import com.kmecpp.osmium.api.tasks.OsmiumTask;

@Plugin(name = EnjinNews.NAME, version = "1.0", authors = { "kmecpp" })
public class EnjinNews extends OsmiumPlugin {

	public static final String NAME = "EnjinNews";

	/*
	 * 
	 * TODO:
	 * 
	 * Commands:
	 * news read <number>
	 * news list
	 * news sync
	 * 
	 * Config:
	 * 
	 * 
	 */

	@PluginInstance
	public static EnjinNews plugin;

	@Override
	public void init() {
		setDefaultConfig(Config.class);
		//		Osmium.execute(this)
	}

	//	@Override
	//	public void onEnable() {
	//		saveDefaultConfig();
	//
	//		String pluginFolder = getDataFolder().getAbsolutePath();
	//
	//		// NEWS FILE
	//		FileUtil.createFile(new File(pluginFolder, "userdata.yml"));
	//
	//		// COMMANDS
	//		getCommand("news").setExecutor(new Commands(this));
	//		getCommand("enjinnews").setExecutor(new Commands(this));
	//
	//		// LISTENERS
	//		Bukkit.getPluginManager().registerEvents(new EventListener(), this);
	//
	//		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
	//		}, 0L, 60000L);
	//	}

	public void reload() {

	}

}
