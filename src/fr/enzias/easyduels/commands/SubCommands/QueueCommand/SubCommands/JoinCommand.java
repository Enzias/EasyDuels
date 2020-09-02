package fr.enzias.easyduels.commands.SubCommands.QueueCommand.SubCommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.managers.SenderManager;
import fr.enzias.easyduels.queue.QueueManager;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {

    Arena arena;
    ArenaFile arenaFile;
    MessageFile messageFile;
    QueueManager queue;
    SenderManager sender;
    public JoinCommand(EasyDuels plugin) {
        super(plugin);
        this.arena = plugin.getArena();
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.queue = plugin.getQueue();
        this.sender = plugin.getSender();
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
