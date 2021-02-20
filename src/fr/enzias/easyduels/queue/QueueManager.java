package fr.enzias.easyduels.queue;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.arena.Spectate;
import fr.enzias.easyduels.filemanager.files.SettingsFile;
import fr.enzias.easyduels.managers.versions.SenderManager;
import fr.enzias.easyduels.utils.DuelPlayerCache;
import fr.enzias.easyduels.utils.VaultHook;
import org.bukkit.entity.Player;

public class QueueManager {

    private final EasyDuels plugin;
    Arena arena;
    Spectate spectate;
    Queue queue;
    SenderManager sender;
    SettingsFile settings;
    VaultHook vaultHook;
    public QueueManager(EasyDuels plugin) {
        this.plugin = plugin;
        this.arena = plugin.getArena();
        this.spectate = plugin.getSpectate();
        this.settings = plugin.getSettingsFile();
        this.queue = new Queue(plugin);
        this.sender = plugin.getSender();
        this.vaultHook = plugin.getVaultHook();
    }

    public boolean isNotFull(){
        return queue.getQueueLength() != settings.getQueueMaxPlayers();
    }

    public boolean isEmpty(){
        return queue.getQueueLength() == 0;
    }

    public void addQueueLast(Player player, Player opponent){
        DuelPlayerCache cache = new DuelPlayerCache(opponent, -1);
        queue.add(player, cache);
        sendQueueJoin(player);
    }

    public void addQueueLast(Player player, Player opponent, int bet){
        DuelPlayerCache cache = new DuelPlayerCache(opponent, bet);
        queue.add(player, cache);
        sendQueueJoin(player);
    }

    public void checkQueue() {
        if(arena.isStatut(ArenaStatuts.IDLE) && !queue.isEmpty()){

            Player target = null;
            Player player = null;

            if(queue.getCache(queue.getFirst()).hasOpponent()){

                target = queue.getCache(queue.getFirst()).getOpponent();
                player = queue.getFirst();

                this.deleteForceQueue(player);
            } else if(queue.getQueueLength() >= 2){
                if(queue.getCache(queue.get(1)).hasOpponent()){

                    target = queue.getCache(queue.get(1)).getOpponent();
                    player = queue.get(1);

                    this.deleteForceQueue(player);
                } else {
                    player = queue.getAndRemoveFirst();
                    target = queue.getFirst();

                    this.deleteForceQueue(target);
                }

            }
            if(target != null && player != null) {

                if(spectate.isSpectating(target))
                    spectate.finishSpectating(target);
                if(spectate.isSpectating(player))
                    spectate.finishSpectating(player);

                if(settings.getMoneyBet() && queue.getCache(player).hasBet()) {
                    arena.setBet(queue.getCache(player).getBet());
                }

                arena.addToArena(target, player);
                arena.setLastLocation(target, player);
                arena.teleportToLocation(target, player, settings.getSyncTimer());
                arena.startMatch();
            }
        }

    }

    public void deleteQueue(Player player){
        int pos = queue.getPosition(player);
        queue.delete(player);
        sendQueueLeave(player);

        for(Player after : queue.getQueue())
            if(queue.getPosition(after) >= pos)
                sendQueueUpdate(after);
    }

    public void deleteForceQueue(Player player){
        int pos = queue.getPosition(player);
        queue.delete(player);

        for(Player after : queue.getQueue())
            if(queue.getPosition(after) >= pos)
                sendQueueUpdate(after);
    }

    public void deleteAllQueue(){
        queue.clear();
    }

    public void tryGiveBackBoth(Player player){
        if(settings.getMoneyBet() && queue.getCache(player).hasBet()) {
            vaultHook.giveBackBoth(queue.getCache(player).getBet(), player, queue.getCache(player).getOpponent());
        }
    }

    public boolean isInQueue(Player player){
        return queue.isInQueue(player);
    }

    public boolean isAnOpponent(Player opponent){
        for(Player player : queue.getQueue()) {
            if (queue.getCache(player).isOpponent(opponent))
                return true;
        }
        return false;
    }

    public Player getOpponentOf(Player opponent){
        for(Player player : queue.getQueue()) {
            if (queue.getCache(player).isOpponent(opponent))
                return player;
        } return null;
    }

