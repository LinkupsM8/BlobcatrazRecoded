package com.ToonBasic.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandVanish extends ICommand implements Listener {
	private static List<Player> vanished = Util.newList();
	private static final PotionEffectType INVIS = PotionEffectType.INVISIBILITY;
	private static final String SEE_INVIS = "blobcatraz.staff.vanish.see";
	
	public CommandVanish() {super("vanish", "<on/off>", "blobcatraz.staff.vanish", "v");}

	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String sub = args[0].toLowerCase();
			if(sub.equals("on")) {
				vanished.add(p);
				for(Player o : Bukkit.getOnlinePlayers()) {
					o.hidePlayer(p);
					if(o.hasPermission(SEE_INVIS)) o.showPlayer(p);
				}
				PotionEffect pe = new PotionEffect(INVIS, Integer.MAX_VALUE, 255, true, false);
				p.addPotionEffect(pe);
				p.sendMessage("You are now invisible to everyone on the server");
			} else if(sub.equals("off")) {
				vanished.remove(p);
				for(Player o : Bukkit.getOnlinePlayers()) {o.showPlayer(p);}
				p.removePotionEffect(INVIS);
				p.sendMessage("You are no longer vanished!");
			} else {
				String error = Util.color(Language.INCORRECT_USAGE);
				p.sendMessage(error);
			}
		}
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPermission(SEE_INVIS)) {
			for(Player o : vanished) {
				p.hidePlayer(o);
			}
		}
	}
}