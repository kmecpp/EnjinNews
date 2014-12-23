package com.kmecpp.enjinnews.util;

import java.io.File;
import java.io.IOException;

import com.kmecpp.enjinnews.Main;
import com.kmecpp.enjinnews.util.RSS;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileUtil {

	public static Main plugin;

	public FileUtil(Main plugin) {
		FileUtil.plugin = plugin;
	}

	public static void createNewFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void updatePlayer(Player player){
		YamlConfiguration yml = loadUserFile();
		yml.set(player.getUniqueId() + ".LastArticle", RSS.getCurrentArticle());
		saveUserFile(yml);
	}

	//LOAD/SAVE
	public static YamlConfiguration loadUserFile() {
		YamlConfiguration yml = loadFile("data" + File.separator + "userdata.yml");
		return yml;
	}

	public static void saveUserFile(YamlConfiguration yml) {
		saveFile(yml, "data" + File.separator + "userdata.yml");
	}

	
	public static YamlConfiguration loadFile(String filePath) {
		YamlConfiguration yml = new YamlConfiguration();
		yml.options().copyDefaults(true);
		try {
			yml.load(new File(plugin.getDataFolder().getAbsolutePath() + File.separator + filePath));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return yml;
	}

	public static void saveFile(YamlConfiguration yml, String filePath) {
		yml.options().copyDefaults(true);
		try {
			yml.save(plugin.getDataFolder().getAbsolutePath() + File.separator + filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
