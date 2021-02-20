package fr.enzias.easyduels.commands.subcommands.admincommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class QueueCommand extends SubCommand {

    public QueueCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(player.hasPermission("easyduels.admin.manager")) {
            if (args.length == 3 && settingsFile.getQueue()) {
                if (Bukkit.getPlayer(args[2]) != null) {
                    Player target = Bukkit.getPlayer(args[2]);
                    if (!queue.isInQueue(target)) {
                        if (!arena.isStatut(ArenaStatuts.IDLE) && arena.getPlayers().contains(player)) {
                            sender.sendMessage(messageFile.getAdminJoinQueueInDuel()
                                    .replaceAll("%player%", target.getName()), player);
                            return;
                        }
                        if (queue.isNotFull()) {
                            queue.addQueueLast(target, null);
                            sender.sendMessage(messageFile.getForcedQueue().replaceAll("%player%", target.getName()), player);
                            queue.checkQueue();
                        } else
                            sender.sendMessage(messageFile.getQueueIsFull(), player);

                    } else
                        sender.sendMessage(messageFile.getAdminAlreadyInQueue()
                                .replaceAll("%player%", target.getName()), player);

                } else {
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[2]), player);
                }
            } else
                sender.sendMessage(messageFile.getAdminUnknown(), player);
        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "queue";
    }
}
