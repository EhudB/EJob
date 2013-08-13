package me.ehudblum.ejob.player;

import java.io.IOException;

import me.ehudblum.ejob.EJob;
import me.ehudblum.ejob.job.EJBlock;
import me.ehudblum.ejob.job.EJMob;
import me.ehudblum.ejob.job.Job;

public class EPlayer 
{
	private String playerName;
	private boolean jobSpam;
	private EParty playerParty;
	private EParty pendingParty;
	private int maxPartySlots;
	private EPlayerJob playerJob;
	
	public EPlayer(String playerName, boolean jobSpam, int maxPartySlots)
	{
		this.playerName = playerName;
		this.jobSpam = jobSpam;
		this.maxPartySlots = maxPartySlots;
		this.playerJob = new EPlayerJob(EJob.getData().defaultJob, this);
	}
	
	public EPlayer(String playerName, boolean jobSpam, int maxPartySlots, int level, long EXP, String jobName)
	{
		this.playerName = playerName;
		this.jobSpam = jobSpam;
		this.maxPartySlots = maxPartySlots;
		this.playerJob = new EPlayerJob(EJob.getData().getJob(jobName), this, level, EXP);
	}
	
	public String getName()
	{
		return this.playerName;
	}
	
	public boolean getJobSpam()
	{
		return this.jobSpam;
	}
	
	public void setjobSpam(boolean jobSpam)
	{
		this.jobSpam = jobSpam;
	}
	
	public EParty getPlayerParty()
	{
		return this.playerParty;
	}
	
	public void setPlayerParty(EParty party)
	{
		this.playerParty = party;
	}
	
	public EParty getPendingParty()
	{
		return this.pendingParty;
	}
	
	public void setPendingParty(EParty party)
	{
		this.pendingParty = party;
	}
	
	public int getMaxPartySlots()
	{
		return this.maxPartySlots;
	}
	
	public void setMaxPartySlots(int maxPartySlots)
	{
		this.maxPartySlots = maxPartySlots;
	}
	
	public EPlayerJob getPlayerJob()
	{
		return this.playerJob;
	}
	
	public void setPlayerJob(Job job)
	{
		if(!EJob.getData().getJob(job).equals(null))
			this.playerJob = new EPlayerJob(job,this);
	}
	
	public void setPlayerJob(EPlayerJob job)
	{
		this.playerJob = job;
	}
	
	public boolean hasActiveJob()
	{
		if(playerJob != null && playerJob.getJobMode())
			return true;
		return false;
	}
	
	public void createParty(String prefix)
	{
		EParty party = new EParty(this, prefix);
		party.addMember(this.getName());
		this.setPlayerParty(party);
		EJob.getData().addParty(party);
	}
	
	public void logout()
	{
		try {
			EJob.getData().logoutPlayer(this);
		} catch (IOException e) {
			EJob.logInfo("problem saving player: " + this.playerName);
		}
	}
	
	public boolean killMob(String mobName)
	{
		EJMob mob = this.getPlayerJob().getJob().getMob(mobName);
		if(mob != null)
		{
			if(this.hasActiveJob())
			{
				if(getPlayerParty() != null)
					this.playerParty.shareExp(this, mob.getExpForMob());
				else
					this.getPlayerJob().addEXP(mob.getExpForMob());
				return true;
			}
		}
		return false;
	}
	
	public boolean breakBlock(int id, int data)
	{
		EJBlock block = this.getPlayerJob().getJob().getBlock(id, data);
		if(block != null)
		{
			if(this.hasActiveJob())
			{
				if(getPlayerParty() != null)
					this.playerParty.shareExp(this, block.getExpForBlock());
				else
					this.getPlayerJob().addEXP(block.getExpForBlock());
				return true;
			}
		}
		return false;
	}
}
