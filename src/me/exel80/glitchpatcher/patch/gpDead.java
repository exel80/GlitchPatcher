package me.exel80.glitchpatcher.patch;

import me.exel80.glitchpatcher.gp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;

public class gpDead implements Listener {
	public static gp plugin;
	public gpDead(gp instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.LOW)
    public void onDead(PlayerDeathEvent event)
	{
		if(plugin.getConfigs("GlitchFix.InfinityInvisible-Enable") == true)
		{
			Player p = event.getEntity();
			for (PotionEffect effect : p.getActivePotionEffects())
			{
				p.removePotionEffect(effect.getType());
			}
		}
	}

}
