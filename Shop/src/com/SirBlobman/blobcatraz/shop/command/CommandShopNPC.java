package com.SirBlobman.blobcatraz.shop.command;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.shop.compat.citizens.ShopTrait;
import com.SirBlobman.blobcatraz.shop.compat.citizens.ShopTrait.ShopType;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.npc.NPCSelector;
import net.citizensnpcs.api.trait.Trait;

public class CommandShopNPC extends PlayerCommand {
	public CommandShopNPC() {super("shopnpc", "<create/type> <name/type>", "blobcatraz.special.shopnpc");}
	
	@Override
	public void run(Player p, String[] args) {
		String sub = args[0].toLowerCase();
		if(sub.equals("create")) {
			String name = args[1];
			NPCRegistry reg = CitizensAPI.getNPCRegistry();
			NPC npc = reg.createNPC(EntityType.PLAYER, name);
			if(npc == null) {
				String error = "Failed to make an NPC with that type, check console for any errors";
				p.sendMessage(error);
			} else {
				npc.addTrait(ShopTrait.class);
				npc.spawn(p.getLocation());
				p.sendMessage("Successfully created a shop NPC. Now do /shopnpc type <type>");
			}
		} else if(sub.equals("type")) {
			NPCSelector sel = CitizensAPI.getDefaultNPCSelector();
			NPC npc = sel.getSelected(p);
			if(npc == null) {
				String error = "You do not have an NPC selected. Do '/npc sel' first!";
				p.sendMessage(error);
			} else {
				Iterable<Trait> traits = npc.getTraits();
				ShopTrait trait = null;
				for(Trait t : traits) {
					if(t instanceof ShopTrait) {
						trait = (ShopTrait) t;
						break;
					}
				}
				if(trait == null) {
					String error = "The NPC you selected is not a shop!";
					p.sendMessage(error);
				} else {
					String type = args[1].toUpperCase();
					ShopType shop = ShopType.valueOf(type);
					if(shop == null) {
						String error = "Invalid shop type!";
						p.sendMessage(error);
					} else {
						trait.setShopType(shop);
						npc.addTrait(trait);
						p.sendMessage("Set the shop type of '" + npc.getFullName() + "' to '" + shop.name() + "'.");
					}
				}
			}
		} else {
			String error = prefix + getFormattedUsage(getCommandUsed());
			p.sendMessage(error);
		}
	}
}