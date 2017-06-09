package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandHeal extends ICommand {
	public CommandHeal() {super("heal", "[player]", "blobcatraz.staff.heal");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		Player t = p;
		if(args.length > 0) {
			String target = args[0];
			t = Bukkit.getPlayer(target);
			if(t == null) t = p;
		}
		heal(t);
		t.sendMessage("You were healed!");
		if(t != p) p.sendMessage(Util.color("You healed &5" + t.getName()));
	}
	
	private void heal(Player p) {
		AttributeInstance ai = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		double max = ai.getBaseValue();
		p.setHealth(max);
		p.setFoodLevel(20);
		p.setSaturation(20.0F);
		for(PotionEffect pe : p.getActivePotionEffects()) {
			PotionEffectType pet = pe.getType();
			p.removePotionEffect(pet);
		}
	}
}