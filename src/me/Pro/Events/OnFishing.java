package me.Pro.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import me.Pro.Managers.VariableManager;
import me.Pro.ProPvP.Main;

public class OnFishing implements Listener {
	
	public OnFishing(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {
		event.getState();
		if (event.getState() == State.CAUGHT_FISH) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		if (VariableManager.arenas.get("1").contains(p.getName())) {
			p.sendMessage(event.getMessage());
			if (event.getMessage().equalsIgnoreCase("spawn") || event.getMessage().equalsIgnoreCase("warp")  || event.getMessage().equalsIgnoreCase("warp pvp") || event.getMessage().equalsIgnoreCase("warp staff") || event.getMessage().equalsIgnoreCase("warp dropparty") || event.getMessage().equalsIgnoreCase("warp donorenchant")) {
				event.setCancelled(true);
				p.sendMessage("Do /duel leave instead.");
			}
		}if (VariableManager.arenas.get("2").contains(p.getName())) {
			p.sendMessage(event.getMessage());
			if (event.getMessage().equalsIgnoreCase("spawn") || event.getMessage().equalsIgnoreCase("warp")  || event.getMessage().equalsIgnoreCase("warp pvp") || event.getMessage().equalsIgnoreCase("warp staff") || event.getMessage().equalsIgnoreCase("warp dropparty") || event.getMessage().equalsIgnoreCase("warp donorenchant")) {
				event.setCancelled(true);
				p.sendMessage("Do /duel leave instead.");
			}
		}if (VariableManager.arenas.get("3").contains(p.getName())) {
			p.sendMessage(event.getMessage());
			if (event.getMessage().equalsIgnoreCase("spawn") || event.getMessage().equalsIgnoreCase("warp")  || event.getMessage().equalsIgnoreCase("warp pvp") || event.getMessage().equalsIgnoreCase("warp staff") || event.getMessage().equalsIgnoreCase("warp dropparty") || event.getMessage().equalsIgnoreCase("warp donorenchant")) {
				event.setCancelled(true);
				p.sendMessage("Do /duel leave instead.");
			}
		}
	}
}
