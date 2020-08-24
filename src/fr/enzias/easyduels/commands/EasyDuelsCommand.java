package fr.enzias.easyduels.commands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.managers.RequestManager;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EasyDuelsCommand implements CommandExecutor {

    Arena arena;
    ArenaFile arenaFile;
    MessageFile messageFile;
    SettingsFile settingsFile;
    RequestManager request;
    SenderManager sender;

    public EasyDuelsCommand(EasyDuels plugin) {
        this.arena = plugin.getArena();
        this.request = plugin.getRequest();
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.settingsFile = plugin.getSettingsFile();
        this.sender = plugin.getSender();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player))
        {
            commandSender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player player = (Player) commandSender;

        // /easyduels = help message

        if(args.length == 1) {

            if(args[0].equalsIgnoreCase("help")){
                if(player.hasPermission("easyduels.help")) {
                    sender.sendMessage(messageFile.getHelpMessages(), player);
                }
                else
                    sender.sendMessage(messageFile.getNoPermission(), player);
            }


            else if(Bukkit.getPlayer(args[0]) != null) {
                if(!Bukkit.getPlayer(args[0]).getName().equalsIgnoreCase(player.getName())) {
                    // /easyduels player
                    if (!request.hasRequest(player)) {

                        Player target = Bukkit.getPlayer(args[0]);
                        request.addRequest(player, target);

                        sender.sendMessage(messageFile.getDuelRequest().replaceAll("%player%", player.getName()), target);
                        sender.sendHover(messageFile.getAcceptButton(), messageFile.getDenyButton(),
                                messageFile.getAcceptHover(), messageFile.getDenyHover(), player.getName(), target);
                        sender.sendMessage(messageFile.getRequestSent().replaceAll("%player%", target.getName()), player);
                    } else {
                        sender.sendMessage(messageFile.getAlreadyARequest(), player);
                    }
                } else
                    sender.sendMessage(messageFile.getDuelYourself(), player);
            } else {
                sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[0]), player);
            }
            return true;
        }

        else if(args.length == 2) {
            if (args[0].equalsIgnoreCase("admin")) {
                if (player.hasPermission("easyduels.admin")) {

                    if (args[1].equalsIgnoreCase("spawn1")) {
                        arenaFile.setFirstLocation(player.getLocation());
                        arenaFile.reload();
                        sender.sendMessage(messageFile.getSetFirstSpawn(), player);
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("spawn2")) {
                        arenaFile.setSecondLocation(player.getLocation());
                        arenaFile.reload();
                        sender.sendMessage(messageFile.getSetSecondSpawn(), player);
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("help")) {
                        sender.sendMessage(messageFile.getAdminHelpMessages(), player);
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("reload")) {
                        sender.sendMessage(messageFile.getReloaded(), player);
                        arenaFile.reload();
                        messageFile.reload();
                        settingsFile.reload();
                        arena.resetArena();
                        return true;
                    } else
                        return false;
                } else {
                    sender.sendMessage(messageFile.getNoPermission(), player);
                    return true;
                }
            }
            else if(args[0].equalsIgnoreCase("accept")) {

                if(Bukkit.getPlayer(args[1]) != null) {

                    Player target = Bukkit.getPlayer(args[1]);

                    if(request.hasRequest(target) && request.hasRequestFrom(target, player)){

                        sender.sendMessage(messageFile.getRequestAccept().replaceAll("%player%", player.getName()), target);
                        sender.sendMessage(messageFile.getYouRequestAccept().replaceAll("%player%", target.getName()), player);
                        request.deleteRequest(target);

                        if(!arena.isStatut(ArenaStatuts.IDLE))
                            sender.sendMessage(messageFile.getArenaNotEmpty(), player, target);
                        else{

                            arena.addToArena(target, player);
                            arena.setLastLocation(target, player);
                            arena.teleportToLocation(target, player, settingsFile.getSyncTimer());
                            arena.startMatch();

                            return true;
                        }

                    } else{
                        sender.sendMessage(messageFile.getNoRequest().replaceAll("%player%", target.getName()), player);
                        return true;
                    }
                } else {
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[1]), player);
                    return true;
                }

            } else if (args[0].equalsIgnoreCase("deny")) {

                if(Bukkit.getPlayer(args[1]) != null) {

                    Player target = Bukkit.getPlayer(args[1]);

                    if(request.hasRequest(target) && request.hasRequestFrom(target, player)){

                        request.deleteRequest(target);
                        sender.sendMessage(messageFile.getRequestDeny().replaceAll("%player%", player.getName()), target);
                        sender.sendMessage(messageFile.getYouRequestDeny().replaceAll("%player%", target.getName()), player);

                    }else{
                        sender.sendMessage(messageFile.getNoRequest().replaceAll("%player%", target.getName()), player);
                    }
                } else {
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[1]), player);
                }
                return true;

            } else
                return false;
        } else
            return false;
    return false;
    }
}
