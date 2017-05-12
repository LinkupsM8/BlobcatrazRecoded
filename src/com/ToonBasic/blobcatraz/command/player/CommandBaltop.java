package com.ToonBasic.blobcatraz.command.player;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandBaltop extends ICommand {
	public CommandBaltop() {super("balancetop", "", "blobcatraz.player.balancetop", "baltop", "topten");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
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
		
		List<Entry<String, Double>> t;
		boolean b = (entryList.size() > 10);
		t = b ? entryList.subList(0, 9) : entryList;
		
		List<String> list = Util.newList();
		int i = 1;
		for(Entry<String, Double> e : t) {
			String name = e.getKey();
			if(name != null) {
				double bal = e.getValue();
				if(bal > 0.0D) {
					String add = Util.color(i + ". &6" + name + ": &e" + Util.money(bal));
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