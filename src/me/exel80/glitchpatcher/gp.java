package me.exel80.glitchpatcher;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.SQLite;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class gp extends JavaPlugin {

	private final me.exel80.glitchpatcher.patch.gpBreak gpBreak = new me.exel80.glitchpatcher.patch.gpBreak(this);
	private final me.exel80.glitchpatcher.patch.gpClicking gpClicking = new me.exel80.glitchpatcher.patch.gpClicking(this);
	private final me.exel80.glitchpatcher.patch.gpDead gpDead = new me.exel80.glitchpatcher.patch.gpDead(this);
	//private final me.exel80.glitchpatcher.patch.gpHopper gpHopper = new me.exel80.glitchpatcher.patch.gpHopper(this);

	public Logger log = Logger.getLogger("Minecraft");
	public String gplogo = ChatColor.GREEN + "[GlitchPatcher] " + ChatColor.WHITE;
	
	public static SQLite sql;
	
	public void onDisable()
	{
		try	{ if(getConfigs("Settings.Stats-Enable") == true) { sql.close(); } }
		catch (Exception ex) { ex.getStackTrace(); }
	}
	public void onEnable()
	{
		Listenhandler();
		if (getConfigs("Settings.Stats-Enable") == true)
		{
			sqlConnection();  
			sqlTableCheck();
		}
				
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
	}
	
	public static void updateSQL(String update)
	{
		ResultSet result;
		try {
			result = sql.query("SELECT " + update + " FROM stats;");
			int count = result.getInt(1);
			result.close();
			count += 1;
			sql.query("UPDATE stats SET " + update + " = '" + count + "';");
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}
	
	private void Listenhandler()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(gpBreak, this);
		pm.registerEvents(gpClicking, this);
		pm.registerEvents(gpDead, this);
		//pm.registerEvents(gpHopper, this);
	}
	
	
	public void sqlConnection() 
	{
		sql = new SQLite(Logger.getLogger("Minecraft"),
		                "[GlitchPatcher] ",
		                this.getDataFolder().getAbsolutePath(), 
		                "GlitchPatcher",
		                ".sqlite");
		try {
			sql.open();
		    } catch (Exception e) {
		        this.getLogger().info(e.getMessage());
		    }
	}
	
	@SuppressWarnings("deprecation")
	public void sqlTableCheck()
	{
	    if(sql.checkTable("stats")){
	    	return;
	    }else{
	    	try {
				sql.query("CREATE TABLE stats (irondoor INT, hoefix INT, anvil INT);");
				sql.query("INSERT INTO stats(irondoor, hoefix, anvil) VALUES(0, 0, 0);");
	    	} catch (SQLException e) {
	    		log.info(e.getStackTrace()+"");
			}
	    }
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args)
	{
		Player p = (Player) sender;
		try
		{
			if(lable.equalsIgnoreCase("gp"))
			{
				if (args[0].toLowerCase().equalsIgnoreCase("stats") && getConfigs("Settings.Stats-Enable") == true)
				{
					if (p.hasPermission("glitchpatcher.stats"))
					{
						PluginDescriptionFile pdf = this.getDescription();
						ResultSet result;
						int ironcount = 0, hoescount = 0, anvcount = 0, totalcount;
						
						try {
							result = sql.query("SELECT irondoor FROM stats;");
							ironcount = result.getInt(1);
							result.close();
							result = sql.query("SELECT hoefix FROM stats;");
							hoescount = result.getInt(1);
							result.close();
							result = sql.query("SELECT anvil FROM stats;");
							anvcount = result.getInt(1);
							result.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						if(getConfigs("GlitchFix.IronDoorFix-Enable") == false) 
							ironcount = 0;
						if(getConfigs("GlitchFix.HoeFix-Enable") == false) 
							hoescount = 0;
						if(getConfigs("GlitchFix.AnvilEnc-Enable") == false)
							anvcount = 0;
						
						totalcount = ironcount + hoescount + anvcount;
						
						p.sendMessage(gplogo + "GlitchPatcher version: " + pdf.getVersion());
						p.sendMessage(ChatColor.GRAY + " - Bug fixed : " + ChatColor.WHITE + totalcount);
						if(getConfigs("GlitchFix.IronDoorFix-Enable") == true)
							p.sendMessage(ChatColor.GRAY + "   + Iron Door : " + ChatColor.WHITE + ironcount);
						if(getConfigs("GlitchFix.HoeFix-Enable") == true) 
							p.sendMessage(ChatColor.GRAY + "   + Hoe Fix : " + ChatColor.WHITE + hoescount);
						if(getConfigs("GlitchFix.AnvilEnc-Enable") == true) 
							p.sendMessage(ChatColor.GRAY + "   + Anvil Fix : " + ChatColor.WHITE + anvcount);
						/*p.sendMessage(ChatColor.GRAY + " - Dube fixed : " + ChatColor.WHITE + "0");*/
					}
				}
				if (args[0].toLowerCase().equalsIgnoreCase("reload") && p.hasPermission("glitchpatcher.reload"))
				{
					File conf = new File(getDataFolder(), "config.yml");
					this.getConfig().load(conf);
					p.sendMessage(gplogo + "Config is now reloaded :)");
				}
				if (args[0].toLowerCase().equalsIgnoreCase("reset") && p.hasPermission("glitchpatcher.reset"))
				{
					if (getConfigs("Settings.Stats-Enable") == true)
					{
						ResultSet result;
						try {
							result = sql.query("UPDATE stats SET irondoor = '0';");
							result.close();
							result = sql.query("UPDATE stats SET hoefix = '0';");
							result.close();
							result = sql.query("UPDATE stats SET anvil = '0';");
							result.close();
							p.sendMessage(gplogo + "Stats is now reseted!");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}	
		} catch(Exception e) { e.getStackTrace(); }
		return true;
	}
	
	public Boolean getConfigs(String name)
	{
		File path = new File(getDataFolder(), "config.yml");
		try {
			this.getConfig().load(path);
		} catch (Exception e) { e.getStackTrace(); }
		
		String getBool = this.getConfig().getString(name);
		
		if (getBool == "true")
		{
			return true;
		}
		else { return false; }
	}
}
