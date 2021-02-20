package fr.enzias.easyduels.tasks;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.arena.Spectate;
import fr.enzias.easyduels.filemanager.files.SettingsFile;
import fr.enzias.easyduels.managers.CountdownManager;
import fr.enzias.easyduels.queue.QueueManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerManager extends BukkitRunnable {

    private final EasyDuels plugin;
    Arena arena;
    SettingsFile settings;
    QueueManager queue;
    CountdownManager countdown;
    Spectate spectate;
    int lobbyTime;
    int fightTime;
    int reloadTime;
    public TimerManager(EasyDuels plugin) {
        this.plugin = plugin;
        this.arena = plugin.getArena();
        this.settings = plugin.getSettingsFile();
        this.queue = plugin.getQueue();
        this.countdown = new CountdownManager(plugin);
        this.lobbyTime = arena.getLobbyTime();
        this.fightTime = arena.getPlayingTime();
        this.reloadTime = arena.getReloadingTime();
        this.spectate = plugin.getSpectate();
    }

    @Override
    public void run() {


        if(arena.isStatut(ArenaStatuts.LOBBY)) {

            countdown.sendLobbyTimer(lobbyTime);

            if (lobbyTime <= 0) {
                arena.setStatut(ArenaStatuts.PLAYING);
            }
            lobbyTime--;
        }
        if(arena.isStatut(ArenaStatuts.PLAYING)){

            countdown.sendFightTime(fightTime);

            if(fightTime <= 0){
                arena.setStatut(ArenaStatuts.RELOADING);
            }

            fightTime--;

        }
        if(arena.isStatut(ArenaStatuts.RELOADING)){

            countdown.sendEndTime(reloadTime);

            if(reloadTime <= 0) {

                if(settings.getSyncTimer()){
                    if(arena.getFirstPlayer().isOnline()
                            && arena.getPlayers().contains(arena.getFirstPlayer())) {
                        arena.teleportToLastLocation(arena.getFirstPlayer());
                    }
                    if(arena.getSecondPlayer().isOnline()
                            && arena.getPlayers().contains(arena.getFirstPlayer())) {
                        arena.teleportToLastLocation(arena.getSecondPlayer());
                    }
                }
                else {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        if(arena.getFirstPlayer().isOnline()
                                && arena.getPlayers().contains(arena.getFirstPlayer())) {
                            arena.teleportToLastLocation(arena.getFirstPlayer());
                        }
                        if(arena.getSecondPlayer().isOnline()
                                && arena.getPlayers().contains(arena.getFirstPlayer())) {
                            arena.teleportToLastLocation(arena.getSecondPlayer());
                        }
                    });
                }

                spectate.endMatch();
                arena.resetArena();
                arena.setStatut(ArenaStatuts.IDLE);
                if(settings.getQueue())
                    queue.checkQueue();
                this.cancel();

            }

            reloadTime--;

        }

    }
}
