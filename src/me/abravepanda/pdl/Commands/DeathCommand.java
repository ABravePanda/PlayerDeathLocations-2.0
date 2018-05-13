package me.abravepanda.pdl.Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.abravepanda.pdl.Main;
import me.abravepanda.pdl.Database.DbCon;
import me.abravepanda.pdl.Utils.Messages;
import net.md_5.bungee.api.ChatColor;

public class DeathCommand 
{
	public static void DeathCommand(CommandSender sender, String[] args) 
	{
			if(sender instanceof Player) 
			{
				Player p = (Player) sender;
				
				if(args.length != 0)
				{
					if(p.hasPermission("death.others"))
					{
						String statementstring4 = "SELECT * FROM " + DbCon.maintable + " WHERE PlayerUUID = '" + Bukkit.getOfflinePlayer(args[0]).getUniqueId() +"';";
						
						try {
							Statement statement = Main.c.createStatement();
							ResultSet res = statement.executeQuery(statementstring4);
							if(res.next()) {
								p.sendMessage("§7-------[§9" + args[0] + "§7]-------");
								p.sendMessage("§6Location:");
								p.sendMessage("§6  x: §a" + res.getString("X"));
								p.sendMessage("§6  y: §a" + res.getString("Y"));
								p.sendMessage("§6  z: §a" + res.getString("Z"));
								p.sendMessage("§6  world: §a" + res.getString("world"));
								p.sendMessage("§6Reason: §a" + res.getString("DeathReason"));
								
								Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(res.getString("time"));
								String newDate = new SimpleDateFormat("EEEEE, MMMMM dd, yyyy - hh:mm:ss a z").format(date);
								
								p.sendMessage("§6Time: §a" + newDate);
								
								String finalline = "--------";
								for(int i = 0; i < args[0].length(); i++)
								{
									finalline = finalline + "-";
								}
								finalline = finalline + "--------";
								
								p.sendMessage("§7" + finalline);
							
							} else if(args.length != 0) {
								p.sendMessage(ChatColor.RED + args[0] + " hasn't died! (yet :D)");
							} else {
								p.sendMessage(ChatColor.RED + "You haven't died! (yet :D)");
							}
						} catch (SQLException | ParseException e) {
							e.printStackTrace();
						}
					}
					else
					{
						p.sendMessage(Messages.noPermission);
					}
				}
				else
				{
					String statementstring4 = "SELECT * FROM " + DbCon.maintable + " WHERE PlayerUUID = '" + p.getUniqueId() +"';";
					
					try {
						Statement statement = Main.c.createStatement();
						ResultSet res = statement.executeQuery(statementstring4);
						if(res.next()) {
							p.sendMessage("§7-------[§9" + p.getName() + "§7]-------");
							p.sendMessage("§6Location:");
							p.sendMessage("§6  x: §a" + res.getString("X"));
							p.sendMessage("§6  y: §a" + res.getString("Y"));
							p.sendMessage("§6  z: §a" + res.getString("Z"));
							p.sendMessage("§6  world: §a" + res.getString("world"));
							p.sendMessage("§6Reason: §a" + res.getString("DeathReason"));
							
							Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(res.getString("time"));
							String newDate = new SimpleDateFormat("EEEEE, MMMMM dd, yyyy - hh:mm:ss a z").format(date);
							
							p.sendMessage("§6Time: §a" + newDate);
							
							String finalline = "--------";
							for(int i = 0; i < p.getName().length(); i++)
							{
								finalline = finalline + "-";
							}
							finalline = finalline + "--------";
							
							p.sendMessage("§7" + finalline);
						
						} else {
							p.sendMessage(ChatColor.RED + "You haven't died! (yet :D)");
						}
					} catch (SQLException | ParseException e) {
						e.printStackTrace();
					}
				}
			}
	}
}
