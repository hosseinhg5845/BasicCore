package club.mineman.basic.guis.menus;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ChangelogMenu implements CommandExecutor, Listener {
    private final Plugin plugin;

    public ChangelogMenu(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cConsole cannot open menus.");
            return true;
        }

        Player player = (Player) sender;
        openChangelogMenu(player);
        return true;
    }

    public void openChangelogMenu(Player player) {
        Inventory gui = this.plugin.getServer().createInventory((InventoryHolder) player, 9, "§e§lLatest News");

        ItemStack blackGlassPane = createBlackGlassPane();
        int[] glassPaneSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        for (int slot : glassPaneSlots) {
            gui.setItem(slot, blackGlassPane);
        }

        player.openInventory(gui);
    }

    private ItemStack createBlackGlassPane() {
        ItemStack blackGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta glassMeta = blackGlassPane.getItemMeta();
        glassMeta.setDisplayName("§r");
        blackGlassPane.setItemMeta(glassMeta);
        return blackGlassPane;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§e§lLatest News")) {
            event.setCancelled(true);
        }
    }
}