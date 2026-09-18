package club.mineman.basic.commands;

import club.mineman.basic.Basic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuyCommand implements CommandExecutor {
    private final Basic basic;

    public BuyCommand(Basic basic) {
        this.basic = basic;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be executed by a player.");
            return true;
        }

        sender.sendMessage("§eStore: §dstore.mineman.club");
        return true;
    }
}