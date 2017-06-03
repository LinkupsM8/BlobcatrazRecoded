package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.TileEntity;

@PlayerOnly
public class CommandBlockData extends ICommand {
	public CommandBlockData() {super("blockdata", "", "blobcatraz.staff.blockdata", "bd");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		Block b = PlayerUtil.lookBlock(p);
		if(b != null) {
			World w = b.getWorld();
			CraftWorld cw = (CraftWorld) w;
			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();
			TileEntity te = cw.getTileEntityAt(x, y, z);
			if(te != null) {
				NBTTagCompound tag = new NBTTagCompound();
				te.save(tag);
				String nbt = tag.toString();
				nbt = Util.strip(nbt);
				String[] msg = Util.color(prefix + "Block NBT Data:", "&e" + nbt);
				p.sendMessage(msg);
			} else {
				String error = "That block does not have any data!";
				p.sendMessage(error);
			}
		} else {
			String error = "You are not looking at a Block!";
			p.sendMessage(error);
		}
	}
}