package me.ehudblum.ejob.player;

import org.bukkit.Bukkit;

import me.ehudblum.ejob.api.Job2Vault;
import me.ehudblum.ejob.event.player.EJExpAddEvent;
import me.ehudblum.ejob.event.player.EJLevelUpEvent;
import me.ehudblum.ejob.job.Job;

public class EPlayerJob
{
	private Job job;
	private int level;
	private double EXP;
	private boolean jobModeOn;
	private double expModifier;
	private double rewardModifier;
	private double expForNextLevel;
	private EPlayer player;
	
	public EPlayerJob(Job job, EPlayer player)
	{
		this.job = job;
		this.level = 1;
		this.EXP = 0;
		this.jobModeOn = true;
		this.player = player;
		this.expModifier = 1;
		this.rewardModifier = 1;
		this.expForNextLevel = job.getExpForLevel(level + 1);
	}
	
	public EPlayerJob(Job job, EPlayer player, int level, long EXP)
	{
		this.job = job;
		this.level = level;
		this.EXP = EXP;
		this.jobModeOn = true;
		this.player = player;
		this.expModifier = 1;
		this.rewardModifier = 1;
		this.expForNextLevel = job.getExpForLevel(this.level + 1);
	}
	
	public Job getJob()
	{
		return job;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public double getEXP()
	{
		return EXP;
	}
	
	public void addEXP(double EXPToAdd)
	{
		boolean leveldUp = false;
		this.EXP += EXPToAdd*expModifier;
		if(EXP >= expForNextLevel)
		{
			leveldUp = true;
			this.LevelUp();
		}
		EJExpAddEvent e = new EJExpAddEvent(EXPToAdd, leveldUp, player);
		Bukkit.getPluginManager().callEvent(e);
	}
	
	public void setJobMode(boolean mode)
	{
		this.jobModeOn = mode;
	}
	
	public boolean getJobMode()
	{
		return this.jobModeOn;
	}
	
	public EPlayer getPlayer()
	{
		return this.player; 
	}
	
	public double getExpModifier()
	{
		return this.expModifier;
	}
	
	public double getRewardModifier()
	{
		return this.rewardModifier;
	}
	
	public void setExpModifer(double expModifier)
	{
		this.expModifier = expModifier;
	}
	
	public void setRewardModifier(double rewardModifier)
	{
		this.rewardModifier = rewardModifier;
	}
	
	public double getExpForNextLevel()
	{
		return this.expForNextLevel;
	}
	
	public void LevelUp()
	{
		level++;
		this.expForNextLevel = job.getExpForLevel(level + 1);
		this.giveRewards(job.getRewardForLevel(level));
		EJLevelUpEvent e = new EJLevelUpEvent(player, level, job, expForNextLevel, job.getRewardForLevel(level));
		Bukkit.getPluginManager().callEvent(e);
	}
	
	public void giveRewards(double balance)
	{
		Job2Vault.depositPlayer(player.getName(), balance);
	}
}
