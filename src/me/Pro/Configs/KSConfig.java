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

public class KSConfig {
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public void reloadKS() {
		if (customConfigFile == null) {
		    customConfigFile = new File(Main.plugin.getDataFolder(), "killstreak.yml");
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(Main.plugin.getResource("killstreak.yml"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (defConfigStream != null) {
		    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		    customConfig.setDefaults(defConfig);
		}
	}
	public FileConfiguration getKS() {
		if (customConfig == null) {
	        reloadKS();
	    }
	    return customConfig;
	}
	public void saveKS() {
		if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        getKS().save(customConfigFile);
	    } catch (IOException ex) {
	        Main.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	public void srKS() {
		saveKS();
		reloadKS();
	}
	public void saveDefaultKS() {
	    if (customConfigFile == null) {
	        customConfigFile = new File(Main.plugin.getDataFolder(), "killstreak.yml");
	    	Main.plugin.getLogger().info("Generating config.yml");
	    	getKS().options().copyDefaults(true);
	    	saveDefaultKS();
	      
	    	Main.plugin.getLogger().info("killstreak.yml has been created!");
	    }
	    if (!customConfigFile.exists()) {            
	         Main.plugin.saveResource("killstreak.yml", false);
	     }
	}
}
