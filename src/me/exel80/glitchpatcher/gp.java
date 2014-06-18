package me.exel80.glitchpatcher;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class gp extends JavaPlugin {

	private final me.exel80.glitchpatcher.patch.gpBreak gpBreak = new me.exel80.glitchpatcher.patch.gpBreak(this);
	private final me.exel80.glitchpatcher.patch.gpClicking gpClicking = new me.exel80.glitchpatcher.patch.gpClicking(this);

	public static Logger log = Logger.getLogger("Minecraft");
	public static String gplogo = ChatColor.GREEN + "[GlitchPatcher] " + ChatColor.WHITE;
	
	public void onDisable()
	{
		
	}
	public void onEnable()
	{
		Listenhandler();
		
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
	}
	
	private void Listenhandler ()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(gpBreak, this);
		pm.registerEvents(gpClicking, this);
	}
	
	public boolean onCommand (CommandSender sender, Command cmd, String lable, String[] args)
	{
		Player p = (Player) sender;
		PluginDescriptionFile pdf = this.getDescription();
		
		try
		{
			if(sender instanceof Player)
			{
				if(lable.equalsIgnoreCase("gp"))
				{
					if (args[0].toLowerCase().equalsIgnoreCase("reload") 
							&& p.hasPermission("glitchpatcher.reload"))
					{
						File conf = new File(getDataFolder(), "config.yml");
						this.getConfig().load(conf);
						p.sendMessage(gplogo + "Config is now reloaded :)");
					}				
				}
			}
			else if (sender instanceof ConsoleCommandSender)
			{
				if(lable.equalsIgnoreCase("gp"))
				{
					if (args[0].toLowerCase().equalsIgnoreCase("reload"))
					{
						File conf = new File(getDataFolder(), "config.yml");
						this.getConfig().load(conf);
						log.info("[GlitchPatcher] Config is now reloaded :)");
					}				
				}
			}
		} catch(Exception e) { e.getStackTrace(); }
		return true;
	}
	
	public String getConfigs (String name)
	{
		File path = new File(getDataFolder(), "config.yml");
		try { this.getConfig().load(path); }
		catch (Exception e) { e.getStackTrace(); }

		return this.getConfig().getString(name).toLowerCase().toString();
	}
	
	public static void patched (String msg, Player p, boolean IsGlitch)
	{
		if (IsGlitch == true)
			log.info("[GlitchPatcher] " + p.getName() + " try use " + msg + " glitch!");
		else
			log.info("[GlitchPatcher] Fix " + msg + " \"glitch\" for " + p.getName());
	}
}
