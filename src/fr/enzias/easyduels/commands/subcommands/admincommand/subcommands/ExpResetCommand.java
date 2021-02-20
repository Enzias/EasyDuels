package fr.enzias.easyduels.commands.subcommands.admincommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ExpResetCommand extends SubCommand {

    public ExpResetCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(player.hasPermission("easyduels.admin.rank")) {
            if (args.length == 3) {

                if (Bukkit.getPlayer(args[2]) != null) {

                    Player target = Bukkit.getPlayer(args[2]);

                    levelManager.resetExp(target);
                    levelManager.saveExpData(target);
                    sender.sendMessage(messageFile.getResetExp()
                            .replaceAll("%player%", target.getName()), player);
                    sender.sendMessage(messageFile.getExpNow()
                            .replaceAll("%experience%", String.valueOf(levelManager.getExpFromManager(target)))
                            .replaceAll("%level%", String.valueOf(levelManager.getLevel(target))), target);

                } else
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[2]), player);

            } else
                sender.sendMessage(messageFile.getAdminUnknown(), player);
        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "reset";
    }
}
