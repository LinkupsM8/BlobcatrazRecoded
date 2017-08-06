package com.SirBlobman.itemutil;

import com.SirBlobman.blobcatraz.command.CommandFramework;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.itemutil.command.*;

import java.io.File;

public class Core extends com.SirBlobman.blobcatraz.Core {
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
	
	@Override
	public void onDisable() {}
	
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