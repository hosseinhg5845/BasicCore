package club.mineman.basic.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler {
    private final Map<Player, Player> lastMessageSender = new HashMap<>();
    private final Map<Player, ConsoleCommandSender> lastConsoleMessageSender = new HashMap<>();
    private final Server server;

    public MessageHandler() {
        this.server = Bukkit.getServer();
    }

    public void setLastMessageSender(Player recipient, Player sender) {
        this.lastMessageSender.put(recipient, sender);
    }

    public Player getLastMessageSender(Player recipient) {
        return this.lastMessageSender.get(recipient);
    }

    public void setLastConsoleMessageSender(Player recipient, ConsoleCommandSender sender) {
        this.lastConsoleMessageSender.put(recipient, sender);
    }

    public ConsoleCommandSender getLastConsoleMessageSender(Player recipient) {
        return this.lastConsoleMessageSender.get(recipient);
    }

    public void sendMessage(Player sender, Player recipient, String message) {
        recipient.sendMessage("§e(From §a" + sender.getName() + "§e) " + message);
        sender.sendMessage("§e(To §a" + recipient.getName() + "§e) " + message);
        setLastMessageSender(recipient, sender);
        setLastConsoleMessageSender(recipient, null);
    }

    public void sendConsoleMessage(Player recipient, String message) {
        recipient.sendMessage("§e(From §cCONSOLE§e) " + message);
        this.server.getConsoleSender().sendMessage("§e(To §a" + recipient.getName() + "§e) " + message);
        setLastConsoleMessageSender(recipient, this.server.getConsoleSender());
        setLastMessageSender(recipient, null);
    }
}