package fr.enzias.easyduels.commands.subcommands.admincommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {

    public HelpCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length == 2) {
            sender.sendMessage(messageFile.getAdminHelpMessages(), player);
        } else
            sender.sendMessage(messageFile.getAdminUnknown(), player);
    }

    @Override
    public String getName() {
        return "help";
    }
}
