package me.Pro.Commands;

import java.util.List;

import me.Pro.Managers.GiveKitManager;
import me.Pro.ProPvP.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitCommand implements CommandExecutor {

	public KitCommand(Main plugin) {}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		final Player p = (Player)sender;
		
		if (args.length == 0) {
			Main.KG.openKitGui(p);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("list")) {
			p.sendMessage(ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED 
					+ "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE 
					+ "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED 
					+ "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE 
					+ "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED 
					+ "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-");
			p.sendMessage(ChatColor.GOLD + "Kits you have access to:");
			List<String> kits = Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".viskitlist");
			for (String kit : kits)
				p.sendMessage(ChatColor.LIGHT_PURPLE + "/kit " + ChatColor.AQUA + kit);
			p.sendMessage(ChatColor.GOLD + "Type " + ChatColor.DARK_RED + "/buykit" + ChatColor.GOLD + " to see all the kits that are buyable.");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("give")){
			if (sender == Bukkit.getConsoleSender()) {
				ItemStack chorus = new ItemStack(Material.POISONOUS_POTATO, Integer.parseInt(args[1]));
				ItemMeta cm = chorus.getItemMeta();
				if (Integer.parseInt(args[2]) == 1)
					cm.setDisplayName(ChatColor.GOLD + "Boost Tier I");
				else if (Integer.parseInt(args[2]) == 2)
					cm.setDisplayName(ChatColor.GOLD + "Boost Tier II");
				chorus.setItemMeta(cm);
				if (Bukkit.getPlayer(args[3])== null)
					sender.sendMessage("No user found");
				else if (!Bukkit.getPlayer(args[3]).isOnline())
					sender.sendMessage("User offline");
				else {
					Bukkit.getPlayer(args[3]).getInventory().addItem(new ItemStack[] {chorus});
					p.sendMessage("Boost give to player " + Bukkit.getPlayer(args[3]));
				}
				return true;
			}
			if (!p.isOp()) {
				p.sendMessage(ChatColor.RED + "You do not have perms for this");
				return true;
			}
			
			ItemStack chorus = new ItemStack(Material.POISONOUS_POTATO, Integer.parseInt(args[1]));
			ItemMeta cm = chorus.getItemMeta();
			if (Integer.parseInt(args[2]) == 1)
				cm.setDisplayName(ChatColor.GOLD + "Boost Tier I");
			else if (Integer.parseInt(args[2]) == 2)
				cm.setDisplayName(ChatColor.GOLD + "Boost Tier II");
			chorus.setItemMeta(cm);
			if (Bukkit.getPlayer(args[3])== null)
				p.sendMessage("No user found");
			else if (!Bukkit.getPlayer(args[3]).isOnline())
					p.sendMessage("User offline");
			else {
				Bukkit.getPlayer(args[3]).getInventory().addItem(new ItemStack[] {chorus});
				p.sendMessage("Boost given to player " + Bukkit.getPlayer(args[3]));
			}
			return true;
		}
		
		if (args[0].equalsIgnoreCase("help")) {
			p.sendMessage(ChatColor.DARK_AQUA + "---------- " + ChatColor.AQUA + "Kit Commands" + ChatColor.DARK_AQUA + "---------- ");
			p.sendMessage(ChatColor.DARK_AQUA + "/kit help: " + ChatColor.AQUA + "Shows all commands for /kit.");
			p.sendMessage(ChatColor.DARK_AQUA + "/kit list: " + ChatColor.AQUA + "To show all the usuable kits.");
			p.sendMessage(ChatColor.DARK_AQUA + "/kit [kit name]: " + ChatColor.AQUA + "Picks the kit entered.");
			p.sendMessage(ChatColor.DARK_AQUA + "/buykit: " + ChatColor.AQUA + "Shows all the buyable kits.");
			p.sendMessage(ChatColor.DARK_AQUA + "/kit: " + ChatColor.AQUA + "Shows all the kits.");
			if (p.isOp())
				p.sendMessage(ChatColor.DARK_AQUA + "Hello operator, do /kit reload to reload the kits.yml, kitinfo.yml, and killstreak.yml.");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload") && p.isOp()) {
			p.sendMessage(ChatColor.GREEN + "kits.yml, kitinfo.yml, and killstreak.yml have been reloaded.");
			Main.KC.reloadKits();
			Main.KSC.reloadKS();
			Main.plugin.saveDefaultConfig();
			Main.ArenaConfig.reloadArena();
			return true;
		}
		
		List<String> viskits = Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".viskitlist");
		List<String> buykits = Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".buykitlist");
		List<String> allkits = Main.KC.getKits().getStringList("kits.list");
		if (viskits.contains(args[0]))
			GiveKitManager.GiveKit(p, args[0]);
		else if (buykits.contains(args[0]))
			p.sendMessage(ChatColor.RED + "You do not have the kit " + args[0] + "! Do " + ChatColor.DARK_RED + "/buykit" + ChatColor.RED + " to buy this kit.");
		else if (allkits.contains(args[0]))
			p.sendMessage(ChatColor.RED + "You have to be a donator to use kit " + args[0] + "! Do " + ChatColor.DARK_RED + "/donate" + ChatColor.RED + " to donate.");
		else
			p.sendMessage(ChatColor.RED + "The kit " + args[0] + " does not exist! Do " + ChatColor.DARK_RED + "/kit" + ChatColor.RED + " for a list of available kits.");
		
		return false;
	}
}
