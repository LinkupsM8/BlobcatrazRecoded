package com.ToonBasic.blobcatraz.config;

import java.util.Comparator;

public class WarpComparator implements Comparator<Warp> {
	@Override
	public int compare(Warp w1, Warp w2) {
		String name1 = w1.name();
		String name2 = w2.name();
		return name1.compareToIgnoreCase(name2);
	}
}