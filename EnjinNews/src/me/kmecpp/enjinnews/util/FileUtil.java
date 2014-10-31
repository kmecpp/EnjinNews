package me.kmecpp.enjinnews.util;

import java.io.File;
import java.io.IOException;

import me.kmecpp.enjinnews.Main;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileUtil {
	
	private File file;
	
	public FileUtil(File file){
		this.file = file;		
		if(!this.file.exists()){
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//News
	public static void writeToNews(Player player, boolean value, String currentArticle){
		YamlConfiguration yml = loadNewsFile();
		
		yml.options().copyDefaults(true);
		
		if(!yml.contains("Players."+player.getName())){
			yml.addDefault("Players", null);
			yml.addDefault("Players."+player.getName(), null);
			yml.addDefault("Players."+player.getName()+".Up-To-Date", value);
			yml.addDefault("Players."+player.getName()+".Last-Article", currentArticle);
		}else{
			yml.addDefault("Players", null);
			yml.addDefault("Players."+player.getName(), null);
			yml.set("Players."+player.getName()+".Up-To-Date", value);
			yml.addDefault("Players."+player.getName()+".Last-Article", currentArticle);
		}
		
		saveNewsFile(yml);
	}
	
	public static Boolean upToDate(Player player){
		YamlConfiguration yml = loadNewsFile();
		if(yml.getBoolean("Players."+player.getName()+ ".Up-To-Date")){
			return true;
		}
		return false;
	}
	
	public static String lastArticleRead(Player player){
		YamlConfiguration yml = loadNewsFile();
		return yml.getString("Players."+player.getName()+".Last-Article");
	}
	
	public static Boolean NewsDataContains(Player player){
		YamlConfiguration yml = loadNewsFile();
		if (yml.contains("Players." + player.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	
	//LOAD / SAVE
	
	//PRIVATE
	private static YamlConfiguration loadNewsFile() {
		YamlConfiguration yml = new YamlConfiguration();
		try {
			yml.load(new File(Main.plugin.getDataFolder().getAbsolutePath() + File.separator + "News Data" + File.separator + "News.yml"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return yml;
	}

	private static void saveNewsFile(YamlConfiguration yml) {
		yml.options().copyDefaults(true);
		try {
			yml.save(Main.plugin.getDataFolder().getAbsolutePath() + File.separator + "News Data" + File.separator + "News.yml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//PUBLIC
	public static YamlConfiguration loadFile(String filePath) {
		YamlConfiguration yml = new YamlConfiguration();
		try {
			yml.load(new File(Main.plugin.getDataFolder().getAbsolutePath() + File.separator + filePath));
			yml.options().copyDefaults(true);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return yml;
	}

	public static void saveFile(String filePath, YamlConfiguration yml) {
		yml.options().copyDefaults(true);
		try {
			yml.save(Main.plugin.getDataFolder().getAbsolutePath() + File.separator + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
