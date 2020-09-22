package fr.enzias.easyduels.files;

import fr.enzias.easyduels.EasyDuels;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
        if(getConfig().contains("messages.player.no-permission"))
            return getConfig().getString("messages.player.no-permission");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou don't have permission to do that!";
    }

    public String getUnknown(){
        if(getConfig().contains("messages.player.unknown-command"))
            return getConfig().getString("messages.player.unknown-command");
        else return "&f[&6&lEasy&c&lDuels&f] &cUnknown command. Please use &6/easyduels help&c!";
    }

    public List<String> getHelpMessages(){
        if(getConfig().contains("messages.player.help-message"))
            return getConfig().getStringList("messages.player.help-message");
        else return Arrays.asList(
                "&6-&c-&6-&c-&6-&c-&6-&c-&6- &6&lEasy&c&lDuels &6-&c-&6-&c-&6-&c-&6-&c-&6-"
                ,"","&6⬥ &c/&6easyduels &chelp &6- &6Send you this message."
                ,"&6⬥ &c/&6easyduels &6<&cplayer&6> &6- &6Send a duel request to the player."
                ,"&6⬥ &c/&6easyduels &caccept &6<&cplayer&6> &6- &6Accept a duel request from a player."
                ,"&6⬥ &c/&6easyduels &cdeny &6<&cplayer&6> &6- &6Deny a duel request from a player."
                ,"&6⬥ &c/&6easyduels &cqueue join &6- &6Join the duel queue."
                ,"&6⬥ &c/&6easyduels &cqueue leave &6- &6Leave the duel queue."
                ,"","&6-&c-&6-&c-&6-&c-&6-&c-&6- &6&lEasy&c&lDuels &6-&c-&6-&c-&6-&c-&6-&c-&6-");
    }

    public String getOfflinePlayer(){
        if(getConfig().contains("messages.player.offline-player"))
            return getConfig().getString("messages.player.offline-player");
        else return "&f[&6&lEasy&c&lDuels&f] &cThe player &6%player% &cis not online or doesn't exist!";
    }

    public String getDuelYourself(){
        if(getConfig().contains("messages.player.duel-yourself"))
            return getConfig().getString("messages.player.duel-yourself");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou can't duel yourself!";
    }

    public String getNoRequest(){
        if(getConfig().contains("messages.player.no-duel-request"))
            return getConfig().getString("messages.player.no-duel-request");
        else return "&f[&6&lEasy&c&lDuels&f] &cThe player &6%player% &cdid not send you a duel request!";
    }

    public String getAlreadyARequest(){
        if(getConfig().contains("messages.player.already-a-request"))
            return getConfig().getString("messages.player.already-a-request");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou already have sent a duel request!";
    }

    public String getRequestAccept(){
        if(getConfig().contains("messages.player.request-accept-1"))
            return getConfig().getString("messages.player.request-accept-1");
        else return "&f[&6&lEasy&c&lDuels&f] &aThe player &2%player% &ahas accepted your duel request.";
    }

    public String getYouRequestAccept(){
        if(getConfig().contains("messages.player.request-accept-2"))
            return getConfig().getString("messages.player.request-accept-2");
        else return "&f[&6&lEasy&c&lDuels&f] &aYou have accepted the duel request of &2%player%&a.";
    }

    public String getRequestDeny(){
        if(getConfig().contains("messages.player.request-deny-1"))
            return getConfig().getString("messages.player.request-deny-1");
        else return "&f[&6&lEasy&c&lDuels&f] &cThe player &6%player% &chas refused your duel request!";
    }

    public String getYouRequestDeny(){
        if(getConfig().contains("messages.player.request-deny-2"))
            return getConfig().getString("messages.player.request-deny-2");
        else return "&f[&6&lEasy&c&lDuels&f] &aYou refused the duel request of &2%player%&a.";
    }

    public String getArenaNotEmpty(){
        if(getConfig().contains("messages.player.arena-not-empty"))
            return getConfig().getString("messages.player.arena-not-empty");
        else return "&f[&6&lEasy&c&lDuels&f] &cThe arena is not available at the moment!";
    }

    public String getRequestSent(){
        if(getConfig().contains("messages.player.request-sent"))
            return getConfig().getString("messages.player.request-sent");
        else return "&f[&6&lEasy&c&lDuels&f] &aYou sent a request to &2%player%&a.";
    }

    public String getDuelRequest(){
        if(getConfig().contains("messages.player.duel-request.message"))
            return getConfig().getString("messages.player.duel-request.message");
        else return "&f[&6&lEasy&c&lDuels&f] &aYou received a duel request from &2%player%&a.";
    }

    public String getDuelBetRequest(){
        if(getConfig().contains("messages.player.duel-request.bet-message"))
            return getConfig().getString("messages.player.duel-request.bet-message");
        else return "&f[&6&lEasy&c&lDuels&f] &6By accepting this duel request, you will put &c%amount% &6coins at stake.";
    }

    public String getAcceptButton(){
        if(getConfig().contains("messages.player.duel-request.accept.button"))
            return getConfig().getString("messages.player.duel-request.accept.button");
        else return "&f[&aAccept&f]";
    }

    public String getAcceptHover(){
        if(getConfig().contains("messages.player.duel-request.accept.hover"))
            return getConfig().getString("messages.player.duel-request.accept.hover");
        else return "&aClick to accept the request !";
    }

    public String getDenyButton(){
        if(getConfig().contains("messages.player.duel-request.deny.button"))
            return getConfig().getString("messages.player.duel-request.deny.button");
        else return "&f[&cDeny&f]";
    }

    public String getDenyHover(){
        if(getConfig().contains("messages.player.duel-request.deny.hover"))
            return getConfig().getString("messages.player.duel-request.deny.hover");
        else return "&cClick to deny the request !";
    }

    public String getExpiredRequest(){
        if(getConfig().contains("messages.player.expired-request"))
            return getConfig().getString("messages.player.expired-request");
        else return "&f[&6&lEasy&c&lDuels&f] &6Your duel request has expired!";
    }

    public String getNoCommand(){
        if(getConfig().contains("messages.player.no-command"))
            return getConfig().getString("messages.player.no-command");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou can't use this command in duel!";
    }

    public String getNoWinner(){
        if(getConfig().contains("messages.player.no-winner"))
            return getConfig().getString("messages.player.no-winner");
        else return "&f[&6&lEasy&c&lDuels&f] &6No one won the duel!";
    }

    public String getWinner(){
        if(getConfig().contains("messages.player.duel-win"))
            return getConfig().getString("messages.player.duel-win");
        else return "&f[&6&lEasy&c&lDuels&f] &aYou won the duel!";
    }

    public String getLoser(){
        if(getConfig().contains("messages.player.duel-lost"))
            return getConfig().getString("messages.player.duel-lost");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou lost the duel!";
    }

    public String getAlreadyInQueue(){
        if(getConfig().contains("messages.player.already-in-queue"))
            return getConfig().getString("messages.player.already-in-queue");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou are already in the queue!";
    }

    public String getNotInQueue(){
        if(getConfig().contains("messages.player.not-in-queue"))
            return getConfig().getString("messages.player.not-in-queue");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou are not in the queue!";
    }

    public String getJoinQueueInDuel(){
        if(getConfig().contains("messages.player.join-queue-in-duel"))
            return getConfig().getString("messages.player.join-queue-in-duel");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou can't join the queue while you are in a duel!";
    }

    public String getRequestInDuel(){
        if(getConfig().contains("messages.player.request-duel-in-duel"))
            return getConfig().getString("messages.player.request-duel-in-duel");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou can't send a request while you are in a duel!";
    }

    public String getAcceptRequestInDuel(){
        if(getConfig().contains("messages.player.accept-duel-in-duel"))
            return getConfig().getString("messages.player.accept-duel-in-duel");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou can't accept a request while you are in a duel!";
    }

    public String getQueueIsFull(){
        if(getConfig().contains("messages.player.queue-is-full"))
            return getConfig().getString("messages.player.queue-is-full");
        else return "&f[&6&lEasy&c&lDuels&f] &cThe queue is currently full!";
    }

    public String getArenaIsLocked(){
        if(getConfig().contains("messages.player.arena-is-locked"))
            return getConfig().getString("messages.player.arena-is-locked");
        else return "&f[&6&lEasy&c&lDuels&f] &cThe arena is locked!";
    }

    public String getYouNotEnoughMoney(){
        if(getConfig().contains("messages.player.you-not-enough-money"))
            return getConfig().getString("messages.player.you-not-enough-money");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou don't have enough money!";
    }

    public String getPlayerNotEnoughMoney(){
        if(getConfig().contains("messages.player.player-not-enough-money"))
            return getConfig().getString("messages.player.player-not-enough-money");
        else return "&f[&6&lEasy&c&lDuels&f] &c%player% does not have enough money!";
    }

    public String getBelowMinimum(){
        if(getConfig().contains("messages.player.below-minimum"))
            return getConfig().getString("messages.player.below-minimum");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou must bet at least &6%amount% &ccoins!";
    }

    public String getGreaterMaximum(){
        if(getConfig().contains("messages.player.greater-maximum"))
            return getConfig().getString("messages.player.greater-maximum");
        else return "&f[&6&lEasy&c&lDuels&f] &cYou cannot bet more than &6%amount% &ccoins!";
    }

    public String getInvalidAmount(){
        if(getConfig().contains("messages.player.invalid-amount"))
            return getConfig().getString("messages.player.invalid-amount");
        else return "&f[&6&lEasy&c&lDuels&f] &cPlease enter a valid bet amount!";
    }

    //Admin

    public String getAdminUnknown(){
        if(getConfig().contains("messages.admin.unknown-command"))
            return getConfig().getString("messages.admin.unknown-command");
        else return "&f[&6&lEasy&c&lDuels&f] &cUnknown command. Please use &6/easyduels admin help&c!";
    }

    public List<String> getAdminHelpMessages(){
        if(getConfig().contains("messages.admin.help-message"))
            return getConfig().getStringList("messages.admin.help-message");
        else return Arrays.asList(
                "&6-&c-&6-&c-&6-&c-&6-&c-&6- &6&lEasy&c&lDuels &6-&c-&6-&c-&6-&c-&6-&c-&6-"
                ,"","&6⬥ &c/&6easyduels &cadmin &6<&cplayer&6> &6<&cplayer&6> &6- &6Force players to fight in a duel."
                ,"&6⬥ &c/&6easyduels &cadmin &cqueue &6<&cplayer&6> &6- &6Force a player to join the queue."
                ,"&6⬥ &c/&6easyduels &cadmin help &6- &6Send you this message."
                ,"&6⬥ &c/&6easyduels &cadmin lock &6- &6Lock or unlock the arena (Lock the queue and request)."
                ,"&6⬥ &c/&6easyduels &cadmin spawn1 &6- &6Set the first spawn to your location."
                ,"&6⬥ &c/&6easyduels &cadmin spawn2 &6- &6Set the second spawn to your location."
                ,"&6⬥ &c/&6easyduels &cadmin reload &6- &6Reload the plugin."
                ,"","&6-&c-&6-&c-&6-&c-&6-&c-&6- &6&lEasy&c&lDuels &6-&c-&6-&c-&6-&c-&6-&c-&6-");
    }

    public String getArenaLocked(){
        if(getConfig().contains("messages.admin.arena-locked"))
            return getConfig().getString("messages.admin.arena-locked");
        else return "&f[&6&lEasy&c&lDuels&f] &aThe arena has been locked.";
    }

    public String getArenaUnlocked(){
        if(getConfig().contains("messages.admin.arena-unlocked"))
            return getConfig().getString("messages.admin.arena-unlocked");
        else return "&f[&6&lEasy&c&lDuels&f] &aThe arena has been unlocked.";
    }

    public String getAdminJoinQueueInDuel(){
        if(getConfig().contains("messages.admin.join-in-duel"))
            return getConfig().getString("messages.admin.join-in-duel");
        else return "&f[&6&lEasy&c&lDuels&f] &cThe player &6%player% &cis in a duel!";
    }

    public String getDuelHimSelf(){
        if(getConfig().contains("messages.admin.duel-himself"))
            return getConfig().getString("messages.admin.duel-himself");
        else return "&f[&6&lEasy&c&lDuels&f] &cA player can't fight himself in a duel!";
    }

    public String getAdminAlreadyInQueue(){
        if(getConfig().contains("messages.admin.already-in-queue"))
            return getConfig().getString("messages.admin.already-in-queue");
        else return "&f[&6&lEasy&c&lDuels&f] &c%player% is already in the queue!";
    }

    public String getForcedDuelStart(){
        if(getConfig().contains("messages.admin.forced-duel-start"))
            return getConfig().getString("messages.admin.forced-duel-start");
        else return "&f[&6&lEasy&c&lDuels&f] &aA forced duel started between &2%player1% &aand &2%player2%.";
    }

    public String getForcedDuelQueue(){
        if(getConfig().contains("messages.admin.forced-duel-queue"))
            return getConfig().getString("messages.admin.forced-duel-queue");
        else return "&f[&6&lEasy&c&lDuels&f] &aYou forced &2%player1% &aand &2%player2% &ato join the queue.";
    }

    public String getForcedQueue(){
        if(getConfig().contains("messages.admin.forced-queue"))
            return getConfig().getString("messages.admin.forced-queue");
        else return "&f[&6&lEasy&c&lDuels&f] &aYou forced &2%player% &ato join the queue.";
    }

    public String getSetFirstSpawn(){
        if(getConfig().contains("messages.admin.set-spawn-1"))
            return getConfig().getString("messages.admin.set-spawn-1");
        else return "&f[&6&lEasy&c&lDuels&f] &aThe first spawn has been set to your location.";
    }

    public String getSetSecondSpawn(){
        if(getConfig().contains("messages.admin.set-spawn-2"))
            return getConfig().getString("messages.admin.set-spawn-2");
        else return "&f[&6&lEasy&c&lDuels&f] &aThe second spawn has been set to your location.";
    }

    public String getReloaded(){
        if(getConfig().contains("messages.admin.reloaded"))
            return getConfig().getString("messages.admin.reloaded");
        else return "&f[&6&lEasy&c&lDuels&f] &aSuccessfully reloaded.";
    }

}
