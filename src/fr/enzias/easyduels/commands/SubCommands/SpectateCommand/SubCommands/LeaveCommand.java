package fr.enzias.easyduels.commands.subcommands.spectatecommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand {

    public LeaveCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (player.hasPermission("easyduels.spectate")) {
            if (spectate.isSpectating(player))
                spectate.finishSpectating(player);
            else
                sender.sendMessage(messageFile.getNotSpectating(), player);
        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "leave";
    }
}
