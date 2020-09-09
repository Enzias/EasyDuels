package fr.enzias.easyduels.managers;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.utils.Count;
import fr.enzias.easyduels.utils.VaultHook;

public class CountdownManager{

    private final EasyDuels plugin;
    Arena arena;
    MessageFile message;
    SettingsFile settings;
    SenderManager sender;
    VaultHook vaultHook;
    Count count = new Count();
    public CountdownManager(EasyDuels plugin) {
        this.plugin = plugin;
        this.arena = plugin.getArena();
        this.message = plugin.getMessageFile();
        this.settings = plugin.getSettingsFile();
        this.sender = plugin.getSender();
        this.vaultHook = plugin.getVaultHook();
    }

    public void sendLobbyTimer(int lobbyTime){

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
    }

    public void sendFightTime(int fightTime){

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
                sender.sendPlayerCommand(settings.getFightPlayersCommand(fightTime)
                                .replaceAll("%player%", arena.getFirstPlayerName()).replaceAll("%opponent%", arena.getSecondPlayerName()),
                        arena.getFirstPlayer());
                sender.sendPlayerCommand(settings.getFightPlayersCommand(fightTime)
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

    }

    public void sendEndTime(int reloadTime) {

        if (arena.getFirstPlayer().isOnline())
            sender.sendGameMode(settings.getEndGameMode(), settings.getSyncTimer(), arena.getFirstPlayer());
        if (arena.getSecondPlayer().isOnline())
            sender.sendGameMode(settings.getEndGameMode(), settings.getSyncTimer(), arena.getSecondPlayer());

        if (reloadTime == settings.getEndTime()) {

            if (arena.getWinner() == null) {
                if (settings.getResultMessage())
                    sender.sendMessage(message.getNoWinner(), arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getEndSoundNoWinner() != null)
                    sender.sendSound(settings.getEndSoundNoWinner(), settings.getEndVolumeNoWinner(), settings.getEndPitchNoWinner(), arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getEndTitleDraw() != null)
                    sender.sendTitle(settings.getEndTitleDraw(), 20, settings.getEndTime() * 20, 20, arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getEndActionbarDraw() != null)
                    sender.sendActionbar(settings.getEndActionbarDraw(), 20, settings.getEndTime() * 20, 20, arena.getFirstPlayer(), arena.getSecondPlayer());
                if (settings.getEndBroadcastNoWinner() != null)
                    sender.sendBroadcast(settings.getEndBroadcastNoWinner()
                            .replaceAll("%player1%", arena.getFirstPlayerName())
                            .replaceAll("%player2%", arena.getSecondPlayerName()), settings.getSyncTimer());
                if(arena.getBet() != 0){
                    if (settings.getBetMessageNoWinner() != null)
                        sender.sendMessage(settings.getBetMessageNoWinner()
                                        .replaceAll("%amount%", arena.getBet()*2 + "")
                                , arena.getFirstPlayer(), arena.getSecondPlayer());

                    vaultHook.giveBackBoth(arena.getBet(), arena.getFirstPlayer(), arena.getSecondPlayer());
                }

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

                if (arena.getLoser().isOnline()) {
                    if (settings.getEndTitleToLoser() != null)
                        sender.sendTitle(settings.getEndTitleToLoser(), 20, settings.getEndTime() * 20, 20, arena.getLoser());
                    if (settings.getEndSoundToLoser() != null)
                        sender.sendSound(settings.getEndSoundToLoser(), settings.getEndVolumeToLoser(), settings.getEndPitchToLoser(), arena.getLoser());
                    if (settings.getEndActionbarToLoser() != null)
                        sender.sendActionbar(settings.getEndActionbarToLoser(), 20, settings.getEndTime() * 20, 20, arena.getLoser());
                    if(arena.getBet() != 0)
                        if (settings.getBetMessageToLoser() != null)
                            sender.sendMessage(settings.getBetMessageToLoser()
                                    .replaceAll("%amount%", arena.getBet()*2 + ""), arena.getLoser());

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
                if(arena.getBet() != 0){
                    if (settings.getBetMessageToWinner() != null)
                        sender.sendMessage(settings.getBetMessageToWinner()
                                .replaceAll("%amount%", arena.getBet()*2 + ""), arena.getWinner());

                    vaultHook.give(arena.getBet()*2, arena.getWinner());
                }
            }
        }
    }

}
