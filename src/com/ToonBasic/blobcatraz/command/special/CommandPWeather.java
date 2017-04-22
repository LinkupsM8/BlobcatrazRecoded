package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.WeatherType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandPWeather extends ICommand {	
	public CommandPWeather() {super("pweather", "<sun/rain/reset>", "blobcatraz.special.pweather");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String sub = args[0].toLowerCase();
		if(sub.equals("sun")) {
			p.setPlayerWeather(WeatherType.CLEAR);
			String msg = prefix + "Your weather is now sunny forever!";
			p.sendMessage(msg);
		} else if(sub.equals("rain")) {
			p.setPlayerWeather(WeatherType.DOWNFALL);
			String msg = prefix + "It will now rain forever!";
			p.sendMessage(msg);
		} else if(sub.equals("reset")) {
			p.resetPlayerWeather();
			String msg = prefix + "Your weather is now the same as the server!";
			p.sendMessage(msg);
		} else {
			String error = prefix + Language.INCORRECT_USAGE;
			p.sendMessage(error);
		}
	}
}