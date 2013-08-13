package me.ehudblum.ejob.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Job2WG
{
	public static WorldGuardPlugin wg;
	
	public static void setUpWorldGuard() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return; // Maybe you want throw an exception instead
	    }
	    wg = (WorldGuardPlugin) plugin;
	}
	
	public static boolean canBuild(Player p, Location l)
	{
		return wg.canBuild(p,l);
	}
	
	
}
