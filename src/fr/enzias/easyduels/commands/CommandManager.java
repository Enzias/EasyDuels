package fr.enzias.easyduels.commands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.subcommands.acceptcommand.AcceptCommand;
import fr.enzias.easyduels.commands.subcommands.admincommand.AdminCommand;
import fr.enzias.easyduels.commands.subcommands.denycommand.DenyCommand;
import fr.enzias.easyduels.commands.subcommands.HelpCommand;
import fr.enzias.easyduels.commands.subcommands.queuecommand.QueueCommand;
import fr.enzias.easyduels.commands.subcommands.spectatecommand.SpectateCommand;
import fr.enzias.easyduels.filemanager.files.ArenaFile;
import fr.enzias.easyduels.filemanager.files.MessageFile;
import fr.enzias.easyduels.filemanager.files.SettingsFile;
import fr.enzias.easyduels.managers.RequestManager;
import fr.enzias.easyduels.managers.SenderManager;
import fr.enzias.easyduels.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<>();
    private final EasyDuels plugin;
    private Arena arena;
    private ArenaFile arenaFile;
    private MessageFile messageFile;
    private SettingsFile settingsFile;
    private RequestManager request;
    private SenderManager sender;
    private VaultHook vaultHook;

    public CommandManager(EasyDuels plugin) {
        this.plugin = plugin;
        this.arena = plugin.getArena();
        this.request = plugin.getRequest();
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.settingsFile = plugin.getSettingsFile();
        this.sender = plugin.getSender();
        this.vaultHook = plugin.getVaultHook();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to use this command.");
        } else {

            Player player = (Player) commandSender;

            if(args.length == 0) {
                sender.sendMessage(messageFile.getUnknown(), player);
                return true;
            }

            for (int i = 0; i < getSubCommands().size(); i++)
                if(args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    getSubCommands().get(i).onCommand(player, args);
                    return true;
            }

            if(args.length == 1 || args.length == 2) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    if (!Bukkit.getPlayer(args[0]).getName().equalsIgnoreCase(player.getName())) {

                        if (!request.hasRequest(player)) {

                            if(arena.isEnable()) {

                                if (!arena.isStatut(ArenaStatuts.IDLE) && arena.getPlayers().contains(player)) {
                                    sender.sendMessage(messageFile.getRequestInDuel(), player);
                                    return true;
                                }

                                Player target = Bukkit.getPlayer(args[0]);

                                if (target.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                                        && arena.getPlayers().contains(target) && !arena.isStatut(ArenaStatuts.IDLE)){
                                    sender.sendMessage(messageFile.getPlayerInDuel().replaceAll("%player%", target.getName()), player);
                                    return true;
                                }

                                if(args.length == 2) {
                                    if (vaultHook.isNotNull() && settingsFile.getMoneyBet()) {
                                        if(player.hasPermission("easyduels.money")) {
                                            if (vaultHook.isValidAmount(args[1])) {
                                                int amount = vaultHook.getValidAmount(args[1]);

                                                if (vaultHook.isAbove(amount)) {

                                                    if (vaultHook.isUnder(amount)) {

                                                        if (vaultHook.hasEnough(amount, player)) {

                                                            if (vaultHook.hasEnough(amount, target)) {

                                                                request.addRequest(player, target, amount);

                                                                sender.sendMessage(messageFile.getDuelRequest().replaceAll("%player%", player.getName()), target);
                                                                sender.sendMessage(messageFile.getDuelBetRequest().replaceAll("%amount%", Integer.toString(amount)), target);
                                                                sender.sendHover(messageFile.getAcceptButton(), messageFile.getDenyButton(),
                                                                        messageFile.getAcceptHover(), messageFile.getDenyHover(),
                                                                        messageFile.getBeforeAccept(), player.getName(), target);
                                                                sender.sendMessage(messageFile.getRequestSent().replaceAll("%player%", target.getName()), player);

                                                            } else {
                                                                sender.sendMessage(messageFile.getPlayerNotEnoughMoney()
                                                                        .replaceAll("%player%", target.getName()), player);
                                                            }
                                                        } else
                                                            sender.sendMessage(messageFile.getYouNotEnoughMoney(), player);
                                                    } else
                                                        sender.sendMessage(messageFile.getGreaterMaximum()
                                                                .replaceAll("%amount%", Integer.toString(settingsFile.getMaxAmount())), player);
                                                } else
                                                    sender.sendMessage(messageFile.getBelowMinimum()
                                                            .replaceAll("%amount%", Integer.toString(settingsFile.getMinAmount())), player);
                                            } else
                                                sender.sendMessage(messageFile.getInvalidAmount(), player);
                                        } else
                                            sender.sendMessage(messageFile.getNoPermission(), player);
                                    } else
                                        sender.sendMessage(messageFile.getUnknown(), player);
                                    return true;
                                }


                                request.addRequest(player, target);

                                sender.sendMessage(messageFile.getDuelRequest().replaceAll("%player%", player.getName()), target);
                                sender.sendHover(messageFile.getAcceptButton(), messageFile.getDenyButton(),
                                        messageFile.getAcceptHover(), messageFile.getDenyHover(),
                                        messageFile.getBeforeAccept(), player.getName(), target);
                                sender.sendMessage(messageFile.getRequestSent().replaceAll("%player%", target.getName()), player);
                            } else {
                                sender.sendMessage(messageFile.getArenaIsLocked(), player);
                            }
                        } else {
                            sender.sendMessage(messageFile.getAlreadyARequest(), player);
                        }
                    } else
                        sender.sendMessage(messageFile.getDuelYourself(), player);
                } else {
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[0]), player);
                }
            } else
                sender.sendMessage(messageFile.getUnknown(), player);

        } return true;
    }

    public void setup(){
        plugin.getCommand("easyduels").setExecutor(this);

        this.commands.add(new HelpCommand(plugin));
        this.commands.add(new AcceptCommand(plugin));
        this.commands.add(new DenyCommand(plugin));
        if(settingsFile.getQueue())
            this.commands.add(new QueueCommand(plugin));
        if(settingsFile.getSpectate())
            this.commands.add(new SpectateCommand(plugin));
        this.commands.add(new AdminCommand(plugin));
    }

    public ArrayList<SubCommand> getSubCommands(){
        return commands;
    }
}
