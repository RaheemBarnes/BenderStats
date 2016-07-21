package me.Bryan.BenderStats.Listeners;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import me.Bryan.BenderStats.Main;
import me.Bryan.BenderStats.SQL.MySQL;

public class PlayerLogin implements Listener {
	Main main;
	public PlayerLogin(Main main) {
		this.main = main;
	}
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		MySQL.getConnection();
		try {
			int previousLogins = 0;
			if (MySQL.playerDataContainsPlayer(event.getPlayer())) {
				PreparedStatement sql = MySQL.getConnection()
						.prepareStatement("SELECT logins FROM `player_data` WHERE player=?");
				sql.setString(1, event.getPlayer().getName());
				ResultSet result = sql.executeQuery();
				result.next();
				previousLogins = result.getInt("logins");
				PreparedStatement loginsUpdate = MySQL.getConnection()
						.prepareStatement("UPDATE `player_data` SET logins=? WHERE player=?;");
				loginsUpdate.setInt(1, previousLogins + 1);
				loginsUpdate.setString(2, event.getPlayer().getName());
				loginsUpdate.executeQuery();
				loginsUpdate.close();
				sql.close();
				result.close();
			} else {
				PreparedStatement newPlayer = MySQL.getConnection()
						.prepareStatement("INSERT INTO `player_data` values(?,0,0,1)");
				newPlayer.setString(1, event.getPlayer().getName());
				newPlayer.execute();
				newPlayer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQL.closeConnection();
		}
	}
}
