package me.ehudblum.ejob.command;

import java.io.IOException;

import me.ehudblum.ejob.EJob;
import me.ehudblum.ejob.job.Job;
import me.ehudblum.ejob.player.EParty;
import me.ehudblum.ejob.player.EPlayer;
import me.ehudblum.ejob.util.EJUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EJCommandHandler{
	
	public void showJobsCommand(Player p)
	{
		EJUtil.sendMessage(p, "<green>/job list<white> - <aqua>Shows a list of all avilable jobs.");
		EJUtil.sendMessage(p, "<green>/job stats <gold>[player]<white> - <aqua>Shows the stats of a player/you.");
		EJUtil.sendMessage(p, "<green>/job join <gold>[job name]<white> - <aqua>Join job.");
		EJUtil.sendMessage(p, "<green>/job leave <white> - <aqua>Leave your job.");
		EJUtil.sendMessage(p, "<green>/job info <gold>[job]<white> - <aqua>Get the info about the job.");
		EJUtil.sendMessage(p, "<green>/job <gold>[on|off]<white> - <aqua>Set the job mode.");
		EJUtil.sendMessage(p, "<green>/job spam <gold>[on|off]<white> - <aqua>Set job spam.");
	}
	
	public void showPartyCommand(Player p)
	{
		EJUtil.sendMessage(p, "<green>/party create <gold><prefix><white> - <aqua>Create a new party.");
		EJUtil.sendMessage(p, "<green>/party info<white> - <aqua>Info on your party.");
		EJUtil.sendMessage(p, "<green>/party leave<white> - <aqua>Leave you party.");
		EJUtil.sendMessage(p, "<green>/party join <gold>[yes|no]<white> - <aqua>Accept or decline a party request.");
		EJUtil.sendMessage(p, "<green>/party kick <gold>[player]<white> - <aqua>Kick a player from the party.");
		EJUtil.sendMessage(p, "<green>/party chat <gold>[on|off]<white> - <aqua>Set party chat mode.");
		EJUtil.sendMessage(p, "<green>/party invite <gold>[player]<white> - <aqua>Invite a player to your party.");
		EJUtil.sendMessage(p, "<green>/party univite <gold>[player]<white> - <aqua>Univite a player from your party.");
	}
	
	public void sendPlayerStats(Player player, String playerName)
	{
		EPlayer p = EJob.getData().getPlayer(playerName);
		if(p != null)
		{
			if(p.getPlayerJob().getJob() != EJob.getData().defaultJob)
			{
				EJUtil.sendMessage(player, "<white>~~ [<green>Job stats for <gold>"+ p.getName()+ "<white>] ~~");
				EJUtil.sendMessage(player, "<darkaqua> Current job: <gold>" + p.getPlayerJob().getJob().getJobName() + "<darkaqua>.");
				EJUtil.sendMessage(player, "<darkaqua> Current exp: <green>"+p.getPlayerJob().getEXP() + "<darkaqua>.");
				EJUtil.sendMessage(player, "<darkaqua> Level: <green>"+p.getPlayerJob().getLevel() + "<darkaqua>.");
				EJUtil.sendMessage(player, "<darkaqua> Exp For next level: <green>" + p.getPlayerJob().getExpForNextLevel() + "<darkaqua>.");
			}
			else
			{
				EJUtil.sendMessage(player, "<red>You are not working.");
			}
		}
		else
		{
			EJUtil.sendMessage(player, "<red>Unkonw player, Check you spelling.");
		}
	}
	
	public void sendJobList(Player p)
	{
		String list = "";
		for(Job job : EJob.getData().getJobs())
		{
			if(job.isListed())
			{
				list += job.getJobName()+", "; 
			}
		}
		if(list != "")
		{
			EJUtil.sendMessage(p, "<white>~~ [<green>Job list:<white>] ~~");
			EJUtil.sendMessage(p, "<darkgreen>" + list);
			EJUtil.sendMessage(p, "<aqua>To get info about a job type <green>/job info [job name]");
		}
	}
	
	public void toggleJobMode(Player player, boolean modeToSet)
	{
		EPlayer p = EJob.getData().getPlayer(player.getName());
		if(p != null)
		{
			if(p.getPlayerJob().getJobMode() != modeToSet)
			{
				p.getPlayerJob().setJobMode(modeToSet);
				EJUtil.sendMessage(player, "<green>Job state has been successful changed to <gold>"+ (modeToSet ? "on" : "off") + "<green>.");
			}
			else
			{
				EJUtil.sendMessage(player, "<red>The job state is already <gold>"+ (modeToSet ? "on" : "off")+ "<red>.");
			}
		}
		else
		{
			EJUtil.sendMessage(player, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
	
	public void toggleSpamMode(Player player, boolean modeToSet)
	{
		EPlayer p = EJob.getData().getPlayer(player.getName());
		if(p != null)
		{
			if(p.getJobSpam()!= modeToSet)
			{
				p.setjobSpam(modeToSet);
				EJUtil.sendMessage(player, "<green>Job spam state has been successful changed to <gold>"+ (modeToSet ? "on" : "off") + "<green>.");
			}
			else
			{
				EJUtil.sendMessage(player, "<red>The job state is already <gold>"+ (modeToSet ? "on" : "off")+ "<red>.");
			}
		}
		else
		{
			EJUtil.sendMessage(player, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
	
	public void leaveJob(Player p)
	{
		EPlayer player = EJob.getData().getPlayer(p.getName());
		if(player != null)
		{
			if(player.getPlayerJob().getJob() != EJob.getData().defaultJob)
			{
				EJUtil.sendMessage(p, "<green>You have left the job: <gold>"+ player.getPlayerJob().getJob().getJobName());
				player.setPlayerJob(EJob.getData().defaultJob);
			}
			else
			{
				EJUtil.sendMessage(p, "<red>You dont have a job.");
			}
		}
		else
		{
			EJUtil.sendMessage(p, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
	
	public void joinJob(Player p, String jobName)
	{
		EPlayer player = EJob.getData().getPlayer(p.getName());
		if(player != null)
		{
			Job job =  EJob.getData().getJob(jobName);
			if(job != null)
			{
				if(player.getPlayerJob().getJob() == EJob.getData().defaultJob)
				{
					player.setPlayerJob(job);
					EJUtil.sendMessage(p, "<green>You have joined the job: <gold>"+ job.getJobName() + "<green>.");
				}
				else
				{
					EJUtil.sendMessage(p, "<red>You alredy have a job.");
				}
			}
			else
			{
				EJUtil.sendMessage(p, "<red>Not a valid job.");
			}
		}
		else
		{
			EJUtil.sendMessage(p, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
	
	
	public void showInfo(Player p, String jobName)
	{
		Job job = EJob.getData().getJob(jobName);
		if(job != null && job.isListed())
		{
			EJUtil.sendMessage(p,"<white>~~ <<green>"+ job.getJobName() +" info:<white>> ~~");
			EJUtil.sendMessage(p,"<gold>description: <blue>"+ job.getDesc());
		}
		else
		{
			EJUtil.sendMessage(p, "<red>Not a valid job.");
		}
	}
	
	public void createParty(Player p, String prefix)
	{
		EPlayer player = EJob.getData().getPlayer(p.getName());
		if(player != null)
		{
			if(player.getPlayerParty() == null)
			{
				if(EJob.getData().isValidPrefix(prefix))
				{
					if(prefix.length() <= 3)
					{
						player.createParty(prefix);
						EJUtil.sendMessage(p, "<green>You have created a party.");
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Prefix must be 3 letters or less.");
					}
				}
				else
				{
					EJUtil.sendMessage(p, "<red>This prefix is already taken.");
				}
			}
			else
			{
				EJUtil.sendMessage(p, "<red>You are alredy in a party.");
			}
		}
		else
		{
			EJUtil.sendMessage(p, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
	
	public void inviteToParty(Player p, String invited)
	{
		EPlayer pl = EJob.getData().getPlayer(p.getName());
		if(pl != null)
		{
			EPlayer invitedPlayer = EJob.getData().getPlayer(invited);
			if(invitedPlayer != null)
			{
				Player player = Bukkit.getPlayer(invited);
				if(player != null && player.isOnline())
				{
					if(invitedPlayer.getPlayerParty() == null)
					{
						if(pl.getPlayerParty() != null && pl.getPlayerParty().getPartyLeader().getName().equalsIgnoreCase(p.getName()))
						{
							if(pl.getPlayerParty().getSize() <= pl.getMaxPartySlots())
							{
								EJUtil.sendMessage(player, "<blue>You have been invited to <gold>" + p.getName() + "'s <blue>party");
								invitedPlayer.setPendingParty(pl.getPlayerParty());
								EJUtil.sendMessage(player, "<blue>/party join [yes|no] to answer.");
								EJUtil.sendMessage(p, "<yellow>"+ player.getName() +" has been invited to the party");
							}
							else
							{
								EJUtil.sendMessage(p, "<red>You dont have enough space in your party to invite people.");
							}
						}
						else
						{
							EJUtil.sendMessage(p, "<red>You are not in a party or not the leader.");
						}
					}
					else
					{
						EJUtil.sendMessage(p, "<red>This player already have a party.");
					}
				}
				else
				{
					EJUtil.sendMessage(p, "<red>Player is not online.");
				}
			}
			else 
			{
				EJUtil.sendMessage(p, "<red>Unknown Player.");
			}
		}
		else
		{
			EJUtil.sendMessage(p, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
	
	public void uninviteToParty(Player p, String uninvited)
	{
		EPlayer pl = EJob.getData().getPlayer(p.getName());
		if(pl != null)
		{
			EPlayer uninvitedPlayer = EJob.getData().getPlayer(uninvited);
			if(uninvited != null)
			{
				Player player = Bukkit.getPlayer(uninvited);
				if(player != null && player.isOnline())
				{
					if(uninvitedPlayer.getPendingParty() == pl.getPlayerParty())
					{
						if(pl.getPlayerParty() != null && pl.getPlayerParty().getPartyLeader().getName().equalsIgnoreCase(p.getName()))
						{
							EJUtil.sendMessage(player, "<blue>You have been uninvited from <gold>" + p.getName() + "'s <blue>party");
							uninvitedPlayer.setPendingParty(null);
							EJUtil.sendMessage(p, "<yellow>"+ player.getName() +" has been uninvited from the party");
						}
						else
						{
							EJUtil.sendMessage(p, "<red>You are not in a party or not the leader.");
						}
					}
					else
					{
						EJUtil.sendMessage(p, "<red>This player is not invited.");
					}
				}
				else
				{
					EJUtil.sendMessage(p, "<red>Player is not online.");
				}
			}
			else 
			{
				EJUtil.sendMessage(p, "<red>Unknown Player.");
			}
		}
		else
		{
			EJUtil.sendMessage(p, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
	
	public void partyLeave(Player p)
	{
		EPlayer player = EJob.getData().getPlayer(p.getName());
		if(player != null)
		{
			if(player.getPlayerParty() != null)
			{
				if(player.getPlayerParty().getPartyLeader().getName().equalsIgnoreCase(p.getName()))
				{
					EParty party = player.getPlayerParty();
					for(String member : party.getPartyMembers())
					{
						EPlayer pl = EJob.getData().getPlayer(member);
						if(pl != null)
						{
							pl.setPlayerParty(null);
							try {
								EJob.getData().logoutPlayer(pl);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						EJUtil.sendMessage(Bukkit.getPlayer(member), "<red>The party leader of your party has disband it.");
					}
					EJob.getData().deleteParty(party);
				}
				else
				{
					EJUtil.sendMessage(p, "<green>You have left the party.");
					EParty party = EJob.getData().getParty(player.getPlayerParty().getPartyLeader().getName());
					party.removePlayer(player);
					player.setPlayerParty(null);
				}
			}
			else
			{
				EJUtil.sendMessage(p, "<red>You are not in a party");
			}
		}
		else
		{
			EJUtil.sendMessage(p, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
	
	public void joinParty(Player p, boolean accept)
	{
		EPlayer player  = EJob.getData().getPlayer(p.getName());
		if(player != null)
		{
			if(player.getPendingParty() != null)
			{
				if(player.getPlayerParty() == null)
				{
					if(accept)
					{
						Player partyLeader = Bukkit.getPlayer(player.getPendingParty().getPartyLeader().getName());
						EPlayer pl = EJob.getData().loadPlayer(partyLeader.getName());
						if(pl.getMaxPartySlots() >= pl.getPlayerParty().getSize())
						{
							player.setPlayerParty(player.getPendingParty());
							EJUtil.sendMessage(p, "<yellow>You have joined the party");
							player.getPlayerParty().addMember(p.getName());
							player.setPendingParty(null);
							if(partyLeader != null && partyLeader.isOnline())
							{
								EJUtil.sendMessage(partyLeader, "<yellow>"+p.getName()+" has joined the party");
							}
							try {
								EJob.getData().saveParty(player.getPlayerParty());
							} catch (IOException e) {
								
							}
						}
						else
						{
							EJUtil.sendMessage(p, "<red>The party is already Full.");
						}
					}
					else
					{
						EJUtil.sendMessage(p, "<yellow>The party request has been decliend");
					}
				}
				else 
				{
					EJUtil.sendMessage(p, "<red>Your are already in a party");
				}
			}
			else
			{
				EJUtil.sendMessage(p, "<red>No pending party.");
			}
		}
		else
		{
			EJUtil.sendMessage(p, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
	
	public void kickPlayerParty(Player p, String playerToKick)
	{
		EPlayer player = EJob.getData().getPlayer(p.getName());
		if(player != null)
		{
			EPlayer kickedPlayer = EJob.getData().getPlayer(playerToKick);
			if(kickedPlayer != null)
			{
				if(kickedPlayer.getPlayerParty() != null && kickedPlayer.getPlayerParty().equals(player.getPlayerParty()))
				{
					if(player.getPlayerParty().getPartyLeader().getName().equalsIgnoreCase(p.getName()))
					{
						if(!player.getPlayerParty().getPartyLeader().getName().equalsIgnoreCase(playerToKick))
						{
							Player pl = Bukkit.getPlayer(playerToKick);
							EParty party = EJob.getData().getParty(player.getPlayerParty().getPartyLeader().getName());
							if(pl != null && pl.isOnline())
							{
								EJUtil.sendMessage(pl, "<blue>You got kicked out of the party.");
							}
							EJUtil.sendMessage(p, "<green>The player <gold>" + playerToKick + "<blue> has been kicked out of the party.");
							player.getPlayerParty().removePlayer(kickedPlayer);
							party.removePlayer(kickedPlayer);
							kickedPlayer.setPlayerParty(null);
						}
						else
						{
							EJUtil.sendMessage(p, "<red>Wont work :). try leaving instead.");
						}

					}
					else
					{
						EJUtil.sendMessage(p, "<red>You are not the party leader.");
					}
				}
				else
				{
					EJUtil.sendMessage(p, "<red>The player is not in the same party.");
				}
			}
			else
			{
				EJUtil.sendMessage(p, "<red>Unknown player.");
			}
		}
		else
		{
			EJUtil.sendMessage(p, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
	
	public void showPartyInfo(Player p)
	{
		EPlayer player = EJob.getData().getPlayer(p.getName());
		if(player != null)
		{
			EParty party = player.getPlayerParty();
			if(party != null)
			{
				EJUtil.sendMessage(p, "<white>~~ <<green>"+ party.getPartyLeader().getName() +"'s party:<white>> ~~");
				EJUtil.sendMessage(p, "<blue>Party prefix: <gold>" + party.getPrefix());
				EJUtil.sendMessage(p, "<blue>Members count/Max members: <gold>" + party.getSize() + "<blue>/<gold>" + party.getPartyLeader().getMaxPartySlots());
				EJUtil.sendMessage(p, "<darkaqua>Members: <darkgreen> " +party.getPartyMembers().toString());
				String onlinePlayers = "";
				for(EPlayer pl : party.getOnlinePlayers())
				{
					onlinePlayers += pl.getName() + ", ";
				}
				EJUtil.sendMessage(p, "<blue>Online members: <green>" + onlinePlayers);
			}
			else
			{
				EJUtil.sendMessage(p, "<red>You are not in a party.");
			}
		}
		else
		{
			EJUtil.sendMessage(p, "<red>Unknown player. contact <blue>EhudBlum<red>.");
		}
	}
}
