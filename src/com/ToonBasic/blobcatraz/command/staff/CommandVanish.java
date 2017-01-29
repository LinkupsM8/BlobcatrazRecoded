package com.ToonBasic.blobcatraz.command.staff;

import static com.ToonBasic.blobcatraz.packets.VanishedPlayers.vanished;

import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.packets.VanishedPlayers;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

@PlayerOnly
public class CommandVanish extends ICommand implements Listener {
	private final ProtocolManager pm = ProtocolLibrary.getProtocolManager();
	private VanishedPlayers vanish;

	public CommandVanish() {super("vanish", "<on/off>", "blobcatraz.staff.vanish", "v");}

	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		UUID uuid = p.getUniqueId();
		if (args.length > 0) {
			String arg = args[0].toLowerCase();
			if (arg.equals("on")) {
				vanished.add(uuid);
				p.sendMessage(prefix + "You are now in Vanish!");
			} else if (arg.equals("off")) {
				vanished.remove(uuid);
				p.sendMessage(prefix + "You are no longer in vanish!");
			}
		}
	}
}