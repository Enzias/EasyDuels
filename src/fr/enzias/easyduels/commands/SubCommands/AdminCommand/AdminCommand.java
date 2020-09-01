package fr.enzias.easyduels.commands.SubCommands.AdminCommand;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.commands.SubCommands.AdminCommand.SubCommands.*;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.managers.SenderManager;
import fr.enzias.easyduels.queue.QueueManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AdminCommand extends SubCommand {

    private ArrayList<SubCommand> commands = new ArrayList<>();
    Arena arena;
    MessageFile messageFile;
    SettingsFile settingsFile;
    SenderManager sender;
    QueueManager queue;

    public AdminCommand(EasyDuels plugin) {
        super(plugin);
        this.arena = plugin.getArena();
        this.messageFile = plugin.getMessageFile();
        this.settingsFile = plugin.getSettingsFile();
        this.sender = plugin.getSender();
        this.queue = plugin.getQueue();
        this.commands.add(new FirstSpawnCommand(plugin));
        this.commands.add(new SecondSpawnCommand(plugin));
        this.commands.add(new HelpCommand(plugin));
        this.commands.add(new ReloadCommand(plugin));
        this.commands.add(new LockCommand(plugin));
        this.commands.add(new QueueCommand(plugin));
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (player.hasPermission("easyduels.admin")) {

            if (args.length >= 2) {
                for (int i = 0; i < getSubCommands().size(); i++)
                    if (args[1].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        getSubCommands().get(i).onCommand(player, args);
                        return;
                    }
            }
            if (args.length == 3) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[1]));
                    return;
                }
                if (Bukkit.getPlayer(args[2]) == null) {
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[2]));
                    return;
                }
                Player player1 = Bukkit.getPlayer(args[1]);
                Player player2 = Bukkit.getPlayer(args[2]);

                if (!arena.isStatut(ArenaStatuts.IDLE) && arena.getPlayers().contains(player1)) {
                    sender.sendMessage(messageFile.getAdminJoinQueueInDuel().replaceAll("%player%", player1.getName()), player);
                    return;
                }
                if (!arena.isStatut(ArenaStatuts.IDLE) && arena.getPlayers().contains(player2)) {
                    sender.sendMessage(messageFile.getAdminJoinQueueInDuel().replaceAll("%player%", player2.getName()), player);
                    return;
                }

                if (settingsFile.getQueue()) {
                    if (queue.isNotFull()) {
                        queue.addQueueLast(player1, player2);
                        queue.checkQueue();
                    } else
                        sender.sendMessage(messageFile.getQueueIsFull(), player);
                } else {
                    if (!arena.isStatut(ArenaStatuts.IDLE))
                        sender.sendMessage(messageFile.getArenaNotEmpty(), player);
                    else {

                        arena.addToArena(player1, player2);
                        arena.setLastLocation(player1, player2);
                        arena.teleportToLocation(player1, player2, settingsFile.getSyncTimer());
                        arena.startMatch();

                    }
                }

            } else {
                sender.sendMessage(messageFile.getAdminUnknown(), player);
            }
        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "admin";
    }

    public ArrayList<SubCommand> getSubCommands() {
        return commands;
    }
}
