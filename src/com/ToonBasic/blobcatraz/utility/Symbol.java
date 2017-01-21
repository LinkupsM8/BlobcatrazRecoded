package com.ToonBasic.blobcatraz.utility;

public enum Symbol
{
	/**:) = &#x263A;*/
	SMILEY1(":)", '\u263A'),
	/**:-) = &#x263B;*/
	SMILEY2(":-)", '\u263B'),
	/**:( = &#x2639;*/
	SADFACE(":(", '\u2639'),
	/**<3 = &#x2665*/
	HEART("<3", '\u2665'),
	/**(8) = &infin;*/
	INFINITY("(8)", '\u221E'),
	/**($c) = &#x00A2;*/
	CENTS("($c)", '\u00A2'),
	/**($e) = &euro;*/
	EUROS("($e)", '\u20AC'),
	/**($p) = &pound;*/
	POUND("($p)", '\u00A3'),
	/**(p) = &para;*/
    PARAGRAPH("(p)", '\u00B6'),
	/**&lt;&lt; = &#x00AB;*/
    LEFT_CHEVRONS("<<", '\u00AB'),
	/**&gt;&gt; = &#x00BB;*/
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