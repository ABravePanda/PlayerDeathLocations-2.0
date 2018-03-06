package me.abravepanda.pdl.Utils;

import org.bukkit.ChatColor;

import me.abravepanda.pdl.Main;

public class Messages {
	
	public static String noConsole = ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("ConsoleCommandError"));
	
	
	public static String noPermission = ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("NoPermission"));
	
	
	public static String noPlayer = ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("NoPlayerSpecified"));
	
	
	public static String cmdNotFound = ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("CommandNotFound"));
	
	
	public static String playerNotFound = ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("PlayerNotFound"));
	/*Variables:
	 * %target% - target
	*/
	
	public static String invReplacedByPlayer = ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("PlayerReplaceInv"));
	/*Variables:
	 * %player% - replacer
	*/
	
	public static String invReplaced = ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("InventoryReplaced"));
	/*Variables:
	 * %target% - target
	*/
	
}
