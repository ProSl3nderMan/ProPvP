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

public class KitsConfig {
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public void reloadKits() {
		if (customConfigFile == null) {
		    customConfigFile = new File(Main.plugin.getDataFolder(), "kits.yml");
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(Main.plugin.getResource("kits.yml"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (defConfigStream != null) {
		    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		    customConfig.setDefaults(defConfig);
		}
	}
	public FileConfiguration getKits() {
		if (customConfig == null) {
	        reloadKits();
	    }
	    return customConfig;
	}
	public void saveKits() {
		if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        getKits().save(customConfigFile);
	    } catch (IOException ex) {
	        Main.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	public void srKits() {
		saveKits();
		reloadKits();
	}
	public void saveDefaultKits() {
	    if (customConfigFile == null) {
	        customConfigFile = new File(Main.plugin.getDataFolder(), "kits.yml");
	    	Main.plugin.getLogger().info("Generating config.yml");
	    	getKits().options().copyDefaults(true);
	    	saveDefaultKits();
	      
	    	Main.plugin.getLogger().info("kits.yml has been created!");
	    }
	    if (!customConfigFile.exists()) {            
	         Main.plugin.saveResource("kits.yml", false);
	     }
	}
}
