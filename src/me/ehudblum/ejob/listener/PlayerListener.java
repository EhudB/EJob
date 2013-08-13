package me.ehudblum.ejob.listener;

import java.io.IOException;
import java.util.ArrayList;

import me.ehudblum.ejob.EJob;
import me.ehudblum.ejob.event.mob.EntityCrippleEvent;
import me.ehudblum.ejob.player.EPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class PlayerListener implements Listener
{
	
	private ArrayList<Entity> spawnerEntities = new ArrayList<Entity>();
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		EJob.getData().onLogin(event.getPlayer().getName());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		try {
			EJob.getData().logoutPlayer(EJob.getData().getPlayer(event.getPlayer().getName()));
		} catch (IOException e) {
			EJob.logInfo("Error saving plaer: "+ event.getPlayer().getName());
		}
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		if (event.getSpawnReason() == SpawnReason.SPAWNER || event.getSpawnReason() == SpawnReason.VILLAGE_DEFENSE || event.getSpawnReason() == SpawnReason.VILLAGE_INVASION)
		{
			this.spawnerEntities.add(event.getEntity());
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		Entity defender = event.getEntity();
		if (defender != null && defender instanceof LivingEntity)
		{
			if (!this.spawnerEntities.contains(defender))
			{
				EntityDamageEvent lastDamageEvent = defender.getLastDamageCause();
				if(lastDamageEvent != null && lastDamageEvent instanceof EntityDamageByEntityEvent)
				{
					Entity attacker = ((EntityDamageByEntityEvent)lastDamageEvent).getDamager();
					if(attacker != null)
					{
						if(attacker instanceof Projectile)
							attacker = ((Projectile)attacker).getShooter();
						if(attacker != null && attacker instanceof Player)
						{
							Player p = (Player) attacker;
							String mobName = defender.getType().getName();
							if(defender instanceof Zombie && ((Zombie)defender).isVillager())
							{
								mobName = "ZombieVillager";
							}
							if(EJob.getData().onMobKill(p.getName(), mobName))
							{
								event.setDroppedExp(0);
								event.getDrops().clear();
							}
						}
					}
				}
			}
			else if(Math.random() < 0.9)
			{
				EntityCrippleEvent e = new EntityCrippleEvent(defender, event);
				Bukkit.getPluginManager().callEvent(e);
				
				if(!e.isCancelled())
				{
					event.setDroppedExp(0);
					event.getDrops().clear();
				}
				
			}
		}
	}
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event)
	{
		Entity[] entities = event.getChunk().getEntities();
		for (int i = 0; i < entities.length; i += 1)
		{
			if (entities[i] instanceof LivingEntity && this.spawnerEntities.contains(entities[i]))
			{
				this.spawnerEntities.remove(entities[i]);
				entities[i].remove();
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		EPlayer player = EJob.getData().getPlayer(e.getPlayer().getName());
		if(player.getPlayerJob() != null)
		{
			String prefix = "";
			if(player.getPlayerParty() != null && player.getPlayerParty().getPrefix() != "")
			{
				ChatColor color = player.getName().equalsIgnoreCase(player.getPlayerParty().getPartyLeader().getName()) ? ChatColor.GOLD : ChatColor.YELLOW;
				prefix += "["+color +player.getPlayerParty().getPrefix() + ChatColor.WHITE + "]";
			}
			ChatColor color = player.getPlayerJob().getJob().getColor();
			int level = player.getPlayerJob().getLevel();
			
			prefix += "["+ color + player.getPlayerJob().getJob().getJobName() + ChatColor.YELLOW + " Lv." + level + ChatColor.WHITE +"]";
			e.setFormat(prefix + e.getFormat());
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
	{
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player)
		{
			Player attacker = (Player)e.getDamager();
			Player defender = (Player)e.getEntity();
			EPlayer EPAttacker = EJob.getData().getPlayer(attacker.getName());
			EPlayer EPDefender = EJob.getData().getPlayer(defender.getName());
			if(EPAttacker != null && EPDefender != null)
			{
				if(EPAttacker.getPlayerParty() != null && EPDefender.getPlayerParty() != null)
				{
					String attackerPartyLeader = EPAttacker.getPlayerParty().getPartyLeader().getName();
					String defenderPartyLeader = EPDefender.getPlayerParty().getPartyLeader().getName();
					if(attackerPartyLeader.equalsIgnoreCase(defenderPartyLeader))
					{
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	public void onEntityCripple(EntityCrippleEvent e)
	{
		if(e.getEntityType() == EntityType.BLAZE)
		{
			e.setCancelled(true);
		}
	}
}	
