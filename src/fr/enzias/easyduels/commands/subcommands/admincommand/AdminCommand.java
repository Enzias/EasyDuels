package fr.enzias.easyduels.commands.subcommands.admincommand;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.commands.subcommands.admincommand.subcommands.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AdminCommand extends SubCommand {

    private ArrayList<SubCommand> commands = new ArrayList<>();

    public AdminCommand(EasyDuels plugin) {
        super(plugin);
        this.commands.add(new FirstSpawnCommand(plugin));
        this.commands.add(new SecondSpawnCommand(plugin));
        this.commands.add(new LobbyCommand(plugin));
        this.commands.add(new SpectateCommand(plugin));
        this.commands.add(new HelpCommand(plugin));
        this.commands.add(new ReloadCommand(plugin));
        this.commands.add(new LockCommand(plugin));
        this.commands.add(new QueueCommand(plugin));

        if(rankFile.getRank()){
            this.commands.add(new ExpGiveCommand(plugin));
            this.commands.add( new ExpTakeCommand(plugin));
            this.commands.add( new ExpSetCommand(plugin));
            this.commands.add( new ExpResetCommand(plugin));
            this.commands.add( new ExpGiveAllCommand(plugin));
            this.commands.add( new ExpTakeAllCommand(plugin));
            this.commands.add( new ExpSetAllCommand(plugin));
            this.commands.add( new ExpResetAllCommand(plugin));
        }
    }
    @Override
    public void onCommand(Player player, String[] args) {

        if (args.length >= 2) {
            for (int i = 0; i < getSubCommands().size(); i++)
                if (args[1].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    getSubCommands().get(i).onCommand(player, args);
                    return;
                }
        }
        if(player.hasPermission("easyduels.admin.manager")) {
            if (args.length == 3 || args.length == 4) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[1]), player);
                    return;
                }
                if (Bukkit.getPlayer(args[2]) == null) {
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[2]), player);
                    return;
                }
                Player player1 = Bukkit.getPlayer(args[1]);
                Player player2 = Bukkit.getPlayer(args[2]);

                if (player1.getName().equalsIgnoreCase(player2.getName())) {
                    sender.sendMessage(messageFile.getDuelHimSelf(), player);
                    return;
                }
                if (!queue.isInQueue(player1)) {
                    if (!arena.isStatut(ArenaStatuts.IDLE) && arena.getPlayers().contains(player1)) {
                        sender.sendMessage(messageFile.getAdminJoinQueueInDuel().replaceAll("%player%", player1.getName()), player);
                        return;
                    }
                    if (!arena.isStatut(ArenaStatuts.IDLE) && arena.getPlayers().contains(player2)) {
                        sender.sendMessage(messageFile.getAdminJoinQueueInDuel().replaceAll("%player%", player2.getName()), player);
                        return;
                    }

                    if (args.length == 4) {
                        if (vaultHook.isEconNotNull() && settingsFile.getMoneyBet()) {
                            if (vaultHook.isValidAmount(args[3])) {
                                int amount = vaultHook.getValidAmount(args[3]);

                                if (vaultHook.isAbove(amount)) {

                                    if (vaultHook.isUnder(amount)) {

                                        if (vaultHook.hasEnough(amount, player1)) {

                                            if (vaultHook.hasEnough(amount, player2)) {

                                                if (settingsFile.getQueue()) {
                                                    if (queue.isNotFull()) {
                                                        queue.addQueueLast(player1, player2, amount);
                                                        vaultHook.takeBoth(amount, player1, player2);
                                                        sender.sendMessage(messageFile.getForcedDuelQueue()
                                                                .replaceAll("%player1%", player1.getName()).replaceAll("%player2%", player2.getName()), player);
                                                        queue.checkQueue();
                                                    } else
                                                        sender.sendMessage(messageFile.getQueueIsFull(), player);
                                                } else {
                                                    if (!arena.isStatut(ArenaStatuts.IDLE))
                                                        sender.sendMessage(messageFile.getArenaNotEmpty(), player);
                                                    else {

                                                        if (spectate.isSpectating(player1))
                                                            spectate.finishSpectating(player1);
                                                        if (spectate.isSpectating(player2))
                                                            spectate.finishSpectating(player2);

                                                        arena.setBet(amount);
                                                        vaultHook.takeBoth(amount, player1, player2);

                                                        sender.sendMessage(messageFile.getForcedDuelStart()
                                                                .replaceAll("%player1%", player1.getName()).replaceAll("%player2%", player2.getName()), player);
                                                        arena.addToArena(player1, player2);
                                                        arena.setLastLocation(player1, player2);
                                                        arena.teleportToLocation(player1, player2, settingsFile.getSyncTimer());
                                                        arena.startMatch();

                                                    }
                                                }

                                            } else {
                                                sender.sendMessage(messageFile.getPlayerNotEnoughMoney()
                                                        .replaceAll("%player%", player2.getName()), player);
                                            }
                                        } else
                                            sender.sendMessage(messageFile.getPlayerNotEnoughMoney()
                                                    .replaceAll("%player%", player1.getName()), player);
                                    } else
                                        sender.sendMessage(messageFile.getGreaterMaximum()
                                                .replaceAll("%amount%", Integer.toString(settingsFile.getMaxAmount())), player);
                                } else
                                    sender.sendMessage(messageFile.getBelowMinimum()
                                            .replaceAll("%amount%", Integer.toString(settingsFile.getMinAmount())), player);
                            } else
                                sender.sendMessage(messageFile.getInvalidAmountBet(), player);
                        } else
                            sender.sendMessage(messageFile.getAdminUnknown(), player);
                        return;
                    }


                    if (settingsFile.getQueue()) {
                        if (queue.isNotFull()) {
                            queue.addQueueLast(player1, player2);
                            sender.sendMessage(messageFile.getForcedDuelQueue()
                                    .replaceAll("%player1%", player1.getName()).replaceAll("%player2%", player2.getName()), player);
                            queue.checkQueue();
                        } else
                            sender.sendMessage(messageFile.getQueueIsFull(), player);
                    } else {
                        if (!arena.isStatut(ArenaStatuts.IDLE))
                            sender.sendMessage(messageFile.getArenaNotEmpty(), player);
                        else {

                            if (spectate.isSpectating(player1))
                                spectate.finishSpectating(player1);
                            if (spectate.isSpectating(player2))
                                spectate.finishSpectating(player2);

                            sender.sendMessage(messageFile.getForcedDuelStart()
                                    .replaceAll("%player1%", player1.getName()).replaceAll("%player2%", player2.getName()), player);
                            arena.addToArena(player1, player2);
                            arena.setLastLocation(player1, player2);
                            arena.teleportToLocation(player1, player2, settingsFile.getSyncTimer());
                            arena.startMatch();

                        }
                    }
                } else
                    sender.sendMessage(messageFile.getAdminAlreadyInQueue()
                            .replaceAll("%player%", player1.getName()), player);

            } else
                sender.sendMessage(messageFile.getAdminUnknown(), player);
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
