package me.abravepanda.pdl.Events;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.abravepanda.pdl.Main;
import me.abravepanda.pdl.Database.DbCon;
import me.abravepanda.pdl.Utils.InventoryStringDeSerializer;
import net.md_5.bungee.api.ChatColor;

public class DeathEvent implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {

		if (e.getEntity() instanceof Player) {
			Player p = e.getEntity();
			Location loc = e.getEntity().getLocation();
			double x = loc.getBlockX() + 0.5;
			double y = loc.getBlockY();
			double z = loc.getBlockZ() + 0.5;
			String dr = e.getDeathMessage();
			String world = loc.getWorld().getName();
			java.util.Date dt = new java.util.Date();

			java.text.SimpleDateFormat sdf = 
					new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String time = sdf.format(dt);
      
			String statementstring = "SELECT * FROM " + DbCon.maintable + " WHERE PlayerUUID = '" + p.getUniqueId() +"';";
			String statementstring2 = "INSERT INTO "+ DbCon.maintable + " (id, PlayerUUID, X, Y, Z, DeathReason, world,Time) VALUES (NULL, '" + p.getUniqueId() + "', '" + x + "', '" + y + "', '" + z + "', '" + dr + "', '" + world + "', '" + time + "');";
		
			try {

				Statement statement = Main.c.createStatement();
				ResultSet res = statement.executeQuery(statementstring);
				if(res.next()) {  // Player already has a death in the database, update it
					String statementstring3 = "UPDATE " + DbCon.maintable + " SET X='" + x + "', Y='" + y + "', Z='" + z + "', DeathReason='" + dr + "', world='" + world + "',Time='" + time + "' WHERE id='" + res.getInt("id") + "';";
					statement.executeUpdate(statementstring3);
					p.sendMessage(ChatColor.GRAY + "- Death location updated. Use" + ChatColor.GREEN + " /death " + ChatColor.GRAY + "to find your last death location!");
				} else {         // First time player has died, create a new death entry
					statement.executeUpdate(statementstring2);
					p.sendMessage(ChatColor.GRAY + "- Death location updated. Use" + ChatColor.GREEN + " /death " + ChatColor.GRAY + "to find your last death location!");
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		
		
		//When the player dies, save the inventory
			try {
				Statement statement = Main.c.createStatement();
				String statementstring333 = "SELECT * FROM invs WHERE uuid = '" + p.getUniqueId() +"';";
				ResultSet res = statement.executeQuery(statementstring333);
				ItemStack[] items = e.getEntity().getInventory().getContents();
				String n = InventoryStringDeSerializer.itemStackArrayToBase64(items);
				if(res.next()) {  // Player already has an inv
					String statementstring3 = "UPDATE invs SET uuid='" + p.getUniqueId() + "', itemstack = '" + n + "' WHERE id='" + res.getInt("id") + "';";
					statement.executeUpdate(statementstring3);
				} else {         // First time player has died, create a new death entry
					String statementstring22 = "INSERT INTO `invs`(`id`, `uuid`, `itemstack`) VALUES (NULL, '" + e.getEntity().getUniqueId() + "','" + n + "')";
					statement.executeUpdate(statementstring22);
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}	
		}
 	}
	
}
