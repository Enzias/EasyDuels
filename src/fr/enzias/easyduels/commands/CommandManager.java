package fr.enzias.easyduels.commands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.commands.SubCommands.AcceptCommand.AcceptCommand;
import fr.enzias.easyduels.commands.SubCommands.AdminCommand.AdminCommand;
import fr.enzias.easyduels.commands.SubCommands.DenyCommand.DenyCommand;
import fr.enzias.easyduels.commands.SubCommands.HelpCommand;
import fr.enzias.easyduels.commands.SubCommands.QueueCommand.QueueCommand;
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

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<>();
    private final EasyDuels plugin;
    Arena arena;
    ArenaFile arenaFile;
    MessageFile messageFile;
    SettingsFile settingsFile;
    RequestManager request;
    SenderManager sender;

    public CommandManager(EasyDuels plugin) {
        this.plugin = plugin;
        this.arena = plugin.getArena();
        this.request = plugin.getRequest();
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.settingsFile = plugin.getSettingsFile();
        this.sender = plugin.getSender();
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

            if(args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    if (!Bukkit.getPlayer(args[0]).getName().equalsIgnoreCase(player.getName())) {

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
        this.commands.add(new AdminCommand(plugin));
    }

    public ArrayList<SubCommand> getSubCommands(){
        return commands;
    }
}
