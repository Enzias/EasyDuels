package fr.enzias.easyduels.managers;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.queue.QueueManager;
import fr.enzias.easyduels.utils.Count;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerManager extends BukkitRunnable {

    private final EasyDuels plugin;
    Arena arena;
    MessageFile message;
    SettingsFile settings;
    SenderManager sender;
    QueueManager queue;
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
        this.queue = plugin.getQueue();
    }

    @Override
    public void run() {


        if(arena.isStatut(ArenaStatuts.LOBBY)) {

            if (settings.checkLobbyTime(lobbyTime)) {

                if (settings.getLobbyMessageToPlayers(lobbyTime) != null)
                    sender.sendMessage(settings.getLobbyMessageToPlayers(lobbyTime)
                                    .replaceAll("%player1%", arena.getFirstPlayerName()).replaceAll("%player2%", arena.getSecondPlayerName()),
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getLobbyTitleToPlayers(lobbyTime) != null)
                    sender.sendTitle(settings.getLobbyTitleToPlayers(lobbyTime), 1, 18, 1,
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getLobbySoundToPlayers(lobbyTime) != null)
                    sender.sendSound(settings.getLobbySoundToPlayers(lobbyTime), settings.getLobbyVolumeToPlayers(lobbyTime), settings.getLobbyPitchToPlayers(lobbyTime),
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getLobbyActionbarToPlayers(lobbyTime) != null)
                    sender.sendActionbar(settings.getLobbyActionbarToPlayers(lobbyTime)
                                    .replaceAll("%player1%", arena.getFirstPlayerName()).replaceAll("%player2%", arena.getSecondPlayerName()),
                            1, 18, 1, arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getLobbyPlayersCommand(lobbyTime) != null) {
                    sender.sendPlayerCommand(settings.getLobbyPlayersCommand(lobbyTime)
                                    .replaceAll("%player%", arena.getFirstPlayerName()).replaceAll("%opponent%", arena.getSecondPlayerName()),
                            arena.getFirstPlayer());
                    sender.sendPlayerCommand(settings.getLobbyPlayersCommand(lobbyTime)
                                    .replaceAll("%player%", arena.getSecondPlayerName()).replaceAll("%opponent%", arena.getFirstPlayerName()),
                            arena.getSecondPlayer());
                }
                if (settings.getLobbyConsoleCommand(lobbyTime) != null)
                    sender.sendConsoleCommand(settings.getLobbyConsoleCommand(lobbyTime)
                            .replaceAll("%player1%", arena.getFirstPlayerName()).replaceAll("%player2%", arena.getSecondPlayerName()), settings.getSyncTimer());
                if (settings.getLobbyBroadcast(lobbyTime) != null)
                    sender.sendConsoleCommand(settings.getLobbyBroadcast(lobbyTime)
                            .replaceAll("%player1%", arena.getFirstPlayerName()).replaceAll("%player2%", arena.getSecondPlayerName()), settings.getSyncTimer());

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
                                    .replaceAll("%player1%", arena.getFirstPlayerName()).replaceAll("%player2%", arena.getSecondPlayerName()),
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getFightTitleToPlayers(fightTime) != null)
                    sender.sendTitle(settings.getFightTitleToPlayers(fightTime), 1, 18, 1,
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getFightSoundToPlayers(fightTime) != null)
                    sender.sendSound(settings.getFightSoundToPlayers(fightTime), settings.getFightVolumeToPlayers(fightTime), settings.getFightPitchToPlayers(fightTime),
                            arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getFightPlayersCommand(fightTime) != null){
                    sender.sendPlayerCommand(settings.getFightPlayersCommand(lobbyTime)
                                    .replaceAll("%player%", arena.getFirstPlayerName()).replaceAll("%opponent%", arena.getSecondPlayerName()),
                            arena.getFirstPlayer());
                    sender.sendPlayerCommand(settings.getFightPlayersCommand(lobbyTime)
                                    .replaceAll("%player%", arena.getSecondPlayerName()).replaceAll("%opponent%", arena.getFirstPlayerName()),
                            arena.getSecondPlayer());
                }
                if (settings.getFightConsoleCommand(fightTime) != null)
                    sender.sendConsoleCommand(settings.getLobbyConsoleCommand(fightTime)
                            .replaceAll("%player1%", arena.getFirstPlayerName()).replaceAll("%player2%", arena.getSecondPlayerName()),
                            settings.getSyncTimer());
                if (settings.getFightBroadcast(fightTime) != null)
                    sender.sendConsoleCommand(settings.getLobbyBroadcast(fightTime)
                            .replaceAll("%player1%", arena.getFirstPlayerName()).replaceAll("%player2%", arena.getSecondPlayerName()),
                            settings.getSyncTimer());

            }
            if (settings.checkFightAll())
                if(settings.getFightAllActionbarToPlayers() != null)
                    sender.sendActionbar(settings.getFightAllActionbarToPlayers()
                                    .replaceAll("%timer%", count.secondToString(fightTime))
                                    .replaceAll("%player1%", arena.getFirstPlayerName()).replaceAll("%player2%", arena.getSecondPlayerName()),
                            1, 18, 1, arena.getFirstPlayer(), arena.getSecondPlayer());

            if(fightTime <= 0){
                arena.setStatut(ArenaStatuts.RELOADING);
            }

            fightTime--;

        }
        if(arena.isStatut(ArenaStatuts.RELOADING)){

            if(arena.getFirstPlayer().isOnline())
                sender.sendGameMode(settings.getEndGameMode(), settings.getSyncTimer(), arena.getFirstPlayer());
            if(arena.getSecondPlayer().isOnline())
                sender.sendGameMode(settings.getEndGameMode(), settings.getSyncTimer(), arena.getSecondPlayer());

            if(reloadTime == settings.getEndTime()) {
                if (arena.getWinner() == null) {
                    if (settings.getResultMessage())
                        sender.sendMessage(message.getNoWinner(), arena.getFirstPlayer(), arena.getSecondPlayer());
                    if(settings.getEndSoundNoWinner() != null)
                        sender.sendSound(settings.getEndSoundNoWinner(), settings.getEndVolumeNoWinner(), settings.getEndPitchNoWinner(), arena.getFirstPlayer(), arena.getSecondPlayer());
                    if (settings.getEndTitleDraw() != null)
                        sender.sendTitle(settings.getEndTitleDraw(), 20, settings.getEndTime() * 20, 20, arena.getFirstPlayer(), arena.getSecondPlayer());
                    if (settings.getEndActionbarDraw() != null)
                        sender.sendActionbar(settings.getEndActionbarDraw(), 20, settings.getEndTime()*20, 20, arena.getFirstPlayer(), arena.getSecondPlayer());
                    if(settings.getEndBroadcastNoWinner() != null)
                        sender.sendBroadcast(settings.getEndBroadcastNoWinner()
                                .replaceAll("%player%", arena.getFirstPlayerName()).replaceAll("%player2%", arena.getSecondPlayerName()), settings.getSyncTimer());

                } else {

                    if (settings.getFakeExplosion())
                        sender.sendExplosion(arena.getWinner());
                    if (settings.getFirework())
                        sender.sendFirework(arena.getWinner(), settings.getSyncTimer());

                    if (settings.getResultMessage()) { //result message
                        sender.sendMessage(message.getWinner().replaceAll("%winner%", arena.getWinnerName()).replaceAll("%loser%", arena.getLoserName()), arena.getWinner());
                        if (arena.getLoser().isOnline())
                            sender.sendMessage(message.getLoser().replaceAll("%winner%", arena.getWinnerName()).replaceAll("%loser%", arena.getLoserName()), arena.getLoser());
                    }

                    if(arena.getLoser().isOnline()){
                        if (settings.getEndTitleToLoser() != null)
                            sender.sendTitle(settings.getEndTitleToLoser(), 20, settings.getEndTime() * 20, 20, arena.getLoser());
                        if (settings.getEndSoundToLoser() != null)
                            sender.sendSound(settings.getEndSoundToLoser(), settings.getEndVolumeToLoser(), settings.getEndPitchToLoser(), arena.getLoser());
                        if (settings.getEndActionbarToLoser() != null)
                            sender.sendActionbar(settings.getEndActionbarToLoser(), 20, settings.getEndTime() * 20, 20, arena.getLoser());
                    }
                    if (settings.getEndTitleToWinner() != null)
                        sender.sendTitle(settings.getEndTitleToWinner(), 20, settings.getEndTime() * 20, 20, arena.getWinner());
                    if (settings.getEndSoundToWinner() != null)
                        sender.sendSound(settings.getEndSoundToWinner(), settings.getEndVolumeToWinner(), settings.getEndPitchToWinner(), arena.getWinner());
                    if (settings.getEndActionbarToWinner() != null)
                        sender.sendActionbar(settings.getEndActionbarToWinner(), 20, settings.getEndTime() * 20, 20, arena.getWinner());
                    if (settings.getEndRewardCommand() != null)
                        sender.sendConsoleCommand(settings.getEndRewardCommand(), settings.getSyncTimer(),
                                "%winner%", arena.getWinnerName(), "%loser%", arena.getLoserName());
                    if (settings.getEndBroadcast() != null)
                        sender.sendBroadcast(settings.getEndBroadcast()
                                .replaceAll("%winner%", arena.getWinnerName()).replaceAll("%loser%", arena.getLoserName()), settings.getSyncTimer());
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
                if(settings.getQueue())
                    queue.checkQueue();
                this.cancel();

            }

            reloadTime--;

        }

    }
}
