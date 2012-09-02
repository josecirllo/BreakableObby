package com.k9rosie.BreakableObby;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;


public class BreakableObbyListener implements Listener{
	private static BreakableObby plugin;
	
	public BreakableObbyListener(BreakableObby instance){
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public static void onEntityExplode(EntityExplodeEvent event) {
		if(event.isCancelled() == true){
			return;
		}
		Entity tnt = event.getEntity();
		final Location tntLoc = tnt.getLocation();
		
		if (tntLoc.getBlock().getType().equals(Material.WATER) || tntLoc.getBlock().getType().equals(Material.STATIONARY_WATER)){
			event.setCancelled(true);
			return;
		}else{

		int radius = plugin.getConfig().getInt("config.radius");
		
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					Location targetLoc = new Location(tnt.getWorld(),
							tntLoc.getX() + x, tntLoc.getY() + y,
							tntLoc.getZ() + z);

					if (tntLoc.distance(targetLoc) <= radius) {
						damageBlock(targetLoc);
					}
				}
			}
		}
		}
		
	}
	
	public static void damageBlock(Location loc){
		if(loc == null){
			return;
		}
		
		Block block = loc.getBlock();
		
		if(block.getTypeId() == 49){
			
			if(block.getRelative(BlockFace.DOWN).getType().equals(Material.WATER)){
				return;
			}
			if(block.getRelative(BlockFace.UP).getType().equals(Material.WATER)){
				return;
			}
			if(block.getRelative(BlockFace.NORTH).getType().equals(Material.WATER)){
				return;
			}
			if(block.getRelative(BlockFace.EAST).getType().equals(Material.WATER)){
				return;
			}
			if(block.getRelative(BlockFace.SOUTH).getType().equals(Material.WATER)){
				return;
			}
			if(block.getRelative(BlockFace.WEST).getType().equals(Material.WATER)){
				return;
			}
			if(!block.hasMetadata("health")){
				block.setMetadata("health", new FixedMetadataValue(plugin, plugin.getConfig().getInt("config.health")));
			}
			int health = block.getMetadata("health").get(0).asInt();
			int damage = plugin.getConfig().getInt("config.damage");
			if(health > 0){
				int newHealth = health-damage;
				block.setMetadata("health", new FixedMetadataValue(plugin, newHealth));
			}else{
				destroyBlock(loc);
			}
		}
	}
	
	public static void destroyBlock(Location loc){
		if(loc == null){
			return;
		}
		
		Block block = loc.getBlock();
		
		if(!block.getType().equals(Material.OBSIDIAN)){
			return;
		}
		
		block.setTypeId(Material.AIR.getId());
		block.removeMetadata("health", plugin);
	}
}
