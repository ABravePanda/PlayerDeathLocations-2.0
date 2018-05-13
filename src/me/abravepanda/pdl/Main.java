package me.abravepanda.pdl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.abravepanda.pdl.Commands.CommandHandler;
import me.abravepanda.pdl.Database.DbCon;
import me.abravepanda.pdl.Database.MySQL;
import me.abravepanda.pdl.Events.DeathEvent;
import me.abravepanda.pdl.Events.PickupEvent;


public class Main extends JavaPlugin {
	
	public static Main instance;
	
	private MySQL MySQLC = null;
	public static Connection c = null;
	public static HashMap<Player, UUID> playerOpenInventories = new HashMap<Player, UUID>();
	public static HashMap<Player, UUID> adminOpenInventories = new HashMap<Player, UUID>();
	public static HashMap<Player, ItemStack[]> adminInventory = new HashMap<Player, ItemStack[]>();
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		registerConfig();
		this.getCommand("death").setExecutor(new CommandHandler());
		this.getServer().getPluginManager().registerEvents(new DeathEvent(), this);
		this.getServer().getPluginManager().registerEvents(new PickupEvent(), this);		
		
		MySQLC = new MySQL(DbCon.host, DbCon.port, DbCon.db, DbCon.username, DbCon.password);
		try {
			c = MySQLC.openConnection();
		} catch (ClassNotFoundException er) {
			er.printStackTrace();
		} catch (SQLException er) {
			er.printStackTrace();
		}
		
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	
					
            	
            	PreparedStatement keepAlive = null;
				try {
					keepAlive = c.prepareStatement("SELECT 1 FROM " + DbCon.maintable);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                        try {
							keepAlive.executeQuery();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            }
        }, 0L, 20l);
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public void registerConfig() {
		saveDefaultConfig();
	}

}
