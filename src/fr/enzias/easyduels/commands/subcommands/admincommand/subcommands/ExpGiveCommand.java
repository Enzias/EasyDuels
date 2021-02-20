package fr.enzias.easyduels.commands.subcommands.admincommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ExpGiveCommand extends SubCommand {

    public ExpGiveCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(player.hasPermission("easyduels.admin.rank")) {
            if (args.length == 4) {

                if (Bukkit.getPlayer(args[2]) != null) {

                    Player target = Bukkit.getPlayer(args[2]);

                    if (levelManager.isValidAmount(args[3])) {
                        levelManager.giveExpForce(target, levelManager.getValidAmount(args[3]));
                        levelManager.saveExpData(target);
                        sender.sendMessage(messageFile.getGiveExp()
                                .replaceAll("%amount%", args[3])
                                .replaceAll("%player%", target.getName()), player);
                        sender.sendMessage(messageFile.getExpNowPlayer()
                                .replaceAll("%amount%", String.valueOf(levelManager.getExpFromManager(target)))
                                .replaceAll("%player%", target.getName()), player);
                        sender.sendMessage(messageFile.getExpNow()
                                .replaceAll("%experience%", String.valueOf(levelManager.getExpFromManager(target)))
                                .replaceAll("%level%", String.valueOf(levelManager.getLevel(target))), target);
                    } else
                        sender.sendMessage(messageFile.getInvalidAmountExp(), player);

                } else
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[2]), player);

            } else
                sender.sendMessage(messageFile.getAdminUnknown(), player);
        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "give";
    }
}
