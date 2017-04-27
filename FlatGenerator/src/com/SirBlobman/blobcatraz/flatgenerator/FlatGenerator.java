package com.SirBlobman.blobcatraz.flatgenerator;

import static java.lang.System.arraycopy;
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class FlatGenerator extends ChunkGenerator {
	private short[] layer;
	private byte[] dataValues;
	
	public FlatGenerator() {this("64,stone");}
	@SuppressWarnings("deprecation")
	public FlatGenerator(String id) {
		if(id != null) {
			try {
				int y = 0;
				layer = new short[128];
				dataValues = null;
				if((id.length() > 0) && (id.charAt(0) == '.')) {
					id = id.substring(1); //No Bedrock
				} else {
					layer[y++] = (short) 7; //Bedrock
				}
				
				if(id.length() > 0) {
					String tokens[] = id.split("[,]");
					if((tokens.length % 2) != 0) throw new Exception();
					for(int i = 0; i < tokens.length; i += 2) {
						int height = Integer.parseInt(tokens[i]);
						if(height <= 0) {
							String error = "Invalid height '" + tokens[i] + "'. Using 64 instead.";
							out.println(error);
							height = 64;
						}
						
						String matTokens[] = tokens[i + 1].split("[:]", 2);
						byte dataVal = 0;
						if(matTokens.length == 2) {
							try {
								dataVal = Byte.parseByte(matTokens[1]);
							} catch(Exception ex) {
								String error = "Invalid Data Value '" + matTokens[1] + "'. Defaulting to 0";
								out.println(error);
								dataVal = 0;
							}
						}
						
						Material mat = Material.matchMaterial(matTokens[0]);
						if(mat == null) {
							try {
								int mid = Integer.parseInt(matTokens[0]);
								mat = Material.getMaterial(mid);
							} catch(Exception ex) {}
							
							if(mat == null) {
								String error = "Invalid Block ID '" + matTokens[0] + "'. Defaulting to STONE";
								out.println(error);
								mat = Material.STONE;
							}
						}
						
						if(!mat.isBlock()) {
							String error = "Error, '" + matTokens[0] + "' is not a Block. Defaulting to STONE";
							out.println(error);
							mat = Material.STONE;
						}
						
						if(y + height > layer.length) {
							int max = Math.max(y + height, layer.length * 2);
							short[] newLayer = new short[max];
							arraycopy(layer, 0, newLayer, 0, y);
							layer = newLayer;
							if(dataValues != null) {
								int max2 = Math.max(y + height, dataValues.length * 2);
								byte[] newData = new byte[max2];
								arraycopy(dataValues, 0, newData, 0, y);
								dataValues = newData;
							}
						}
						
						Arrays.fill(layer, y, y + height, (short) mat.getId());
						if(dataVal != 0) {
							if(dataValues == null) {
								dataValues = new byte[layer.length];
							}
							Arrays.fill(dataValues, y, y + height, dataVal);
						}
						
						y += height;
					}
				}
				
				if(layer.length > y) {
					short[] newLayer = new short[y];
					arraycopy(layer, 0, newLayer, 0, y);
					layer = newLayer;
				}
				
				if(dataValues != null && dataValues.length > y) {
					byte[] newData = new byte[y];
					arraycopy(dataValues, 0, newData, 0, y);
					dataValues = newData;
				}
			} catch(Exception ex) {
				String error = "Error parsing ID '" + id + "'. using default:: '64,1': " + ex.toString();
				out.println(error);
				ex.printStackTrace();
				dataValues = null;
				layer = new short[65];
				layer[0] = (short) 7; //Bedrock
				Arrays.fill(layer, 1, 65, (short) 1); //Stone
			}
		} else {
			dataValues = null;
			layer = new short[65];
			layer[0] = (short) 7; //Bedrock
			Arrays.fill(layer, 1, 65, (short) 1); //Stone
		}
	}
	
	@Override
	public short[][] generateExtBlockSections(World w, Random r, int x, int z, BiomeGrid bg) {
		int max = w.getMaxHeight();
		if(layer.length > max) {
			String error = "Error, chunk height " + layer.length + " is greater than the world height (" + max + "). Trimming...";
			out.println(error);
			short[] newLayer = new short[max];
			arraycopy(layer, 0, newLayer, 0, max);
			layer = newLayer;
		}
		
		short[][] result = new short[max / 16][];
		for(int i = 0; i < layer.length; i += 16) {
			result[i >> 4] = new short[4096];
			for(int y = 0; y < Math.min(16, layer.length - i); y++) {
				Arrays.fill(result[i >> 4], y * 16 * 16, (y + 1) * 16 * 16, layer[i + y]);
			}
		}
		return result;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World w) {
		if(dataValues != null) {
			FlatPopulator fp = new FlatPopulator(dataValues);
			return Arrays.asList(fp);
		} else {
			return new ArrayList<BlockPopulator>();
		}
	}
	
	@Override
	public Location getFixedSpawnLocation(World w, Random r) {
		if(!w.isChunkLoaded(0, 0)) {
			w.loadChunk(0, 0);
		}
		
		int maxY = w.getHighestBlockYAt(0, 0);
		if(maxY <= 0 && w.getBlockAt(0, 0, 0).getType() == Material.AIR) {
			Location l = new Location(w, 0, 64, 0);
			return l;
		}
		
		Location l = new Location(w, 0, maxY, 0);
		return l;
	}
}