package me.ehudblum.ejob.listener;

import me.ehudblum.ejob.event.player.EJExpAddEvent;
import me.ehudblum.ejob.event.player.EJLevelUpEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EJListener implements Listener
{
	
	@EventHandler
	public void onLevelUp(EJLevelUpEvent e)
	{
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		if(p != null && p.isOnline())
		{
			p.sendMessage(ChatColor.GOLD + "~~Level Up~~");
			p.sendMessage(ChatColor.BLUE + "You have reached level: "+e.getLevel());
			p.sendMessage(ChatColor.BLUE + "Exp for next level: " + e.getExpForNextLevel());
			p.sendMessage(ChatColor.BLUE + "You have been rewarded: " + e.getRewardForLevel());
		}
	}
	
	@EventHandler
	public void onExpAdded(EJExpAddEvent e)
	{
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		if(p != null && p.isOnline())
		{
			if(e.getPlayer().hasActiveJob() && e.getPlayer().getJobSpam())
			{
				p.sendMessage(ChatColor.GREEN + "You got " + e.getExpAdded() + " exp");
			}
		}
	}
	
}
