package fr.enzias.easyduels.commands.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AcceptCommand extends SubCommand {

    public AcceptCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (args.length == 2) {
            if (Bukkit.getPlayer(args[1]) != null) {

                Player target = Bukkit.getPlayer(args[1]);

                if (request.hasRequest(target) && request.hasRequestFrom(target, player)) {

                    if(arena.isEnable()) {

                        if (!arena.isStatut(ArenaStatuts.IDLE) && arena.getPlayers().contains(player)) {
                            sender.sendMessage(messageFile.getAcceptRequestInDuel(), player);
                            return;
                        }

                        if(settingsFile.getMoneyBet() && request.hasBet(target)){
                            int amount = request.getBet(target);
                            if(vaultHook.hasEnough(amount, player)) {

                                if (vaultHook.hasEnough(amount, target)) {

                                    sender.sendMessage(messageFile.getRequestAccept().replaceAll("%player%", player.getName()), target);
                                    sender.sendMessage(messageFile.getYouRequestAccept().replaceAll("%player%", target.getName()), player);
                                    request.deleteRequest(target);

                                    if (settingsFile.getQueue()) {
                                        if (queue.isNotFull()) {
                                            if(!queue.isEmpty() || !arena.isStatut(ArenaStatuts.IDLE)) {
                                                queue.addQueueLast(player, target, amount);
                                                vaultHook.takeBoth(amount, target, player);
                                                queue.checkQueue();
                                            }else{
                                                if(spectate.isSpectating(target))
                                                    spectate.finishSpectating(target);
                                                if(spectate.isSpectating(player))
                                                    spectate.finishSpectating(player);

                                                arena.setBet(amount);
                                                vaultHook.takeBoth(amount, target, player);

                                                arena.addToArena(target, player);
                                                arena.setLastLocation(target, player);
                                                arena.teleportToLocation(target, player, settingsFile.getSyncTimer());
                                                arena.startMatch();
                                            }
                                        } else
                                            sender.sendMessage(messageFile.getQueueIsFull(), player, target);
                                    } else {
                                        if (!arena.isStatut(ArenaStatuts.IDLE))
                                            sender.sendMessage(messageFile.getArenaNotEmpty(), player, target);
                                        else {

                                            if(spectate.isSpectating(target))
                                                spectate.finishSpectating(target);
                                            if(spectate.isSpectating(player))
                                                spectate.finishSpectating(player);

                                            arena.setBet(amount);
                                            vaultHook.takeBoth(amount, target, player);

                                            arena.addToArena(target, player);
                                            arena.setLastLocation(target, player);
                                            arena.teleportToLocation(target, player, settingsFile.getSyncTimer());
                                            arena.startMatch();

                                        }
                                    }

                                }else
                                    sender.sendMessage(messageFile.getPlayerNotEnoughMoney()
                                            .replaceAll("%player%", target.getName()), player);
                            }else
                                sender.sendMessage(messageFile.getYouNotEnoughMoney(), player);
                            return;
                        }

                        sender.sendMessage(messageFile.getRequestAccept().replaceAll("%player%", player.getName()), target);
                        sender.sendMessage(messageFile.getYouRequestAccept().replaceAll("%player%", target.getName()), player);
                        request.deleteRequest(target);

                        if (settingsFile.getQueue()) {
                            if (queue.isNotFull()){
                                if(!queue.isEmpty() || !arena.isStatut(ArenaStatuts.IDLE)) {
                                    queue.addQueueLast(player, target);
                                    queue.checkQueue();
                                }else{
                                    if(spectate.isSpectating(target))
                                        spectate.finishSpectating(target);
                                    if(spectate.isSpectating(player))
                                        spectate.finishSpectating(player);

                                    arena.addToArena(target, player);
                                    arena.setLastLocation(target, player);
                                    arena.teleportToLocation(target, player, settingsFile.getSyncTimer());
                                    arena.startMatch();
                                }
                            } else
                                sender.sendMessage(messageFile.getQueueIsFull(), player, target);
                        } else {
                            if (!arena.isStatut(ArenaStatuts.IDLE))
                                sender.sendMessage(messageFile.getArenaNotEmpty(), player, target);
                            else {

                                if(spectate.isSpectating(target))
                                    spectate.finishSpectating(target);
                                if(spectate.isSpectating(player))
                                    spectate.finishSpectating(player);

                                arena.addToArena(target, player);
                                arena.setLastLocation(target, player);
                                arena.teleportToLocation(target, player, settingsFile.getSyncTimer());
                                arena.startMatch();

                            }
                        }
                    } else {
                        sender.sendMessage(messageFile.getArenaIsLocked(), player);
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
