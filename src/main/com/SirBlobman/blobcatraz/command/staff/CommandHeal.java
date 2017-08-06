package com.SirBlobman.blobcatraz.command.staff;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandHeal extends PlayerCommand {
	public CommandHeal() {super("heal", "[player]", "blobcatraz.staff.heal");}
	
	@Override
	public void run(Player p, String[] args) {
		Player t = p;
		if(args.length > 0) {
			String target = args[0];
			t = Bukkit.getPlayer(target);
			if(t == null) {
				t = p;
				String error = Util.format(prefix + Language.INVALID_TARGET, target);
				p.sendMessage(error);
			}
		} heal(t, p);
	}
	
	private void heal(Player p, Player healer) {
		Attribute MAX = Attribute.GENERIC_MAX_HEALTH;
		AttributeInstance ai = p.getAttribute(MAX);
		double max = ai.getBaseValue();
		
		p.setHealth(max);
		p.setFoodLevel(20);
		p.setSaturation(20.0F);
		p.setFireTicks(0);
		Collection<PotionEffect> pes = p.getActivePotionEffects();
		for(PotionEffect pe : pes) {
			PotionEffectType pet = pe.getType();
			p.removePotionEffect(pet);
		}
	}
}