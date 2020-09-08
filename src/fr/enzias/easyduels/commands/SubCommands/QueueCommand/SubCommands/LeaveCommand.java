package fr.enzias.easyduels.commands.SubCommands.QueueCommand.SubCommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.managers.SenderManager;
import fr.enzias.easyduels.queue.QueueManager;
import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand {

    Arena arena;
    ArenaFile arenaFile;
    MessageFile messageFile;
    QueueManager queue;
    SenderManager sender;
    public LeaveCommand(EasyDuels plugin) {
        super(plugin);
        this.arena = plugin.getArena();
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.queue = plugin.getQueue();
        this.sender = plugin.getSender();
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (player.hasPermission("easyduels.queue")) {
            if (queue.isInQueue(player)) {
                queue.tryGiveBackBoth(player);
                queue.deleteQueue(player);
            } else if (queue.isAnOpponent(player)) {
                if (queue.isInQueue(queue.getOpponentOf(player))) {
                    queue.tryGiveBackBoth(queue.getOpponentOf(player));
                    queue.deleteQueue(queue.getOpponentOf(player));
                }
            } else
                sender.sendMessage(messageFile.getNotInQueue(), player);
        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "leave";
    }
}
