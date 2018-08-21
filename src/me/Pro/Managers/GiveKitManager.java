package me.Pro.Managers;

import java.util.List;

import me.Pro.ProPvP.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GiveKitManager {
	
	public GiveKitManager(Main plugin) {}
	
	public static void GiveKit(final Player p, final String kit) {
		if(!VariableManager.cooldowns.get(p.getName()).contains(kit) || p.hasPermission("ProPvP.nocooldown")) {
			List<String> cooldown = VariableManager.cooldowns.get(p.getName());
			cooldown.add(kit);
			VariableManager.cooldowns.put(p.getName(), cooldown);
			VariableManager.secs.put(p + "." + kit, Main.KC.getKits().getInt("kits.loadouts." + kit + ".cooldown"));
			new BukkitRunnable() {
				@Override
				public void run() {
					if (VariableManager.secs.get(p + "." + kit) <= 0) {
						this.cancel();
						List<String> cooldown = VariableManager.cooldowns.get(p.getName());
						cooldown.remove(kit);
						VariableManager.cooldowns.put(p.getName(), cooldown);
						p.sendMessage(ChatColor.GREEN + "You may now use the the kit " + kit + ".");
						return;
					} else {
						VariableManager.secs.put(p + "." + kit, (VariableManager.secs.get(p + "." + kit) - 1));
					}
				}
			}.runTaskTimer(Main.plugin, 20L , 20L);
			
			List<ItemStack> items = Main.KM.getKit(kit);
			
			for (ItemStack item : items)
				p.getInventory().addItem(item);
		} else {
			int sec = VariableManager.secs.get(p + "." + kit);
			int minutes;
			int seconds;
			if (sec > 60){
				minutes = sec/60;
				seconds = sec%60;
				if (minutes > 60) {
					int hours;
					hours = minutes/60;
					minutes = minutes%60;
					p.sendMessage(ChatColor.RED + "You need to wait " + hours + ":" + minutes + " hours and minutes to do that!");
				} else {
					p.sendMessage(ChatColor.RED + "You need to wait " + minutes + ":" + seconds + " minutes and seconds to do that!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You need to wait " + sec + " seconds to do that!");
			}
		}
	}
}
