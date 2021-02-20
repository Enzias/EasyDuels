package fr.enzias.easyduels.listeners;


import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.arena.Spectate;
import fr.enzias.easyduels.filemanager.files.ArenaFile;
import fr.enzias.easyduels.filemanager.files.SettingsFile;
import fr.enzias.easyduels.managers.LevelManager;
import fr.enzias.easyduels.managers.RequestManager;
import fr.enzias.easyduels.queue.QueueManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectEvent implements Listener {

    private final EasyDuels plugin;
    SettingsFile settings;
    ArenaFile arenaFile;
    Arena arena;
    RequestManager request;
    QueueManager queue;
    Spectate spectate;
    LevelManager levelManager;
    public ConnectEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettingsFile();
        this.arenaFile = plugin.getArenaFile();
        this.arena = plugin.getArena();
        this.request = plugin.getRequest();
        this.queue = plugin.getQueue();
        this.spectate = plugin.getSpectate();
        this.levelManager = plugin.getLevelManager();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(levelManager.getActive())
            levelManager.saveExpDataAndDelete(player);

        if (request.hasRequest(player))
            request.deleteRequest(player);

        if(queue.isInQueue(player)){
            queue.tryGiveBackBoth(player);
            queue.deleteQueue(player);
        }
        if(queue.isAnOpponent(player)){
            if(queue.isInQueue(queue.getOpponentOf(player))) {
                queue.tryGiveBackBoth(queue.getOpponentOf(player));
                queue.deleteQueue(queue.getOpponentOf(player));
            }
        }

        if (!arena.isStatut(ArenaStatuts.IDLE)) {

            if (player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                    && spectate.isSpectating(player))
                spectate.finishSpectating(player);
        }

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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (levelManager.getActive()) {
            levelManager.setExperienceFromStorage(player);
            levelManager.checkPermission(player);
        }
    }

}
