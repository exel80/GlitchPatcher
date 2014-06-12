package me.exel80.glitchpatcher;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class gpUpdate implements Listener {
	public static gp plugin;
	public gpUpdate(gp instance) {
		plugin = instance;
	}
	
	public void Join(PlayerJoinEvent event, Player p)
	{
		if(p.hasPermission("glitchpatcher.update") || p.isOp())
		{
			
		}
	}
	
	public void update(Player p)
	{
		
	}
}
