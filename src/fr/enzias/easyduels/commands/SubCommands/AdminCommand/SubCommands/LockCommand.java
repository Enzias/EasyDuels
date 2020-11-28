package fr.enzias.easyduels.commands.subcommands.admincommand.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.entity.Player;

public class LockCommand extends SubCommand {

    public LockCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length == 2) {
            if (arena.isEnable()) {
                arena.setEnable(false);
                queue.deleteAllQueue();
                sender.sendMessage(messageFile.getArenaLocked(), player);
            } else {
                arena.setEnable(true);
                sender.sendMessage(messageFile.getArenaUnlocked(), player);
            }
        }else
            sender.sendMessage(messageFile.getAdminUnknown(), player);
    }

    @Override
    public String getName() {
        return "lock";
    }
}
