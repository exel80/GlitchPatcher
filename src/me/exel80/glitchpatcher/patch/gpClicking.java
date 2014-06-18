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
					
					if(rawSlot == view.convertSlot(rawSlot))
					{
						if(rawSlot == 2)
						{
							ItemStack item = event.getCurrentItem();
						
							if(item != null && item.getAmount() != 1
									&& plugin.getConfigs("GlitchPatch.AnvilEnchant.Enable").equals("true"))
							{
								item.setAmount(1);								
								if (plugin.getConfigs("Settings.Display.PatchAlert").equals("true"))
									gp.patched("AnvilEnchant", ((Player) ent).getPlayer(), true);
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
			if (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)
			{
				int b;
				try { b = event.getClickedBlock().getTypeId(); } catch (Exception ex) { b = 0; ex.getStackTrace(); }
				
				if (b != 0 && event.getClickedBlock().getType() == Material.CHEST && plugin.getConfigs("GlitchPatch.SkullsGlitch.Enable").equals("true") &&
						(event.getItem().getType() == Material.SKULL || event.getItem().getType() == Material.SKULL_ITEM))
				{
					event.setCancelled(true);
					if (plugin.getConfigs("Settings.Display.PatchAlert").equals("true"))
						gp.patched("Bedrock", p, true);
				}
			}
		}
		catch (Exception e) { e.getStackTrace(); }
	}

}
