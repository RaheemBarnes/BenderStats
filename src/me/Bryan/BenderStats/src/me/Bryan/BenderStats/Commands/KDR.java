package me.Bryan.BenderStats.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Bryan.BenderStats.Main;
import me.Bryan.BenderStats.Utils.ChatColor;

public class KDR implements CommandExecutor{
	Main main;
	public KDR (Main main){
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;	
		int kills = main.config.getInt("playerkills." + player.getName().toLowerCase());
		int deaths = main.config.getInt("playerdeaths." + player.getName().toLowerCase());
		double kd = kills;
		
		if(deaths>0) {
			kd = (double)kills/deaths;
		}
		player.sendMessage(ChatColor.Color("&b-=-=-=-PERSONAL KDR BOARD-=-=-=-"));
		player.sendMessage(ChatColor.Color("&4Your kills: "+ deaths));
		player.sendMessage(ChatColor.Color("&4Your deaths: "+ kills));
		player.sendMessage(ChatColor.Color("&4Your KDR: " + kd));
		return true;
	}
	
}
