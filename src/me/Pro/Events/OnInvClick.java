package me.Pro.Events;

import me.Pro.Commands.SetkillmsgCommand;
import me.Pro.ProPvP.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;

public class OnInvClick implements Listener {
	
	public OnInvClick(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Kits")) {
			Main.KG.kitGuiListener(e);
			return;
		}
		if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Buyable Kits")) {
			Main.KG.buyKitGuiListener(e);
			return;
		}
		if(e.getInventory() instanceof AnvilInventory && e.getInventory().getTitle().equalsIgnoreCase("Enter your kill message")){
			SetkillmsgCommand.onAnvilClick(e);
			return;
		}
	}
}
