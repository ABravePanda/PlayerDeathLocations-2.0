package me.abravepanda.pdl.Commands;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.abravepanda.pdl.Main;
import me.abravepanda.pdl.Database.DbCon;
import me.abravepanda.pdl.Utils.InventoryStringDeSerializer;
import me.abravepanda.pdl.Utils.Messages;

public class RestoreCommand {
	
	public static void RestoreCmd(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("death.restore")) {
				if(args.length > 1) {
					
					try {
						OfflinePlayer ofp = Bukkit.getOfflinePlayer(args[1]);
						Statement statement = Main.c.createStatement();
						ResultSet res = statement.executeQuery("SELECT * FROM " + DbCon.inventoryTable + " WHERE uuid = '" + ofp.getUniqueId() + "';");
						if(res == null)
						{
							p.sendMessage(ChatColor.RED + "Error");
						}
						if(res.next()) {
							String serializedItemstack = res.getString("itemstack");
							ItemStack[] deserializedItemstack = InventoryStringDeSerializer.itemStackArrayFromBase64(serializedItemstack);
							
							Player target = Bukkit.getPlayer(args[1]);
							if(target != null) {
								target.getInventory().setContents(deserializedItemstack);
								target.sendMessage(Messages.invReplacedByPlayer.replace("%player%", p.getName()));
								p.sendMessage(Messages.invReplaced.replace("%target%", target.getName()));
							} else {
								p.sendMessage(Messages.playerNotFound.replace("%target%", args[1]));
							}
							
						}
					} catch (SQLException | IOException e) {
						e.printStackTrace();
					}
					
				} else {
					p.sendMessage(Messages.noPlayer);
				}
			} else {
				p.sendMessage(Messages.noPermission);
			}
		}
		if(sender instanceof ConsoleCommandSender) {
			ConsoleCommandSender console = Bukkit.getConsoleSender();
		
			
			if(args.length > 1) {
				
				try {
					Statement statement = Main.c.createStatement();
					ResultSet res = statement.executeQuery("SELECT * FROM " + DbCon.inventoryTable + " WHERE name = '" + args[1] + "';");
					if(res.next()) {
						String serializedItemstack = res.getString("itemstack");
						ItemStack[] deserializedItemstack = InventoryStringDeSerializer.itemStackArrayFromBase64(serializedItemstack);
						
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							target.getInventory().setContents(deserializedItemstack);
							target.sendMessage(Messages.invReplacedByPlayer.replace("%player%", "Console"));
							console.sendMessage(Messages.invReplacedByPlayer.replace("%target%", target.getName()));
						} else {
							console.sendMessage(Messages.playerNotFound.replace("%target%", args[1]));
						}
						
					}
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
				
			} else {
				console.sendMessage(Messages.noPlayer);
			}
			
		}
		
		
	}

}
