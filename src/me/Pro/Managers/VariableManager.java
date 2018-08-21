package me.Pro.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.Pro.ProPvP.Main;

public class VariableManager {
	
	public VariableManager(Main plugin) {}
	
	public VariableManager() {}

	public static HashMap<String, List<String>> cooldowns = new HashMap<String, List<String>>();
	public static HashMap<String, Integer> secs = new HashMap<String, Integer>();
	public static HashMap<String, Integer> killstreak = new HashMap<String, Integer>();
	public static HashMap<String, Integer> deaths = new HashMap<String, Integer>();
	public static HashMap<String, Integer> kills = new HashMap<String, Integer>();
	public static ArrayList<String> inarena = new ArrayList<String>();
	public static HashMap<String, List<String>> arenas = new HashMap<String, List<String>>();
	public static HashMap<String, String> invited = new HashMap<String, String>();
	public static ArrayList<String> accepted = new ArrayList<String>();
	public static HashMap<String, Integer> booster = new HashMap<String, Integer>();
	public HashMap<String, Integer> cooldown = new HashMap<String, Integer>();
	public int tk;
	public int tier;
	
	public static void StatsUpdater() {
		for (String s : VariableManager.kills.keySet()) {
			String path = "players." + s + ".";
			Main.PC.getPlayers().set(path + "kills", (Main.PC.getPlayers().getInt(path + "kills") + VariableManager.kills.get(s)));
			VariableManager.kills.put(s, 0);
		}
		for (String s : VariableManager.deaths.keySet()) {
			String path = "players." + s + ".";
			Main.PC.getPlayers().set(path + "deaths", (Main.PC.getPlayers().getInt(path + "deaths") + VariableManager.deaths.get(s)));
			VariableManager.deaths.put(s, 0);
		}
		Main.PC.srPlayers();
	}
}
