package com.ToonBasic.blobcatraz.utility;

import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_11_R1.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.Set;

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

	public static PlayerConnection connection(Player p)
    {
        CraftPlayer cp = (CraftPlayer) p;
        EntityPlayer ep = cp.getHandle();
        PlayerConnection pc = ep.playerConnection;
        return pc;
    }

	public static void title(Player p, String title, String subtitle)
    {
        String t = json(title);
        String s = json(subtitle);
        IChatBaseComponent it = ChatSerializer.a(t);
        IChatBaseComponent is = ChatSerializer.a(s);
        PacketPlayOutTitle ppot1 = new PacketPlayOutTitle(EnumTitleAction.TITLE, it);
        PacketPlayOutTitle ppot2 = new PacketPlayOutTitle(EnumTitleAction.TITLE, is);
        PlayerConnection pc = connection(p);
        pc.sendPacket(ppot1);
        pc.sendPacket(ppot2);
    }

    public static void action(Player p, String action)
    {
        String a = json(action);
        IChatBaseComponent ac = ChatSerializer.a(a);
        byte a2 = 2;
        PacketPlayOutChat ppoc = new PacketPlayOutChat(ac, a2);
        PlayerConnection pc = connection(p);
        pc.sendPacket(ppoc);
    }

    public static void feed(Player p, boolean heal)
    {
        p.setFoodLevel(20);
        p.setSaturation(20.0F);
        if(heal)
        {
            Attribute MAX_HEALTH = Attribute.GENERIC_MAX_HEALTH;
            AttributeInstance att = p.getAttribute(MAX_HEALTH);
            double max = att.getBaseValue();
            p.setHealth(max);
            for(PotionEffect pe : p.getActivePotionEffects())
            {
                PotionEffectType pet = pe.getType();
                p.removePotionEffect(pet);
            }
        }
    }

    public static void clear(Player p, boolean saveArmor, boolean echest)
    {
        PlayerInventory pi = p.getInventory();
        ItemStack[] armor = pi.getArmorContents().clone();
        ItemStack off = pi.getItemInOffHand().clone();
        pi.clear();
        if(saveArmor)
        {
            pi.setArmorContents(armor);
            pi.setItemInOffHand(off);
        }
        if(echest)
        {
            Inventory chest = p.getEnderChest();
            chest.clear();
        }
    }

    public static Block looking(Player p)
    {
        Set<Material> n = Collections.emptySet();
        int range = 200;
        Block look = p.getTargetBlock(n, range);
        return look;
    }

    public static Location lookingLocation(Player p)
    {
        Block block = looking(p);
        Location look = block.getLocation();
        return look;
    }
}