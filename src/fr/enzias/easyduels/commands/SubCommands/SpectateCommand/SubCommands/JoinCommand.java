package fr.enzias.easyduels.commands.SubCommands.SpectateCommand.SubCommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.arena.Spectate;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {

    Arena arena;
    Spectate spectate;
    ArenaFile arenaFile;
    MessageFile messageFile;
    SenderManager sender;
    public JoinCommand(EasyDuels plugin) {
        super(plugin);
        this.arena = plugin.getArena();
        this.spectate = plugin.getSpectate();
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.sender = plugin.getSender();
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(player.hasPermission("easyduels.spectate")) {
            if (!spectate.isSpectating(player)) {
                if (!arena.isStatut(ArenaStatuts.IDLE)) {
                    if (player.getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName())
                            && arena.getPlayers().contains(player)) {
                        sender.sendMessage(messageFile.getSpectateInDuel(), player);
                        return;
                    }
                    spectate.setSpectating(player);
                }else
                    sender.sendMessage(messageFile.getNoDuel(), player);
            } else
                sender.sendMessage(messageFile.getAlreadySpectating(), player);
        } else
            sender.sendMessage(messageFile.getNoPermission(), player);
    }

    @Override
    public String getName() {
        return "join";
    }
}
