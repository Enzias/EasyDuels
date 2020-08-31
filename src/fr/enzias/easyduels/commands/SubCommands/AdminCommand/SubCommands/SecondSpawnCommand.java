package fr.enzias.easyduels.commands.SubCommands.AdminCommand.SubCommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.entity.Player;

public class SecondSpawnCommand extends SubCommand {

    ArenaFile arenaFile;
    MessageFile messageFile;
    SenderManager sender;
    public SecondSpawnCommand(EasyDuels plugin) {
        super(plugin);
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.sender = plugin.getSender();
    }

    @Override
    public void onCommand(Player player, String[] args) {

        arenaFile.setSecondLocation(player.getLocation());
        arenaFile.reload();
        sender.sendMessage(messageFile.getSetSecondSpawn(), player);

    }

    @Override
    public String getName() {
        return "spawn2";
    }
}