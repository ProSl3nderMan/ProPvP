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

public class NewConfig {
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public void reloadArena() {
		if (customConfigFile == null) {
		    customConfigFile = new File(Main.plugin.getDataFolder(), "newclear.yml");
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(Main.plugin.getResource("newclear.yml"), "UTF-8");
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
	        customConfigFile = new File(Main.plugin.getDataFolder(), "newclear.yml");
	    	Main.plugin.getLogger().info("Generating newclear.yml");
	    	getArena().options().copyDefaults(true);
	    	saveDefaultArena();
	      
	    	Main.plugin.getLogger().info("newclear.yml has been created!");
	    }
	    if (!customConfigFile.exists()) {            
	         Main.plugin.saveResource("newclear.yml", false);
	     }
	}
}
