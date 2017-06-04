package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;

import net.md_5.bungee.api.chat.TextComponent;

@PlayerOnly
public class CommandItem extends ICommand {
	public CommandItem() {super("item", "<item> [amount] [meta] [nbt]", "blobcatraz.staff.item", "i", "selfgive");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		String id = args[0];
		int amount = 1;
		short data = 0;
		String nbt = "{}";
		if(args.length > 1) amount = NumberUtil.getInteger(args[1]);
		if(args.length > 2) data = NumberUtil.toShort(NumberUtil.getInteger(args[2]));
		if(args.length > 3) nbt = args[3];
		if(amount < 1) {
			String error = "Invalid amount value! Defaulting to 1";
			p.sendMessage(error);
			amount = 1;
		} if(data < 0) {
			String error = "Invalid data value! Defaulting to 0";
			p.sendMessage(error);
			data = NumberUtil.toShort(0);
		} if(!nbt.startsWith("{")) nbt = "{}";

		ItemStack give = ItemUtil.getItem(id, amount, data);
		ItemUtil.setNBT(give, nbt);
		pi.addItem(give);
		String msg = Util.color(prefix + "&fYou received &e");
		TextComponent text = new TextComponent(msg);
		TextComponent item = ItemUtil.getHover(give);
		text.addExtra(item);
		Spigot s = p.spigot();
		s.sendMessage(text);
	}
}