package com.SirBlobman.blobcatraz.flatgenerator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class FlatPopulator extends BlockPopulator {
	byte[] data;
	protected FlatPopulator(byte[] data) {this.data = data;}
	
	@Override
	@SuppressWarnings("deprecation")
	public void populate(World w, Random r, Chunk c) {
		if(data != null) {
			int x = c.getX() << 4;
			int z = c.getZ() << 4;
			for(int y = 0; y < data.length; y++) {
				byte d = data[y];
				if(d == 0) continue;
				for(int xx = 0; xx < 16; xx++) {
					for(int zz = 0; zz < 16; zz++) {
						Block b = w.getBlockAt(x + xx, y, z + zz);
						b.setData(d);
					}
				}
			}
		}
	}
}
