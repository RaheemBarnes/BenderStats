package me.Bryan.BenderStats;

import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.Bryan.BenderStats.ConfigManager.Config;
import me.Bryan.BenderStats.Listeners.PlayerLogin;
import me.Bryan.BenderStats.Methods.KillDeathRatio;
import me.Bryan.BenderStats.Methods.KillStreakMethod;
import me.Bryan.BenderStats.SQL.MySQL;

public class Main extends JavaPlugin {
	public static Main main;
	private final static KillStreakMethod killStreakMethod = new KillStreakMethod(main);
	private final static KillDeathRatio killDeathRatio = new KillDeathRatio(main);
	private final static Config configClass= new Config(main);
	public FileConfiguration config = main.getConfig();
	
	public void onDisable() {
		try {
			if (MySQL.connection != null && !MySQL.connection.isClosed()) {
				MySQL.connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		configClass.saveYamls();
	}

	public void onEnable() {
		registerListeners();
		configClass.loadConfig();
		try{
			getConfigClass().firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
		config = new YamlConfiguration();
		configClass.loadYamls();
	}

	public void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerLogin(this), this);
		getServer().getPluginManager().registerEvents(new KillStreakMethod(this), this);
		getServer().getPluginManager().registerEvents(new KillDeathRatio(this), this);
	}

	public void registerCommands() {
		//this.getCommand("").setExecutor(new Class(this));
	}

	public KillStreakMethod getKillStreakMethod() {
		return killStreakMethod;
	}
	
	public KillDeathRatio getKDRC() {
		return killDeathRatio;
	}
	public Config getConfigClass() {
		return configClass;
	}

}
