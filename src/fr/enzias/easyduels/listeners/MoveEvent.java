package fr.enzias.easyduels.listeners;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.SettingsFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    private final EasyDuels plugin;
    SettingsFile settings;
    ArenaFile arenaFile;
    Arena arena;

    public MoveEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettingsFile();
        this.arenaFile = plugin.getArenaFile();
        this.arena = plugin.getArena();

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMoveWhileLobby(PlayerMoveEvent event) {
        if (settings.getPreventMove() && arena.isStatut(ArenaStatuts.LOBBY)) {

            Player player = event.getPlayer();

            if (player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                    && arena.getPlayers().contains(player)) {
                event.setCancelled(true);
            }
        }
    }

}
