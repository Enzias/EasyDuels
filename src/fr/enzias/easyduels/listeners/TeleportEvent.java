package fr.enzias.easyduels.listeners;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.arena.Spectate;
import fr.enzias.easyduels.filemanager.files.ArenaFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportEvent implements Listener {

    private final EasyDuels plugin;
    ArenaFile arenaFile;
    Arena arena;
    Spectate spectate;

    public TeleportEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.arenaFile = plugin.getArenaFile();
        this.arena = plugin.getArena();
        this.spectate = plugin.getSpectate();
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){

        if (!arena.isStatut(ArenaStatuts.IDLE)) {

            Player player = event.getPlayer();

            if (player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                    && spectate.isSpectating(player))
                spectate.finishSpectating(player);
        }

        if (arena.isStatut(ArenaStatuts.PLAYING) || arena.isStatut(ArenaStatuts.LOBBY))
            if(event.getCause() != PlayerTeleportEvent.TeleportCause.PLUGIN) {

                Player player = event.getPlayer();

                if (player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                        && arena.getPlayers().contains(player)) {
                    event.setCancelled(true);
                }
            }
    }

}
