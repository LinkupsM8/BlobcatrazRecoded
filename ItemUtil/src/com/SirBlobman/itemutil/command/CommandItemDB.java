package com.SirBlobman.itemutil.command;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandItemDB extends ICommand {
	public CommandItemDB() {super("itemdb", "[meta]", "blobcatraz.special.itemdb", "iteminfo", "itemname");}
	
	@Override
	@SuppressWarnings("deprecation")
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();	
		if(!ItemUtil.air(is)) {
			Material mat = is.getType();
			if(args.length > 0) {
				String sub = args[0].toLowerCase();
				if(sub.equals("meta")) {
					ItemMeta meta = is.getItemMeta();
					Class<? extends ItemMeta> metac = meta.getClass();
					String ctype = metac.getSimpleName();
					String name = ItemUtil.name(is);
					List<String> lore = meta.getLore();
					String[] msg = Util.color(new String[] {
						prefix + "Item Metadata:",
						"&e Class Type: &f" + ctype,
						"&e Display Name: &f" + name,
						"&e Lore: &f\n" + Util.formatList('-', lore),
					});
					p.sendMessage(msg);
				} else if(sub.equals("nbt")) {
					String nbt = Util.strip(ItemUtil.nbt(is));
					String[] msg = Util.color(new String[] {
						prefix + "Item NBT Data:",
						"&e" + nbt
					});
					p.sendMessage(msg);
				} else {
					String error = prefix + Language.INCORRECT_USAGE;
					p.sendMessage(error);
				}
			} else {
				String name = mat.name();
				int amount = is.getAmount();
				int id = mat.getId();
				int data = is.getDurability();
				String[] msg = Util.color(new String[] {
					prefix + "Item Information:",
					"&e Material Name: &f" + name,
					"&e Amount: &f" + amount,
					"&e ID: &f" + id,
					"&e Data: &f" + data
				});
				p.sendMessage(msg);
			}
		} else {
			String[] msg = Util.color(new String[] {
				prefix + "Item Information:",
				"&e Material Name: &fAIR",
				"&e ID: &f0",
				"&e Data: &f0",
				"&e Meta: &fnull"
			});
			p.sendMessage(msg);
		}
}
}