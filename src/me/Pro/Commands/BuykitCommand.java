package me.Pro.Commands;

import java.util.List;

import me.Pro.ProPvP.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuykitCommand implements CommandExecutor {
	
	public BuykitCommand(Main plugin) {}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		Player p = (Player)sender;
		if (args.length == 0) {
			Main.KG.openBuyKitGui(p);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("list")) {
			p.sendMessage(ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED 
					+ "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE 
					+ "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED 
					+ "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE 
					+ "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED 
					+ "-" + ChatColor.DARK_PURPLE + "=" + ChatColor.DARK_RED + "-");
			p.sendMessage(ChatColor.GOLD + "Kits you could buy:");
			List<String> kits = Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".buykitlist");
			for (String kit : kits) {
				p.sendMessage(ChatColor.LIGHT_PURPLE + "/buykit " + ChatColor.AQUA + kit + ChatColor.LIGHT_PURPLE + " price: " + ChatColor.AQUA + Main.KC.getKits().getInt("kits.loadouts." + kit + ".price") + "$");
			}
			p.sendMessage(ChatColor.GOLD + "Type " + ChatColor.DARK_RED + "/kit info " + ChatColor.GRAY + "<" + ChatColor.DARK_RED + "name" + ChatColor.GRAY + ">" + ChatColor.GOLD + " for information on each kit.");
			return true;
		}
		
		List<String> buyablekits = Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".buykitlist");
		List<String> kits = Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".viskitlist");
		if (!(buyablekits.contains(args[0])) && !(kits.contains(args[0])))
			p.sendMessage(ChatColor.RED + "The kit " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " does not exist!");
		
		else if (!(buyablekits.contains(args[0])) && (kits.contains(args[0])))
			p.sendMessage(ChatColor.RED + "You already have kit " + ChatColor.DARK_RED + args[0] + ChatColor.RED + "! Do " + ChatColor.DARK_RED + "/kit " + args[0] + ChatColor.RED + " to use that kit.");
		
		else if (buyablekits.contains(args[0]) && !kits.contains(args[0])) {
			processBuy(p, args[0]);
			return true;
		}
		return true;
	}
	
	public static void processBuy(Player p, String kit) {
		List<String> buyablekits = Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".buykitlist");
		List<String> kits = Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".viskitlist");
		double pmoney = Main.econ.getBalance(p);
		
		if (pmoney < Main.KC.getKits().getInt("kits.loadouts." + kit + ".price")) {
			p.sendMessage(ChatColor.RED + "You do not have enough money to buy kit " + ChatColor.DARK_RED + kit + ChatColor.RED + "!");
		} else if (pmoney >= Main.KC.getKits().getInt("kits.loadouts." + kit + ".price")) {
			buyablekits.remove(kit);
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".buykitlist" , buyablekits);
			kits.add(kit);
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".viskitlist" , kits);
			Main.PC.srPlayers();
			Main.econ.withdrawPlayer(p, Main.KC.getKits().getInt("kits.loadouts." + kit + ".price"));
			p.sendMessage(ChatColor.DARK_GREEN + "You just bought kit " + ChatColor.GREEN + kit + ChatColor.DARK_GREEN + "!");
		}
	}
}
