package me.Pro.Managers;

import java.util.List;

import me.Pro.Commands.BuykitCommand;
import me.Pro.ProPvP.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitGUI {

	public void openKitGui(Player p) {
		List<String> kits = Main.PC.getPlayers().getStringList("players." + p.getUniqueId() + ".viskitlist");
		
		Inventory inv = Bukkit.createInventory(null, 36, ChatColor.GOLD + "Kits");
		
		for (String kit : kits)
			inv.addItem(Main.KM.getIcon(kit, false));
		
		p.openInventory(inv);
	}
	
	public void kitGuiListener(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		e.setCancelled(true);
		InventoryAction a = e.getAction();
		ItemStack is = e.getCurrentItem();
		if (a == InventoryAction.UNKNOWN || a == InventoryAction.NOTHING || is == null || is.getType() == Material.AIR)
			return;
		
		String kit = Main.KM.getKitFromIcon(e.getCurrentItem(), false);
		if (kit.equalsIgnoreCase("null"))
			return;
		p.closeInventory();
		GiveKitManager.GiveKit(p, kit);
	}
	
	public void openBuyKitGui(Player p) {
		List<String> kits = Main.PC.getPlayers().getStringList("players." + p.getUniqueId() + ".buykitlist");
		
		Inventory inv = Bukkit.createInventory(null, 36, ChatColor.GOLD + "Buyable Kits");
		
		for (String kit : kits)
			inv.addItem(Main.KM.getIcon(kit, true));
		
		p.openInventory(inv);
	}
	
	public void buyKitGuiListener(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		e.setCancelled(true);
		InventoryAction a = e.getAction();
		ItemStack is = e.getCurrentItem();
		if (a == InventoryAction.UNKNOWN || a == InventoryAction.NOTHING || is == null || is.getType() == Material.AIR)
			return;
		
		String kit = Main.KM.getKitFromIcon(e.getCurrentItem(), true);
		if (kit == null)
			return;
		p.closeInventory();
		BuykitCommand.processBuy(p, kit);
	}
}
