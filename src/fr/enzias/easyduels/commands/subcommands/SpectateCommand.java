package fr.enzias.easyduels.commands.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class SpectateCommand extends SubCommand {

    public SpectateCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (args.length == 1) {
            if(player.hasPermission("easyduels.spectate")) {
                if (spectate.isSpectating(player)) {
                    spectate.finishSpectating(player);
                } else {
                    if (!arena.isStatut(ArenaStatuts.IDLE)) {
                        if (player.getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                                && arena.getPlayers().contains(player)) {
                            sender.sendMessage(messageFile.getSpectateInDuel(), player);
                            return;
                        }
                        spectate.setSpectating(player);
                    } else
                        sender.sendMessage(messageFile.getNoDuel(), player);
                }
            }else
                sender.sendMessage(messageFile.getNoPermission(), player);
        }
        sender.sendMessage(messageFile.getUnknown(), player);
    }

    @Override
    public String getName() {
        return "spectate";
    }
}
