package me.Pro.Managers;

import me.Pro.ProPvP.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class KillstreakItems {
	
	public KillstreakItems(Main plugin) {}
	
	public static void GiveItem(int ks, Player k) {
		if (Main.KSC.getKS().getString("killstreaks." + ks + ".rewards.potion").equalsIgnoreCase("yes")) {
			Potion potion = new Potion(PotionType.valueOf(Main.KSC.getKS().getString("killstreaks." + ks + ".rewards.item")));
			potion.setLevel(Main.KSC.getKS().getInt("killstreaks." + ks + ".rewards.tier"));
			if (Main.KSC.getKS().getString("killstreaks." + ks + ".rewards.splash").equalsIgnoreCase("yes"))
				potion.setSplash(true);
			
			k.getInventory().addItem(potion.toItemStack(Main.KSC.getKS().getInt("killstreaks." + ks + ".rewards.amount")));
		} else {
			ItemStack item = new ItemStack(Material.getMaterial(Main.KSC.getKS().getString("killstreaks." + ks + ".rewards.item")) , Main.KSC.getKS().getInt("killstreaks." + ks + ".rewards.amount"));
			k.getInventory().addItem(item);
		}
	}
}
