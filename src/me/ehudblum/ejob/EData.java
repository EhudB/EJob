package me.ehudblum.ejob;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.ehudblum.ejob.job.EJBlock;
import me.ehudblum.ejob.job.EJMob;
import me.ehudblum.ejob.job.Job;
import me.ehudblum.ejob.player.EParty;
import me.ehudblum.ejob.player.EPlayer;

public class EData
{
	private ArrayList<EPlayer> players = new ArrayList<EPlayer>();
	private ArrayList<EParty> parties = new ArrayList<EParty>();
	private ArrayList<Job> jobs = new ArrayList<Job>();
	private ArrayList<String> prefixes = new ArrayList<String>();
	private ArrayList<String> forbiddenPrefixes = new ArrayList<String>();
	
	//party stuff
	public int partyDefaultSlots = 5;
	public int maxPartySlots = 1000;
	public double partyShareSelf = 70;
	public double partyShareOthers = 40;
	public double partyRadius = 25;
	
	//job stuff
	public Job defaultJob = null;
	public String defaultExpFormula = "150*(1.34^level)";
	public String defaultRewardFormula = "9.8*(1.2^level)";
	
	public void load()
	{
		List<String> jobs = EJob.getInstance().getConfig().getStringList("jobs");
		for(String str : jobs)
		{
			File file = new File(EJob.getInstance().getDataFolder() + "/jobs",str+".yml");
			if(file.exists())
			{
				loadJob(str, file);
			}
			else
			{
				EJob.logInfo("Failed to load job: " + str);
			}
		}
		File folder = new File(EJob.getInstance().getDataFolder() + "/parties");
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles.length >= 0)
			parties = loadParties(listOfFiles);
		this.loadPrefixes();
		ConfigurationSection partySection = EJob.getInstance().getConfig().getConfigurationSection("party");
		partyDefaultSlots = partySection.getInt("default-slots",3);
		maxPartySlots = partySection.getInt("max-slots", 1000);
		partyShareSelf = partySection.getInt("share-self", 70);
		partyShareOthers = partySection.getInt("share-others", 30);
		partyRadius = partySection.getInt("radius", 25);
	}
	
	public void loadOnlinePlayers(Player[] players)
	{
		for(int i = 0; i <players.length; i++)
		{
			this.players.add(this.loadPlayer(players[i].getName()));
			
		}
	}

	public void saveAllOnlinePlayers(Player[] players)
	{
		for(int i = 0; i <players.length; i++)
		{
			try {
				logoutPlayer(this.getPlayer(players[i].getName()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void saveAllParties()
	{
		for(EParty party : parties)
		{
			try {
				this.saveParty(party);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void savePlayer(EPlayer player)
	{
		try {
			logoutPlayer(player);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<EParty> loadParties(File[] listOfFiles) {
		ArrayList<EParty> partys = new ArrayList<EParty>();
		for(int i = 0; i < listOfFiles.length; i++)
		{
			String partyLeader = listOfFiles[i].getName().substring(0,  listOfFiles[i].getName().length() -4);
			EParty party = this.loadParty(partyLeader);
			if(party != null)
			{
				partys.add(party);
			}
		}
		return partys;
	}
	
	public void loadPrefixes()
	{
		for(EParty party : parties)
		{
			if(party != null && party.getPrefix() != null)
				this.prefixes.add(party.getPrefix());
		}
	}

	public ArrayList<Job> getJobs()
	{
		return this.jobs;
	}
	
	public void loadJob(String job, File file)
	{
		FileConfiguration cFile = YamlConfiguration.loadConfiguration(file);
		String jobName = job;
		String desc = cFile.getString("desc", "");
		String expFormula = cFile.getString("exp-formula", defaultExpFormula);
		String rewardFormula = cFile.getString("reward-formula", defaultRewardFormula);
		ChatColor color = ChatColor.valueOf(cFile.getString("color"));
		boolean isListed = cFile.getBoolean("listed",true);
		ArrayList<EJBlock> blocks = new ArrayList<EJBlock>();
		ArrayList<EJMob> mobs = new ArrayList<EJMob>();
		if(cFile.isConfigurationSection("blocks"))
		{
			ConfigurationSection blocksSection = cFile.getConfigurationSection("blocks");
			blocks = getBlocks(blocksSection);
		}
		if(cFile.isConfigurationSection("mobs"))
		{
			ConfigurationSection mobsSection = cFile.getConfigurationSection("mobs");
			mobs = getMobs(mobsSection);
		}
		Job jobToAdd = new Job(jobName,desc,expFormula,rewardFormula,color,blocks,mobs, isListed);
		jobs.add(jobToAdd);
		if(defaultJob == null)
			defaultJob = jobToAdd;
	}
	
	public ArrayList<EJBlock> getBlocks(ConfigurationSection blocksSection)
	{
		ArrayList<EJBlock> blocks = new ArrayList<EJBlock>();
		for(String key : blocksSection.getKeys(false)){
			ConfigurationSection blockSection = blocksSection.getConfigurationSection(key);
			double exp = blockSection.getDouble("exp");
			String name = blockSection.getString("name");
			blocks.add( new EJBlock(exp, name) );
		}
		return blocks;
	}
	
	public ArrayList<EJMob> getMobs(ConfigurationSection mobsSection)
	{
		ArrayList<EJMob> mobs = new ArrayList<EJMob>();
		for(String key : mobsSection.getKeys(false)){
			ConfigurationSection mobSection = mobsSection.getConfigurationSection(key);
			String mobName = mobSection.getString("mob-name");
			double exp = mobSection.getDouble("exp");
			mobs.add( new EJMob(mobName, exp) );
		}
		return mobs;
	}
	
	public void addPlayer(EPlayer player)
	{
		if(!players.contains(player))
			players.add(player);
	}
	
	public void addParty(EParty party)
	{
		if(!parties.contains(party))
		{
			parties.add(party);
			if(party.getPrefix() != null)
				this.prefixes.add(party.getPrefix());
			try {
				saveParty(party);
			} catch (IOException e) {
				EJob.logInfo("Error saving party: " +party.getPartyLeader());
			}
		}
	}
	
	public void addJob(Job job)
	{
		if(!jobs.contains(job))
			jobs.add(job);
	}
	
	public Job getJob(String jobName)
	{
		for(Job job : jobs)
			if(job.getJobName().equalsIgnoreCase(jobName))
				return job;
		return null;
	}
	
	public EPlayer getPlayer(String playerName)
	{
		for(EPlayer player : players)
			if(player.getName().equalsIgnoreCase(playerName))
				return player;
		return null;
	}
	
	public EParty getParty(String partyLeader)
	{
		if(partyLeader != null)
		{
			for(EParty party : parties)
				if(party.getPartyLeader().getName().equalsIgnoreCase(partyLeader))
					return party;
			return null;
		}
		return null;
	}
	
	public Job getJob(Job job)
	{
		for(Job job1 : jobs)
			if(job1 == job)
				return job1;
		return null;
	}
	
	public void logoutPlayer(EPlayer player) throws IOException
	{
		if(players.contains(player))
		{
			File thePlayer = new File(EJob.getInstance().getDataFolder() +"/players",player.getName() +".yml");
			if(!thePlayer.exists())
				thePlayer.createNewFile();
			FileConfiguration file = YamlConfiguration.loadConfiguration(thePlayer);
			file.set("max-party-slots", player.getMaxPartySlots());
			file.set("job-spam", player.getJobSpam());
			if(player.getPlayerParty() != null)
				file.set("party", player.getPlayerParty().getPartyLeader().getName());
			else
				file.set("party", null);
			file.set("job-name", player.getPlayerJob().getJob().getJobName());
			file.set("level", player.getPlayerJob().getLevel());
			file.set("exp", player.getPlayerJob().getEXP());
			file.save(thePlayer);
		}
	}
	
	public EPlayer onLogin(String playerName)
	{
		File thePlayer = new File(EJob.getInstance().getDataFolder() +"/players",playerName +".yml");
		EPlayer player;
		if(!thePlayer.exists() && this.isFileEmpty(thePlayer))
		{
			player = new EPlayer(playerName, true, this.partyDefaultSlots);
			players.add(player);
			return player;
		}
		else
		{
			player = loadPlayer(playerName);
			players.add(player);
			return player;
		}
	}
	
	public EPlayer loadPlayer(String playerName)
	{
		File thePlayer = new File(EJob.getInstance().getDataFolder() +"/players",playerName +".yml");
		if(!thePlayer.exists() && this.isFileEmpty(thePlayer))
			return new EPlayer(playerName, true, this.partyDefaultSlots);
		else
		{
			FileConfiguration file = YamlConfiguration.loadConfiguration(thePlayer);
			int maxPartySlots = file.getInt("max-party-slots", this.partyDefaultSlots);
			boolean jobSpam = file.getBoolean("job-spam", true);
			String jobName = file.getString("job-name");
			int level = file.getInt("level");
			long exp = file.getLong("exp");
			EPlayer player = new EPlayer(playerName, jobSpam, maxPartySlots, level, exp, jobName);
			player = addPlayerParty(player);
			return player;
			
		}
	}
	
	public EPlayer addPlayerParty(EPlayer player)
	{
		File thePlayer = new File(EJob.getInstance().getDataFolder() +"/players",player.getName() +".yml");
		if(!thePlayer.exists() && this.isFileEmpty(thePlayer))
			return player;
		else
		{
			FileConfiguration file = YamlConfiguration.loadConfiguration(thePlayer);
			EParty party = this.getParty(file.getString("party"));
			player.setPlayerParty(party);
			return player;
			
		}
	}
	
	public void saveParty(EParty party) throws IOException
	{
		File theParty = new File(EJob.getInstance().getDataFolder() + "/parties", party.getPartyLeader().getName()+".yml");
		if(!theParty.exists())
		{
			theParty.createNewFile();
		}
		FileConfiguration file = YamlConfiguration.loadConfiguration(theParty);
		file.set("party-members", party.getPartyMembers());
		file.set("prefix", party.getPrefix());
		file.save(theParty);
	}
	
	public EParty loadParty(String partyLeader)
	{
		if(partyLeader != null)
		{
			File theParty = new File(EJob.getInstance().getDataFolder() + "/parties", partyLeader+".yml");
			if(!theParty.exists())
				return null;
			else
			{
				FileConfiguration file = YamlConfiguration.loadConfiguration(theParty);
				EPlayer player = loadPlayer(partyLeader);
				List<String> members = file.getStringList("party-members");
				String prefix = file.getString("prefix");
				return new EParty(player, prefix, members);
			}
		}
		return null;
	}
	
	public List<EPlayer> string2EPlayer(List<String> members)
	{
		List<EPlayer> players = new ArrayList<EPlayer>();
		for(String name : members)
		{
			players.add(loadPlayer(name));
		}
		return players;
	}
	
	public boolean onMobKill(String playerName, String mobName)
	{
		EPlayer player = getPlayer(playerName);
		if(player != null)
			return player.killMob(mobName);
		return false;
	}
	
	public boolean onBlockBreak(String playerName, Material material)
	{
		EPlayer player = getPlayer(playerName);
		if(player != null)
			return player.breakBlock(material);
		return false;
	}
	
	public boolean isFileEmpty(File file)
	{
		if(file.length() == 0)
			return true;
		else
			return false;
	}
	
	public void deleteParty(EParty party)
	{
		File theParty = new File(EJob.getInstance().getDataFolder() + "/parties", party.getPartyLeader().getName()+".yml");
		if(theParty.exists())
		{
			theParty.delete();
			parties.remove(party);
			prefixes.remove(party.getPrefix());
		}
	}
	
	public boolean isValidPrefix(String prefix)
	{
		for(String pre : prefixes)
		{
			if(pre.equalsIgnoreCase(prefix))
				return false;
		}
		for(String pre : this.forbiddenPrefixes)
		{
			if(pre.equalsIgnoreCase(prefix))
				return false;
		}
		return true;
	}
	
	public void removePlayerFromParty(EParty party, EPlayer p)
	{
		for(EParty part : parties)
		{
			if(part.getPartyLeader().getName().equalsIgnoreCase(party.getPartyLeader().getName()))
			{
				part.removePlayer(p);
			}
		}
	}
	
}
