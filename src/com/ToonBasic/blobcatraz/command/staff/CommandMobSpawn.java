package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@ICommand.PlayerOnly
public class CommandMobSpawn extends ICommand {
    public CommandMobSpawn() {
        super("spawnmob", "[mob]", "blobcatraz.staff.spawnmob", "mob");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if (args.length < 1) {
            cs.sendMessage(prefix + "/spawnmob [mobname]");
        } else if (args.length > 1) {
            cs.sendMessage(prefix + "/spawnmob [mobname]");
        } else {
            Player p = (Player) cs;
            World world = p.getWorld();
            Location loc = p.getLocation();
            if (args[0].equalsIgnoreCase("bat")) {
                world.spawnEntity(loc, EntityType.BAT);
            } else if (args[0].equalsIgnoreCase("blaze")) {
                world.spawnEntity(loc, EntityType.BLAZE);
            } else if (args[0].equalsIgnoreCase("cavespider")) {
                world.spawnEntity(loc, EntityType.CAVE_SPIDER);
            } else if (args[0].equalsIgnoreCase("chicken")) {
                world.spawnEntity(loc, EntityType.CHICKEN);
            } else if (args[0].equalsIgnoreCase("cow")) {
                world.spawnEntity(loc, EntityType.COW);
            } else if (args[0].equalsIgnoreCase("creeper")) {
                world.spawnEntity(loc, EntityType.CREEPER);
            } else if (args[0].equalsIgnoreCase("donkey")) {
                world.spawnEntity(loc, EntityType.DONKEY);
            } else if (args[0].equalsIgnoreCase("eguardian")) {
                world.spawnEntity(loc, EntityType.ELDER_GUARDIAN);
            } else if (args[0].equalsIgnoreCase("enderman")) {
                world.spawnEntity(loc, EntityType.ENDERMAN);
            } else if (args[0].equalsIgnoreCase("endermite")) {
                world.spawnEntity(loc, EntityType.ENDERMITE);
            } else if (args[0].equalsIgnoreCase("evoker")) {
                world.spawnEntity(loc, EntityType.EVOKER);
            } else if (args[0].equalsIgnoreCase("ghast")) {
                world.spawnEntity(loc, EntityType.GHAST);
            } else if (args[0].equalsIgnoreCase("guardian")) {
                world.spawnEntity(loc, EntityType.GUARDIAN);
            } else if (args[0].equalsIgnoreCase("horse")) {
                world.spawnEntity(loc, EntityType.HORSE);
            } else if (args[0].equalsIgnoreCase("husk")) {
                world.spawnEntity(loc, EntityType.HUSK);
            } else if (args[0].equalsIgnoreCase("llama")) {
                world.spawnEntity(loc, EntityType.HUSK);
            } else if (args[0].equalsIgnoreCase("magmacube")) {
                world.spawnEntity(loc, EntityType.MAGMA_CUBE);
            } else if (args[0].equalsIgnoreCase("mooshroom")) {
                world.spawnEntity(loc, EntityType.MUSHROOM_COW);
            } else if (args[0].equalsIgnoreCase("mule")) {
                world.spawnEntity(loc, EntityType.MULE);
            } else if (args[0].equalsIgnoreCase("ocelot")) {
                world.spawnEntity(loc, EntityType.OCELOT);
            } else if (args[0].equalsIgnoreCase("pig")) {
                world.spawnEntity(loc, EntityType.COW);
            } else if (args[0].equalsIgnoreCase("polarbear")) {
                world.spawnEntity(loc, EntityType.POLAR_BEAR);
            } else if (args[0].equalsIgnoreCase("rabbit")) {
                world.spawnEntity(loc, EntityType.RABBIT);
            } else if (args[0].equalsIgnoreCase("sheep")) {
                world.spawnEntity(loc, EntityType.SHEEP);
            } else if (args[0].equalsIgnoreCase("shulker")) {
                world.spawnEntity(loc, EntityType.SHULKER);
            } else if (args[0].equalsIgnoreCase("silverfish")) {
                world.spawnEntity(loc, EntityType.SILVERFISH);
            } else if (args[0].equalsIgnoreCase("skeleton")) {
                world.spawnEntity(loc, EntityType.SKELETON);
            } else if (args[0].equalsIgnoreCase("shorse")) {
                world.spawnEntity(loc, EntityType.SKELETON_HORSE);
            } else if (args[0].equalsIgnoreCase("slime")) {
                world.spawnEntity(loc, EntityType.SLIME);
            } else if (args[0].equalsIgnoreCase("spider")) {
                world.spawnEntity(loc, EntityType.SPIDER);
            } else if (args[0].equalsIgnoreCase("squid")) {
                world.spawnEntity(loc, EntityType.SQUID);
            } else if (args[0].equalsIgnoreCase("stray")) {
                world.spawnEntity(loc, EntityType.STRAY);
            } else if (args[0].equalsIgnoreCase("vex")) {
                world.spawnEntity(loc, EntityType.VEX);
            } else if (args[0].equalsIgnoreCase("villager")) {
                world.spawnEntity(loc, EntityType.VILLAGER);
            } else if (args[0].equalsIgnoreCase("vindicator")) {
                world.spawnEntity(loc, EntityType.VINDICATOR);
            } else if (args[0].equalsIgnoreCase("witch")) {
                world.spawnEntity(loc, EntityType.WITCH);
            } else if (args[0].equalsIgnoreCase("wskeleton")) {
                world.spawnEntity(loc, EntityType.WITHER_SKELETON);
            } else if (args[0].equalsIgnoreCase("wolf")) {
                world.spawnEntity(loc, EntityType.WOLF);
            } else if (args[0].equalsIgnoreCase("zombie")) {
                world.spawnEntity(loc, EntityType.ZOMBIE);
            } else if (args[0].equalsIgnoreCase("zhorse")) {
                world.spawnEntity(loc, EntityType.ZOMBIE_HORSE);
            } else if (args[0].equalsIgnoreCase("zpigman")) {
                world.spawnEntity(loc, EntityType.PIG_ZOMBIE);
            } else if (args[0].equalsIgnoreCase("zvillager")) {
                world.spawnEntity(loc, EntityType.ZOMBIE_VILLAGER);
            } else if (args[0].equalsIgnoreCase("wither")) {
                world.spawnEntity(loc, EntityType.WITHER);
            } else if (args[0].equalsIgnoreCase("edragon")) {
                world.spawnEntity(loc, EntityType.WOLF.ENDER_DRAGON);
            }
        }
    }
}
