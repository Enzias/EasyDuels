package fr.enzias.easyduels.commands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.Spectate;
import fr.enzias.easyduels.filemanager.files.ArenaFile;
import fr.enzias.easyduels.filemanager.files.MessageFile;
import fr.enzias.easyduels.filemanager.files.SettingsFile;
import fr.enzias.easyduels.managers.RequestManager;
import fr.enzias.easyduels.managers.SenderManager;
import fr.enzias.easyduels.queue.QueueManager;
import fr.enzias.easyduels.utils.VaultHook;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    private final EasyDuels plugin;

    protected ArenaFile arenaFile;
    protected MessageFile messageFile;
    protected SettingsFile settingsFile;
    protected Arena arena;
    protected Spectate spectate;
    protected RequestManager request;
    protected QueueManager queue;
    protected SenderManager sender;
    protected VaultHook vaultHook;

    public SubCommand(EasyDuels plugin){
        this.plugin = plugin;
        this.arenaFile = plugin.getArenaFile();
        this.messageFile = plugin.getMessageFile();
        this.settingsFile = plugin.getSettingsFile();
        this.arena = plugin.getArena();
        this.spectate = plugin.getSpectate();
        this.request = plugin.getRequest();
        this.queue = plugin.getQueue();
        this.sender = plugin.getSender();
        this.vaultHook = plugin.getVaultHook();
    }

    public abstract void onCommand(Player player, String[] args);

    public abstract String getName();
}
