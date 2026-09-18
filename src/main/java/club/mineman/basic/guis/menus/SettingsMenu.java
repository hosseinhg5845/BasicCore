package club.mineman.basic.guis.menus;

import club.mineman.basic.Basic;
import club.mineman.basic.managers.SettingsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class SettingsMenu implements Listener {
    private final Basic plugin;

    public SettingsMenu(Basic plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void openSettingsGUI(Player player) {
        Inventory gui = this.plugin.getServer().createInventory((InventoryHolder) player, 27, "§e§lSettings");
        SettingsManager.PlayerSettings settings = this.plugin.getSettingsManager().getSettings(player);

        ItemStack blackGlassPane = createGlassPane();
        int[] glassSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
        for (int slot : glassSlots) {
            gui.setItem(slot, blackGlassPane);
        }

        gui.setItem(10, createButton("§e§lGlobal Chat",
                getToggleLore(settings.isGlobalChatEnabled()), Material.PAPER));
        gui.setItem(11, createButton("§e§lPrivate Messages",
                getToggleLore(settings.isPrivateMessagesEnabled()), Material.BOOK_AND_QUILL));
        gui.setItem(12, createButton("§e§lMessage Sounds",
                getToggleLore(settings.isMessageSoundsEnabled()), Material.EGG));
        gui.setItem(13, createButton("§e§lProfanity Filter",
                getToggleLore(settings.isProfanityFilterEnabled()), Material.ROTTEN_FLESH));

        player.openInventory(gui);
    }

    private ItemStack createGlassPane() {
        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName("§r");
        pane.setItemMeta(meta);
        return pane;
    }

    private ItemStack createButton(String name, List<String> lore, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private List<String> getToggleLore(boolean enabled) {
        String current = enabled ? "┃" : "❙";
        String newSymbol = enabled ? "❙" : "┃";
        return Arrays.asList(
                "§7Click to toggle.",
                " ",
                "§e§l" + current + " " + (enabled ? "§7Yes" : "§aYes"),
                "§e§l" + newSymbol + " " + (enabled ? "§aNo" : "§7No"),
                " ",
                "§eClick to change!"
        );
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("§e§lSettings") || event.getAction() != InventoryAction.PICKUP_ALL) {
            return;
        }

        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;

        Player player = (Player) event.getWhoClicked();
        SettingsManager.PlayerSettings settings = this.plugin.getSettingsManager().getSettings(player);
        Material type = clicked.getType();

        switch (type) {
            case PAPER:
                settings.setGlobalChatEnabled(!settings.isGlobalChatEnabled());
                break;
            case BOOK_AND_QUILL:
                settings.setPrivateMessagesEnabled(!settings.isPrivateMessagesEnabled());
                break;
            case EGG:
                settings.setMessageSoundsEnabled(!settings.isMessageSoundsEnabled());
                break;
            case ROTTEN_FLESH:
                settings.setProfanityFilterEnabled(!settings.isProfanityFilterEnabled());
                break;
            default:
                return;
        }

        this.plugin.getSettingsManager().saveSettings(player);
        openSettingsGUI(player);
    }
}