package fr.enzias.easyduels.files;

import fr.enzias.easyduels.EasyDuels;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MessageFile {

    private final EasyDuels plugin;
    private File messageFile;
    private FileConfiguration messageConfig;
    public MessageFile(EasyDuels plugin) {
        this.plugin = plugin;
    }

    public void setup(){
        messageFile = new File(plugin.getDataFolder(), "messages.yml");

        if(!messageFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        messageConfig = new YamlConfiguration();
        try {
            messageConfig.load(messageFile);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public void save(){
        try{
            messageConfig.save(messageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        save();
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
    }

    public FileConfiguration getConfig(){
        return messageConfig;
    }

    //Messages

    public String getNoPermission(){
        return getConfig().getString("messages.player.no-permission");
    }

    public String getUnknown(){
        return getConfig().getString("messages.player.unknown-command");
    }

    public List<String> getHelpMessages(){
        return getConfig().getStringList("messages.player.help-message");
    }

    public String getOfflinePlayer(){
        return getConfig().getString("messages.player.offline-player");
    }

    public String getDuelYourself(){
        return getConfig().getString("messages.player.duel-yourself");
    }

    public String getNoRequest(){
        return getConfig().getString("messages.player.no-duel-request");
    }

    public String getAlreadyARequest(){
        return getConfig().getString("messages.player.already-a-request");
    }

    public String getRequestAccept(){
        return getConfig().getString("messages.player.request-accept-1");
    }

    public String getYouRequestAccept(){
        return getConfig().getString("messages.player.request-accept-2");
    }

    public String getRequestDeny(){
        return getConfig().getString("messages.player.request-deny-1");
    }

    public String getYouRequestDeny(){
        return getConfig().getString("messages.player.request-deny-2");
    }

    public String getArenaNotEmpty(){
        return getConfig().getString("messages.player.arena-not-empty");
    }

    public String getRequestSent(){
        return getConfig().getString("messages.player.request-sent");
    }

    public String getDuelRequest(){
        return getConfig().getString("messages.player.duel-request.message");
    }

    public String getAcceptButton(){
        return getConfig().getString("messages.player.duel-request.accept.button");
    }

    public String getAcceptHover(){
        return getConfig().getString("messages.player.duel-request.accept.hover");
    }

    public String getDenyButton(){
        return getConfig().getString("messages.player.duel-request.deny.button");
    }

    public String getDenyHover(){
        return getConfig().getString("messages.player.duel-request.deny.hover");
    }

    public String getExpiredRequest(){
        return getConfig().getString("messages.player.expired-request");
    }

    public String getNoCommand(){
        return getConfig().getString("messages.player.no-command");
    }

    public String getNoWinner(){
        return getConfig().getString("messages.player.no-winner");
    }

    public String getWinner(){
        return getConfig().getString("messages.player.duel-win");
    }

    public String getLoser(){
        return getConfig().getString("messages.player.duel-lost");
    }

    public String getAlreadyInQueue(){
        return getConfig().getString("messages.player.already-in-queue");
    }

    public String getNotInQueue(){
        return getConfig().getString("messages.player.not-in-queue");
    }

    public String getJoinQueueInDuel(){
        return getConfig().getString("messages.player.join-queue-in-duel");
    }

    public String getRequestInDuel(){
        return getConfig().getString("messages.player.request-duel-in-duel");
    }

    public String getAcceptRequestInDuel(){
        return getConfig().getString("messages.player.accept-duel-in-duel");
    }

    public String getQueueIsFull(){
        return getConfig().getString("messages.player.queue-is-full");
    }

    public String getArenaIsLocked(){
        return getConfig().getString("messages.player.arena-is-locked");
    }

    //Admin

    public String getAdminUnknown(){
        return getConfig().getString("messages.admin.unknown-command");
    }

    public List<String> getAdminHelpMessages(){
        return getConfig().getStringList("messages.admin.help-message");
    }

    public String getArenaLocked(){
        return getConfig().getString("messages.admin.arena-locked");
    }

    public String getArenaUnlocked(){
        return getConfig().getString("messages.admin.arena-unlocked");
    }

    public String getAdminJoinQueueInDuel(){
        return getConfig().getString("messages.admin.join-in-duel");
    }

    public String getDuelHimSelf(){
        return getConfig().getString("messages.admin.duel-himself");
    }

    public String getAdminAlreadyInQueue(){
        return getConfig().getString("messages.admin.already-in-queue");
    }

    public String getForcedDuelStart(){
        return getConfig().getString("messages.admin.forced-duel-start");
    }

    public String getForcedDuelQueue(){
        return getConfig().getString("messages.admin.forced-duel-queue");
    }

    public String getForcedQueue(){
        return getConfig().getString("messages.admin.forced-queue");
    }

    public String getSetFirstSpawn(){
        return getConfig().getString("messages.admin.set-spawn-1");
    }

    public String getSetSecondSpawn(){
        return getConfig().getString("messages.admin.set-spawn-2");
    }

    public String getReloaded(){
        return getConfig().getString("messages.admin.reloaded");
    }

}
