package me.Pro.Managers;

import java.util.ArrayList;
import java.util.List;

import me.Pro.ProPvP.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class KitManager {
	
	public ItemStack getIcon(String kit, boolean Costs) {
		String path = "kits.loadouts." + kit + ".icon";
		ItemStack item;
		if (Main.KC.getKits().contains(path + ".potion"))
			item = getPotion(kit);
		else {
			item = new ItemStack(Material.getMaterial(Main.KC.getKits().getString(path + ".piece")), 1);
			for (int i = 1; i <= Main.KC.getKits().getInt(path + ".enchantamount"); i++) {
				path = path + ".enchant" + i;
				item.addEnchantment(Enchantment.getByName(Main.KC.getKits().getString(path + ".type")), Main.KC.getKits().getInt(path + ".level"));
			}
		}
		
		path = "kits.loadouts." + kit + ".icon";
		ItemMeta itemm = item.getItemMeta();
		itemm.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.KC.getKits().getString(path + ".name")));
		
		ArrayList<String> lore = new ArrayList<String>();
		if (Costs)
			lore.add("Cost: " + Main.KC.getKits().getInt("kits.loadouts." + kit + ".price"));
		lore.add(ChatColor.translateAlternateColorCodes('&', Main.KC.getKits().getString(path + ".lore")));
		itemm.setLore(lore);
		
		item.setItemMeta(itemm);
		
		return item;
	}
	
	public ItemStack getPotion(String kit) {
		Potion potion = new Potion(PotionType.valueOf(Main.KC.getKits().getString("kits.loadouts." + kit + ".icon.potion.type")));
		potion.setLevel(Main.KC.getKits().getInt("kits.loadouts." + kit + ".icon.potion.level"));
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".icon.potion.splash"))
			potion.setSplash(true);
		return potion.toItemStack(Main.KC.getKits().getInt("kits.loadouts." + kit + ".icon.amount"));
	}
	
	public String getKitFromIcon(ItemStack icon, boolean Costs) {
		for (String kit : Main.KC.getKits().getConfigurationSection("kits.loadouts").getKeys(false)) {
			if (icon.getItemMeta().getDisplayName().equalsIgnoreCase(getIcon(kit, Costs).getItemMeta().getDisplayName()))
				return kit;
		}
		return null;
	}
	
	public List<ItemStack> getKit(String kit) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".sword")) {
			ItemStack sword = new ItemStack(Material.getMaterial(Main.KC.getKits().getString("kits.loadouts." + kit + ".sword.piece")) , 1);
			if (Main.KC.getKits().contains("kits.loadouts." + kit + ".sword.enchant1")) {
				for (int i = 1; i <= Main.KC.getKits().getInt("kits.loadouts." + kit + ".sword.enchantamount"); i++) {
					sword.addUnsafeEnchantment(Enchantment.getByName(Main.KC.getKits().getString("kits.loadouts." + kit + ".sword.enchant" + i + ".type")), Main.KC.getKits().getInt("kits.loadouts." + kit 
							+ ".sword.enchant" + i + ".level"));
				}
			}
			items.add(sword);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".fishingrod")) {
			ItemStack fishingrod = new ItemStack(Material.FISHING_ROD);
			items.add(fishingrod);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".bow")) {
			ItemStack bow = new ItemStack(Material.BOW , 1);
			if (Main.KC.getKits().contains("kits.loadouts." + kit + ".bow.enchant1")) {
				for (int i = 1; i <= Main.KC.getKits().getInt("kits.loadouts." + kit + ".bow.enchantamount"); i++) {
					bow.addEnchantment(Enchantment.getByName(Main.KC.getKits().getString("kits.loadouts." + kit + ".bow.enchant" + i + ".type")), Main.KC.getKits().getInt("kits.loadouts." + kit 
							+ ".bow.enchant" + i + ".level"));
				}
			}
			items.add(bow);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".chorus")) {
			ItemStack chorus = new ItemStack(Material.POISONOUS_POTATO, 1);
			ItemMeta cm = chorus.getItemMeta();
			if (Main.KC.getKits().getInt("kits.loadouts." + kit + ".chorus.tier") == 1)
				cm.setDisplayName(ChatColor.GOLD + "Boost Tier I");
			else if (Main.KC.getKits().getInt("kits.loadouts." + kit + ".chorus.tier") == 2)
				cm.setDisplayName(ChatColor.GOLD + "Boost Tier II");
			chorus.setItemMeta(cm);
			items.add(chorus);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".gapple")) {
			ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, Main.KC.getKits().getInt("kits.loadouts." + kit + ".gapple.ammount"));
			items.add(gapple);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".epearl")) {
			ItemStack epearl = new ItemStack(Material.ENDER_PEARL, Main.KC.getKits().getInt("kits.loadouts." + kit + ".epearl.ammount"));
			items.add(epearl);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".stick")) {
			ItemStack stick = new ItemStack(Material.STICK, 1);
			stick.addUnsafeEnchantment(Enchantment.getByName(Main.KC.getKits().getString("kits.loadouts." + kit + ".stick.type")), Main.KC.getKits().getInt("kits.loadouts." + kit + ".stick.level"));
			items.add(stick);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".potion1")) {
			for (int i = 1; i <= Main.KC.getKits().getInt("kits.loadouts." + kit + ".potionamount"); i++) {
				Potion potion = new Potion(PotionType.valueOf(Main.KC.getKits().getString("kits.loadouts." + kit + ".potion" + i + ".type")));
				potion.setLevel(Main.KC.getKits().getInt("kits.loadouts." + kit + ".potion" + i + ".level"));
				if (Main.KC.getKits().contains("kits.loadouts." + kit + ".potion" + i + ".splash"))
					potion.setSplash(true);
				items.add(potion.toItemStack(Main.KC.getKits().getInt("kits.loadouts." + kit + ".potion" + i + ".amount")));
			}
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".helmet")) {
			ItemStack helm = new ItemStack(Material.getMaterial(Main.KC.getKits().getString("kits.loadouts." + kit + ".helmet.piece")), 1);
			if (Main.KC.getKits().contains("kits.loadouts." + kit + ".helmet.enchant1")) {
				if (Main.KC.getKits().getInt("kits.loadouts." + kit + ".helmet.enchantamount") == 1) {
					helm.addEnchantment(Enchantment.getByName(Main.KC.getKits().getString("kits.loadouts." + kit + ".helmet.enchant1.type")), Main.KC.getKits().getInt("kits.loadouts." + kit 
							+ ".helmet.enchant1.level"));
				}
				for (int i = 1; i <= Main.KC.getKits().getInt("kits.loadouts." + kit + ".enchantamount") + 1; i++) {
					helm.addEnchantment(Enchantment.getByName(Main.KC.getKits().getString("kits.loadouts." + kit + ".helmet.enchant" + i + ".type")), Main.KC.getKits().getInt("kits.loadouts." + kit 
							+ ".helmet.enchant" + i + ".level"));
				}
			}
			items.add(helm);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".chestplate")) {
			ItemStack chest = new ItemStack(Material.getMaterial(Main.KC.getKits().getString("kits.loadouts." + kit + ".chestplate.piece")), 1);
			if (Main.KC.getKits().contains("kits.loadouts." + kit + ".chestplate.enchant1")) {
				for (int i = 1; i <= Main.KC.getKits().getInt("kits.loadouts." + kit + ".chestplate.enchantamount"); i++) {
					chest.addEnchantment(Enchantment.getByName(Main.KC.getKits().getString("kits.loadouts." + kit + ".chestplate.enchant" + i + ".type")), Main.KC.getKits().getInt("kits.loadouts." + kit 
							+ ".chestplate.enchant" + i + ".level"));
				}
			}
			items.add(chest);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".leggings")) {
			ItemStack legs = new ItemStack(Material.getMaterial(Main.KC.getKits().getString("kits.loadouts." + kit + ".leggings.piece")), 1);
			if (Main.KC.getKits().contains("kits.loadouts." + kit + ".leggings.enchant1")) {
				for (int i = 1; i <= Main.KC.getKits().getInt("kits.loadouts." + kit + ".leggings.enchantamount"); i++) {
					legs.addEnchantment(Enchantment.getByName(Main.KC.getKits().getString("kits.loadouts." + kit + ".leggings.enchant" + i + ".type")), Main.KC.getKits().getInt("kits.loadouts." + kit 
							+ ".leggings.enchant" + i + ".level"));
				}
			}
			items.add(legs);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".boots")) {
			ItemStack boots = new ItemStack(Material.getMaterial(Main.KC.getKits().getString("kits.loadouts." + kit + ".boots.piece")), 1);
			if (Main.KC.getKits().contains("kits.loadouts." + kit + ".boots.enchant1")) {
				for (int i = 1; i <= Main.KC.getKits().getInt("kits.loadouts." + kit + ".boots.enchantamount"); i++) {
					boots.addEnchantment(Enchantment.getByName(Main.KC.getKits().getString("kits.loadouts." + kit + ".boots.enchant" + i + ".type")), Main.KC.getKits().getInt("kits.loadouts." + kit 
							+ ".boots.enchant" + i + ".level"));
				}
			}
			items.add(boots);
		}
		
		
		if (Main.KC.getKits().contains("kits.loadouts." + kit + ".arrow")) {
			ItemStack arrow = new ItemStack(Material.ARROW , Main.KC.getKits().getInt("kits.loadouts." + kit + ".arrow.ammount"));
			items.add(arrow);
		}
		
		return items;
	}
}
