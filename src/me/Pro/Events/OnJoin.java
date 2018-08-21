package me.Pro.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Pro.Managers.ArenaManager;
import me.Pro.Managers.VariableManager;
import me.Pro.ProPvP.Main;

public class OnJoin implements Listener {
	
	public OnJoin(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	

	ArrayList<String> viskits = new ArrayList<String>();
	ArrayList<String> buykits = new ArrayList<String>();
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event) {

		
		Player p = event.getPlayer();
		
		/*
		if (!Main.NC.getArena().contains("players." + event.getPlayer().getUniqueId())) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setDisplayName(p.getName());
			p.setPlayerListName(p.getName());
			Main.NC.getArena().set("players." + p.getUniqueId(), "1");
			Main.NC.srArena();
		}
		*/
		
		String uuid = p.getPlayer().getUniqueId().toString();
		if(Main.PC.getPlayers().contains("players." + p.getPlayer().getUniqueId())) {
			for (String s : Main.KC.getKits().getStringList("kits.buyablekits")) {
				if (Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".viskitlist").contains(s)) {
					
				}else if (Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".buykitlist").contains(s)) {
					
				} else {
					ArrayList<String> tempkits = new ArrayList<String>();
					if (Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".buykitlist").size() != 0)
						tempkits.addAll(Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".buykitlist"));
					tempkits.add(s);
					Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".buykitlist", tempkits);
				}
			}
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".IGN" , p.getName());
			List<String> viskitss = Main.PC.getPlayers().getStringList("players." + p.getPlayer().getUniqueId() + ".viskitlist");
			if (p.hasPermission("propvp.kit.vip") && !viskitss.contains("vip")) {
				viskitss.add("vip");
				Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".viskitlist", viskitss);
				Main.PC.srPlayers();
			}
			if (p.hasPermission("propvp.kit.vipplus") && !viskitss.contains("vipplus")) {
				viskitss.add("vipplus");
				Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".viskitlist", viskitss);
				Main.PC.srPlayers();
			}
			if (p.hasPermission("propvp.kit.vipplusplus") && !viskitss.contains("vipplusplus")) {
				viskitss.add("vipplusplus");
				Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".viskitlist", viskitss);
				Main.PC.srPlayers();
			}
			Main.PC.srPlayers();
			buykits.clear();
			if (!VariableManager.cooldowns.containsKey(p.getName())) {
				List<String> game = VariableManager.cooldowns.get("");
				game = new ArrayList<String>();
				VariableManager.cooldowns.put(p.getName(), game);
			}
		} else {
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() , uuid);
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".IGN" , p.getName());
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".kills", 0);
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".deaths", 0);
			viskits.addAll(Main.KC.getKits().getStringList("kits.starterkits"));
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".viskitlist", viskits);
			buykits.addAll(Main.KC.getKits().getStringList("kits.buyablekits"));
			Main.PC.getPlayers().set("players." + p.getPlayer().getUniqueId() + ".buykitlist", buykits);
    		Main.PC.srPlayers();
    		viskits.clear();
    		buykits.clear();
			List<String> game = VariableManager.cooldowns.get("");
			game = new ArrayList<String>();
			VariableManager.cooldowns.put(p.getName(), game);
		}
		if (!VariableManager.killstreak.containsKey(p.getName())) {
			VariableManager.killstreak.put(p.getName(), 0);
			VariableManager.deaths.put(p.getPlayer().getUniqueId().toString(), 0);
			VariableManager.kills.put(p.getUniqueId().toString(), 0);
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (Main.VM.cooldown.containsKey(p.getName())) {
			if ((Main.VM.tk - Main.VM.cooldown.get(p.getName())) >= 720) {
				Main.VM.cooldown.remove(p.getName());
			} 
		}
		if (VariableManager.arenas.get("1").contains(p.getName())) {
			ArenaManager.EndArena("1");
			VariableManager.arenas.get("1").remove(p.getName());
			p.damage(200000);
		}
		if (VariableManager.arenas.get("2").contains(p.getName())) {
			ArenaManager.EndArena("2");
			VariableManager.arenas.get("2").remove(p.getName());
			ArenaManager.EndArena("2");
			p.damage(200000);
		}
		if (VariableManager.arenas.get("3").contains(p.getName())) {
			ArenaManager.EndArena("3");
			VariableManager.arenas.get("3").remove(p.getName());
			ArenaManager.EndArena("3");
			p.damage(200000);
		}
	}
}
