package club.mineman.basic.managers;

import club.mineman.basic.Basic;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SettingsManager {
    private final Basic plugin;
    private final Map<UUID, PlayerSettings> settingsCache;

    public SettingsManager(Basic plugin) {
        this.plugin = plugin;
        this.settingsCache = new HashMap<>();
    }

    public PlayerSettings getSettings(Player player) {
        UUID uuid = player.getUniqueId();
        if (this.settingsCache.containsKey(uuid)) {
            return this.settingsCache.get(uuid);
        }

        PlayerSettings settings = loadSettings(player);
        this.settingsCache.put(uuid, settings);
        return settings;
    }

    public void saveSettings(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerSettings settings = this.settingsCache.get(uuid);
        if (settings == null) {
            return;
        }

        if (this.plugin.getMongoDBManager().isConnected()) {
            Document doc = new Document("_id", uuid.toString())
                    .append("global-chat", settings.isGlobalChatEnabled())
                    .append("private-messages", settings.isPrivateMessagesEnabled())
                    .append("message-sounds", settings.isMessageSoundsEnabled())
                    .append("profanity-filter", settings.isProfanityFilterEnabled());
            this.plugin.getMongoDBManager().saveDocument("settings", doc);
        }
    }

    public void saveAll() {
        for (UUID uuid : this.settingsCache.keySet()) {
            Player player = this.plugin.getServer().getPlayer(uuid);
            if (player != null) {
                saveSettings(player);
            }
        }
    }

    private PlayerSettings loadSettings(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerSettings settings = new PlayerSettings();

        if (this.plugin.getMongoDBManager().isConnected()) {
            Document doc = this.plugin.getMongoDBManager().loadDocument("settings", uuid.toString());
            if (doc != null) {
                settings.setGlobalChatEnabled(doc.getBoolean("global-chat", true));
                settings.setPrivateMessagesEnabled(doc.getBoolean("private-messages", true));
                settings.setMessageSoundsEnabled(doc.getBoolean("message-sounds", true));
                settings.setProfanityFilterEnabled(doc.getBoolean("profanity-filter", true));
            }
        }

        return settings;
    }

    public static class PlayerSettings {
        private boolean globalChatEnabled = true;
        private boolean privateMessagesEnabled = true;
        private boolean messageSoundsEnabled = true;
        private boolean profanityFilterEnabled = true;

        public boolean isGlobalChatEnabled() {
            return globalChatEnabled;
        }

        public void setGlobalChatEnabled(boolean globalChatEnabled) {
            this.globalChatEnabled = globalChatEnabled;
        }

        public boolean isPrivateMessagesEnabled() {
            return privateMessagesEnabled;
        }

        public void setPrivateMessagesEnabled(boolean privateMessagesEnabled) {
            this.privateMessagesEnabled = privateMessagesEnabled;
        }

        public boolean isMessageSoundsEnabled() {
            return messageSoundsEnabled;
        }

        public void setMessageSoundsEnabled(boolean messageSoundsEnabled) {
            this.messageSoundsEnabled = messageSoundsEnabled;
        }

        public boolean isProfanityFilterEnabled() {
            return profanityFilterEnabled;
        }

        public void setProfanityFilterEnabled(boolean profanityFilterEnabled) {
            this.profanityFilterEnabled = profanityFilterEnabled;
        }
    }
}