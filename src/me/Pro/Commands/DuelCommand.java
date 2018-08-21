package me.Pro.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Pro.Managers.ArenaManager;
import me.Pro.Managers.VariableManager;
import me.Pro.ProPvP.Main;
import net.md_5.bungee.api.ChatColor;

public class DuelCommand implements CommandExecutor {
	public DuelCommand(Main main) {}
	
	public String getOpenArena() {
		if (VariableManager.arenas.get("1").size() == 0)
			return "1";
		if (VariableManager.arenas.get("2").size() == 0)
			return "2";
		if (VariableManager.arenas.get("3").size() == 0)
			return "3";
		return "none";
	}
	
	public HashMap<String, Integer> seconds = new HashMap<String, Integer>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player p = (Player)sender;
		if (args.length == 0) {
			p.sendMessage(ChatColor.DARK_GREEN + "Do /duel invite <player name> to invite someone to a duel.");
			p.sendMessage(ChatColor.DARK_GREEN + "Do /duel spectate <1,2,3> to spectate the duel arena.");
			p.sendMessage(ChatColor.DARK_GREEN + "Do /duel accept to accept a duel invite.");
			p.sendMessage(ChatColor.DARK_GREEN + "Do /duel leave to leave a duel arena.");
			return true;
		}
		if (args[0].equalsIgnoreCase("invite")) {
			if (VariableManager.inarena.contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "You are already in an arena.");
				return true;
			}
			final String arena = getOpenArena();
			if (arena.equalsIgnoreCase("none")) {
				p.sendMessage(ChatColor.RED + "There is no arena that is open, sorry!");
				return true;
			} else {
				if (args.length == 0) {
					p.sendMessage(ChatColor.DARK_RED + "Correct usage: /duel invite <player name>");
					return true;
				}
				final Player inv = Bukkit.getPlayer(args[1]);
				if (!Bukkit.getOnlinePlayers().contains(inv)) {
					p.sendMessage(ChatColor.RED + "Player " + args[1] + " is not online right now.");
					return true;
				}
				p.sendMessage(ChatColor.GREEN + "You have just sent a duel invite to " + inv.getName() + "!");
				inv.sendMessage(ChatColor.GREEN + "You have been invited to a game from " + p.getName() + ". Do /duel accept to accept.");
				VariableManager.invited.put(inv.getName(), arena);
				List<String> temp = new ArrayList<String>();
				temp.add(inv.getName());
				temp.add(p.getName());
				VariableManager.arenas.put(arena, temp);
				seconds.put(p.getName(), 0);
				new BukkitRunnable() {
					@Override
					public void run() {
						seconds.put(p.getName(), seconds.get(p.getName()) + 1);
						if (seconds.get(p.getName()) == 10) {
							List<String> game = VariableManager.arenas.get("");
							game = new ArrayList<String>();
							VariableManager.arenas.put(arena, game);
							VariableManager.invited.remove(p.getName());
							p.sendMessage(ChatColor.RED + "Player " + inv.getName() + " did not accept within the 10 seconds, invite has expired.");
							inv.sendMessage(ChatColor.RED + "Invite from " + p.getName() + " has expired!");
							seconds.remove(p.getName());
							cancel();
						}
						if (VariableManager.accepted.contains(inv.getName())) {
							p.sendMessage(ChatColor.GREEN + "Player " + inv.getName() + " has accepted your invite!");
							VariableManager.invited.remove(p.getName());
							VariableManager.accepted.remove(inv.getName());
							seconds.remove(p.getName());
							ArenaManager.startArena(arena);
							cancel();
						}
					}
				}.runTaskTimer(Main.plugin, 20L , 20L);
			}
		}
		if (args[0].equalsIgnoreCase("accept")) {
			if (VariableManager.invited.containsKey(p.getName())) {
				VariableManager.accepted.add(p.getName());
			} else {
				p.sendMessage(ChatColor.RED + "No one has invited you to a duel!");
			}
		}
		if (args[0].equalsIgnoreCase("leave")) {
			if (VariableManager.arenas.get("1").contains(p.getName())) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "Player " + p.getName() + " gave up and loss in game 1.");
				ArenaManager.EndArena("1");
			} else if (VariableManager.arenas.get("2").contains(p.getName())) {
					Bukkit.broadcastMessage(ChatColor.GOLD + "Player " + p.getName() + " gave up and loss in game 2.");
					ArenaManager.EndArena("2");
			} else if (VariableManager.arenas.get("3").contains(p.getName())) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "Player " + p.getName() + " gave up and loss in game 3.");
				ArenaManager.EndArena("3");
			} else {
				p.sendMessage(ChatColor.RED + "You are not in an arena!");
			}
		}
		if (args[0].equalsIgnoreCase("reload") && p.isOp()) {
			p.sendMessage("Arena config reloaded!");
			Main.ArenaConfig.reloadArena();
		}
		return true;
	}
}
