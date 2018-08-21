package me.Pro.Commands;

import me.Pro.ProPvP.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetkillmsgCommand implements CommandExecutor {
	
	public SetkillmsgCommand(Main plugin) {}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		Player p = (Player)sender;
		if (args.length == 0) {
			if (p.hasPermission("propvp.killmsg") || p.isOp()) {
				openAnvil(p);
				return true;
			} else {
				p.sendMessage(ChatColor.RED + "You must be a donor member to use this command! Do " + ChatColor.DARK_RED + "/donate" + ChatColor.RED + " to get the link to donating.");
				return true;
			}
		} else {
			if (p.hasPermission("propvp.killmsg") || p.isOp()) {
				StringBuilder str = new StringBuilder(args[0]);
				for(int i = 1; i < args.length; i++) {
				    str.append(' ').append(args[i]);
				}
				Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".killmessage.msg", str.toString());
				Main.PC.srPlayers();
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GREEN + "You just set your kill message as '" + str.toString() + "'!"));
			} else {
				p.sendMessage(ChatColor.RED + "You must be a donor member to use this command! Do " + ChatColor.DARK_RED + "/donate" + ChatColor.RED + " to get the link to donating.");
			}
		}
		return true;
	}
	
	public static void onAnvilClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		InventoryView view = event.getView();
		int rawSlot = event.getRawSlot();
		event.setCancelled(true);
		if(rawSlot == view.convertSlot(rawSlot)) {
			/*
			slot 0 = left item slot
			slot 1 = right item slot
			slot 2 = result item slot
 
			see if the player clicked in the result item slot of the anvil inventory
			*/
			if(rawSlot == 2) {
				/*
				get the current item in the result slot
				I think inv.getItem(rawSlot) would be possible too
				 */
				ItemStack item = event.getCurrentItem();
				// check if there is an item in the result slot
				if(item != null) {
					ItemMeta meta = item.getItemMeta();
					// it is possible that the item does not have meta data
					if(meta != null) {
						// see whether the item is being renamed
						if(meta.hasDisplayName()){
							String displayName = meta.getDisplayName().toString();
							Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".killmessage.msg", displayName);
							Main.PC.srPlayers();
							p.closeInventory();
							p.sendMessage(ChatColor.GOLD + "Kill message set to: '" + ChatColor.WHITE + p.getName() + " " + ChatColor.translateAlternateColorCodes('&', displayName)
									+ ChatColor.GOLD + "'!");
							return;
						} else {
							p.sendMessage(ChatColor.DARK_RED + "You must have a kill message!");
							return;
						}
					} else {
						p.sendMessage(ChatColor.DARK_RED + "You must have a kill message!");
						return;
					}
				}
			}
		}
	}
	
	public void openAnvil(Player p) {
		Inventory inv = Bukkit.createInventory(p, InventoryType.ANVIL);
		for (int i = 0 ; i < 3 ; i++)
			inv.setItem(i, new ItemStack(Material.NAME_TAG, i + 1));
		p.openInventory(inv);
	}
}
