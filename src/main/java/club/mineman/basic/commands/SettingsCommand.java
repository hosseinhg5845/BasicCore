package club.mineman.basic.commands;

import club.mineman.basic.guis.menus.SettingsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {
    private final SettingsMenu settingsMenu;

    public SettingsCommand(SettingsMenu settingsMenu) {
        this.settingsMenu = settingsMenu;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        this.settingsMenu.openSettingsGUI(player);
        return true;
    }
}