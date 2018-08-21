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

public class PlayersConfig {
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public void reloadPlayers() {
		if (customConfigFile == null) {
		    customConfigFile = new File(Main.plugin.getDataFolder(), "players.yml");
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(Main.plugin.getResource("players.yml"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (defConfigStream != null) {
		    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		    customConfig.setDefaults(defConfig);
		}
	}
	public FileConfiguration getPlayers() {
		if (customConfig == null) {
	        reloadPlayers();
	    }
	    return customConfig;
	}
	public void savePlayers() {
		if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        getPlayers().save(customConfigFile);
	    } catch (IOException ex) {
	        Main.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	public void srPlayers() {
		savePlayers();
		reloadPlayers();
	}
	public void saveDefaultPlayers() {
	    if (customConfigFile == null) {
	        customConfigFile = new File(Main.plugin.getDataFolder(), "players.yml");
	    	Main.plugin.getLogger().info("Generating config.yml");
	    	getPlayers().options().copyDefaults(true);
	    	saveDefaultPlayers();
	      
	    	Main.plugin.getLogger().info("players.yml has been created!");
	    }
	    if (!customConfigFile.exists()) {            
	         Main.plugin.saveResource("players.yml", false);
	     }
	}
}
