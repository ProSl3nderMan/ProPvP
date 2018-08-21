package me.Pro.ProPvP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.Pro.Commands.BuykitCommand;
import me.Pro.Commands.KitCommand;
import me.Pro.Commands.SetkillmsgCommand;
import me.Pro.Commands.StatsCommand;
import me.Pro.Configs.ArenaConfig;
import me.Pro.Configs.KSConfig;
import me.Pro.Configs.KitsConfig;
import me.Pro.Configs.PlayersConfig;
import me.Pro.Events.OnDeath;
import me.Pro.Events.OnFishing;
import me.Pro.Events.OnHunger;
import me.Pro.Events.OnInvClick;
import me.Pro.Events.OnJoin;
import me.Pro.Managers.ArenaManager;
import me.Pro.Managers.GiveKitManager;
import me.Pro.Managers.KillstreakItems;
import me.Pro.Managers.KitGUI;
import me.Pro.Managers.KitManager;
import me.Pro.Managers.VariableManager;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

	protected MainLogger log;
	public static Main plugin;
	public static Economy econ = null;
	public static ArenaConfig ArenaConfig = new ArenaConfig();
	public static VariableManager VM = new VariableManager();
	public static PlayersConfig PC = new PlayersConfig();
	public static KSConfig KSC = new KSConfig();
	public static KitsConfig KC = new KitsConfig();
	public static KitGUI KG = new KitGUI();
	public static KitManager KM = new KitManager();
	
	
	
	

	
	public void onDisable() {
		VariableManager.StatsUpdater();
		if (VM.cooldown.size() != 0) {
			for (String s : Main.VM.cooldown.keySet()) {
				getConfig().set("player." + s, VM.cooldown.get(s));
			}
			getConfig().set("temp.time", VM.tk);
			getConfig().set("temp.tier", VM.tier);
			saveConfig();
			reloadConfig();
		}
	}
	
	
	public void onEnable() {
		plugin = this;
		this.log = new MainLogger(this);
		
		
		getCommand("kit").setExecutor(new KitCommand(this));
		getCommand("buykit").setExecutor(new BuykitCommand(this));
		getCommand("setkillmsg").setExecutor(new SetkillmsgCommand(this));
		getCommand("stats").setExecutor(new StatsCommand(this));
		new KitCommand(this);
		new StatsCommand(this);
		new BuykitCommand(this);
		new SetkillmsgCommand(this);
		
		new OnDeath(this);
		new OnJoin(this);
		new OnFishing(this);
		new OnHunger(this);
		new OnInvClick(this);
		
		new GiveKitManager(this);
		new VariableManager(this);
		new KillstreakItems(this);
		new ArenaManager(this);
		
		File file = new File(getDataFolder() + File.separator + "config.yml");
	    if (!file.exists()) {
	    	getLogger().info("Generating config.yml");
	      
	    	getConfig().addDefault("KitPvP.enabled:" , "true");
	    	getConfig().options().copyDefaults(true);
	    	saveDefaultConfig();
	      
	    	getLogger().info("Config.yml has been created! Please add world names in config!");
	    }
	    getConfig().options().copyDefaults(true);
	    saveDefaultConfig();
	    
	    KC.saveDefaultKits();
	    PC.saveDefaultPlayers();
	    KSC.saveDefaultKS();
	    ArenaConfig.saveDefaultArena();
	    
		KC.reloadKits();
		PC.reloadPlayers();
		KSC.reloadKS();
		ArenaConfig.reloadArena();
		
		if (!setupEconomy()) {
			getLogger().info(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName() }));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		List<String> game = VariableManager.arenas.get("");
		game = new ArrayList<String>();
		VariableManager.arenas.put("1", game);
		VariableManager.arenas.put("2", game);
		VariableManager.arenas.put("3", game);
		
		TimeKeeper();
		
		if (getConfig().contains("player.")) {
			for (String s : getConfig().getConfigurationSection("player.").getKeys(false)) {
				VM.cooldown.put(s, getConfig().getInt("player." + s));
				getConfig().set("player." + s, null);
			}
		}
		if (getConfig().contains("temp.time")) {
			VM.tk = getConfig().getInt("temp.time");
			getConfig().set("temp.time", null);
		}
		if (getConfig().contains("temp.tier")) {
			VM.tier = getConfig().getInt("temp.tier");
			getConfig().set("temp.tier", null);
		}
		saveConfig();
		reloadConfig();
	}
	
	public void TimeKeeper() {
		VM.tk = 0;
		new BukkitRunnable() {
			public void run() {
				VM.tk++;
			}
		}.runTaskTimer(plugin, 1200L, 1200L);
	}
	
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = (Economy)rsp.getProvider();
		return econ != null;
	}

}
