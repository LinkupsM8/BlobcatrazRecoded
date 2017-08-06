package com.SirBlobman.blobcatraz.log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.SirBlobman.blobcatraz.config.Config;
import com.SirBlobman.blobcatraz.utility.Util;

public class EconomyLog extends Config {
	private static final File FILE = new File(FOLDER, "economy.log");
	
	public static void print(String msg) {
		String s = Util.strip(msg);
		try {
			long time = System.currentTimeMillis();
			Date d = new Date(time);
			String f = "[MM/dd/yyy kk:mm:ss.SSS zzz]";
			TimeZone EST = TimeZone.getTimeZone("EST");
			SimpleDateFormat sdf = new SimpleDateFormat(f);
			sdf.setTimeZone(EST);
			String format = sdf.format(d) + " " + s;
			
			if(!FILE.exists()) {
				FOLDER.mkdirs();
				FILE.createNewFile();
			}
			
			FileWriter fw = new FileWriter(FILE, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(format);
			pw.close();
		} catch(Throwable ex) {
			String error = "Failed to log to economy, logging to CONSOLE instead!";
			Util.print(error);
			Util.print(s);
		}
	}
}