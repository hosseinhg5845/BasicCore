package club.mineman.basic.commands;

import club.mineman.basic.Basic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JoinCommand implements CommandExecutor {
    public JoinCommand(Basic basic) {}

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§eUsage: §d/join §f<server>");
            return true;
        }

        String serverName = args[0];
        sender.sendMessage("§cError: There is no online server named §e" + serverName + "§c.");
        return true;
    }
}