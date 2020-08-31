package fr.enzias.easyduels.commands.SubCommands.AcceptCommand;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.managers.RequestManager;
import fr.enzias.easyduels.managers.SenderManager;
import fr.enzias.easyduels.queue.QueueManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AcceptCommand extends SubCommand {

    Arena arena;
    MessageFile messageFile;
    SettingsFile settingsFile;
    RequestManager request;
    SenderManager sender;
    QueueManager queue;

    public AcceptCommand(EasyDuels plugin) {
        super(plugin);
        this.arena = plugin.getArena();
        this.request = plugin.getRequest();
        this.messageFile = plugin.getMessageFile();
        this.settingsFile = plugin.getSettingsFile();
        this.sender = plugin.getSender();
        this.queue = plugin.getQueue();
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (args.length == 2) {
            if (Bukkit.getPlayer(args[1]) != null) {

                Player target = Bukkit.getPlayer(args[1]);

                if (request.hasRequest(target) && request.hasRequestFrom(target, player)) {

                    sender.sendMessage(messageFile.getRequestAccept().replaceAll("%player%", player.getName()), target);
                    sender.sendMessage(messageFile.getYouRequestAccept().replaceAll("%player%", target.getName()), player);
                    request.deleteRequest(target);

                    if(settingsFile.getQueue()){
                        if(queue.isNotFull()){
                            queue.addQueueLast(player, target);
                            queue.checkQueue();
                        } else
                            sender.sendMessage(messageFile.getQueueIsFull(), player, target);
                    } else {
                        if (!arena.isStatut(ArenaStatuts.IDLE))
                            sender.sendMessage(messageFile.getArenaNotEmpty(), player, target);
                        else {

                            arena.addToArena(target, player);
                            arena.setLastLocation(target, player);
                            arena.teleportToLocation(target, player, settingsFile.getSyncTimer());
                            arena.startMatch();

                        }
                    }

                } else {
                    sender.sendMessage(messageFile.getNoRequest().replaceAll("%player%", target.getName()), player);
                }
            } else {
                sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[1]), player);
            }
        } else
            sender.sendMessage(messageFile.getUnknown(), player);
    }

    @Override
    public String getName() {
        return "accept";
    }
}
