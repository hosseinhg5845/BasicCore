package club.mineman.basic.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormat implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getDisplayName();
        String message = event.getMessage();
        String formattedMessage = ChatColor.GREEN + playerName + ChatColor.GRAY + ": " + ChatColor.RESET + message;
        event.setFormat(formattedMessage);
    }
}