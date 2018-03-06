package me.abravepanda.pdl;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import me.abravepanda.pdl.Commands.CommandHandler;
import me.abravepanda.pdl.Database.DbCon;
import me.abravepanda.pdl.Database.MySQL;
import me.abravepanda.pdl.Events.DeathEvent;


public class Main extends JavaPlugin {
	
	public static Main instance;
	
	private MySQL MySQLC = null;
	public static Connection c = null;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		registerConfig();
		this.getCommand("death").setExecutor(new CommandHandler());
		this.getServer().getPluginManager().registerEvents(new DeathEvent(), this);
		
		
		MySQLC = new MySQL(DbCon.host, DbCon.port, DbCon.db, DbCon.username, DbCon.password);
		try {
			c = MySQLC.openConnection();
		} catch (ClassNotFoundException er) {
			er.printStackTrace();
		} catch (SQLException er) {
			er.printStackTrace();
		}
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public void registerConfig() {
		saveDefaultConfig();
	}

}
