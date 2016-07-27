package me.Bryan.BenderStats.ConfigManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;

import me.Bryan.BenderStats.Main;

public class Config {
	Main main;
	File configFile;
	FileConfiguration config;

	public Config(Main main) {
		this.main = main;
	}

	public void loadConfig() {
		String userpath = "MySQL.username";
		String passpath = "MySQL.password";
		String isolationpath = "MySQL.isolation";
		String driverpath = "MySQL.driver";
		String urlpath = "MySQL.url";

		main.getConfig().options().copyDefaults(true);
		main.getConfig().addDefault(userpath, null);
		main.getConfig().addDefault(passpath, null);
		main.getConfig().addDefault(isolationpath, null);
		main.getConfig().addDefault(driverpath, null);
		main.getConfig().addDefault(urlpath, null);

		main.saveConfig();
	}

	public void initilization() {
		configFile = new File(main.getDataFolder(), "config.yml");
	}

	public void firstRun() throws Exception {
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			copyFile(main.getResource("config.yml"), configFile);
		}
	}

	private void copyFile(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveYamls() {
		try{
			config.save(configFile);
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	
	public void loadYamls() {
		try{
			config.load(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
