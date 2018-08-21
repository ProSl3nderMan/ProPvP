package me.Pro.Configs;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import me.Pro.ProPvP.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ArenaConfig {
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public void reloadArena() {
		if (customConfigFile == null) {
		    customConfigFile = new File(Main.plugin.getDataFolder(), "arenas.yml");
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(Main.plugin.getResource("arenas.yml"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (defConfigStream != null) {
		    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		    customConfig.setDefaults(defConfig);
		}
	}
	public FileConfiguration getArena() {
		if (customConfig == null) {
	        reloadArena();
	    }
	    return customConfig;
	}
	public void saveArena() {
		if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        getArena().save(customConfigFile);
	    } catch (IOException ex) {
	        Main.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	public void srArena() {
		saveArena();
		reloadArena();
	}
	public void saveDefaultArena() {
	    if (customConfigFile == null) {
	        customConfigFile = new File(Main.plugin.getDataFolder(), "arenas.yml");
	    	Main.plugin.getLogger().info("Generating arenas.yml");
	    	getArena().options().copyDefaults(true);
	    	saveDefaultArena();
	      
	    	Main.plugin.getLogger().info("arenas.yml has been created!");
	    }
	    if (!customConfigFile.exists()) {            
	         Main.plugin.saveResource("arenas.yml", false);
	     }
	}
}
