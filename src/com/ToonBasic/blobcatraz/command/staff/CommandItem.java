package com.ToonBasic.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;
import com.google.common.base.Joiner;

import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_11_R1.MojangsonParser;
import net.minecraft.server.v1_11_R1.NBTTagCompound;

@PlayerOnly	
public class CommandItem extends ICommand {
	public CommandItem() {super("item", "<item> [amount] [meta] [nbt]", "blobcatraz.staff.item", "i", "selfgive");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String id = args[0];
		int amount = 1;
		short data = 0;
		if(args.length > 1) amount = NumberUtil.getInteger(args[1]);
		if(args.length > 2) data = NumberUtil.toShort(NumberUtil.getInteger(args[2]));
		if(amount < 1) {
			String error = "Invalid amount value! Defaulting to 1";
			p.sendMessage(error);
			amount = 1;
		} if(data < 0) {
			String error = "Invalid data value! Defaulting to 0";
			p.sendMessage(error);
			data = NumberUtil.toShort(0);
		}

		ItemStack give = ItemUtil.getItem(id, amount, data);
		if(args.length > 3) {
        	String nbt = Util.finalArgs(3, args);
            try {
            	ItemStack copy = ItemUtil.setNBT(give, nbt);
                give(p, copy);
            } catch (Throwable ex) {
        		String error = "Invalid NBT Tag: '" + nbt + "':\n" + ex.getMessage();
                p.sendMessage(error);
                give(p, give);
            }
        }
	}
	
	private void give(Player p, ItemStack give) {		
		PlayerInventory pi = p.getInventory();
		int slot = pi.firstEmpty();
		if(slot != -1) {
			pi.addItem(give);
			String msg = Util.color(prefix + "&fYou received &e");
			TextComponent text = new TextComponent(msg);
			TextComponent item = ItemUtil.getGiveHover(give);
			text.addExtra(item);
			Spigot s = p.spigot();
			s.sendMessage(text);
		} else {
			String error = "Your inventory is too full to receive items!";
			p.sendMessage(error);
		}
	}
}