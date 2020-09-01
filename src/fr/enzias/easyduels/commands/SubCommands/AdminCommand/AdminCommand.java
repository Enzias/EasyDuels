package fr.enzias.easyduels.commands.SubCommands.AdminCommand;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.commands.SubCommands.AdminCommand.SubCommands.*;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AdminCommand extends SubCommand {

    private ArrayList<SubCommand> commands = new ArrayList<>();
    MessageFile messageFile;
    SenderManager sender;
    public AdminCommand(EasyDuels plugin) {
        super(plugin);
        this.messageFile = plugin.getMessageFile();
        this.sender = plugin.getSender();
        this.commands.add(new FirstSpawnCommand(plugin));
        this.commands.add(new SecondSpawnCommand(plugin));
        this.commands.add(new HelpCommand(plugin));
        this.commands.add(new ReloadCommand(plugin));
        this.commands.add(new LockCommand(plugin));
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (player.hasPermission("easyduels.admin")) {

            if(args.length == 2) {
                for (int i = 0; i < getSubCommands().size(); i++)
                    if (args[1].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        getSubCommands().get(i).onCommand(player, args);
                        return;
                    }
            }
            sender.sendMessage(messageFile.getAdminUnknown(), player);

        } else {
            sender.sendMessage(messageFile.getNoPermission(), player);
        }
    }

    @Override
    public String getName() {
        return "admin";
    }

    public ArrayList<SubCommand> getSubCommands(){
        return commands;
    }
}
