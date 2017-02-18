package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandItem extends ICommand {
	public CommandItem() {super("item", "<item> [amount] [meta]", "blobcatraz.staff.item", "i", "selfgive");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		ItemStack give = match(args);
		pi.addItem(give);
		String msg = Util.color(prefix + "&fYou received &e" + give.getAmount() + " &fof &c" + give.getType().name() + ":" + give.getDurability());
		p.sendMessage(msg);
	}
	
	private ItemStack match(String[] args) {
		Material mat = Material.AIR;
		int amount = 1;
		short meta = 0;
		
		try {
			String ma = args[0].toUpperCase();
			mat = Material.matchMaterial(ma);
			String am = args[1];
			amount = Integer.parseInt(am);
			String me = args[2];
			meta = Short.parseShort(me);
		} catch(Exception ex) {}
		finally {
			if(mat == null) mat = Material.AIR;
		}
		
		ItemStack is = new ItemStack(mat, amount, meta);
		return is;
	}
}