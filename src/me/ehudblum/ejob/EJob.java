package me.ehudblum.ejob;

import java.io.File;
import java.util.logging.Logger;

import me.ehudblum.ejob.api.Job2Vault;
import me.ehudblum.ejob.api.Job2WG;
import me.ehudblum.ejob.command.EJCommandExecutor;
import me.ehudblum.ejob.command.EJCommandHandler;
import me.ehudblum.ejob.listener.BlockListener;
import me.ehudblum.ejob.listener.EJListener;
import me.ehudblum.ejob.listener.PlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EJob extends JavaPlugin
{

	private static EJob instance;
	private static EData data;
	private static Logger log = Logger.getLogger("Minecraft");
	private EJCommandHandler commandHandler = new EJCommandHandler();
	private EJCommandExecutor commandExecutor;

	@Override
	public void onEnable()
	{
		this.saveDefaultConfig();
		instance = this;
		data = new EData();
		data.load();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new EJListener(), this);
		commandExecutor = new EJCommandExecutor(commandHandler);
		getCommand("job").setExecutor(commandExecutor);
		getCommand("party").setExecutor(commandExecutor);
		Job2Vault.setupPermissions();
		Job2Vault.setupEconomy();
		File players = new File(this.getDataFolder()+"/players");
		if(!players.exists())
			players.mkdirs();
		File parties = new File(this.getDataFolder()+"/parties");
		if(!parties.exists())
			parties.mkdirs();
		File jobs = new File(this.getDataFolder()+"/jobs");
		if(!jobs.exists())
			jobs.mkdirs();
		if(Bukkit.getOnlinePlayers().length > 0)
		{
			data.loadOnlinePlayers(Bukkit.getOnlinePlayers());
		}
		Job2WG.setUpWorldGuard();
	}

	@Override
	public void onDisable()
	{
		data.saveAllOnlinePlayers(Bukkit.getOnlinePlayers());
		data.saveAllParties();
	}

	public static EJob getInstance()
	{
		return instance;
	}
	
	public static EData getData()
	{
		return data;
	}

	public static void logInfo(String str)
	{
		log.info("[eJob]"+str);
	}
}
