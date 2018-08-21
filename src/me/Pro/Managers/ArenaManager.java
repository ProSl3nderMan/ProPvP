package me.Pro.Managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.Pro.ProPvP.Main;
import net.md_5.bungee.api.ChatColor;

public class ArenaManager {
	public ArenaManager(Main main) {}
	
	public static void startArena(String arena) {
		int i = 0;
		for (String s : VariableManager.arenas.get(arena)) {
			i = i + 1;
			Player p = Bukkit.getPlayer(s);
			int x = Main.ArenaConfig.getArena().getInt("arenas." + arena + ".side" + i + ".x");
			int y = Main.ArenaConfig.getArena().getInt("arenas." + arena + ".side" + i + ".y");
			int z = Main.ArenaConfig.getArena().getInt("arenas." + arena + ".side" + i + ".z");
			p.teleport(new Location(Bukkit.getWorld(Main.ArenaConfig.getArena().getString("arenas." + arena + ".world")),x,y,z));
			p.sendMessage(ChatColor.GOLD + "You have been tped to arena " + arena + "! Good luck on your duel.");
		}
	}
	public static void EndArena(String arena) {
		Bukkit.broadcastMessage(ChatColor.GOLD + "Arena " + arena + " has ended!");
		for (String s : VariableManager.arenas.get(arena)) {
			Player p = Bukkit.getPlayer(s);
			int x = Main.ArenaConfig.getArena().getInt("spawn.coords.x");
			int y = Main.ArenaConfig.getArena().getInt("spawn.coords.y");
			int z = Main.ArenaConfig.getArena().getInt("spawn.coords.z");
			p.teleport(new Location(Bukkit.getWorld(Main.ArenaConfig.getArena().getString("spawn.world")),x,y,z));
			VariableManager.arenas.get(arena).remove(p.getName());
		}
	}
}