    public void sendQueueJoin(Player player) {
        if(settings.checkQueueJoin()) {
            if (queue.getCache(player).hasOpponent()) {
                Player opponent = queue.getCache(player).getOpponent();
                if (settings.getQueueJoinMessageToPlayer() != null)
                    sender.sendMessage(settings.getQueueJoinMessageToPlayer()
                            .replaceAll("%player_position%", queue.getPosition(player) + "")
                            .replaceAll("%queue_size%", queue.getQueueLength() + ""), player, opponent);
                if (settings.getQueueJoinTitleToPlayer() != null) {
                    sender.sendTitlePlaceHolders(settings.getQueueJoinTitleToPlayer(), 18, 100, 1, player,
                            "%player_position%", queue.getPosition(player) + "", "%queue_size%", queue.getQueueLength() + "");
                    sender.sendTitlePlaceHolders(settings.getQueueJoinTitleToPlayer(), 18, 100, 1, opponent,
                            "%player_position%", queue.getPosition(player) + "", "%queue_size%", queue.getQueueLength() + "");
                }
                if (settings.getQueueJoinSoundToPlayer() != null)
                    sender.sendSound(settings.getQueueJoinSoundToPlayer(), settings.getQueueJoinVolumeToPlayer(), settings.getQueueJoinPitchToPlayer(), player, opponent);
                if (settings.getQueueJoinActionbarToPlayer() != null)
                    sender.sendActionbar(settings.getQueueJoinActionbarToPlayer()
                                    .replaceAll("%player_position%", queue.getPosition(player) + "")
                                    .replaceAll("%queue_size%", queue.getQueueLength() + "")
                            , 18, 100, 18, player, opponent);
            } else {
                if (settings.getQueueJoinMessageToPlayer() != null)
                    sender.sendMessage(settings.getQueueJoinMessageToPlayer()
                            .replaceAll("%player_position%", queue.getPosition(player) + "")
                            .replaceAll("%queue_size%", queue.getQueueLength() + ""), player);
                if (settings.getQueueJoinTitleToPlayer() != null)
                    sender.sendTitlePlaceHolders(settings.getQueueJoinTitleToPlayer(), 18, 100, 1, player,
                            "%player_position%", queue.getPosition(player) + "", "%queue_size%", queue.getQueueLength() + "");
                if (settings.getQueueJoinSoundToPlayer() != null)
                    sender.sendSound(settings.getQueueJoinSoundToPlayer(), settings.getQueueJoinVolumeToPlayer(), settings.getQueueJoinPitchToPlayer(), player);
                if (settings.getQueueJoinActionbarToPlayer() != null)
                    sender.sendActionbar(settings.getQueueJoinActionbarToPlayer()
                                    .replaceAll("%player_position%", queue.getPosition(player) + "")
                                    .replaceAll("%queue_size%", queue.getQueueLength() + "")
                            , 18, 100, 18, player);
            }
        }
    }

