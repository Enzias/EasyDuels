package fr.enzias.easyduels.commands.SubCommands.AdminCommand.SubCommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.entity.Player;

public class SpectateCommand extends SubCommand {

    ArenaFile arenaFile;
    MessageFile messageFile;
    SenderManager sender;
    public SpectateCommand(EasyDuels plugin) {
        super(plugin);
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.sender = plugin.getSender();
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length == 2) {

            arenaFile.setSpectateLocation(player.getLocation());
            arenaFile.reload();
            sender.sendMessage(messageFile.getSetSpectate(), player);
        } else
            sender.sendMessage(messageFile.getAdminUnknown(), player);
    }

    @Override
    public String getName() {
        return "spectate";
    }
}