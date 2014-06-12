package me.exel80.glitchpatcher.patch;

import me.exel80.glitchpatcher.gp;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class gpClicking implements Listener {
	public static gp plugin;
	public gpClicking(gp instance) {
		plugin = instance;
	}

	int signy;
	int signx;
	int signz;
	
	@EventHandler(priority = EventPriority.LOW)
	public void InventoryClick(InventoryClickEvent event)
	{
		if(!event.isCancelled())
		{
			HumanEntity ent = event.getWhoClicked();
			
			if(ent instanceof Player)
			{
				Inventory inv = event.getInventory();
					if(inv instanceof AnvilInventory)
					{
						InventoryView view = event.getView();
						int rawSlot = event.getRawSlot();
						if(rawSlot == view.convertSlot(rawSlot)){
							if(rawSlot == 2)
							{
							ItemStack item = event.getCurrentItem();
								if(item != null)
								{
									if(item.getAmount() != 1)
									{
										if(plugin.getConfigs("GlitchFix.AnvilEnc-Enable") == true) { item.setAmount(1); }
										if (plugin.getConfigs("Settings.Stats-Enable") == true) { gp.updateSQL("anvil"); }	
									}
								}
							}
						}
					}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void InteractEvent(PlayerInteractEvent event)
	{
		Player p = event.getPlayer();
		org.bukkit.World world = p.getWorld();
		
		try
		{
			if(event.getClickedBlock().getTypeId() != 0)
			{
				//HOE DIRT ABOVE WHICH THERE IS RANDOM BLOCK
				if(plugin.getConfigs("GlitchFix.HoeFix-Enable") == true)
				{
					if(p.getItemInHand().getType() == Material.WOOD_HOE || p.getItemInHand().getType() == Material.STONE_HOE ||
					  p.getItemInHand().getType() == Material.IRON_HOE || p.getItemInHand().getType() == Material.GOLD_HOE ||
					  p.getItemInHand().getType() == Material.DIAMOND_HOE)
					{		
						if(event.getClickedBlock().getTypeId() == 3)
						{
							int signy = event.getClickedBlock().getY() + 1;
							int signx = event.getClickedBlock().getX();
							int signz = event.getClickedBlock().getZ();
							if(world.getBlockAt(signx, signy, signz).getTypeId() >= 1)
							{
								if (plugin.getConfigs("Settings.Stats-Enable") == true) { gp.updateSQL("hoefix"); }					
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
		catch (Exception e) { e.getStackTrace(); }
	}

}
