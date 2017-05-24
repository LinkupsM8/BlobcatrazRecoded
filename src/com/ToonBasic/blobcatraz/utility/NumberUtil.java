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
		if(r < min) r += Math.abs(min);
		return r;
	}
	
	public static double randomChance(Random rand) {
		double r = rand.nextGaussian();
		return r;
	}
    
    public static int getInteger(String o) {
    	try {
    		String n = o.replaceAll("[^\\d-]", "");
    		int i = Integer.parseInt(n);
    		return i;
    	} catch(Exception ex) {return 0;}
    }
    
    public static double getDouble(String o) {
    	try {
    		String n = o.replaceAll("[^\\d-.E]", "");
    		double d = Double.parseDouble(n);
    		return d;
    	} catch(Exception ex) {return 0.0D;}
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
    
    public static double relative(String sd, double od) {
    	if(sd.equals("~")) return od;
    	else {
        	char[] cd = sd.toCharArray();
        	boolean relative = (cd[0] == '~');
        	double d = getDouble(sd);
        	if(relative) {
        		double n = d + od;
        		return n;
        	} else return d;
    	}
    }
}