package fr.enzias.easyduels.tasks;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.managers.RequestManager;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.entity.Player;

public class Cooldown implements Runnable {

    private final EasyDuels plugin;
    private int cooldown;
    SettingsFile settings;
    SenderManager sender;
    MessageFile message;
    RequestManager request;
    Player player;

    public Cooldown(EasyDuels plugin, Player player) {
        this.plugin = plugin;
        settings = plugin.getSettingsFile();
        sender = plugin.getSender();
        message = plugin.getMessageFile();
        cooldown = settings.getExpirationRequest();
        request = plugin.getRequest();
        this.player = player;
    }

    @Override
    public void run() {

        cooldown--;

        if(cooldown == 0){

            sender.sendMessage(message.getExpiredRequest(), player);
            request.deleteRequest(player);
        }

    }

}
