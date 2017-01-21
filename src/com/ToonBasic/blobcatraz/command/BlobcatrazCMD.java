package com.ToonBasic.blobcatraz.command;

import org.bukkit.command.CommandSender;

public abstract class BlobcatrazCMD extends Manager
{
	public BlobcatrazCMD() {super("blobcatraz", "blobcatraz.blobcatraz", true);}

	@Override
    public void execute(CommandSender cs, String[] args)
    {
        super.execute(cs, args);
    }
}