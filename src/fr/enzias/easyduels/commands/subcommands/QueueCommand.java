package fr.enzias.easyduels.commands.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;
public class QueueCommand extends SubCommand {

    public QueueCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (args.length == 1) {
            if (player.hasPermission("easyduels.queue")) {
                    if (queue.isInQueue(player)) {
                        queue.tryGiveBackBoth(player);
                        queue.deleteQueue(player);
                    } else if (queue.isAnOpponent(player)) {
                        if (queue.isInQueue(queue.getOpponentOf(player))) {
                            queue.tryGiveBackBoth(queue.getOpponentOf(player));
                            queue.deleteQueue(queue.getOpponentOf(player));
                        }
                    } else if (arena.isEnable()) {
                        if (!arena.isStatut(ArenaStatuts.IDLE) && arena.getPlayers().contains(player)) {
                            sender.sendMessage(messageFile.getJoinQueueInDuel(), player);
                            return;
                        }
                        if (queue.isNotFull()) {
                            queue.addQueueLast(player, null);
                            queue.checkQueue();
                        } else
                            sender.sendMessage(messageFile.getQueueIsFull(), player);
                    }else {
                    sender.sendMessage(messageFile.getArenaIsLocked(), player);
                }
            } else
                sender.sendMessage(messageFile.getNoPermission(), player);
        }
        sender.sendMessage(messageFile.getUnknown(), player);
    }

    @Override
    public String getName() {
        return "queue";
    }
}
