package fr.enzias.easyduels.commands.subcommands.spectatecommand;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.commands.subcommands.spectatecommand.subcommands.JoinCommand;
import fr.enzias.easyduels.commands.subcommands.spectatecommand.subcommands.LeaveCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SpectateCommand extends SubCommand {

    private ArrayList<SubCommand> commands = new ArrayList<>();

    public SpectateCommand(EasyDuels plugin) {
        super(plugin);
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
