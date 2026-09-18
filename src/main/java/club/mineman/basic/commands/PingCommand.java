package club.mineman.basic.commands;

import club.mineman.basic.Basic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class PingCommand implements CommandExecutor {
    public PingCommand(Basic basic) {}

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            int ping = getPing(player);
            player.sendMessage("§eYour ping: §d" + ping + " ms");
        }
        return true;
    }

    private int getPing(Player player) {
        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object pingField = entityPlayer.getClass().getField("ping").get(entityPlayer);
            return (int) pingField;
        } catch (Exception e) {
            try {
                Method spigotMethod = player.getClass().getMethod("spigot");
                Object spigot = spigotMethod.invoke(player);
                Method getPingMethod = spigot.getClass().getMethod("getPing");
                return (int) getPingMethod.invoke(spigot);
            } catch (Exception ex) {
                return 0;
            }
        }
    }
}