package fr.enzias.easyduels.commands.SubCommands.SpectateCommand.SubCommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.Spectate;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand {

    Arena arena;
    Spectate spectate;
    ArenaFile arenaFile;
    MessageFile messageFile;
    SenderManager sender;
    public LeaveCommand(EasyDuels plugin) {
        super(plugin);
        this.arena = plugin.getArena();
        this.spectate = plugin.getSpectate();
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.sender = plugin.getSender();
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (player.hasPermission("easyduels.spectate")) {
            if (spectate.isSpectating(player))
                spectate.finishSpectating(player);
            else
                sender.sendMessage(messageFile.getNotSpectating(), player);
        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "leave";
    }
}
