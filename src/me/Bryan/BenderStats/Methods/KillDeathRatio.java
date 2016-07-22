package me.Bryan.BenderStats.Methods;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.Bryan.BenderStats.Main;

public class KillDeathRatio implements Listener {
	Main main;
	int killCount = 0;
	int deathCount = 0;
	public KillDeathRatio(Main main) {
		this.main = main;
	}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity deadEntity = event.getEntity();
		Entity killer = event.getEntity().getKiller();

		Player player = (Player) killer;
		Player dead = (Player) deadEntity;

		String killPath = "playerkills." + player.getName().toLowerCase();
		String deathPath = "playerdeaths." + dead.getName().toLowerCase();
		if (killer instanceof Player && deadEntity instanceof Player) {

			if (main.config.contains(killPath)) {
				killCount = main.config.getInt(killPath);
			}

			if (main.config.contains(deathPath)) {
				deathCount = main.config.getInt(deathPath);
			}

			main.config.set(killPath, killCount + 1);
			main.config.set(deathPath, deathCount + 1);
			
			main.saveConfig();
		}
	}

}
