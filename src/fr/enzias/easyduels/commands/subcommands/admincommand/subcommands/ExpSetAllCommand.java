package fr.enzias.easyduels.commands.subcommands.admincommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class ExpSetAllCommand extends SubCommand {

    public ExpSetAllCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(player.hasPermission("easyduels.admin.rankall")) {
            if (args.length == 3) {

                if (levelManager.isValidAmount(args[2])) {
                    levelManager.setAllExp(levelManager.getValidAmount(args[2]));
                    levelManager.saveAllExpData();
                    sender.sendMessage(messageFile.getSetAllExp()
                            .replaceAll("%amount%", args[2]), player);
                } else
                    sender.sendMessage(messageFile.getInvalidAmountExp(), player);

            } else
                sender.sendMessage(messageFile.getAdminUnknown(), player);
        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "setall";
    }
}
