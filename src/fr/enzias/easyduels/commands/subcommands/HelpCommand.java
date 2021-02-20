package fr.enzias.easyduels.commands.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {

    public HelpCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length == 1) {
            if (player.hasPermission("easyduels.help")) {
                sender.sendMessage(messageFile.getHelpMessages(), player);
            } else
                sender.sendMessage(messageFile.getNoPermission(), player);
        } else
            sender.sendMessage(messageFile.getUnknown(), player);
    }

    @Override
    public String getName() {
        return "help";
    }
}
