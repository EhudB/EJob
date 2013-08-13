package me.ehudblum.ejob.job;

import java.util.ArrayList;

import me.ehudblum.ejob.util.formulaSolver;

import org.bukkit.ChatColor;

public class Job
{
	private String jobName;
	private String desc;
	private String expFormula;
	private String rewardFormula;
	private ChatColor color;
	private ArrayList<EJBlock> blocks;
	private ArrayList<EJMob> mobs;
	private boolean isListed;
	
	public Job(String jobName,String desc, String expFormula, String rewardFormula, ChatColor color, boolean isListed)
	{
		this.jobName = jobName;
		this.desc = desc;
		this.expFormula = expFormula;
		this.rewardFormula = rewardFormula;
		this.color = color;
		this.blocks = new ArrayList<EJBlock>();
		this.mobs = new ArrayList<EJMob>();
		this.isListed = isListed;
	}
	
	public Job(String jobName,String desc, String expFormula, String rewardFormula, ChatColor color,
			ArrayList<EJBlock> blocks, ArrayList<EJMob> mobs, boolean isListed)
	{
		this.jobName = jobName;
		this.desc = desc;
		this.expFormula = expFormula;
		this.rewardFormula = rewardFormula;
		this.color = color;
		this.blocks = blocks;
		this.mobs = mobs;
		this.isListed = isListed;
	}
	
	public String getJobName()
	{
		return this.jobName;
	}
	
	public boolean isListed()
	{
		return isListed;
	}
	
	public String getDesc()
	{
		return this.desc;
	}
	
	public String getExpFromula()
	{
		return this.expFormula;
	}
	
	public String getRewardFormula()
	{
		return this.rewardFormula;
	}
	
	public ChatColor getColor()
	{
		return this.color;
	}
	
	public boolean isMobToJob(String mobName)
	{
		for(EJMob mob : mobs)
			if(mob.getMobName() ==  mobName)
				return true;
		return false;
	}
	
	public boolean isBlockToJob(int id)
	{
		for(EJBlock block : blocks)
			if(block.getId() ==  id)
				return true;
		return false;
	}
	
	public EJBlock getBlock(int id)
	{
		for(EJBlock block : blocks)
			if(block.getId() ==  id)
				return block;
		return null;
	}
	
	public EJBlock getBlock(int id, int data)
	{
		for(EJBlock block : blocks)
			if(block.getId() ==  id && block.getData() == data)
				return block;
		return null;
	}
	
	public EJMob getMob(String mobName)
	{
		for(EJMob mob : mobs)
			if(mob.getMobName().equalsIgnoreCase(mobName))
			{
				return mob;
			}
		return null;
	}
	
	public double getExpForLevel(int level)
	{
		double result = 0;
		try
		{
			result = (Double) formulaSolver.solveFormula(expFormula, level);
		}
		catch(NumberFormatException nfe)
		{
			
		}
		return result;
	}
	
	public double getRewardForLevel(int level)
	{
		double result = 0;
		result = (Double) formulaSolver.solveFormula(rewardFormula, level);
		return result;
	}
}
