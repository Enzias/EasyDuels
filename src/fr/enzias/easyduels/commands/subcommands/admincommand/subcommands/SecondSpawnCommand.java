package fr.enzias.easyduels.commands.subcommands.admincommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class SecondSpawnCommand extends SubCommand {

    public SecondSpawnCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(player.hasPermission("easyduels.admin.set")) {
            if (args.length == 2) {

                arenaFile.setSecondLocation(player.getLocation());
                arenaFile.reload();
                sender.sendMessage(messageFile.getSetSecondSpawn(), player);

            } else
                sender.sendMessage(messageFile.getAdminUnknown(), player);

        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "spawn2";
    }
}
