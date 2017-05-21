package com.ToonBasic.blobcatraz.utility;

import java.util.Random;

public class NumberUtil extends Util {
	public static Random random() {
		Random rand = new Random();
		return rand;
	}
	
	public static Random random(long seed) {
		Random rand = random();
		rand.setSeed(seed);
		return rand;
	}
	
	public static int randomInt(Random rand, int min, int max) {
		int r = rand.nextInt(max);
		r = r + min;
		return r;
	}
	
	public static double randomChance(Random rand) {
		double r = rand.nextGaussian();
		return r;
	}

    public static String onlyInteger(String o) {
    	String n = o.replaceAll("[^\\d-]", "");
    	return n;
    }
    
    public static String onlyDouble(String o) {
    	String n = o.replaceAll("[^\\d-.E]", "");
    	return n;
    }
    
    public static short toShort(Number n) {
    	if(n.doubleValue() > Short.MAX_VALUE) n = Short.MAX_VALUE;
    	if(n.doubleValue() < 0) n = 0;
    	return n.shortValue();
    }
    
    public static String cropDecimal(double d, int places) {
    	String format = "%,." + places + "f";
    	String dec = String.format(format, d);
    	return dec;
    }
    
    public static String money(double amount) {
    	String format = cropDecimal(amount, 2);
    	boolean neg = (amount < 0);
    	boolean inf = (amount >= Double.POSITIVE_INFINITY);
    	boolean neginf = (amount <= Double.NEGATIVE_INFINITY);
    	String money = neg ? color("&c$" + format) : color("&a$" + format);
    	if(inf) money = color("&a$\u221E");
    	if(neginf) money = color("&c$-\u221E");
    	return money;
    }
}