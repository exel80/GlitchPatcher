package me.exel80.glitchpatcher.patch;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.exel80.glitchpatcher.gp;

public class gpDisable implements Listener {
	public static gp plugin;
	public gpDisable(gp instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerCommandPreprocess (PlayerCommandPreprocessEvent event) {
		for(int i=0; i <= plugin.getConfig().getStringList("Disable.Commands").size(); i++) {
			if(event.getMessage().contains(plugin.getConfig().getStringList("Disable.Commands").get(i).toString())) {
				event.getPlayer().sendMessage( ChatColor.RED + "This command is disabled by Server!");
				event.setCancelled(true);
			}
		}
	}
}
