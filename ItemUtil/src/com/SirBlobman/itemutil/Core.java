package com.SirBlobman.itemutil;

import java.io.File;

import com.SirBlobman.itemutil.command.CommandAddLore;
import com.SirBlobman.itemutil.command.CommandChangeType;
import com.SirBlobman.itemutil.command.CommandHideAll;
import com.SirBlobman.itemutil.command.CommandItemDB;
import com.SirBlobman.itemutil.command.CommandRename;
import com.SirBlobman.itemutil.command.CommandSetLore;
import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.utility.Util;

public class Core extends com.ToonBasic.blobcatraz.Core {
	public static Core instance;
	public static File folder;
	private static CommandFramework framework;
	
	@Override
	public void onEnable() {
		instance = this;
		folder = super.getDataFolder();
		framework = new CommandFramework(this);
		icommands();
		Util.print("&2ItemUtil addon is now enabled!");
	}
	
	private void icommands() {
		framework.registerCommands(
			new CommandAddLore(),
			new CommandChangeType(),
			new CommandHideAll(),
			new CommandItemDB(),
			new CommandRename(),
			new CommandSetLore(),
			new CommandUnbreakable()
		);
		framework.registerCommands();
	}
}