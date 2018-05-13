package me.abravepanda.pdl.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.abravepanda.pdl.Main;

public class PickupEvent implements Listener
{
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e)
	{
		Player p = e.getPlayer();
		if(Main.adminOpenInventories.containsKey(p))
		{
			e.setCancelled(true);
		}
	}
}
