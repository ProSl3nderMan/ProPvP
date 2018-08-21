package me.Pro.Events;

import me.Pro.ProPvP.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class OnHunger implements Listener {

	public OnHunger(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
}
