package me.abravepanda.pdl.Database;

import me.abravepanda.pdl.Main;

public class DbCon {
	
	public static String host     = Main.instance.getConfig().getString("Hostname");
	public static String port     = Main.instance.getConfig().getString("Port");
	public static String db       = Main.instance.getConfig().getString("Database");
	public static String username = Main.instance.getConfig().getString("Username");
	public static String password = Main.instance.getConfig().getString("Password");
	public static String maintable    = Main.instance.getConfig().getString("MainTable");
	public static String inventoryTable    = Main.instance.getConfig().getString("InventoryTable");

}
