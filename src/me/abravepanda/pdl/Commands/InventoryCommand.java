package me.abravepanda.pdl.Commands;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.abravepanda.pdl.Main;
import me.abravepanda.pdl.Utils.InventoryStringDeSerializer;
import me.abravepanda.pdl.Utils.Messages;

public class InventoryCommand 
{
	public static void InventoryCommand(CommandSender sender, String[] args) 
	{
			if(sender instanceof Player) 
			{
				Player p = (Player) sender;
				
				if(p.hasPermission("death.inventory.update")) 
				{
					if(args.length == 2) 
					{
						if(args[1].equalsIgnoreCase("close"))
						{
							if(Main.adminOpenInventories.containsKey(p))
							{
								UUID invUUID = Main.adminOpenInventories.get(p);
								try {
									Statement statement = Main.c.createStatement();
									String statementstring333 = "SELECT * FROM invs WHERE uuid = '" + p.getUniqueId() +"';";
									ResultSet res = statement.executeQuery(statementstring333);
									PlayerInventory i = p.getInventory();
									ItemStack[] items = i.getContents();
									String n = InventoryStringDeSerializer.itemStackArrayToBase64(items);
									if(res.next()) {
										String statementstring3 = "UPDATE invs SET uuid='" + invUUID + "', itemstack = '" + n + "' WHERE id='" + res.getInt("id") + "';";
										statement.executeUpdate(statementstring3);
									}
								} catch (SQLException e2) {
									e2.printStackTrace();
								}	
								Main.adminOpenInventories.remove(p);
									p.getInventory().clear();
									p.getInventory().setContents(Main.adminInventory.get(p));
									Main.adminInventory.remove(p);
							}
						}

						if(Main.adminOpenInventories.containsKey(p))
						{
							p.sendMessage(ChatColor.RED + "Please close your current inventory!");
							return;
						}
						
						try
						{
							OfflinePlayer ofp = Bukkit.getOfflinePlayer(args[1]);
							Statement statement = Main.c.createStatement(); 
							String statementstring = "SELECT * FROM invs WHERE uuid = '" + ofp.getUniqueId() + "';";
							ResultSet res = statement.executeQuery(statementstring);
							if(res.next())
							{
								Player target = Bukkit.getPlayer(args[1]);
								if(target != null) 
								{
		
										String itemstack = res.getString("itemstack");
											
										ItemStack[] r = InventoryStringDeSerializer.itemStackArrayFromBase64(itemstack);
										
										Main.adminInventory.put(p, p.getInventory().getContents());
										p.getInventory().clear();
										p.getInventory().setContents(r);

										Main.adminOpenInventories.put(p, target.getUniqueId());
								}
								else 
								{
									p.sendMessage(Messages.playerNotFound.replace("%target%", args[1]));
								}
							}
						}
						catch(SQLException | IOException e) 
						{
							e.printStackTrace();
						}
					}	
					return;
				}
				if(p.hasPermission("death.inventory"))
				{
					if(args.length == 2) 
					{
						try
						{
							OfflinePlayer ofp = Bukkit.getOfflinePlayer(args[1]);
							Statement statement = Main.c.createStatement(); 
							String statementstring = "SELECT * FROM invs WHERE uuid = '" + ofp.getUniqueId() + "';";
							ResultSet res = statement.executeQuery(statementstring);
							if(res.next())
							{
								Player target = Bukkit.getPlayer(args[1]);
								if(target != null) 
								{
		
										String itemstack = res.getString("itemstack");
											
										ItemStack[] r = InventoryStringDeSerializer.itemStackArrayFromBase64(itemstack);
										
										Inventory i = Bukkit.createInventory(null, 45, args[1] + "'s Inventory");
										i.setContents(r);
										p.openInventory(i);

										Main.adminOpenInventories.put(p, target.getUniqueId());
								}
								else 
								{
									p.sendMessage(Messages.playerNotFound.replace("%target%", args[1]));
								}
							}
						}
						catch(SQLException | IOException e) 
						{
							e.printStackTrace();
						}
					}
				}
				else
				{
					p.sendMessage(Messages.noPermission);
				}
			}
	}
}
