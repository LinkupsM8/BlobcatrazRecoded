package com.SirBlobman.blobcatraz.command.special;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.ContainerAnvil;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_12_R1.PlayerConnection;

public class CommandAnvil extends PlayerCommand {
	public CommandAnvil() {super("anvil", "", "blobcatraz.special.anvil");}
	
	@Override
	public void run(Player p, String[] args) {
		if(p instanceof CraftPlayer) {
			CraftPlayer cp = (CraftPlayer) p;
			EntityPlayer ep = cp.getHandle();
			PlayerConnection pc = ep.playerConnection;
			
			FakeAnvil fa = new FakeAnvil(ep);
			int id = ep.nextContainerCounter();
			
			ChatMessage cm = new ChatMessage("Repairing");
			PacketPlayOutOpenWindow pow = new PacketPlayOutOpenWindow(id, "minecraft:anvil", cm, 0);
			pc.sendPacket(pow);
			
			ep.activeContainer = fa;
			ep.activeContainer.windowId = id;
			ep.activeContainer.addSlotListener(ep);
			ep.activeContainer = fa;
			ep.activeContainer.windowId = id;
		}
	}
	
	public final class FakeAnvil extends ContainerAnvil {
		public FakeAnvil(EntityPlayer ep) {
			super(
				ep.inventory,
				ep.world,
				new BlockPosition(0, 0, 0),
				ep
			);
		}
		
		@Override
		public boolean a(EntityHuman eh) {return true;}
	}
}