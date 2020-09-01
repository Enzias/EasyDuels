package fr.enzias.easyduels.commands.SubCommands.AdminCommand.SubCommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {

    Arena arena;
    ArenaFile arenaFile;
    MessageFile messageFile;
    SettingsFile settingsFile;
    SenderManager sender;
    public ReloadCommand(EasyDuels plugin) {
        super(plugin);
        this.arena = plugin.getArena();
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.settingsFile = plugin.getSettingsFile();
        this.sender = plugin.getSender();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 2) {
            sender.sendMessage(messageFile.getReloaded(), player);
            arenaFile.reload();
            messageFile.reload();
            settingsFile.reload();
            arena.resetArena();
        }else
            sender.sendMessage(messageFile.getAdminUnknown(), player);
    }

    @Override
    public String getName() {
        return "reload";
    }
}
