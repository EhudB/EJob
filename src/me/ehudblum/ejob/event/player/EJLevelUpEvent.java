package me.ehudblum.ejob.event.player;

import org.bukkit.event.HandlerList;

import me.ehudblum.ejob.job.Job;
import me.ehudblum.ejob.player.EPlayer;

public class EJLevelUpEvent extends EJLevelEvent
{
	private double expForNextLevel;
	private double rewardForLevel;
	
	public EJLevelUpEvent(final EPlayer player, final int level, final Job job, final double expToNextLevel, final double reward)
	{
		super(player, level, job);
		this.expForNextLevel = expToNextLevel;
		this.rewardForLevel = reward;
	}
	
	public double getExpForNextLevel()
	{
		return this.expForNextLevel;
	}
	
	public double getRewardForLevel()
	{
		return this.rewardForLevel;
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
