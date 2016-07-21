package me.Bryan.BenderStats.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.entity.Player;

public class MySQL {
	private static int ip;
	private static int port;
	private static String password;
	private static String name;
	public static Connection connection;

	public synchronized static void openConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + name, name, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static boolean playerDataContainsPlayer(Player player) {
		try {
			// Make prepared statement
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `player_data` WHERE player=?");
			sql.setString(1, player.getName());
			// Get result set of query
			ResultSet resultSet = sql.executeQuery();
			// check if player is there and if is empty
			boolean containsPlayer = resultSet.next();
			sql.close();
			resultSet.close();

			return containsPlayer;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public synchronized static void closeConnection() {
		try {
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}

}
