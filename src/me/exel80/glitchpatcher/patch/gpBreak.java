package me.exel80.glitchpatcher.patch;

import me.exel80.glitchpatcher.gp;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class gpBreak implements Listener {
	public static gp plugin;
	public gpBreak(gp instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onBlockBreak(BlockBreakEvent event)
	{
		Block b = event.getBlock();
		Player p = event.getPlayer();
		org.bukkit.World world = p.getWorld();
		GameMode gm = p.getGameMode();
		
		//IRON DOOR GLITCH FIX
		if(plugin.getConfigs("GlitchFix.IronDoorFix-Enable") == true)
		{
			if(b.getTypeId() == 71 && b.getDrops().isEmpty() == false)
			{		
				if(gm == GameMode.SURVIVAL)
				{
					if(p.getItemInHand().getTypeId() == 257 || p.getItemInHand().getTypeId() == 278 ||
					   p.getItemInHand().getTypeId() == 285 || p.getItemInHand().getTypeId() == 270 ||
					   p.getItemInHand().getTypeId() == 274)
					{
						//NOTHING...
					}
					else 
					{
						ItemStack item = new ItemStack(Material.IRON_DOOR, 1);
						world.dropItem(b.getLocation(), item);
						if (plugin.getConfigs("Settings.Stats-Enable") == true) { gp.updateSQL("irondoor"); }
					}
				}
			}
		}
	}

}
