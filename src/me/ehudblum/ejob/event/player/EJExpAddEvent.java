package me.ehudblum.ejob.event.player;

import me.ehudblum.ejob.player.EPlayer;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EJExpAddEvent extends Event
{
	private double expAdded;
	private boolean leveledUp;
	private EPlayer player;
	
	public EJExpAddEvent(double ExpAdded, boolean leveledUp, EPlayer player)
	{
		this.expAdded = ExpAdded;
		this.leveledUp = leveledUp;
		this.player = player;		
	}
	
	public EPlayer getPlayer()
	{
		return this.player;
	}
	
	public double getExpAdded()
	{
		return this.expAdded;
	}
	
	public boolean getLeveledUp()
	{
		return this.leveledUp;
	}
	
	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers()
	{
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
}
