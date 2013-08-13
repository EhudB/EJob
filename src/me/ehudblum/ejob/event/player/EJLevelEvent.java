package me.ehudblum.ejob.event.player;

import me.ehudblum.ejob.job.Job;
import me.ehudblum.ejob.player.EPlayer;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EJLevelEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private final int level;
    private final EPlayer player;
    private final Job job;
    
 
    public EJLevelEvent(final EPlayer player, final int level, final Job job) {
        this.player = player;
        this.level = level;
        this.job = job;
    }
 
    public int getLevel()
    {
    	return this.level;
    }
    
    public EPlayer getPlayer()
    {
    	return this.player;
    }
    
    public Job getJob()
    {
    	return this.job;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
