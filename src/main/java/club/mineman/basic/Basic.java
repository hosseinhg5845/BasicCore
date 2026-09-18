package club.mineman.basic;

import club.mineman.basic.commands.*;
import club.mineman.basic.guis.menus.ChangelogMenu;
import club.mineman.basic.guis.menus.SettingsMenu;
import club.mineman.basic.handlers.ChatFormat;
import club.mineman.basic.handlers.MessageHandler;
import club.mineman.basic.managers.MongoDBManager;
import club.mineman.basic.managers.SettingsManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Basic extends JavaPlugin {
    private MongoDBManager mongoDBManager;
    private SettingsManager settingsManager;
    private MessageHandler messageHandler;

    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        this.mongoDBManager = new MongoDBManager(this);
        if (!this.mongoDBManager.connect()) {
            getLogger().warning("Failed to connect to MongoDB! Using offline mode.");
        }

        this.settingsManager = new SettingsManager(this);
        this.messageHandler = new MessageHandler();

        getServer().getPluginManager().registerEvents((Listener) new ChatFormat(), (Plugin) this);

        ChangelogMenu changelogMenu = new ChangelogMenu((Plugin) this);
        SettingsMenu settingsMenu = new SettingsMenu(this);

        getCommand("msg").setExecutor(new MsgCommand(this.messageHandler, this));
        getCommand("buy").setExecutor(new BuyCommand(this));
        getCommand("store").setExecutor(new BuyCommand(this));
        getCommand("discord").setExecutor(new DiscordCommand(this));
        getCommand("ping").setExecutor(new PingCommand(this));
        getCommand("twitter").setExecutor(new TwitterCommand(this));
        getCommand("website").setExecutor(new WebsiteCommand(this));
        getCommand("youtube").setExecutor(new YouTubeCommand(this));
        getCommand("reply").setExecutor(new ReplyCommand(this.messageHandler));
        getCommand("r").setExecutor(new ReplyCommand(this.messageHandler));
        getCommand("settings").setExecutor(new SettingsCommand(settingsMenu));
        getCommand("preferences").setExecutor(new SettingsCommand(settingsMenu));
        getCommand("changelog").setExecutor(new ChangelogCommand(changelogMenu));
        getCommand("tiktok").setExecutor(new TikTokCommand(this));
        getCommand("join").setExecutor(new JoinCommand(this));

        getLogger().info("Basic has successfully enabled.");
    }

    public void onDisable() {
        if (this.settingsManager != null) {
            this.settingsManager.saveAll();
        }
        if (this.mongoDBManager != null) {
            this.mongoDBManager.disconnect();
        }
    }

    public MongoDBManager getMongoDBManager() {
        return this.mongoDBManager;
    }

    public SettingsManager getSettingsManager() {
        return this.settingsManager;
    }

    public MessageHandler getMessageHandler() {
        return this.messageHandler;
    }
}