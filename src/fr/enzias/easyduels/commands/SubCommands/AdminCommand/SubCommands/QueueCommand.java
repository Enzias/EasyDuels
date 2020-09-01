package fr.enzias.easyduels.commands.SubCommands.AdminCommand.SubCommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.managers.SenderManager;
import fr.enzias.easyduels.queue.QueueManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class QueueCommand extends SubCommand {

    Arena arena;
    MessageFile messageFile;
    SettingsFile settings;
    SenderManager sender;
    QueueManager queue;
    public QueueCommand(EasyDuels plugin) {
        super(plugin);
        this.arena = plugin.getArena();
        this.settings = plugin.getSettingsFile();
        this.messageFile = plugin.getMessageFile();
        this.sender = plugin.getSender();
        this.queue = plugin.getQueue();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length == 3 && settings.getQueue()){
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
    }

    @Override
    public String getName() {
        return "queue";
    }
}
