package fr.enzias.easyduels.listeners;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.files.ArenaFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportEvent implements Listener {

    private final EasyDuels plugin;
    ArenaFile arenaFile;
    Arena arena;

    public TeleportEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.arenaFile = plugin.getArenaFile();
        this.arena = plugin.getArena();
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if (arena.isStatut(ArenaStatuts.PLAYING) || arena.isStatut(ArenaStatuts.LOBBY)) {

            Player player = event.getPlayer();

            if (player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                    && arena.getPlayers().contains(player)) {
                event.setCancelled(true);
            }
        }
    }

}
