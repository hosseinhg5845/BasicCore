package club.mineman.basic.commands;

import club.mineman.basic.handlers.MessageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {
    private final MessageHandler messageHandler;

    public ReplyCommand(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length < 1) {
                player.sendMessage("§eYou're currently messaging §d" + ((this.messageHandler.getLastMessageSender(player) != null) ? this.messageHandler.getLastMessageSender(player).getName() : "no one") + "§e.");
                return true;
            }

            String message = String.join(" ", (CharSequence[]) args);
            Player lastMessageSender = this.messageHandler.getLastMessageSender(player);
            ConsoleCommandSender lastConsoleMessageSender = this.messageHandler.getLastConsoleMessageSender(player);

            if (lastMessageSender == null && lastConsoleMessageSender == null) {
                player.sendMessage("§cYou have no one to reply to.");
                return true;
            }

            if (lastMessageSender != null && lastMessageSender.isOnline()) {
                this.messageHandler.sendMessage(player, lastMessageSender, message);
            } else {
                player.sendMessage("§cYou have no one to reply to.");
            }

            return true;
        }

        sender.sendMessage("§cYou cannot reply to messages in console.");
        return true;
    }
}