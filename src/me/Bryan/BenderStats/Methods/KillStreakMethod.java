package me.Bryan.BenderStats.Methods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

import me.Bryan.BenderStats.Main;
import me.Bryan.BenderStats.SQL.MySQL;

public class KillStreakMethod implements Listener {
	public HashMap<String, Integer> killstreak = new HashMap<String, Integer>();
	Main main;

	public KillStreakMethod(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!killstreak.containsKey(player.getName())) {
				killstreak.put(player.getName(), 0);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = ((OfflinePlayer) event).getPlayer();
		if (main.getKillStreakMethod().killstreak.containsKey(player.getName())) {
			main.getKillStreakMethod().killstreak.put(player.getName(), 0);
		}
		Player killed = event.getEntity();
		if (killed.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent dmgEvent = (EntityDamageByEntityEvent) killed.getLastDamageCause();
			if (dmgEvent.getDamager() instanceof Player) {
				Player killer = (Player) dmgEvent.getDamager();
				if (main.getKillStreakMethod().killstreak.containsKey(killer.getName())) {
					main.getKillStreakMethod().killstreak.put(killer.getName(),
							main.getKillStreakMethod().killstreak.get(killer.getName() + 1));
				}
				if(main.getKillStreakMethod().killstreak.containsKey(killed.getName())) {
					main.getKillStreakMethod().killstreak.put(killed.getName(), 0);
				}
			}
		}
		// MySQL Basis
		MySQL.getConnection();
		try {
			int deaths = 0;
			if (MySQL.playerDataContainsPlayer(player)) {
				PreparedStatement sql = MySQL.getConnection()
						.prepareStatement("SELECT deaths FROM `player_data` WHERE player=?");
				sql.setString(1, player.getName());
				ResultSet result = sql.executeQuery();
				result.next();
				deaths = result.getInt("deaths");
				PreparedStatement deathsUpdate = MySQL.getConnection()
						.prepareStatement("UPDATE `player_data` SET deaths=? WHERE player=?;");
				deathsUpdate.setInt(1, deaths + 1);
				deathsUpdate.setString(2, player.getName());
				deathsUpdate.executeQuery();
				deathsUpdate.close();
				sql.close();
				result.close();
			} else {
				PreparedStatement newPlayer = MySQL.getConnection()
						.prepareStatement("INSERT INTO `player_data` values(?,0,0,1)");
				newPlayer.setString(1, player.getName());
				newPlayer.execute();
				newPlayer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQL.closeConnection();
		}

	}

	public void manualCheck() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		scheduler.scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (!killstreak.containsKey(player.getName())) {
						killstreak.put(player.getName(), 0);
					}
				}
			}

		}, 20L);// CHANGE THIS TO EVERY 5 minutes
	}
	
	
}
