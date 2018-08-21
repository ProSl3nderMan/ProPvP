package me.Pro.Commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Pro.Managers.VariableManager;
import me.Pro.ProPvP.Main;
import net.md_5.bungee.api.ChatColor;

public class StatsCommand implements CommandExecutor{
	
	public StatsCommand(Main plugin) {}
	HashMap<String, Integer> hm = new HashMap<String, Integer>();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player p = (Player)sender;
		if (args.length == 0) {
			VariableManager.StatsUpdater();
			int deaths = Main.PC.getPlayers().getInt("players." + p.getPlayer().getUniqueId() + ".deaths");
			int kills = Main.PC.getPlayers().getInt("players." + p.getPlayer().getUniqueId() + ".kills");
			
			p.sendMessage(ChatColor.GOLD + "------ " + p.getPlayer().getName() + " Stats ------");
			p.sendMessage(ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + kills);
			p.sendMessage(ChatColor.GOLD + "Deaths: " + ChatColor.YELLOW + deaths);
			p.sendMessage(ChatColor.GOLD + "Kill/Death Ratio: " + ChatColor.YELLOW + (kills/(deaths + 0.0)));
			p.sendMessage(ChatColor.GOLD + "Do " + ChatColor.YELLOW + "/stats <Player> " + ChatColor.GOLD + "to see someone else stats.");
			p.sendMessage(ChatColor.GOLD + "Do " + ChatColor.YELLOW + "/stats reset " + ChatColor.GOLD + "to reset your stats.");
			p.sendMessage(ChatColor.GOLD + "Do " + ChatColor.YELLOW + "/stats top " + ChatColor.GOLD + "to see the top amount of kills.");
			return true;
		} if (args[0].equalsIgnoreCase("top")) {
			VariableManager.StatsUpdater();
			for(String s : Main.PC.getPlayers().getConfigurationSection("players").getKeys(false)) {
	            hm.put(s, Integer.valueOf(Main.PC.getPlayers().getInt("players." + s + ".kills")));
	        }
	        List<String> sorted = new ArrayList<String>(hm.keySet());
	        Collections.sort(sorted, new Comparator<String>() {
	        	public int compare(String s1, String s2) {
	        		return Integer.valueOf(hm.get(s2)).compareTo(hm.get(s1));
	        	}
	        });
	        for (int i = 0; i < hm.size(); i++) {
	        	if (i == 10) break; {
	        		String name = Main.PC.getPlayers().getString(new StringBuilder("players.").append((String)sorted.get(i)).append(".IGN").toString());
	        		p.sendMessage(ChatColor.GOLD + "(" + (i + 1) + "). " + ChatColor.YELLOW + 
	        				Main.PC.getPlayers().getString(new StringBuilder("players.").append((String)sorted.get(i)).append(".IGN").toString()) + ChatColor.YELLOW + 
	        				" : " + ChatColor.GOLD + hm.get(sorted.get(i)) + ChatColor.YELLOW + " kills, " + ChatColor.GOLD
	        				+ Main.PC.getPlayers().getInt("players." + Bukkit.getOfflinePlayer(name).getUniqueId() + ".deaths") + ChatColor.YELLOW + " deaths.");
	                }     
	        	}
		} else if (args[0].equalsIgnoreCase("reset") && p.isOp()) {
			VariableManager.StatsUpdater();
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".deaths" , 0);
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".kills", 0);
			Main.PC.srPlayers();
			p.sendMessage(ChatColor.GOLD + "Your stats have been reset!");
		}	else {
			VariableManager.StatsUpdater();
			if (Main.PC.getPlayers().contains("players." + Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
				int deaths = Main.PC.getPlayers().getInt("players." + Bukkit.getOfflinePlayer(args[0]).getUniqueId() + ".deaths");
				int kills = Main.PC.getPlayers().getInt("players." + Bukkit.getOfflinePlayer(args[0]).getUniqueId() + ".kills");
				p.sendMessage(ChatColor.GOLD + "------ " + Bukkit.getOfflinePlayer(args[0]).getName() + " Stats ------");
				p.sendMessage(ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + kills);
				p.sendMessage(ChatColor.GOLD + "Deaths: " + ChatColor.YELLOW + deaths);
				p.sendMessage(ChatColor.GOLD + "Kill/Death Ratio: " + ChatColor.YELLOW + (kills/(deaths + 0.0)));
				p.sendMessage(ChatColor.GOLD + "Do " + ChatColor.YELLOW + "/stats " + ChatColor.GOLD + "to see your stats.");
				p.sendMessage(ChatColor.GOLD + "Do " + ChatColor.YELLOW + "/stats reset " + ChatColor.GOLD + "to reset your stats.");
				p.sendMessage(ChatColor.GOLD + "Do " + ChatColor.YELLOW + "/stats top " + ChatColor.GOLD + "to see the top amount of kills.");
			} else if (!Main.PC.getPlayers().contains("players." + Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
				p.sendMessage(ChatColor.RED + "The player " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " does not exist!");
			}
		}
		return true;
	}
}
