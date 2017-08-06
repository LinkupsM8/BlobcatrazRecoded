package com.SirBlobman.blobcatraz.economy.command.player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CommandBaltop extends ICommand {
	public CommandBaltop() {super("balancetop", "", "blobcatraz.player.balancetop", "baltop", "topten");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		List<String> list = baltop();
		String[] msg = list.toArray(new String[0]);
		cs.sendMessage("Top Ten Players:");
		cs.sendMessage(msg);
	}
	
	private List<String> baltop() {
		Map<String, Double> map = ConfigDatabase.balances();
		List<Entry<String, Double>> entryList = Util.newList(map.entrySet());
		Comparator<Entry<String, Double>> compare = new BalanceComparator();
		Comparator<Entry<String, Double>> reverse = Collections.reverseOrder(compare);
		Collections.sort(entryList, reverse);
		
		List<Entry<String, Double>> t = Util.cropList(entryList, 0, 9);
		List<String> list = Util.newList();
		int i = 1;
		for(Entry<String, Double> e : t) {
			String name = e.getKey();
			if(name != null) {
				double bal = e.getValue();
				if(bal != Double.POSITIVE_INFINITY && bal > 0.0D) {
					String add = Util.color(i + ". &6" + name + ": &e" + NumberUtil.money(bal));
					list.add(add);
					i++;
				}
			}
		}
		return list;	
	}
	
	private class BalanceComparator implements Comparator<Entry<String, Double>> {
		@Override
		public int compare(Entry<String, Double> e1, Entry<String, Double> e2) {
			Double d1 = e1.getValue();
			Double d2 = e2.getValue();
			return d1.compareTo(d2);
		}
	}
}