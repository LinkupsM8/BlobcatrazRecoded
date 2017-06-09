package com.ToonBasic.blobcatraz.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.utility.Util;

public class EconomyLog {
	private static final File FOLDER = Core.folder;
	private static final File FILE = new File(FOLDER, "economy.log");
	
	public static void print(String msg) {
		msg = Util.strip(msg);
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("[MM/dd/yyyy kk:mm:ss.SSS zzz]");
		TimeZone EST = TimeZone.getTimeZone("EST");
		sdf.setTimeZone(EST);
		String format = sdf.format(date) + " " + msg;
		
		try {
			if(!FILE.exists()) {
				FOLDER.mkdirs();
				FILE.createNewFile();
			}
			
			FileWriter fw = new FileWriter(FILE, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(format);
			pw.close();
		} catch (Exception ex) {
			String error = "Failed to log to economy, logging to CONSOLE insted";
			Util.print(error);
			Util.print(msg);
		}
	}
}