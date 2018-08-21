package me.Pro.ProPvP;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;

public class MainLogger {
	private Main plugin;
	private Logger log;
	
	public MainLogger(Main plugin){
		this.plugin = plugin;
		this.log = Logger.getLogger("Minecraft");
	}

	private String getFormattedMessage(String message){
		PluginDescriptionFile pdf = plugin.getDescription();
		
		return "[" + pdf.getName() + " v" + pdf.getVersion() + "]:" + message;
	}
	
	public void info(String message){
		this.log.info(this.getFormattedMessage(message));
	}
}
