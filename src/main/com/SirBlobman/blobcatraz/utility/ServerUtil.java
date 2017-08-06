package com.SirBlobman.blobcatraz.utility;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import net.minecraft.server.v1_12_R1.MinecraftServer;

@SuppressWarnings("deprecation")
public class ServerUtil extends Util {
	private static MinecraftServer MCS = MinecraftServer.getServer();
	private static final long MEGABYTES = 1048576L;
	
	public static double latestTPS() {
		double[] dd = MCS.recentTps;
		double d = dd[0];
		return d;
	}
	
	public static double cpuUsage() {
		OperatingSystemMXBean osmxb = ManagementFactory.getOperatingSystemMXBean();
		com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) osmxb;
		double d = os.getProcessCpuLoad();
		double c = (d * 100.0D);
		return c;
	}
	
	public static long freeRAM() {
		Runtime r = Runtime.getRuntime();
		long f = r.freeMemory();
		long m = (f / MEGABYTES);
		return m;
	}
	
	public static long totalRAM() {
		Runtime r = Runtime.getRuntime();
		long t = r.totalMemory();
		long m = (t / MEGABYTES);
		return m;
	}
	
	public static long usedRAM() {
		long t = totalRAM();
		long f = freeRAM();
		long u = (t - f);
		return u;
	}
}