    public void sendQueueLeave(Player player) {
        if(settings.checkQueueLeave()) {
            if (queue.getCache(player).hasOpponent() && queue.getCache(player).getOpponent().isOnline()) {
                Player opponent = queue.getCache(player).getOpponent();
                if (settings.getQueueLeaveMessageToPlayer() != null)
                    sender.sendMessage(settings.getQueueLeaveMessageToPlayer()
                            .replaceAll("%queue_size%", queue.getQueueLength() + ""), opponent);
                if (settings.getQueueLeaveTitleToPlayer() != null) {
                    sender.sendTitlePlaceHolders(settings.getQueueLeaveTitleToPlayer(), 18, 100, 1, opponent,
                            "%queue_size%", queue.getQueueLength() + "");
                }
                if (settings.getQueueLeaveSoundToPlayer() != null)
                    sender.sendSound(settings.getQueueLeaveSoundToPlayer(), settings.getQueueLeaveVolumeToPlayer(), settings.getQueueLeavePitchToPlayer(), opponent);
                if (settings.getQueueLeaveActionbarToPlayer() != null)
                    sender.sendActionbar(settings.getQueueLeaveMessageToPlayer()
                                    .replaceAll("%queue_size%", queue.getQueueLength() + "")
                            , 18, 100, 18, opponent);
            }
            if (player.isOnline()) {
                if (settings.getQueueLeaveMessageToPlayer() != null)
                    sender.sendMessage(settings.getQueueLeaveMessageToPlayer()
                            .replaceAll("%queue_size%", queue.getQueueLength() + ""), player);
                if (settings.getQueueLeaveTitleToPlayer() != null)
                    sender.sendTitlePlaceHolders(settings.getQueueLeaveTitleToPlayer(), 18, 100, 1, player,
                            "%queue_size%", queue.getQueueLength() + "");
                if (settings.getQueueLeaveSoundToPlayer() != null)
                    sender.sendSound(settings.getQueueLeaveSoundToPlayer(), settings.getQueueLeaveVolumeToPlayer(), settings.getQueueLeavePitchToPlayer(), player);
                if (settings.getQueueLeaveActionbarToPlayer() != null)
                    sender.sendActionbar(settings.getQueueLeaveActionbarToPlayer()
                                    .replaceAll("%queue_size%", queue.getQueueLength() + "")
                            , 18, 100, 18, player);
            }
        }
    }

    public void sendQueueUpdate(Player player) {
        if(settings.checkQueueUpdate()) {
            if (queue.getCache(player).hasOpponent()) {
                Player opponent = queue.getCache(player).getOpponent();
                if (settings.getQueueUpdateMessageToPlayer() != null)
                    sender.sendMessage(settings.getQueueUpdateMessageToPlayer()
                            .replaceAll("%player_position%", queue.getPosition(player) + "")
                            .replaceAll("%queue_size%", queue.getQueueLength() + ""), player, opponent);
                if (settings.getQueueUpdateTitleToPlayer() != null) {
                    sender.sendTitlePlaceHolders(settings.getQueueUpdateTitleToPlayer(), 18, 100, 1, player,
                            "%player_position%", queue.getPosition(player) + "", "%queue_size%", queue.getQueueLength() + "");
                    sender.sendTitlePlaceHolders(settings.getQueueUpdateTitleToPlayer(), 18, 100, 1, opponent,
                            "%player_position%", queue.getPosition(player) + "", "%queue_size%", queue.getQueueLength() + "");
                }
                if (settings.getQueueUpdateSoundToPlayer() != null)
                    sender.sendSound(settings.getQueueUpdateSoundToPlayer(), settings.getQueueUpdateVolumeToPlayer(), settings.getQueueUpdatePitchToPlayer(), player, opponent);
                if (settings.getQueueUpdateActionbarToPlayer() != null)
                    sender.sendActionbar(settings.getQueueUpdateActionbarToPlayer()
                                    .replaceAll("%player_position%", queue.getPosition(player) + "")
                                    .replaceAll("%queue_size%", queue.getQueueLength() + "")
                            , 18, 100, 18, player, opponent);
            } else {
                if (settings.getQueueUpdateMessageToPlayer() != null)
                    sender.sendMessage(settings.getQueueUpdateMessageToPlayer()
                            .replaceAll("%player_position%", queue.getPosition(player) + "")
                            .replaceAll("%queue_size%", queue.getQueueLength() + ""), player);
                if (settings.getQueueUpdateTitleToPlayer() != null)
                    sender.sendTitlePlaceHolders(settings.getQueueUpdateTitleToPlayer(), 18, 100, 1, player,
                            "%player_position%", queue.getPosition(player) + "", "%queue_size%", queue.getQueueLength() + "");
                if (settings.getQueueUpdateSoundToPlayer() != null)
                    sender.sendSound(settings.getQueueUpdateSoundToPlayer(), settings.getQueueUpdateVolumeToPlayer(), settings.getQueueUpdatePitchToPlayer(), player);
                if (settings.getQueueUpdateActionbarToPlayer() != null)
                    sender.sendActionbar(settings.getQueueUpdateActionbarToPlayer()
                                    .replaceAll("%player_position%", queue.getPosition(player) + "")
                                    .replaceAll("%queue_size%", queue.getQueueLength() + "")
                            , 18, 100, 18, player);
            }
        }
    }

}
