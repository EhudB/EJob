package me.ehudblum.ejob.listener;

import me.ehudblum.ejob.EJob;
import me.ehudblum.ejob.api.Job2WG;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener
{
	@EventHandler(priority = EventPriority.LOW)
	public void onBlockBreak(BlockBreakEvent event)
	{
		if(!event.isCancelled())
		{
			Player p = event.getPlayer();
			Block b = event.getBlock();
			if(Job2WG.canBuild(p, b.getLocation()))
			{
				if(EJob.getData().onBlockBreak(p.getName(), b.getTypeId(), b.getData()))
				{
					if(p.getItemInHand().getType().getMaxDurability() > 0)
					{
						p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() + 1));
						if(p.getItemInHand().getDurability() >= p.getItemInHand().getType().getMaxDurability())
						{
							p.setItemInHand(null);
						}
					}
					b.setTypeIdAndData(Material.AIR.getId(), (byte) 0, true);
					event.setExpToDrop(0);
					event.setCancelled(true);
				}
				
			}
		}
	}
}
