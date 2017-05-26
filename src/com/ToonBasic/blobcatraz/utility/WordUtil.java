package com.ToonBasic.blobcatraz.utility;

public class WordUtil extends Util {
	public static String possesive(String o) {
		String l = o.toLowerCase();
		boolean end = (l.endsWith("s") || l.endsWith("z"));
		String p = end ? (o + "'") : (o + "'s");
		return p;
	}
	
	public static String plural(String o) {
		String l = o.toLowerCase();
		boolean ies = (l.endsWith("y"));
		boolean es = (l.endsWith("s"));
		boolean i = (l.endsWith("us"));
		String p = o;
		if(ies) p = (o.substring(0, o.length() - 1)) + "ies";
		else if(es) p = o + "es";
		else if(i) p = o.substring(0, o.length() - 2) + "i";
		else p = o + "s";
		return p;
	}
	
	public static String capitalize(String o) {
		o = o.toLowerCase();
		char[] array = o.toCharArray();
		array[0] = Character.toUpperCase(array[0]);
		String s = new String(array);
		return s;
	}
}