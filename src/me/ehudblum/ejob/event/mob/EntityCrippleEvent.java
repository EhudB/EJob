package me.ehudblum.ejob.event.mob;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;

public class EntityCrippleEvent extends EntityEvent implements Cancellable
{

	private static final HandlerList handlers = new HandlerList();
	private EntityDeathEvent event;
	private boolean cancelled;
	
	public EntityCrippleEvent(Entity what, EntityDeathEvent event)
	{
		super(what);
		this.event = event;
		this.cancelled = false;
	}
	
	public EntityDeathEvent getTriggerEvent()
	{
	return this.event;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
		
	}

	public HandlerList getHandlers()
	{
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
}
