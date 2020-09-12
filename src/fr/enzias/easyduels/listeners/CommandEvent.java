package fr.enzias.easyduels.listeners;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandEvent implements Listener {

    private final EasyDuels plugin;
    private SettingsFile settings;
    private MessageFile message;
    private ArenaFile arenaFile;
    private SenderManager sender;
    private Arena arena;

    public CommandEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettingsFile();
        this.message = plugin.getMessageFile();
        this.arenaFile = plugin.getArenaFile();
        this.sender = plugin.getSender();
        this.arena = plugin.getArena();
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {

        if (!arena.isStatut(ArenaStatuts.IDLE)) {

            Player player = event.getPlayer();

            if (player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                    && arena.getPlayers().contains(player)) {
                if (settings.getAllCommands()) {
                    for (String command : settings.getWhitelistedCommands()) {
                        if (event.getMessage().toLowerCase().startsWith("/" + command))
                            return;
                    }
                    event.setCancelled(true);
                    sender.sendMessage(message.getNoCommand(), player);

                } else
                    for (String command : settings.getBlacklistedCommands())
                        if (event.getMessage().toLowerCase().startsWith("/" + command)) {
                            event.setCancelled(true);
                            sender.sendMessage(message.getNoCommand(), player);
                        }
            }
        }

        Player player = event.getPlayer();
        String strippedCommand = event.getMessage().replace("/", "");
        String[] arguments = strippedCommand.split(" ");

        for(String alias : settings.getAliases()){
            if (arguments[0].equalsIgnoreCase(alias)) {
                event.setCancelled(true);
                String preparedArguments = arguments.length > 1 ? Arrays
                        .stream(arguments, 1, arguments.length)
                        .collect(Collectors.joining(" ")) : "";
                player.performCommand("easyduels ".concat(preparedArguments));
                break;
            }
        }

    }

    @EventHandler
    public void onInvOpen(InventoryOpenEvent event) {

        if (!arena.isStatut(ArenaStatuts.IDLE) && settings.getInventory()) {

            Player player = (Player) event.getPlayer();

            if (player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                    && arena.getPlayers().contains(player)) {

                if (!(event.getInventory() instanceof PlayerInventory)) {

                    event.getPlayer().closeInventory();
                    event.setCancelled(true);

                }
            }
        }
    }
}
