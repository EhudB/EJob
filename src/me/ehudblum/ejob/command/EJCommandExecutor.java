package me.ehudblum.ejob.command;

import me.ehudblum.ejob.api.Job2Vault;
import me.ehudblum.ejob.util.EJUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EJCommandExecutor implements CommandExecutor
{
	EJCommandHandler handler;
	
	public EJCommandExecutor(EJCommandHandler handler)
	{
		this.handler = handler;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		
		if(!(sender instanceof Player))
		{
			return false;
		}
		Player p = (Player)sender;
		
		
		if(label.equalsIgnoreCase("job"))
		{
			if(args.length == 0)
			{
				handler.showJobsCommand(p);
			}
			else if(args.length >= 1)
			{
				if(args[0].equalsIgnoreCase("list") && Job2Vault.hasPermission(sender, "ejob.job.list"))
				{
					if(args.length == 1)
					{
						handler.sendJobList(p);
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /job list .");
					}
				}
				else if((args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off")) && Job2Vault.hasPermission(sender, "ejob.job.setmode"))
				{
					if(args.length == 1)
					{
						handler.toggleJobMode(p, args[0].equalsIgnoreCase("on"));
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /job [on|off] .");
					}
				}
				else if(args[0].equalsIgnoreCase("leave") && Job2Vault.hasPermission(sender, "ejob.job.leave"))
				{
					if(args.length == 1)
					{
						handler.leaveJob(p);
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /job leave .");
					}
				}
				else if(args[0].equalsIgnoreCase("stats") && Job2Vault.hasPermission(sender, "ejob.job.stats"))
				{
					if(args.length == 1)
					{
						handler.sendPlayerStats(p, p.getName());
					}
					else if(args.length == 2 && Job2Vault.hasPermission(sender, "ejob.job.stats.other"))
					{
						handler.sendPlayerStats(p, args[1]);
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /job stats <player> .");
					}
				}
				else if(args[0].equalsIgnoreCase("info") && Job2Vault.hasPermission(sender, "ejob.job.info"))
				{
					if(args.length == 2)
					{
						handler.showInfo(p, args[1]);
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /job info [job] .");
					}
				}
				else if(args[0].equalsIgnoreCase("join") && Job2Vault.hasPermission(sender, "ejob.job.join"))
				{
					if(args.length == 2)
					{
						handler.joinJob(p, args[1]);
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /job join [job] .");
					}
				}
				else if(args[0].equalsIgnoreCase("spam") && Job2Vault.hasPermission(sender, "ejob.job.spam"))
				{
					if(args.length == 2)
					{
						if(args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("off"))
						{
							handler.toggleSpamMode(p, args[1].equalsIgnoreCase("on"));
						}
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /job spam [on|off] .");
					}
				}
			}
			return true;
		}
		else if(label.equalsIgnoreCase("party"))
		{
			if(args.length == 0)
				handler.showPartyCommand(p);
			else if(args.length >= 1)
			{
				if(args[0].equalsIgnoreCase("create") && Job2Vault.hasPermission(sender, "ejob.party.create"))
				{
					if(args.length == 1)
					{
						handler.createParty(p, "");
					}
					else if(args.length == 2 && Job2Vault.hasPermission(sender, "ejob.party.create.prefix"))
					{
						handler.createParty(p, args[1]);
					}
					else 
					{
						EJUtil.sendMessage(p, "<red>Usage: /party create <prefix> .");
					}
				}
				else if(args[0].equalsIgnoreCase("invite") && Job2Vault.hasPermission(sender, "ejob.party.invite"))
				{
					if(args.length == 2)
					{
						handler.inviteToParty(p, args[1]);
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /party invite [player] .");
					}
				}
				else if(args[0].equalsIgnoreCase("univite") && Job2Vault.hasPermission(sender, "ejob.party.uninvite"))
				{
					if(args.length == 2)
					{
						handler.uninviteToParty(p, args[1]);
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /party uninvite [player]");
					}
				}
				else if(args[0].equalsIgnoreCase("leave") && Job2Vault.hasPermission(sender, "ejob.party.leave"))
				{
					if(args.length == 1)
					{
						handler.partyLeave(p);
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /party leave .");
					}
				}
				else if(args[0].equalsIgnoreCase("join") && Job2Vault.hasPermission(sender, "ejob.party.join"))
				{
					if(args.length == 2)
					{
						if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("no"))
						{
							handler.joinParty(p, args[1].equalsIgnoreCase("yes"));
						}
						else
						{
							EJUtil.sendMessage(p, "<red>Usage: /party join [yes|no] .");
						}
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /party join [yes|no] .");
					}
				}
				else if(args[0].equalsIgnoreCase("kick") && Job2Vault.hasPermission(sender, "ejob.party.kick"))
				{
					if(args.length == 2)
					{
						//EJUtil.sendMessage(p, "<red>Command is currently disabled.");
						handler.kickPlayerParty(p, args[1]);
					}
					else
					{
						//EJUtil.sendMessage(p, "<red>Command is currently disabled.");
						EJUtil.sendMessage(p, "<red>Usage: /party kick [player] .");
					}
				}
				else if(args[0].equalsIgnoreCase("info") && Job2Vault.hasPermission(sender, "ejob.party.info"))
				{
					if(args.length == 1)
					{
						handler.showPartyInfo(p);
					}
					else
					{
						EJUtil.sendMessage(p, "<red>Usage: /party info .");
					}
				}
			}
			return true;
		}
		
		return false;
	}

}
