package com.ToonBasic.blobcatraz.utility;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import net.minecraft.server.v1_11_R1.MinecraftServer;

@SuppressWarnings("deprecation")
public class ServerUtil extends Util {
	private static MinecraftServer MC_SERVER = MinecraftServer.getServer();

	public static double latestTicksPerSecond() {
		double[] recent = MC_SERVER.recentTps;
		double latest = recent[0];
		return latest;
	}
	
	public static double processorUsage() {
		OperatingSystemMXBean OS = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		double usage = OS.getProcessCpuLoad() * 100.00D;
		return usage;
	}
	
	public static long memoryFree() {
		Runtime r = Runtime.getRuntime();
		long free = r.freeMemory() / 1048576L;
		return free;
	}
	
	public static long memoryTotal() {
		Runtime r = Runtime.getRuntime();
		long total = r.totalMemory() / 1048576L;
		return total;
	}
	
	public static double memoryUsage() {
		long total = memoryTotal();
		long free = memoryFree();
		double usage = total - free;
		return usage;
	}
}