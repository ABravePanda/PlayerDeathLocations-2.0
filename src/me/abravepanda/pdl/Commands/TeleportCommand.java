package me.abravepanda.pdl.Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.abravepanda.pdl.Main;
import me.abravepanda.pdl.Database.DbCon;
import me.abravepanda.pdl.Utils.Messages;
import net.md_5.bungee.api.ChatColor;

public class TeleportCommand 
{
	public static void TeleportCommand(CommandSender sender, String[] args) 
	{
			if(sender instanceof Player) 
			{
				Player p = (Player) sender;
				
				if(p.hasPermission("death.teleport")) 
				{
					String statementstring4 = "SELECT * FROM " + DbCon.maintable + " WHERE PlayerUUID = '" + Bukkit.getOfflinePlayer(args[1]).getUniqueId() +"';";
					try {
						Statement statement = Main.c.createStatement();
						ResultSet res = statement.executeQuery(statementstring4);
						if(res.next()) {
							Location l = new Location(Bukkit.getWorld(res.getString("world")), res.getDouble("X"), res.getInt("Y"), res.getDouble("Z"));
							p.teleport(l);
							p.sendMessage("§6You have been teleported to:");
							p.sendMessage("§6  x: §a" + res.getString("X"));
							p.sendMessage("§6  y: §a" + res.getString("Y"));
							p.sendMessage("§6  z: §a" + res.getString("Z"));
							p.sendMessage("§6  world: §a" + res.getString("world"));
						} else {
							p.sendMessage(ChatColor.RED + args[1] + " hasn't died! (yet :D)");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else
				{
					p.sendMessage(Messages.noPermission);
				}
			}
	}
}
