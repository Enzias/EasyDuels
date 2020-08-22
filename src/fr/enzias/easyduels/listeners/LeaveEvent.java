package fr.enzias.easyduels.listeners;


import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.managers.RequestManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    private final EasyDuels plugin;
    SettingsFile settings;
    ArenaFile arenaFile;
    Arena arena;
    RequestManager request;
    public LeaveEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.settings = new SettingsFile(plugin);
        this.arenaFile = plugin.getArenaFile();
        this.arena = plugin.getArena();
        this.request = plugin.getRequest();

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (request.hasRequest(player)) //change request to name
            request.deleteRequest(player);
        if (arena.isStatut(ArenaStatuts.LOBBY) || arena.isStatut(ArenaStatuts.PLAYING)) {
            if (player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                    && arena.getPlayers().contains(player)) {


                if (player.getUniqueId().equals(arena.getFirstPlayerUUID()))
                    arena.setWinner(arena.getSecondPlayer());
                else
                    arena.setWinner(arena.getFirstPlayer());
                arena.teleportToLastLocation(player);
                arena.setStatut(ArenaStatuts.RELOADING);
            }
        } else if(arena.isStatut(ArenaStatuts.RELOADING)) {
            if (player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                    && arena.getPlayers().contains(player)) {
                arena.teleportToLastLocation(player);
            }
        }
    }

}
