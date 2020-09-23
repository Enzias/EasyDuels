package fr.enzias.easyduels;

import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.Spectate;
import fr.enzias.easyduels.commands.CommandManager;
import fr.enzias.easyduels.files.ArenaFile;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.listeners.*;
import fr.enzias.easyduels.listeners.damager.*;
import fr.enzias.easyduels.managers.RequestManager;
import fr.enzias.easyduels.managers.SenderManager;
import fr.enzias.easyduels.managers.versions.*;
import fr.enzias.easyduels.queue.QueueManager;
import fr.enzias.easyduels.utils.UpdateChecker;
import fr.enzias.easyduels.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class EasyDuels extends JavaPlugin {

    Arena arena;
    Spectate spectate;
    RequestManager request;
    QueueManager queue;
    ArenaFile arenaFile;
    MessageFile messageFile;
    SettingsFile settingsFile;
    SenderManager manager;
    VaultHook vaultHook;
    public boolean upToDate;

    @Override
    public void onEnable() {

        if(!setupSender()) {
            getLogger().warning("[EasyDuels] Failed to load EasyDuels! Unsupported version.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        setupConfig();
        setupObject();
        if(!vaultHook.setupEconomy()){
            Bukkit.getLogger().warning("[EasyDuels] Money bet is not available.");
        } else Bukkit.getLogger().info("[EasyDuels] Money bet is available.");
        checkForUpdates(settingsFile.getCheckForUpdates());
        registerEvents();
        new CommandManager(this).setup();

        Bukkit.getServer().getLogger().info("[EasyDuels] Plugin successfully enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getLogger().info("[EasyDuels] Plugin successfully disabled.");
    }

    private void setupObject(){
        arena = new Arena(this, arenaFile.getFirstLocation(), arenaFile.getSecondLocation(), arenaFile.getLobbyLocation(),
                settingsFile.getLobbyTime(), settingsFile.getFightTime(), settingsFile.getEndTime());
        spectate = new Spectate(this, arenaFile.getSpectateLocation());
        vaultHook = new VaultHook(this);
        request = new RequestManager(this);
        queue = new QueueManager(this);
    }

    private void setupConfig(){
        arenaFile = new ArenaFile(this);
        messageFile = new MessageFile(this);
        settingsFile = new SettingsFile(this);
        arenaFile.setup();
        messageFile.setup();
        settingsFile.setup();
    }

    private boolean setupSender(){
        String serverVersion;

        try{
            serverVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e){
            return false;
        }

        switch (serverVersion){
            case "v1_12_R1":
                manager = new SenderManager_1_12_R1(this);
                break;
            case "v1_13_R1":
                manager = new SenderManager_1_13_R1(this);
                break;
            case "v1_13_R2":
                manager = new SenderManager_1_13_R2(this);
                break;
            case "v1_14_R1":
                manager = new SenderManager_1_14_R1(this);
                break;
            case "v1_15_R1":
                manager = new SenderManager_1_15_R1(this);
                break;
            case "v1_16_R1":
                manager = new SenderManager_1_16_R1(this);
                break;
            case "v1_16_R2":
                manager = new SenderManager_1_16_R2(this);
                break;
            default:
                return false;
        }
        Bukkit.getLogger().info("[EasyDuels] Loading plugin for " + serverVersion + ".");
        return true;
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new CommandEvent(this), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(this), this);
        getServer().getPluginManager().registerEvents(new TeleportEvent(this), this);
        getServer().getPluginManager().registerEvents(new ArrowHitEvent(this), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(this), this);
        getServer().getPluginManager().registerEvents(new PotionHitEvent(this), this);
        getServer().getPluginManager().registerEvents(new SnowBallHitEvent(this), this);
        if(!getServer().getVersion().contains("1.12"))
            getServer().getPluginManager().registerEvents(new TridentHitEvent(this), this);
    }

    public void checkForUpdates(boolean check){
        if(check){
            new UpdateChecker(this, 83031).getVersion(version -> {
                if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
                    Bukkit.getLogger().info("[EasyDuels] Plugin is up to date.");
                    upToDate = true;
                }else{
                    Bukkit.getLogger().warning("=====================================");
                    Bukkit.getLogger().warning("[EasyDuels] Plugin is outdated!");
                    Bukkit.getLogger().warning("[EasyDuels] Latest version: " + version);
                    Bukkit.getLogger().warning("[EasyDuels] Your version: " + getDescription().getVersion());
                    Bukkit.getLogger().warning("=====================================");
                    upToDate = false;
                }
            });
        }
    }

    public Arena getArena(){
        return arena;
    }

    public Spectate getSpectate() {
        return spectate;
    }

    public RequestManager getRequest() {
        return request;
    }

    public QueueManager getQueue() {
        return queue;
    }

    public ArenaFile getArenaFile() {
        return arenaFile;
    }

    public MessageFile getMessageFile() {
        return messageFile;
    }

    public SettingsFile getSettingsFile() {
        return settingsFile;
    }

    public SenderManager getSender() {
        return manager;
    }

    public VaultHook getVaultHook(){
        return vaultHook;
    }
}
