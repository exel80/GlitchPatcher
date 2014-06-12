package me.exel80.glitchpatcher.patch;

import me.exel80.glitchpatcher.gp;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class gpHopper implements Listener {
	public static gp plugin;
	public gpHopper(gp instance) {
		plugin = instance;
	}
	
	@EventHandler
    public void onHopper(InventoryPickupItemEvent event)
	{
		
	}

}
