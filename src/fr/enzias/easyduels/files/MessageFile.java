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

    //Admin

    public List<String> getAdminHelpMessages(){
        return getConfig().getStringList("messages.admin.help-message");
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