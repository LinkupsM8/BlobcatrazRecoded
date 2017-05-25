package com.ToonBasic.blobcatraz.command.troll;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandTroll extends ICommand {
	public CommandTroll() {super("troll", "<player> <troll> [amount of times]", "blobcatraz.troll", "punish");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String troll = args[1].toLowerCase();
			int amount = 1;
			try {amount = Integer.parseInt(args[2]);} catch(Throwable ex) {amount = 1;}
			if(amount > 50) amount = 50;
			t.setGameMode(GameMode.SURVIVAL);
			for(int i = amount; i > 0; i--) {
				if(troll.equals("fall")) fall(t);
				else if(troll.equals("explode")) explode(t);
				else if(troll.equals("strike")) strike(t);
				else if(troll.equals("burn")) burn(t);
				else if(troll.equals("hounds")) hounds(t);
				else if(troll.equals("golem")) golem(t);
				else if(troll.equals("end")) end(t);
				else if(troll.equals("creeper")) creeper(t);
				else if(troll.equals("pigmen")) pigmen(t);
				else {
					String error = "Invalid Troll!";
					p.sendMessage(error);
					break;
				}
			}
		} else {
			String error = prefix + Language.INVALID_TARGET;
			p.sendMessage(error);
		}
	}
	
	private void fall(Player p) {
		Location l = p.getLocation();
		int y = l.getBlockY();
		int ny = y + 200;
		l.setY(ny);
		p.setFlying(false);
		p.setAllowFlight(false);
		p.teleport(l);
	}
	
	private void explode(Player p) {
		World w = p.getWorld();
		Location l = p.getLocation();
		w.createExplosion(l.getX(), l.getY(), l.getZ(), 100.0F, false, false);
	}
	
	private void strike(Player p) {
		World w = p.getWorld();
		Location l = p.getLocation();
		w.strikeLightning(l);
	}
	
	private void burn(Player p) {
		p.setFireTicks(100);
	}
	
	private void hounds(Player p) {
		World w = p.getWorld();
		Location l = p.getLocation();
		Wolf wolf = w.spawn(l, Wolf.class);
		wolf.setAngry(true);
		wolf.setTarget(p);
	}
	
	private void golem(Player p) {
		World w = p.getWorld();
		Location l = p.getLocation();
		IronGolem ig = w.spawn(l, IronGolem.class);
		ig.setPlayerCreated(false);
		ig.setTarget(p);
	}
	
	private void end(Player p) {
		p.setHealth(0.0D);
	}
	
	private void creeper(Player p) {
		World w = p.getWorld();
		Location l = p.getLocation();
		Creeper creeper = w.spawn(l, Creeper.class);
		creeper.setPowered(true);
	}
	
	private void pigmen(Player p) {
		World w = p.getWorld();
		Location l = p.getLocation();
		PigZombie pig = w.spawn(l, PigZombie.class);
		pig.setAngry(true);
		pig.setAnger(Integer.MAX_VALUE);
		pig.setTarget(p);
	}
}