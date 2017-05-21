package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandItem extends ICommand {
	public CommandItem() {super("item", "<item> [amount] [meta]", "blobcatraz.staff.item", "i", "selfgive");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		String id = args[0];
		int amount = 1;
		short data = 0;
		try{amount = Integer.parseInt(NumberUtil.onlyInteger(args[1]));} catch(Exception ex) {p.sendMessage("Invalid item amount. Defaulting to 1"); amount = 1;}
		try{data = Short.parseShort(NumberUtil.onlyInteger(args[2]));} catch(Exception ex) {p.sendMessage("Invalid meta value. Defaulting to 0"); data = (short) 0;}

		ItemStack give = ItemUtil.getItem(id, amount, data);
		pi.addItem(give);
		String msg = Util.color(prefix + "&fYou received &e" + amount + "&f of &c" + ItemUtil.name(give));
		p.sendMessage(msg);
	}
}