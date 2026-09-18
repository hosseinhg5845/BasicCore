package club.mineman.basic.commands;

import club.mineman.basic.guis.menus.ChangelogMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangelogCommand implements CommandExecutor {
    private final ChangelogMenu changelogMenu;

    public ChangelogCommand(ChangelogMenu changelogMenu) {
        this.changelogMenu = changelogMenu;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        this.changelogMenu.openChangelogMenu(player);
        return true;
    }
}