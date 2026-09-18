package club.mineman.basic.commands;

import club.mineman.basic.Basic;
import club.mineman.basic.handlers.MessageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MsgCommand implements CommandExecutor {
    private final MessageHandler messageHandler;

    public MsgCommand(MessageHandler messageHandler, Basic basic) {
        this.messageHandler = messageHandler;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§eUsage: §d/msg §f<player> <message>");
            return true;
        }

        Player senderPlayer = null;
        if (sender instanceof Player) {
            senderPlayer = (Player) sender;
        }

        Player recipient = sender.getServer().getPlayer(args[0]);
        if (recipient == null || !recipient.isOnline()) {
            sender.sendMessage("§cPlayer not found or not online.");
            return true;
        }

        if (senderPlayer != null && recipient.equals(senderPlayer)) {
            sender.sendMessage("§cYou cannot message yourself.");
            return true;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            messageBuilder.append(args[i]).append(" ");
        }
        String message = messageBuilder.toString().trim();

        if (senderPlayer != null) {
            this.messageHandler.sendMessage(senderPlayer, recipient, message);
        } else {
            this.messageHandler.sendConsoleMessage(recipient, message);
        }

        return true;
    }
}