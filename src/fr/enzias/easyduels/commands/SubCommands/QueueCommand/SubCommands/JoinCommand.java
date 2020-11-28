package fr.enzias.easyduels.commands.subcommands.queuecommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {

    public JoinCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(player.hasPermission("easyduels.queue")) {
            if (arena.isEnable()) {
                if (!queue.isInQueue(player)) {
                    if (!arena.isStatut(ArenaStatuts.IDLE) && arena.getPlayers().contains(player)) {
                        sender.sendMessage(messageFile.getJoinQueueInDuel(), player);
                        return;
                    }
                    if (queue.isNotFull()) {
                        queue.addQueueLast(player, null);
                        queue.checkQueue();
                    } else
                        sender.sendMessage(messageFile.getQueueIsFull(), player);

                } else
                    sender.sendMessage(messageFile.getAlreadyInQueue(), player);
            } else {
                sender.sendMessage(messageFile.getArenaIsLocked(), player);
            }
        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "join";
    }
}
