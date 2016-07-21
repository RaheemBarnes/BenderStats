package me.Bryan.BenderStats;

import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import me.Bryan.BenderStats.Listeners.PlayerLogin;
import me.Bryan.BenderStats.Methods.KillStreakMethod;
import me.Bryan.BenderStats.SQL.MySQL;

public class Main extends JavaPlugin {
	public static Main main;
	private final static KillStreakMethod killStreakMethod = new KillStreakMethod(main);

	public void onDisable() {
		try {
			if (MySQL.connection != null && !MySQL.connection.isClosed()) {
				MySQL.connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void onEnable() {
		registerListeners();
	}

	public void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerLogin(this), this);
		getServer().getPluginManager().registerEvents(new KillStreakMethod(this), this);
	}

	public void registerCommands() {
		//this.getCommand("").setExecutor(new Class(this));
	}

	public KillStreakMethod getKillStreakMethod() {
		return killStreakMethod;
	}

}
