//package com.kmecpp.enjinnews;
//
//import org.bukkit.ChatColor;
//
//public enum Level {
//
//	DEBUG(ChatColor.GREEN),
//	INFO(ChatColor.GREEN),
//	WARNING(ChatColor.YELLOW),
//	ERROR(ChatColor.RED),
//	FATAL(ChatColor.RED)
//
//	;
//
//	private final String color;
//
//	private Level(ChatColor color) {
//		this.color = color.toString();
//	}
//
//	public String getColor() {
//		return color;
//	}
//
//	public java.util.logging.Level getLevel() {
//		switch (this) {
//		case INFO:
//			return java.util.logging.Level.INFO;
//		case WARNING:
//			return java.util.logging.Level.WARNING;
//		case ERROR:
//		case FATAL:
//			return java.util.logging.Level.SEVERE;
//		default:
//			return java.util.logging.Level.ALL;
//		}
//	}
//
//}
