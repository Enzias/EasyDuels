package fr.enzias.easyduels.commands.subcommands.admincommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class SpectateCommand extends SubCommand {

    public SpectateCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(player.hasPermission("easyduels.admin.set")) {
            if (args.length == 2) {

                arenaFile.setSpectateLocation(player.getLocation());
                arenaFile.reload();
                sender.sendMessage(messageFile.getSetSpectate(), player);

            } else
                sender.sendMessage(messageFile.getAdminUnknown(), player);

        } else
                sender.sendMessage(messageFile.getNoPermission(), player);

    }

    @Override
    public String getName() {
        return "spectate";
    }
}
