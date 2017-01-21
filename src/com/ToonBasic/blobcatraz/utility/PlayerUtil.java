package com.ToonBasic.blobcatraz.utility;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class PlayerUtil extends Util
{
	/**
	 * Send two loud experience noises that will get the {@link Player}'s attention
	 * @param p Player that needs to pay attention
	 */
	public static void ping(Player p)
	{
		Location l = p.getLocation();
		Sound EXP = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
		SoundCategory NEU = SoundCategory.NEUTRAL;
		p.playSound(l, EXP, NEU, 100.0F, 1.0F);
		Runnable later = new Runnable()
        {
            @Override
            public void run() {p.playSound(l, EXP, NEU, 100.0F, 1.0F);}
        };
		SCHEDULER.runTaskLater(PLUGIN, later, 5);
	}
}