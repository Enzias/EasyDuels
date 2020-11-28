package fr.enzias.easyduels.commands.subcommands.denycommand;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DenyCommand extends SubCommand {

    public DenyCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (args.length == 2) {
            if (Bukkit.getPlayer(args[1]) != null) {

                Player target = Bukkit.getPlayer(args[1]);

                if (request.hasRequest(target) && request.hasRequestFrom(target, player)) {

                    request.deleteRequest(target);
                    sender.sendMessage(messageFile.getRequestDeny().replaceAll("%player%", player.getName()), target);
                    sender.sendMessage(messageFile.getYouRequestDeny().replaceAll("%player%", target.getName()), player);

                } else {
                    sender.sendMessage(messageFile.getNoRequest().replaceAll("%player%", target.getName()), player);
                }
            } else {
                sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[1]), player);
            }
        } else
            sender.sendMessage(messageFile.getUnknown(), player);
    }

    @Override
    public String getName() {
        return "deny";
    }
}
