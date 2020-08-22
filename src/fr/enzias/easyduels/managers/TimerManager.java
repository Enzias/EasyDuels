package fr.enzias.easyduels.managers;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.utils.Count;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerManager extends BukkitRunnable {

    private final EasyDuels plugin;
    Arena arena;
    MessageFile message;
    SettingsFile settings;
    SenderManager sender;
    Count count = new Count();
    int lobbyTime;
    int fightTime;
    int reloadTime;
    public TimerManager(EasyDuels plugin) {
        this.plugin = plugin;
        this.arena = plugin.getArena();
        this.message = plugin.getMessageFile();
        this.settings = plugin.getSettingsFile();
        this.lobbyTime = arena.getLobbyTime();
        this.fightTime = arena.getPlayingTime();
        this.reloadTime = arena.getReloadingTime();
        this.sender = plugin.getSender();
    }

    @Override
    public void run() {


        if(arena.isStatut(ArenaStatuts.LOBBY)) {

            if (settings.checkLobbyTime(lobbyTime)) {

                if (settings.getLobbyMessageToPlayers(lobbyTime) != null)
                    sender.sendMessage(settings.getLobbyMessageToPlayers(lobbyTime)
                                    .replaceAll("%player1%", arena.getFirstPlayer().getName()).replaceAll("%player2%", arena.getSecondPlayer().getName()),
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getLobbyTitleToPlayers(lobbyTime) != null)
                    sender.sendTitle(settings.getLobbyTitleToPlayers(lobbyTime), 1, 18, 1,
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getLobbySoundToPlayers(lobbyTime) != null)
                    sender.sendSound(settings.getLobbySoundToPlayers(lobbyTime), settings.getLobbyVolumeToPlayers(lobbyTime), settings.getLobbyPitchToPlayers(lobbyTime),
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getLobbyActionbarToPlayers(lobbyTime) != null)
                    sender.sendActionbar(settings.getLobbyActionbarToPlayers(lobbyTime)
                                    .replaceAll("%player1%", arena.getFirstPlayer().getName()).replaceAll("%player2%", arena.getSecondPlayer().getName()),
                            1, 18, 1, arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getLobbyPlayersCommand(lobbyTime) != null) {
                    sender.sendPlayerCommand(settings.getLobbyPlayersCommand(lobbyTime)
                                    .replaceAll("%player%", arena.getFirstPlayer().getName()).replaceAll("%opponent%", arena.getSecondPlayer().getName()),
                            arena.getFirstPlayer());
                    sender.sendPlayerCommand(settings.getLobbyPlayersCommand(lobbyTime)
                                    .replaceAll("%player%", arena.getSecondPlayer().getName()).replaceAll("%opponent%", arena.getFirstPlayer().getName()),
                            arena.getSecondPlayer());
                }
                if (settings.getLobbyConsoleCommand(lobbyTime) != null)
                    sender.sendConsoleCommand(settings.getLobbyConsoleCommand(lobbyTime)
                            .replaceAll("%player1%", arena.getFirstPlayer().getName()).replaceAll("%player2%", arena.getSecondPlayer().getName()), settings.getSyncTimer());
                if (settings.getLobbyBroadcast(lobbyTime) != null)
                    sender.sendConsoleCommand(settings.getLobbyBroadcast(lobbyTime)
                            .replaceAll("%player1%", arena.getFirstPlayer().getName()).replaceAll("%player2%", arena.getSecondPlayer().getName()), settings.getSyncTimer());

            }

            if (lobbyTime <= 0) {
                arena.setStatut(ArenaStatuts.PLAYING);
            }
            lobbyTime--;
        }
        if(arena.isStatut(ArenaStatuts.PLAYING)){

            sender.sendGameMode(settings.getFightGameMode(), settings.getSyncTimer(), arena.getFirstPlayer(), arena.getSecondPlayer());

            if (settings.checkFightTime(fightTime)) {

                if (settings.getFightMessageToPlayers(fightTime) != null)
                    sender.sendMessage(settings.getFightMessageToPlayers(fightTime)
                                    .replaceAll("%player1%", arena.getFirstPlayer().getName()).replaceAll("%player2%", arena.getSecondPlayer().getName()),
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getFightTitleToPlayers(fightTime) != null)
                    sender.sendTitle(settings.getFightTitleToPlayers(fightTime), 1, 18, 1,
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getFightSoundToPlayers(fightTime) != null)
                    sender.sendSound(settings.getFightSoundToPlayers(fightTime), settings.getFightVolumeToPlayers(fightTime), settings.getFightPitchToPlayers(fightTime),
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getFightPlayersCommand(fightTime) != null){
                    sender.sendPlayerCommand(settings.getFightPlayersCommand(lobbyTime)
                                    .replaceAll("%player%", arena.getFirstPlayer().getName()).replaceAll("%opponent%", arena.getSecondPlayer().getName()),
                            arena.getFirstPlayer());
                    sender.sendPlayerCommand(settings.getFightPlayersCommand(lobbyTime)
                                    .replaceAll("%player%", arena.getSecondPlayer().getName()).replaceAll("%opponent%", arena.getFirstPlayer().getName()),
                            arena.getSecondPlayer());
                }
                if (settings.getFightConsoleCommand(fightTime) != null)
                    sender.sendConsoleCommand(settings.getLobbyConsoleCommand(fightTime)
                            .replaceAll("%player1%", arena.getFirstPlayer().getName()).replaceAll("%player2%", arena.getSecondPlayer().getName()),
                            settings.getSyncTimer());
                if (settings.getFightBroadcast(fightTime) != null)
                    sender.sendConsoleCommand(settings.getLobbyBroadcast(fightTime)
                            .replaceAll("%player1%", arena.getFirstPlayer().getName()).replaceAll("%player2%", arena.getSecondPlayer().getName()),
                            settings.getSyncTimer());

            }
            if (settings.checkFightAll())
                if(settings.getFightAllActionbarToPlayers() != null)
                    sender.sendActionbar(settings.getFightAllActionbarToPlayers()
                                    .replaceAll("%timer%", count.secondToString(fightTime))
                                    .replaceAll("%player1%", arena.getFirstPlayer().getName()).replaceAll("%player2%", arena.getSecondPlayer().getName()),
                            1, 18, 1, arena.getFirstPlayer(), arena.getSecondPlayer());

            if(fightTime <= 0){
                arena.setStatut(ArenaStatuts.RELOADING);
            }

            fightTime--;

        }
        if(arena.isStatut(ArenaStatuts.RELOADING)){

            if(arena.getFirstPlayer().isOnline())
                sender.sendGameMode(settings.getEndGameMode()
                                .replaceAll("%player1%", arena.getFirstPlayer().getName()).replaceAll("%player2%", arena.getSecondPlayer().getName()),
                        settings.getSyncTimer(), arena.getFirstPlayer());
            if(arena.getSecondPlayer().isOnline())
                sender.sendGameMode(settings.getEndGameMode(), settings.getSyncTimer(), arena.getSecondPlayer());
            if(reloadTime == settings.getEndTime()) {
                if (arena.getWinner() == null) { //no winner
                    if (settings.getResultMessage())
                        sender.sendMessage(message.getNoWinner(), arena.getFirstPlayer(), arena.getSecondPlayer());
                    if(settings.getEndSoundNoWinner() != null)
                        sender.sendSound(settings.getEndSoundNoWinner(), settings.getEndVolumeNoWinner(), settings.getEndPitchNoWinner(), arena.getFirstPlayer(), arena.getSecondPlayer());
                    if (settings.getEndTitleDraw() != null)
                        sender.sendTitle(settings.getEndTitleDraw(), 20, settings.getEndTime() * 20, 20, arena.getFirstPlayer(), arena.getSecondPlayer());
                    if (settings.getEndActionbarDraw() != null)
                        sender.sendActionbar(settings.getEndActionbarDraw(), 20, settings.getEndTime()*20, 20, arena.getFirstPlayer(), arena.getSecondPlayer());

                } else { //a winner

                    //getWinner
                    if (settings.getFakeExplosion())
                        sender.sendExplosion(arena.getWinner());
                    if (settings.getFirework())
                        sender.sendFirework(arena.getWinner(), settings.getSyncTimer());

                    if (arena.getWinner().getUniqueId().equals(arena.getSecondPlayerUUID())) { //first is the loser
                        if (settings.getResultMessage()) { //result message
                            sender.sendMessage(message.getWinner().replaceAll("%winner%", arena.getWinner().getName()).replaceAll("%loser%", arena.getFirstPlayer().getName()), arena.getWinner());
                            if (arena.getFirstPlayer().isOnline())
                                sender.sendMessage(message.getLoser().replaceAll("%winner%", arena.getWinner().getName()).replaceAll("%loser%", arena.getFirstPlayer().getName()), arena.getFirstPlayer());
                        }
                        if (settings.getEndTitleToWinner() != null)
                            sender.sendTitle(settings.getEndTitleToWinner(), 20, settings.getEndTime() * 20, 20, arena.getWinner());
                        if (settings.getEndTitleToLoser() != null && arena.getFirstPlayer().isOnline())
                            sender.sendTitle(settings.getEndTitleToLoser(), 20, settings.getEndTime() * 20, 20, arena.getFirstPlayer());
                        if(settings.getEndSoundToWinner() != null)
                            sender.sendSound(settings.getEndSoundToWinner(), settings.getEndVolumeToWinner(), settings.getEndPitchToWinner(), arena.getWinner());
                        if(settings.getEndSoundToLoser() != null && arena.getFirstPlayer().isOnline())
                                sender.sendSound(settings.getEndSoundToLoser(), settings.getEndVolumeToLoser(), settings.getEndPitchToLoser(), arena.getFirstPlayer());
                        if(settings.getEndActionbarToWinner() != null)
                            sender.sendActionbar(settings.getEndActionbarToWinner(), 20, settings.getEndTime()*20, 20, arena.getWinner());
                        if(settings.getEndActionbarToLoser() != null && arena.getFirstPlayer().isOnline())
                                sender.sendActionbar(settings.getEndActionbarToLoser(), 20, settings.getEndTime()*20, 20, arena.getSecondPlayer());
                }else { //second is the loser

                        if (settings.getResultMessage()) { //result message
                            sender.sendMessage(message.getWinner().replaceAll("%winner%", arena.getWinner().getName()).replaceAll("%loser%", arena.getSecondPlayer().getName()), arena.getWinner());
                            if (arena.getSecondPlayer().isOnline())
                                sender.sendMessage(message.getLoser().replaceAll("%winner%", arena.getWinner().getName()).replaceAll("%loser%", arena.getSecondPlayer().getName()), arena.getSecondPlayer());
                        }
                        if (settings.getEndTitleToWinner() != null)
                            sender.sendTitle(settings.getEndTitleToWinner(), 20, settings.getEndTime() * 20, 20, arena.getWinner());
                        if (settings.getEndTitleToLoser() != null && arena.getSecondPlayer().isOnline())
                            sender.sendTitle(settings.getEndTitleToLoser(), 20, settings.getEndTime() * 20, 20, arena.getSecondPlayer());
                        if (settings.getEndSoundToWinner() != null)
                            sender.sendSound(settings.getEndSoundToWinner(), settings.getEndVolumeToWinner(), settings.getEndPitchToWinner(), arena.getWinner());
                        if (settings.getEndSoundToLoser() != null && arena.getSecondPlayer().isOnline())
                            sender.sendSound(settings.getEndSoundToLoser(), settings.getEndVolumeToLoser(), settings.getEndPitchToLoser(), arena.getSecondPlayer());
                        if (settings.getEndActionbarToWinner() != null)
                            sender.sendActionbar(settings.getEndActionbarToWinner(), 20, settings.getEndTime() * 20, 20, arena.getWinner());
                        if (settings.getEndActionbarToLoser() != null && arena.getSecondPlayer().isOnline())
                                sender.sendActionbar(settings.getEndActionbarToLoser(), 20, settings.getEndTime() * 20, 20, arena.getSecondPlayer());
                    }
                }
            }
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

                arena.resetArena();
                arena.setStatut(ArenaStatuts.IDLE);
                this.cancel();

            }

            reloadTime--;

        }

    }
}
