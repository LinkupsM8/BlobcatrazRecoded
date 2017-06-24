package com.SirBlobman.blobcatraz.utility;

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
	
	public static int getInteger(String o) {
		try {
			String n = o.replaceAll("[^\\d-]", "");
			int i = Integer.parseInt(n);
			return i;
		} catch(Throwable ex) {return 0;}
	}
	
	public static double getDouble(String o) {
		try {
			String n = o.replaceAll("[^\\d-.E]", "");
			double d = Double.parseDouble(n);
			return d;
		} catch(Exception ex) {return 0.0D;}
	}
	
	public static short toShort(Number n) {
		double d = n.doubleValue();
		if(d > Short.MAX_VALUE) n = Short.MAX_VALUE;
		if(d < 0) n = 0;
		return n.shortValue();
	}
	
	public static String cropDecimal(double d, int places) {
		String format = "%,." + places + "f";
		String dec = format(format, d);
		return dec;
	}
	
	public static String money(double amount) {
		String format = cropDecimal(amount, 2);
		boolean neg = (amount < 0);
		boolean inf = (amount >= Double.POSITIVE_INFINITY);
		boolean neginf = (amount <= Double.NEGATIVE_INFINITY);
		String money = neg ? ("&c$" + format) : ("&a$" + format);
		if(inf) money = "&a$\u221E";
		if(neginf) money = "&c$-\u221E";
		return color(money);
	}
	
	public static double relative(String sd, double od) {
		if(sd.equals("~")) return od;
		else {
			char[] cd = sd.toCharArray();
			char c0 = cd[0];
			boolean relative = (c0 == '~');
			double d = getDouble(sd);
			if(relative) {
				double n = od + d;
				return n;
			} else return d;
		}
	}
	
	public static boolean isEven(int i) {
		boolean even = ((i % 2) == 0);
		return even;
	}
	
	public static boolean isOdd(int i) {
		boolean even = isEven(i);
		return !even;
	}
}