package me.abravepanda.pdl.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.abravepanda.pdl.Utils.Messages;

public class CommandHandler implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("death")) {
		
			if(args.length != 0) {
			
				switch (args[0]) {	
					case "help": HelpCommand.HelpCmd(sender, args);
						break;
					case "restore": RestoreCommand.RestoreCmd(sender, args);
						break;
					default: sender.sendMessage(Messages.cmdNotFound);
				}		
			} else {
				HelpCommand.HelpCmd(sender, args);
			}
		}
		return true;
		
	}

}
