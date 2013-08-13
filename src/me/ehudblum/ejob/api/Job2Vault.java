package me.ehudblum.ejob.api;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Job2Vault {
	public static Permission permission = null;
	public static Economy economy = null;
	public static Chat chat = null;
	
	public static boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);

		if (permissionProvider != null)
		{
			permission = permissionProvider.getProvider();
		}

		return (permission != null);
	}
	
	public static boolean setupChat()
	{
		RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);

		if (chatProvider != null)
		{
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}
	
	public static boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

		if (economyProvider != null)
		{
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
	
	public static boolean hasPermission(CommandSender sender, String permissionNode)
	{
		if (permission != null)
		{
			return permission.has(sender, permissionNode);
		}
		return sender.hasPermission(permissionNode);
	}
}
