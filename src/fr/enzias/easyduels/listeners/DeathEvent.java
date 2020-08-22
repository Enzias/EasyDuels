package fr.enzias.easyduels.listeners;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.SettingsFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    private final EasyDuels plugin;
    private ArenaFile arenaFile;
    private SettingsFile settings;
    private Arena arena;

    public DeathEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.arenaFile = plugin.getArenaFile();
        this.settings = plugin.getSettingsFile();
        this.arena = plugin.getArena();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        if (arena.isStatut(ArenaStatuts.PLAYING)) {
            Player player = event.getEntity();
            if (player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                    && arena.getPlayers().contains(player)) {

                if (player.getUniqueId().equals(arena.getFirstPlayerUUID())) {
                    arena.setWinner(arena.getSecondPlayer());
                }
                else {
                    arena.setWinner(arena.getFirstPlayer());
                }
                arena.setStatut(ArenaStatuts.RELOADING);

            }

        }

    }
}
