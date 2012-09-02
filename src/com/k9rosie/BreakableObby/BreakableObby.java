package com.k9rosie.BreakableObby;

import org.bukkit.plugin.java.JavaPlugin;

public class BreakableObby extends JavaPlugin{
	
	public void onEnable(){
        getServer().getPluginManager().registerEvents(new BreakableObbyListener(this), this);
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
		System.out.println("BreakableObby loaded. Written by k9rosie.");
	}
	
	public void onDisable(){
		System.out.println("BreakableObby unloaded.");
	}

}
