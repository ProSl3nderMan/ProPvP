package me.Pro.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Pro.Managers.VariableManager;
import me.Pro.ProPvP.Main;

public class OnRightClick implements Listener {
	public OnRightClick(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (!e.getAction().equals(Action.RIGHT_CLICK_AIR) || !e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;
		if (p.getInventory().getItemInHand().getType() != Material.POISONOUS_POTATO) 
			return;
		ItemStack item = p.getInventory().getItemInHand();
		if (Main.VM.cooldown.containsKey(p.getName())) {
			if ((Main.VM.tk - Main.VM.cooldown.get(p.getName())) >= 720) {
				Main.VM.cooldown.remove(p.getName());
			} else {
				p.sendMessage(ChatColor.RED + "You have a " + (Main.VM.tk - Main.VM.cooldown.get(p.getName())) + " minutes left before you can use another boost.");
				return;
			}
		}
		if (VariableManager.booster.size() == 1) {
			p.sendMessage(ChatColor.RED + "There's a boost activated already.");
			p.sendMessage(ChatColor.GREEN + "Only " + VariableManager.booster.get(0) + " minutes left of the current money boost.");
		}
		if (item.getAmount() == 1)
			p.getInventory().remove(item);
		else if (item.getAmount() > 1) {
			p.getInventory().setItemInHand(new ItemStack(Material.POISONOUS_POTATO, item.getAmount() - 1));
		}
		if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Boost Tier I")) {
			p.sendMessage(ChatColor.GOLD + "Starting Boost Tier I, will last 1 hour");
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Player " + p.getName() + " has just started a Boost Tier I, everyone will now get double coins.");
			Main.VM.cooldown.put(p.getName(), Main.VM.tk);
			Main.VM.tier = 1;
			new BukkitRunnable() {
				public void run() {
					if ((Main.VM.tk - Main.VM.cooldown.get(p.getName())) == 30) {
						Main.VM.tier = 0;
						Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "The Boost Tier I has just ran out!");
						cancel();
						return;
					}
				}
			}.runTaskTimer(Main.plugin, 1200L, 1200L);
		} else if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Boost Tier II")) {
			if (VariableManager.booster.size() == 1) {
				p.sendMessage(ChatColor.RED + "There's a boost activated already.");
				p.sendMessage(ChatColor.GREEN + "Only " + VariableManager.booster.get(0) + " minutes left of the current money boost.");
			} else {
				p.sendMessage(ChatColor.GOLD + "Starting Boost Tier II, will last 1 hour");
				Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Player " + p.getName() + " has just started a Boost Tier II, everyone will now get double coins.");
				Main.VM.cooldown.put(p.getName(), Main.VM.tk);
				Main.VM.tier = 2;
				VariableManager.booster.put(p.getName(), 30);
				new BukkitRunnable() {
					public void run() {
						VariableManager.booster.put(p.getName(), VariableManager.booster.get(p.getName()) - 1);
						if ((Main.VM.tk - Main.VM.cooldown.get(p.getName())) == 30) {
							Main.VM.tier = 0;
							VariableManager.booster.remove(p.getName());
							Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "The Boost Tier II has just ran out!");
							cancel();
							return;
						}
					}
				}.runTaskTimer(Main.plugin, 1200L, 1200L);
			}
		}
	}
}
