package fr.enzias.easyduels.commands.SubCommands.SpectateCommand;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.commands.SubCommands.SpectateCommand.SubCommands.JoinCommand;
import fr.enzias.easyduels.commands.SubCommands.SpectateCommand.SubCommands.LeaveCommand;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SpectateCommand extends SubCommand {

    private ArrayList<SubCommand> commands = new ArrayList<>();
    MessageFile messageFile;
    SenderManager sender;
    public SpectateCommand(EasyDuels plugin) {
        super(plugin);
        this.messageFile = plugin.getMessageFile();
        this.sender = plugin.getSender();
        this.commands.add(new JoinCommand(plugin));
        this.commands.add(new LeaveCommand(plugin));
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (args.length == 2) {
            for (int i = 0; i < getSubCommands().size(); i++)
                if (args[1].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    getSubCommands().get(i).onCommand(player, args);
                    return;
                }
        }
        sender.sendMessage(messageFile.getUnknown(), player);
    }

    @Override
    public String getName() {
        return "spectate";
    }

    public ArrayList<SubCommand> getSubCommands(){
        return commands;
    }
}
