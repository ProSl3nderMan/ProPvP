package me.Pro.Events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.Pro.Managers.ArenaManager;
import me.Pro.Managers.KillstreakItems;
import me.Pro.Managers.VariableManager;
import me.Pro.ProPvP.Main;

public class OnDeath implements Listener {
	
	public OnDeath(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent event) {
		
		Player p = event.getEntity();
		if (VariableManager.arenas.get("1").contains(p.getName())) {
			ArenaManager.EndArena("1");
		}
		if (VariableManager.arenas.get("2").contains(p.getName())) {
			ArenaManager.EndArena("2");
		}
		if (VariableManager.arenas.get("3").contains(p.getName())) {
			ArenaManager.EndArena("3");
		}
		
		if(p.getKiller() instanceof Player) {
			p.sendMessage(ChatColor.RED + "You have been killed by " + p.getKiller().getName() + "!");
			Player killer = p.getKiller();
			int killstreakk = VariableManager.killstreak.get(killer.getName());
			int killstreakp = VariableManager.killstreak.get(p.getName());
			VariableManager.killstreak.put(p.getName(), killstreakp + 1);
			VariableManager.killstreak.put(killer.getName(), killstreakk + 1);
			VariableManager.kills.put(killer.getPlayer().getUniqueId().toString(), (VariableManager.kills.get(killer.getPlayer().getUniqueId().toString()) + 1));
	  		killer.sendMessage(ChatColor.GREEN + "You have just recieved $5 for killing " + p.getName() + "!");
	  		if (Main.VM.tier == 0)
	  			Main.econ.depositPlayer(killer, 5);
	  		else if (Main.VM.tier == 1) 
	  			Main.econ.depositPlayer(killer, 10);
	  		else if (Main.VM.tier == 2)
	  			Main.econ.depositPlayer(killer, 15);
	  		List<Integer> kslist = Main.KSC.getKS().getIntegerList("killstreaks.list");
	  		if (kslist.contains(killstreakk)) {
	  			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GREEN + killer.getName() + ChatColor.AQUA + " is " + Main.KSC.getKS().getString("killstreaks." + killstreakk
	  					+ ".messagek")));
	  			Main.econ.depositPlayer(killer, Main.KSC.getKS().getInt("killstreaks." + killstreakk + ".rewards.moneyforks"));
	  			killer.sendMessage(ChatColor.GREEN + "You were rewarded " + ChatColor.DARK_GREEN + Main.KSC.getKS().getInt("killstreaks." + killstreakk + ".rewards.moneyforks")
	  					+ ChatColor.GREEN + "$ for getting a " + ChatColor.DARK_GREEN + killstreakk + ChatColor.GREEN + " kill killstreak!");
	  			KillstreakItems.GiveItem(killstreakk, killer);
	  		}
	  		if (kslist.contains(killstreakp)) {
	  			Main.econ.depositPlayer(killer, Main.KSC.getKS().getInt("killstreaks." + killstreakp + ".rewards.moneyforendingks"));
	  			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.DARK_RED + killer.getName() + ChatColor.DARK_GREEN + " has been awarded " + Main.KSC.getKS().getInt("killstreaks." 
	  					+ killstreakp + ".rewards.moneyforendingks") + "$ for ending " + killer.getName() + "'s... " + Main.KSC.getKS().getString("killstreaks." + killstreakp + ".messagep")));
	  			killer.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.DARK_GREEN + "You have been awarded " + Main.KSC.getKS().getInt("killstreaks." 
	  					+ killstreakp + ".rewards.moneyforendingks") + "$ for " + Main.KSC.getKS().getString("killstreaks." + killstreakp + ".messagep")));
	  		}
    		if (killer.hasPermission("propvp.killmsg") && Main.PC.getPlayers().contains("players." + killer.getPlayer().getUniqueId() + ".killmessage"))
    			event.setDeathMessage(ChatColor.translateAlternateColorCodes('&',p.getName() + " " + Main.PC.getPlayers().getString("players." + killer.getPlayer().getUniqueId() + ".killmessage.msg")));
		}
		VariableManager.deaths.put(p.getPlayer().getUniqueId().toString(), (VariableManager.deaths.get(p.getPlayer().getUniqueId().toString()) + 1));
		VariableManager.killstreak.put(p.getName(), 0);
	}
}
