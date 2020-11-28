package fr.enzias.easyduels.commands.subcommands.queuecommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand {

    public LeaveCommand(EasyDuels plugin) {
        super(plugin);
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
