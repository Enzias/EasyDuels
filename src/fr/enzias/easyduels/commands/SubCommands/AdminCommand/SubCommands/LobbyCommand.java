package fr.enzias.easyduels.commands.subcommands.admincommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class LobbyCommand extends SubCommand {

    public LobbyCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length == 2) {

            arenaFile.setLobbyLocation(player.getLocation());
            arenaFile.reload();
            sender.sendMessage(messageFile.getSetLobby(), player);
        } else
            sender.sendMessage(messageFile.getAdminUnknown(), player);
    }

    @Override
    public String getName() {
        return "lobby";
    }
}
