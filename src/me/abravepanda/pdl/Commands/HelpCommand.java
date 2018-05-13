package me.abravepanda.pdl.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class HelpCommand {
	
	
	public static void HelpCmd(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			p.sendMessage(ChatColor.YELLOW + "/death" + ChatColor.GOLD + " Displays the coordinates of your last death");
			p.sendMessage(ChatColor.YELLOW + "/death help" + ChatColor.GOLD + " Displays this information page");
			if(p.hasPermission("death.others")) {	
				p.sendMessage(ChatColor.YELLOW + "/death <player>" + ChatColor.GOLD + " Displays the co-ordinates of players last death" + ChatColor.RED + " (death.others)");
			}
			if(p.hasPermission("death.teleport")) {	
				p.sendMessage(ChatColor.YELLOW + "/death tp <player>" + ChatColor.GOLD + " Teleport to players last death location" + ChatColor.RED + " (death.teleport)");
			}
			if(p.hasPermission("death.restore")) {	
				p.sendMessage(ChatColor.YELLOW + "/death restore <player>" + ChatColor.GOLD + " Restores players last inventory" + ChatColor.RED + " (death.restore)");
			}
			if(p.hasPermission("death.inventory") || p.hasPermission("death.inventory.update")) {	
				p.sendMessage(ChatColor.YELLOW + "/death inventory <player>" + ChatColor.GOLD + " Displays players last inventory" + ChatColor.RED + " (death.inventory)");
			}
			
		}
		if(sender instanceof ConsoleCommandSender) {
			ConsoleCommandSender console = Bukkit.getConsoleSender();
			
			console.sendMessage(ChatColor.YELLOW + "/death" + ChatColor.GOLD + " Displays the coordinates of your last death");
			console.sendMessage(ChatColor.YELLOW + "/death help" + ChatColor.GOLD + " Displays this information page");
			console.sendMessage(ChatColor.YELLOW + "/death <player>" + ChatColor.GOLD + " Displays the co-ordinates of players last death" + ChatColor.RED + " (death.others)");
			console.sendMessage(ChatColor.YELLOW + "/death tp <player>" + ChatColor.GOLD + " Teleport to players last death location" + ChatColor.RED + " (death.teleport)");
			console.sendMessage(ChatColor.YELLOW + "/death restore <player>" + ChatColor.GOLD + " Restores players last inventory" + ChatColor.RED + " (death.restore)");
			console.sendMessage(ChatColor.YELLOW + "/death inventory <player>" + ChatColor.GOLD + " Displays players last inventory" + ChatColor.RED + " (death.inventory)");
		}
		
		
	}

}
