//package com.kmecpp.enjinnews;
//
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//
//public class Logger {
//
//	/**
//	 * Logs a DEBUG level message to the console, with the plugin's name as the
//	 * prefix
//	 *
//	 * @param message
//	 *            the message
//	 */
//	public static void debug(String message) {
//		log(Level.DEBUG, EnjinNews.NAME, message);
//	}
//
//	/**
//	 * Logs an INFO level message to the console, with the plugin's name as the
//	 * prefix
//	 *
//	 * @param message
//	 *            the message
//	 */
//	public static void info(String message) {
//		log(Level.INFO, EnjinNews.NAME, message);
//	}
//
//	/**
//	 * Logs an WARNING level message to the console, with the plugin's name as
//	 * the prefix
//	 *
//	 * @param message
//	 *            the message
//	 */
//	public static void warn(String message) {
//		log(Level.WARNING, EnjinNews.NAME, message);
//	}
//
//	/**
//	 * Logs an ERROR level message to the console, with the plugin's name as the
//	 * prefix
//	 *
//	 * @param message
//	 *            the message
//	 */
//	public static void error(String message) {
//		log(Level.ERROR, EnjinNews.NAME, message);
//	}
//
//	/**
//	 * Logs an FATAL level message to the console, with the plugin's name as the
//	 * prefix
//	 *
//	 * @param message
//	 *            the message
//	 */
//	public static void fatal(String message) {
//		log(Level.FATAL, EnjinNews.NAME, message);
//	}
//
//	/**
//	 * Logs a message to the console with the given prefix
//	 *
//	 * @param level
//	 *            the level of the message
//	 * @param prefix
//	 *            the prefix of the message
//	 * @param message
//	 *            the message
//	 */
//	public static void log(Level level, String prefix, String message) {
//		if (level == Level.DEBUG && !Config.debug) {
//			return;
//		}
//
//		if (Bukkit.getConsoleSender() != null) { //This seems to only be an issue on legacy servers during startup
//			Bukkit.getConsoleSender().sendMessage(""
//					+ ChatColor.DARK_AQUA + "["
//					+ ChatColor.AQUA + prefix + (level != Level.DEBUG && level != Level.INFO ? ChatColor.DARK_AQUA + "|" + level.getColor() + level : "")
//					+ ChatColor.DARK_AQUA + "] "
//					+ level.getColor() + message);
//		} else {
//			Bukkit.getLogger().log(level.getLevel(), "[" + prefix + "] " + message);
//		}
//	}
//
//}
