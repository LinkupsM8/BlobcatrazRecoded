package com.SirBlobman.blobcatraz.command.special;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.TileEntity;

public class CommandBlockData extends PlayerCommand {
	public CommandBlockData() {super("blockdata", "", "blobcatraz.special.blockdata", "bd");}
	
	@Override
	public void run(Player p, String[] args) {
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
				String s = Util.strip(nbt);
				String[] msg = Util.color(prefix + "Block NBT Data:", "&e" + s);
				p.sendMessage(msg);
			} else {
				String error = "That block does not have any tile data.";
				p.sendMessage(error);
			}
		} else {
			String error = "You are not looking at a Block!";
			p.sendMessage(error);
		}
	}
}