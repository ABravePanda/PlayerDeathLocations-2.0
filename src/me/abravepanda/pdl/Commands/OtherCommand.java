package me.abravepanda.pdl.Commands;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.abravepanda.pdl.Main;
import me.abravepanda.pdl.Database.DbCon;
import net.md_5.bungee.api.ChatColor;

public class OtherCommand 
{

	public static void otherCmd(CommandSender sender, String[] args) 
	{
			
			if(sender instanceof Player) 
			{
				Player p = (Player) sender;
				
				if(p.hasPermission("death.others")) 
				{
					if(args.length > 1) 
					{
						Statement statement;
						try {
							OfflinePlayer ofp = Bukkit.getOfflinePlayer(args[1]);
							statement = Main.c.createStatement();
							ResultSet res = statement.executeQuery("SELECT * FROM " + DbCon.maintable + " WHERE PlayerUUID = '" + ofp.getUniqueId() + "';");
							while(res.next())
							{
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date = sdf.parse(res.getString("time"));
								SimpleDateFormat newDate = new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm:ss a z");
								String formattedDate = newDate.format(date);
								
								p.sendMessage(ChatColor.GRAY + "-------[" + ChatColor.DARK_GREEN + Bukkit.getPlayer(UUID.fromString(res.getString("PlayerUUID"))).getName() + ChatColor.GRAY + "]-------");
								p.sendMessage("");
								p.sendMessage(ChatColor.GRAY + "Location: " +  ChatColor.GREEN + "" +
										res.getString("world") + ": " + res.getString("X") + ", " + res.getString("Y") + ", " + res.getString("Z"));
								p.sendMessage("");
								p.sendMessage(ChatColor.GRAY + "Date: " + ChatColor.GREEN + "" + 
										formattedDate + ChatColor.GRAY + ".");
								p.sendMessage("");
								p.sendMessage(ChatColor.GRAY + "Cause of Death: " + ChatColor.GREEN + res.getString("DeathReason") + ChatColor.GRAY + "." );
								p.sendMessage("");
								p.sendMessage(ChatColor.GRAY + "Inventory: " + ChatColor.GREEN + "/death inventory " + Bukkit.getPlayer(UUID.fromString(res.getString("PlayerUUID"))).getName());
							}
						} catch (SQLException | ParseException e) {
							e.printStackTrace();
						}
						
					}
				}
			}
	}
}