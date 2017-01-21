package com.ToonBasic.blobcatraz.utility;

public enum Symbol
{
	//Faces
	SMILEY1(":)", '\u263A'),
	SMILEY2(":-)", '\u263B'),
	SADFACE(":(", '\u2639'),
	HEART("<3", '\u2665'),
	INFINITY("(8)", '\u221E'),
	CENTS("($c)", '\u00A2'),
	EUROS("($e)", '\u20AC'),
	POUND("($p)", '\u00A3'),
    PARAGRAPH("(p)", '\u00B6'),
    LEFT_CHEVRONS("<<", '\u00AB'),
    RIGHT_CHEVRONS(">>", '\u00BB');
	
	private String replace;
	private char symbol;
	Symbol(String replace, char symbol)
	{
		this.replace = replace;
		this.symbol = symbol;
	}
	
	public String replace() {return replace;}
	public char symbol() {return symbol;}
	
	/**
	 * Replaces all valid codes with their actual symbol
	 * @param o Original Text
	 * @return Formatted Text with symbols
	 */
	public static String replace(String o)
	{
		String ret = "";
		for(Symbol s : values())
		{
			String re = s.replace();
			String sy = Character.toString(s.symbol());
			ret = o.replace(re, sy);
		}
		return ret;
	}
}