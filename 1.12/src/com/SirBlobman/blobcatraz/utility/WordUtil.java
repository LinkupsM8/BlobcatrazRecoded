package com.SirBlobman.blobcatraz.utility;

public class WordUtil extends Util {
	public static String possessive(String o) {
		String l = o.toLowerCase();
		boolean s = (l.endsWith("s") || l.endsWith("z"));
		String p = s ? (o + "'") : (o + "'s");
		return p;
	}
	
	public static String capitalize(String o) {
		String l = o.toLowerCase();
		char[] cc = l.toCharArray();
		char c0 = cc[0];
		char up = Character.toUpperCase(c0);
		cc[0] = up;
		String s = new String(cc);
		return s;
	}
	
	public static String asID(String o) {
		String l = o.toLowerCase();
		String id = l.replace(' ', '_');
		return id;
	}
}