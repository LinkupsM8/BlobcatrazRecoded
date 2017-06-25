package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

import org.bukkit.WeatherType;
import org.bukkit.entity.Player;

public class CommandPWeather extends PlayerCommand {
	private static final WeatherType SUN = WeatherType.CLEAR;
	private static final WeatherType RAIN = WeatherType.DOWNFALL;
	public CommandPWeather() {super("pweather", "<sun/rain>", "blobcatraz.special.pweather");}
	
	@Override
	public void run(Player p, String[] args) {
		String sub = args[0].toLowerCase();
		if(sub.equals("sun")) {
			p.setPlayerWeather(SUN);
			String msg = prefix + "The weather is now sunny forever!";
			p.sendMessage(msg);
		} else if(sub.equals("rain")) {
			p.setPlayerWeather(RAIN);
			String msg = prefix + "The weather is now rainy forever!";
			p.sendMessage(msg);
		} else if(sub.endsWith("reset")) {
			p.resetPlayerWeather();
			String msg = prefix + "Your weather is now the same as the server.";
			p.sendMessage(msg);
		} else {
			String error = prefix + getFormattedUsage(getCommandUsed());
			p.sendMessage(error);
		}
	}
}