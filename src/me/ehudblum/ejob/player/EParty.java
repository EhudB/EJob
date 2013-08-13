package me.ehudblum.ejob.player;

import java.util.ArrayList;
import java.util.List;

import me.ehudblum.ejob.EJob;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EParty 
{
	private EPlayer partyLeader;
	private ArrayList<String> members;
	private String prefix;
	
	public EParty(EPlayer partyLeader, String prefix)
	{
		this.partyLeader = partyLeader;
		this.prefix = prefix;
		this.members = new ArrayList<String>();
	}
	
	public EParty(EPlayer partyLeader, String prefix, List<String> members)
	{
		this.partyLeader = partyLeader;
		this.prefix = prefix;
		this.members = (ArrayList<String>) members;
	}
	
	public EPlayer getPartyLeader()
	{
		return this.partyLeader;
	}
	
	public List<String> getPartyMembers()
	{
		return this.members;
	}
	
	public void addMember(String player)
	{
		if(!members.contains(player))
			members.add(player);
	}
	
	public void removePlayer(EPlayer player)
	{
		if(this.members.contains(player.getName()))
			this.members.remove(player.getName());
	}
	
	public String getPrefix()
	{
		return this.prefix;
	}
	
	public List<EPlayer> getOnlinePlayers()
	{
		List<EPlayer> result = new ArrayList<EPlayer>();
		for(String player : members)
		{
			Player p = Bukkit.getServer().getPlayer(player);
			if(p != null && p.isOnline())
			{
				EPlayer pl = EJob.getData().getPlayer(player);
				result.add(pl);
			}
		}
		return result;
	}
	
	public int getSize()
	{
		return this.members.size();
	}
	
	public ArrayList<EPlayer> getNearbyMembers(EPlayer EPlayer)
	{
		ArrayList<EPlayer> result = new ArrayList<EPlayer>();
		Player player = Bukkit.getServer().getPlayer(EPlayer.getName());
		if(player != null && player.isOnline())
		{
			List<EPlayer> onlineMembers = this.getOnlinePlayers();
			for(EPlayer onlinePlayer : onlineMembers)
			{
				Player member = Bukkit.getPlayer(onlinePlayer.getName());
				if(member != null && member.isOnline() && !player.getName().equalsIgnoreCase(member.getName())
						&& player.getLocation().getWorld() == member.getLocation().getWorld() &&
						player.getLocation().distance(member.getLocation()) <= EJob.getData().partyRadius
						&& onlinePlayer.hasActiveJob() && onlinePlayer.getPlayerJob().getJob() != EJob.getData().defaultJob)
				{
					result.add(onlinePlayer);
				}
					
			}
		}
		return result;
	}
	
	public double shareExp(EPlayer player, double exp)
	{
		EPlayer earningPlayer = player;
		ArrayList<EPlayer> nearbyMembers = getNearbyMembers(earningPlayer);
		if (!nearbyMembers.isEmpty())
		{
			double expSelf = exp * EJob.getData().partyShareSelf /100F;
			double expShare = (exp * EJob.getData().partyShareOthers / 100.0D)/(double)nearbyMembers.size();
			earningPlayer.getPlayerJob().addEXP(expSelf);
			for (EPlayer member : nearbyMembers)
			{
				member.getPlayerJob().addEXP(expShare);
			}
			return expSelf;
		}
		else{
			earningPlayer.getPlayerJob().addEXP(exp);
			return exp;
		}
	}
}
