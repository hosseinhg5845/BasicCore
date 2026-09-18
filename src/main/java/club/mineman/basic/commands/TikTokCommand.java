package club.mineman.basic.commands;

import club.mineman.basic.Basic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TikTokCommand implements CommandExecutor {
    public TikTokCommand(Basic basic) {}

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("§eTikTok: §dtiktok.com/@minemen.club");
        }
        return true;
    }
}