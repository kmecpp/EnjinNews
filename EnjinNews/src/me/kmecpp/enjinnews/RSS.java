package me.kmecpp.enjinnews;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import me.kmecpp.enjinnews.util.URLShortener;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RSS {
	
	public static Main plugin;
		
	public RSS(Main plugin){
		RSS.plugin = plugin;
	}
	
	public static void readRSS(CommandSender player, int number){
		if(isConfigSafe(player)){
			try {
				URL rssUrl;
				rssUrl = new URL(plugin.getConfig().getString("news-url"));
				BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream(), Charset.forName("ISO-8859-1")),2048);
				String line;
				List<String> titles = new ArrayList<String>();
				List<String> content = new ArrayList<String>();
				List<String> links = new ArrayList<String>();
				List<String> dates = new ArrayList<String>();
				while((line = in.readLine()) != null){
					if(line.contains("<title>")){
						int firstPos = line.indexOf("<title>");
						String temp = line.substring(firstPos);
						temp = temp.replace("<title>","");
						int lastPos = temp.indexOf("</title>");
						temp = temp.substring(0, lastPos);
						titles.add(temp);
					}
					if(line.contains("<content:encoded>")){
						int firstPos = line.indexOf("<content:encoded>");
						String temp = line.substring(firstPos);
						temp = temp.replace("<content:encoded>","");
						int lastPos = temp.indexOf("</content:encoded>");
						temp = temp.substring(9, lastPos - 3);
						content.add(temp);
					}
					if(line.contains("<link>")){
						int firstPos = line.indexOf("<link>");
						String temp = line.substring(firstPos);
						temp = temp.replace("<link>","");
						int lastPos = temp.indexOf("</link>");
						temp = temp.substring(11, lastPos);
						links.add(temp);
					}
					if(line.contains("<pubDate>")){
						int firstPos = line.indexOf("<pubDate>");
						String temp = line.substring(firstPos);
						temp = temp.replace("<pubDate>","");
						int lastPos = temp.indexOf("</pubDate>");
						temp = temp.substring(0, lastPos);
						dates.add(temp);
					}
				}
				in.close();
				StringBuilder stringbuilder = new StringBuilder();
				int stringbuildercount = 0;
				for(String datePart : dates.get(number - 1).split(" ")){
					if(stringbuildercount <= 3){
						stringbuilder.append(datePart + " ");
						stringbuildercount += 1;
					}
				}
				player.sendMessage(" ");
				player.sendMessage(ChatColor.GOLD + "Article: " + ChatColor.AQUA + String.valueOf(number));
				player.sendMessage(ChatColor.GOLD + "Date: " + ChatColor.AQUA + stringbuilder.toString());
				player.sendMessage(ChatColor.GOLD + "Link: " + ChatColor.AQUA + URLShortener.shorten(links.get(number)));
				player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "---------------------------------------");
				player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + titles.get(number));
				player.sendMessage(" ");
				String data = content.get(number - 1);
				player.sendMessage(ChatColor.YELLOW + content.get(number - 1));
				player.sendMessage(" ");
			} catch (Exception e) {
				player.sendMessage(ChatColor.RED + "Web server did not nespond!");

			}
		}
	}
	
	public static void sendTitles(CommandSender player) {
		if(isConfigSafe(player)){
			try {
				int count = 0;
				URL rssUrl;
				rssUrl = new URL(plugin.getConfig().getString("news-url"));
				BufferedReader in = new BufferedReader(new InputStreamReader(
						rssUrl.openStream(), Charset.forName("ISO-8859-1")), 2048);
				String line;
				List<String> titles = new ArrayList<String>();
				List<String> dates = new ArrayList<String>();
				while ((line = in.readLine()) != null) {
					if (count < 6) {
						if (line.contains("<title>")) {
							int firstPos = line.indexOf("<title>");
							String temp = line.substring(firstPos);
							temp = temp.replace("<title>", "");
							int lastPos = temp.indexOf("</title>");
							temp = temp.substring(0, lastPos);
							titles.add(temp);
							count += 1;
						}
					}
					if (count < 7) {
						if (line.contains("<pubDate>")) {
							int firstPos = line.indexOf("<pubDate>");
							String temp = line.substring(firstPos);
							temp = temp.replace("<pubDate>", "");
							int lastPos = temp.indexOf("</pubDate>");
							temp = temp.substring(0, lastPos);
							dates.add(temp);
						}
					}
				}
				in.close();
				if (titles.isEmpty()) {
					player.sendMessage(ChatColor.RED + "Web server did not nespond!");
				} else {
					player.sendMessage(" ");
					player.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "News Articles");
					player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "-----------------------------------");
					player.sendMessage(" ");
					int titleNumber = 0;
					for (String title : titles) {
						if (title != titles.get(0)) {
							player.sendMessage(ChatColor.RED + String.valueOf(titleNumber + 1) + ") " + ChatColor.GREEN + ChatColor.BOLD.toString() + title);
							StringBuilder stringbuilder = new StringBuilder();
							int stringbuildercount = 0;
							for (String datePart : dates.get(titleNumber)
									.split(" ")) {
								if (stringbuildercount <= 3) {
									stringbuilder.append(datePart + " ");
									stringbuildercount += 1;
								}
							}
							//TODO ADD AUTHOR IF THIS IS IN API 2.0!!!
							player.sendMessage(ChatColor.GOLD + "Post Date: " + ChatColor.AQUA + stringbuilder.toString());
							player.sendMessage(" ");
							titleNumber += 1;
						}
					}
				}
			} catch (Exception e) {
				player.sendMessage(ChatColor.RED + "Web server did not nespond!");
			}
		}
	}
	
	public static boolean isConfigSafe(CommandSender sender){
		if(!plugin.getConfig().getString("news-url").toLowerCase().contains("yourwebsite.com")){
			if(plugin.getConfig().getString("news-url").toLowerCase().endsWith("/rss/true")){
				if(plugin.getConfig().getString("news-url").toLowerCase().startsWith("www.") || !plugin.getConfig().getString("news-url").toLowerCase().startsWith("http://")){
					try{
						BufferedReader br = new BufferedReader(new FileReader(plugin.getDataFolder().getAbsolutePath() + File.separator + "config.yml"));
						String line;
						String input = "";
						while ((line = br.readLine()) != null){
							if(line.contains("news-url: ")){
								input += "news-url: http://" + line.split(" ")[1] + '\n'; 
							}else{
								input += line + '\n';
							}
						}
						br.close();
						FileOutputStream os = new FileOutputStream(plugin.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
						os.write(input.getBytes());
						os.close();
					} catch (Exception e) {
						System.out.println("An " + e.getCause() + " error occurred when trying to read from the EnjinNews configuration file!");
						e.printStackTrace();
					}
					plugin.reloadConfig();
				}
			}else{
				sender.sendMessage(ChatColor.RED + "The URL specified in the EnjinNews configuration file does not seem to have the proper format. It should end with \"/rss/true\"");
			}
		}else{
			sender.sendMessage(ChatColor.RED + "An error has occurred! It doesn't appear that there has been a website added to the EnjinNews configuration file!");
			return false;
		}
		return true;
	}
	
	public static String getCurrentArticle(){
		try {
			URL rssUrl;
			rssUrl = new URL(plugin.getConfig().getString("news-url"));
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream(), Charset.forName("ISO-8859-1")),2048);
			String line;
			List<String> links = new ArrayList<String>();
			while((line = in.readLine()) != null){
				if(line.contains("<link>")){
					int firstPos = line.indexOf("<link>");
					String temp = line.substring(firstPos);
					temp = temp.replace("<link>","");
					int lastPos = temp.indexOf("</link>");
					temp = temp.substring(11, lastPos);
					links.add(temp);
				}
			}
			return links.get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Boolean isOnline(){
		try {
			URL rssUrl;
			rssUrl = new URL(plugin.getConfig().getString("news-url"));
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream(), Charset.forName("ISO-8859-1")),2048);
			String line;
			List<String> links = new ArrayList<String>();
			while((line = in.readLine()) != null){
				if(line.contains("<link>")){
					int firstPos = line.indexOf("<link>");
					String temp = line.substring(firstPos);
					temp = temp.replace("<link>","");
					int lastPos = temp.indexOf("</link>");
					temp = temp.substring(11, lastPos);
					links.add(temp);
				}
			}
			links.get(0);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
