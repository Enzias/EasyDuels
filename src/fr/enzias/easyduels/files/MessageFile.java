package fr.enzias.easyduels.files;

import fr.enzias.easyduels.EasyDuels;

import java.util.List;

public class MessageFile {

    private final EasyDuels plugin;
    public MessageFile(EasyDuels plugin) {
        this.plugin = plugin;
    }

    //Messages

    public String getNoPermission(){
        return plugin.getConfig().getString("messages.player.no-permission");
    }

    public List<String> getHelpMessages(){
        return plugin.getConfig().getStringList("messages.player.help-message");
    }

    public String getOfflinePlayer(){
        return plugin.getConfig().getString("messages.player.offline-player");
    }

    public String getDuelYourself(){
        return plugin.getConfig().getString("messages.player.duel-yourself");
    }

    public String getNoRequest(){
        return plugin.getConfig().getString("messages.player.no-duel-request");
    }

    public String getAlreadyARequest(){
        return plugin.getConfig().getString("messages.player.already-a-request");
    }

    public String getRequestAccept(){
        return plugin.getConfig().getString("messages.player.request-accept-1");
    }

    public String getYouRequestAccept(){
        return plugin.getConfig().getString("messages.player.request-accept-2");
    }

    public String getRequestDeny(){
        return plugin.getConfig().getString("messages.player.request-deny-1");
    }

    public String getYouRequestDeny(){
        return plugin.getConfig().getString("messages.player.request-deny-2");
    }

    public String getArenaNotEmpty(){
        return plugin.getConfig().getString("messages.player.arena-not-empty");
    }

    public String getRequestSent(){
        return plugin.getConfig().getString("messages.player.request-sent");
    }

    public String getDuelRequest(){
        return plugin.getConfig().getString("messages.player.duel-request.message");
    }

    public String getAcceptButton(){
        return plugin.getConfig().getString("messages.player.duel-request.accept.button");
    }

    public String getAcceptHover(){
        return plugin.getConfig().getString("messages.player.duel-request.accept.hover");
    }

    public String getDenyButton(){
        return plugin.getConfig().getString("messages.player.duel-request.deny.button");
    }

    public String getDenyHover(){
        return plugin.getConfig().getString("messages.player.duel-request.deny.hover");
    }

    public String getExpiredRequest(){
        return plugin.getConfig().getString("messages.player.expired-request");
    }

    public String getNoCommand(){
        return plugin.getConfig().getString("messages.player.no-command");
    }

    public String getNoWinner(){
        return plugin.getConfig().getString("messages.player.no-winner");
    }

    public String getWinner(){
        return plugin.getConfig().getString("messages.player.duel-win");
    }

    public String getLoser(){
        return plugin.getConfig().getString("messages.player.duel-lost");
    }

    //Admin

    public List<String> getAdminHelpMessages(){
        return plugin.getConfig().getStringList("messages.admin.help-message");
    }

    public String getSetFirstSpawn(){
        return plugin.getConfig().getString("messages.admin.set-spawn-1");
    }

    public String getSetSecondSpawn(){
        return plugin.getConfig().getString("messages.admin.set-spawn-2");
    }

    public String getReloaded(){
        return plugin.getConfig().getString("messages.admin.reloaded");
    }

}
