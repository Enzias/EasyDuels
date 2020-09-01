package fr.enzias.easyduels.commands.SubCommands.AdminCommand.SubCommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.managers.SenderManager;
import fr.enzias.easyduels.queue.QueueManager;
import org.bukkit.entity.Player;

public class LockCommand extends SubCommand {

    Arena arena;
    MessageFile messageFile;
    SenderManager sender;
    QueueManager queue;
    public LockCommand(EasyDuels plugin) {
        super(plugin);
        this.arena = plugin.getArena();
        this.messageFile = plugin.getMessageFile();
        this.sender = plugin.getSender();
        this.queue = plugin.getQueue();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(arena.isEnable()) {
            arena.setEnable(false);
            queue.deleteAllQueue();
            sender.sendMessage(messageFile.getArenaLocked(), player);
        }else {
            arena.setEnable(true);
            sender.sendMessage(messageFile.getArenaUnlocked(), player);
        }
    }

    @Override
    public String getName() {
        return "lock";
    }
}
