package fr.enzias.easyduels.filemanager.files;

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
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
        save();
    }

    public FileConfiguration getConfig(){
            return messageConfig;
    }

    public String getPrefix(){
        if(getConfig().contains("messages.prefix"))
            return getConfig().getString("messages.prefix");
        else
            return "&f[&6&lEasy&c&lDuels&f]";
    }

    //Messages

    public String getNoPermission(){
        if(getConfig().contains("messages.player.no-permission"))
            return getConfig().getString("messages.player.no-permission");
        else return "%prefix% &cYou don't have permission to do that!";
    }

    public String getUnknown(){
        if(getConfig().contains("messages.player.unknown-command"))
            return getConfig().getString("messages.player.unknown-command");
        else return "%prefix% &cUnknown command. Please use &6/easyduels help&c!";
    }

    public List<String> getHelpMessages(){
        if(getConfig().contains("messages.player.help-message"))
            return getConfig().getStringList("messages.player.help-message");
        else return Arrays.asList(
                "&6-&c-&6-&c-&6-&c-&6-&c-&6- &6&lEasy&c&lDuels &6-&c-&6-&c-&6-&c-&6-&c-&6-"
                ,"","&6⬥ &c/&6easyduels &chelp &6- &6Send you this message."
                ,"&6⬥ &c/&6easyduels &6<&cplayer&6> [money] &6- &6Send a duel request to the player with money bet or not."
                ,"&6⬥ &c/&6easyduels &caccept &6<&cplayer&6> &6- &6Accept a duel request from a player."
                ,"&6⬥ &c/&6easyduels &cdeny &6<&cplayer&6> &6- &6Deny a duel request from a player."
                ,"&6⬥ &c/&6easyduels &cqueue join &6- &6Join the duel queue."
                ,"&6⬥ &c/&6easyduels &cqueue leave &6- &6Leave the duel queue."
                ,"&6⬥ &c/&6easyduels &cprofile &6- &6Show your experience profile."
                ,"","&6-&c-&6-&c-&6-&c-&6-&c-&6- &6&lEasy&c&lDuels &6-&c-&6-&c-&6-&c-&6-&c-&6-");
    }

    public String getOfflinePlayer(){
        if(getConfig().contains("messages.player.offline-player"))
            return getConfig().getString("messages.player.offline-player");
        else return "%prefix% &cThe player &6%player% &cis not online or doesn't exist!";
    }

    public String getDuelYourself(){
        if(getConfig().contains("messages.player.duel-yourself"))
            return getConfig().getString("messages.player.duel-yourself");
        else return "%prefix% &cYou can't duel yourself!";
    }

    public String getNoRequest(){
        if(getConfig().contains("messages.player.no-duel-request"))
            return getConfig().getString("messages.player.no-duel-request");
        else return "%prefix% &cThe player &6%player% &cdid not send you a duel request!";
    }

    public String getAlreadyARequest(){
        if(getConfig().contains("messages.player.already-a-request"))
            return getConfig().getString("messages.player.already-a-request");
        else return "%prefix% &cYou already have sent a duel request!";
    }

    public String getRequestAccept(){
        if(getConfig().contains("messages.player.request-accept-1"))
            return getConfig().getString("messages.player.request-accept-1");
        else return "%prefix% &aThe player &2%player% &ahas accepted your duel request.";
    }

    public String getYouRequestAccept(){
        if(getConfig().contains("messages.player.request-accept-2"))
            return getConfig().getString("messages.player.request-accept-2");
        else return "%prefix% &aYou have accepted the duel request of &2%player%&a.";
    }

    public String getRequestDeny(){
        if(getConfig().contains("messages.player.request-deny-1"))
            return getConfig().getString("messages.player.request-deny-1");
        else return "%prefix% &cThe player &6%player% &chas refused your duel request!";
    }

    public String getYouRequestDeny(){
        if(getConfig().contains("messages.player.request-deny-2"))
            return getConfig().getString("messages.player.request-deny-2");
        else return "%prefix% &aYou refused the duel request of &2%player%&a.";
    }

    public String getArenaNotEmpty(){
        if(getConfig().contains("messages.player.arena-not-empty"))
            return getConfig().getString("messages.player.arena-not-empty");
        else return "%prefix% &cThe arena is not available at the moment!";
    }

    public String getPlayerInDuel(){
        if(getConfig().contains("messages.player.player-in-duel"))
            return getConfig().getString("messages.player.player-in-duel");
        else return "%prefix% &cThe player &6%player% &cis in a duel!";
    }

    public String getRequestSent(){
        if(getConfig().contains("messages.player.request-sent"))
            return getConfig().getString("messages.player.request-sent");
        else return "%prefix% &aYou sent a request to &2%player%&a.";
    }

    public String getDuelRequest(){
        if(getConfig().contains("messages.player.duel-request.message"))
            return getConfig().getString("messages.player.duel-request.message");
        else return "%prefix% &aYou received a duel request from &2%player%&a.";
    }

    public String getDuelBetRequest(){
        if(getConfig().contains("messages.player.duel-request.bet-message"))
            return getConfig().getString("messages.player.duel-request.bet-message");
        else return "%prefix% &6By accepting this duel request, you will put &c%amount% &6coins at stake.";
    }

    public String getBeforeAccept(){
        if(getConfig().contains("messages.player.duel-request.before-button-message"))
            return getConfig().getString("messages.player.duel-request.before-button-message");
        else return "%prefix% &6Choose &6an &6option: ";
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
        else return "%prefix% &6Your duel request has expired!";
    }

    public String getNoCommand(){
        if(getConfig().contains("messages.player.no-command"))
            return getConfig().getString("messages.player.no-command");
        else return "%prefix% &cYou can't use this command in duel!";
    }

    public String getNoCommandSpectate(){
        if(getConfig().contains("messages.player.no-command-spectate"))
            return getConfig().getString("messages.player.no-command-spectate");
        else return "%prefix% &cYou can''t use this command while spectating!";
    }

    public String getNoWinner(){
        if(getConfig().contains("messages.player.no-winner"))
            return getConfig().getString("messages.player.no-winner");
        else return "%prefix% &6No one won the duel!";
    }

    public String getWinner(){
        if(getConfig().contains("messages.player.duel-win"))
            return getConfig().getString("messages.player.duel-win");
        else return "%prefix% &aYou won the duel!";
    }

    public String getLoser(){
        if(getConfig().contains("messages.player.duel-lost"))
            return getConfig().getString("messages.player.duel-lost");
        else return "%prefix% &cYou lost the duel!";
    }

    public String getJoinQueueInDuel(){
        if(getConfig().contains("messages.player.join-queue-in-duel"))
            return getConfig().getString("messages.player.join-queue-in-duel");
        else return "%prefix% &cYou can't join the queue while you are in a duel!";
    }

    public String getRequestInDuel(){
        if(getConfig().contains("messages.player.request-duel-in-duel"))
            return getConfig().getString("messages.player.request-duel-in-duel");
        else return "%prefix% &cYou can't send a request while you are in a duel!";
    }

    public String getAcceptRequestInDuel(){
        if(getConfig().contains("messages.player.accept-duel-in-duel"))
            return getConfig().getString("messages.player.accept-duel-in-duel");
        else return "%prefix% &cYou can't accept a request while you are in a duel!";
    }

    public String getQueueIsFull(){
        if(getConfig().contains("messages.player.queue-is-full"))
            return getConfig().getString("messages.player.queue-is-full");
        else return "%prefix% &cThe queue is currently full!";
    }

    public String getArenaIsLocked(){
        if(getConfig().contains("messages.player.arena-is-locked"))
            return getConfig().getString("messages.player.arena-is-locked");
        else return "%prefix% &cThe arena is locked!";
    }

    public String getYouNotEnoughMoney(){
        if(getConfig().contains("messages.player.you-not-enough-money"))
            return getConfig().getString("messages.player.you-not-enough-money");
        else return "%prefix% &cYou don't have enough money!";
    }

    public String getPlayerNotEnoughMoney(){
        if(getConfig().contains("messages.player.player-not-enough-money"))
            return getConfig().getString("messages.player.player-not-enough-money");
        else return "%prefix% &c%player% does not have enough money!";
    }

    public String getBelowMinimum(){
        if(getConfig().contains("messages.player.below-minimum"))
            return getConfig().getString("messages.player.below-minimum");
        else return "%prefix% &cYou must bet at least &6%amount% &ccoins!";
    }

    public String getGreaterMaximum(){
        if(getConfig().contains("messages.player.greater-maximum"))
            return getConfig().getString("messages.player.greater-maximum");
        else return "%prefix% &cYou cannot bet more than &6%amount% &ccoins!";
    }

    public String getInvalidAmountBet(){
        if(getConfig().contains("messages.player.invalid-amount"))
            return getConfig().getString("messages.player.invalid-amount");
        else return "%prefix% &cPlease enter a valid bet amount!";
    }

    public String getNowSpectating(){
        if(getConfig().contains("messages.player.now-spectating"))
            return getConfig().getString("messages.player.now-spectating");
        else return "%prefix% &aYou are now spectating the duel.";
    }

    public String getNoSpectating(){
        if(getConfig().contains("messages.player.no-spectating"))
            return getConfig().getString("messages.player.no-spectating");
        else return "%prefix% &aYou are no longer spectating the duel.";
    }

    public String getSpectateInDuel(){
        if(getConfig().contains("messages.player.spectate-in-duel"))
            return getConfig().getString("messages.player.spectate-in-duel");
        else return "%prefix% &cYou can''t spectate a match while you are in a duel!";
    }

    public String getNoDuel(){
        if(getConfig().contains("messages.player.no-duel"))
            return getConfig().getString("messages.player.no-duel");
        else return "%prefix% &cThere is no duel to spectate!";
    }

    //Ranks

    public String getExpNow(){
        if(getConfig().contains("messages.player.exp-now"))
            return getConfig().getString("messages.player.exp-now");
        else return "%prefix% &aYou now have &2%experience% &aexperience points. (&2Level %level%&a)";
    }

    public String getExpGain(){
        if(getConfig().contains("messages.player.exp-gain"))
            return getConfig().getString("messages.player.exp-gain");
        else return "%prefix% &aYou have received &2%reward% &aexperience points.";
    }

    public List<String> getProfile(){
        if(getConfig().contains("messages.player.profile"))
            return getConfig().getStringList("messages.player.profile");
        else return Arrays.asList(
                "&6-&c-&6-&c-&6-&c-&6-&c-&6- &6&lEasy&c&lDuels &f- &4%player% &6-&c-&6-&c-&6-&c-&6-&c-&6-",
                "",
                "&6- &cRank: %rank%",
                "&6- &cLevel: &6%level%&c/&6%max_level%",
                "&6- &cEXP: &6%experience% &cexperience points",
                "&6- &cEXP to next Level: &6%needed% &cneeded",
                "&6- &cCooldown before winning experience: &6%cooldown% second(s)",
                "",
                "&6-&c-&6-&c-&6-&c-&6-&c-&6- &6&lEasy&c&lDuels &f- &4%player% &6-&c-&6-&c-&6-&c-&6-&c-&6-");
    }

    //Admin

    public String getAdminUnknown(){
        if(getConfig().contains("messages.admin.unknown-command"))
            return getConfig().getString("messages.admin.unknown-command");
        else return "%prefix% &cUnknown command. Please use &6/easyduels admin help&c!";
    }

    public List<String> getAdminHelpMessages(){
        if(getConfig().contains("messages.admin.help-message"))
            return getConfig().getStringList("messages.admin.help-message");
        else return Arrays.asList(
                "&6-&c-&6-&c-&6-&c-&6-&c-&6- &6&lEasy&c&lDuels &6-&c-&6-&c-&6-&c-&6-&c-&6-"
                ,"","&6⬥ &c/&6easyduels &cadmin &6<&cplayer&6> &6<&cplayer&6> [money] &6- &6Force players to fight in a duel with money bet or not."
                ,"&6⬥ &c/&6easyduels &cadmin &cqueue &6<&cplayer&6> &6- &6Force a player to join the queue."
                ,"&6⬥ &c/&6easyduels &cadmin help &6- &6Send you this message."
                ,"&6⬥ &c/&6easyduels &cadmin lock &6- &6Lock or unlock the arena (Lock the queue and request)."
                ,"&6⬥ &c/&6easyduels &cadmin spawn1 &6- &6Set the first spawn to your location."
                ,"&6⬥ &c/&6easyduels &cadmin spawn2 &6- &6Set the second spawn to your location."
                ,"&6⬥ &c/&6easyduels &cadmin spectate &6- &6Set the spectate location to your location."
                ,"&6⬥ &c/&6easyduels &cadmin lobby &6- &6Set the lobby location to your location."
                ,"&6⬥ &c/&6easyduels &cadmin reload &6- &6Reload the plugin."
                ,"&6⬥ &cRanks commands &6- &cPlease check the wiki for more."
                ,"","&6-&c-&6-&c-&6-&c-&6-&c-&6- &6&lEasy&c&lDuels &6-&c-&6-&c-&6-&c-&6-&c-&6-");
    }

    public String getArenaLocked(){
        if(getConfig().contains("messages.admin.arena-locked"))
            return getConfig().getString("messages.admin.arena-locked");
        else return "%prefix% &aThe arena has been locked.";
    }

    public String getArenaUnlocked(){
        if(getConfig().contains("messages.admin.arena-unlocked"))
            return getConfig().getString("messages.admin.arena-unlocked");
        else return "%prefix% &aThe arena has been unlocked.";
    }

    public String getAdminJoinQueueInDuel(){
        if(getConfig().contains("messages.admin.join-in-duel"))
            return getConfig().getString("messages.admin.join-in-duel");
        else return "%prefix% &cThe player &6%player% &cis in a duel!";
    }

    public String getDuelHimSelf(){
        if(getConfig().contains("messages.admin.duel-himself"))
            return getConfig().getString("messages.admin.duel-himself");
        else return "%prefix% &cA player can't fight himself in a duel!";
    }

    public String getAdminAlreadyInQueue(){
        if(getConfig().contains("messages.admin.already-in-queue"))
            return getConfig().getString("messages.admin.already-in-queue");
        else return "%prefix% &c%player% is already in the queue!";
    }

    public String getForcedDuelStart(){
        if(getConfig().contains("messages.admin.forced-duel-start"))
            return getConfig().getString("messages.admin.forced-duel-start");
        else return "%prefix% &aA forced duel started between &2%player1% &aand &2%player2%.";
    }

    public String getForcedDuelQueue(){
        if(getConfig().contains("messages.admin.forced-duel-queue"))
            return getConfig().getString("messages.admin.forced-duel-queue");
        else return "%prefix% &aYou forced &2%player1% &aand &2%player2% &ato join the queue.";
    }

    public String getForcedQueue(){
        if(getConfig().contains("messages.admin.forced-queue"))
            return getConfig().getString("messages.admin.forced-queue");
        else return "%prefix% &aYou forced &2%player% &ato join the queue.";
    }

    public String getInvalidAmountExp(){
        if(getConfig().contains("messages.admin.invalid-amount"))
            return getConfig().getString("messages.admin.invalid-amount");
        else return "%prefix% &aThe first spawn has been set to your location.";
    }

    public String getGiveExp(){
        if(getConfig().contains("messages.admin.give-exp"))
            return getConfig().getString("messages.admin.give-exp");
        else return "%prefix% &aYou gave &2%amount% &aexperience points to &2%player%&a.";
    }

    public String getTakeExp(){
        if(getConfig().contains("messages.admin.take-exp"))
            return getConfig().getString("messages.admin.take-exp");
        else return "%prefix% &aYou take &2%amount% &aexperience points to &2%player%&a.";
    }

    public String getSetExp(){
        if(getConfig().contains("messages.admin.set-exp"))
            return getConfig().getString("messages.admin.set-exp");
        else return "%prefix% &aYou set the experience of &2%player% &ato &2%amount% &apoints.";
    }

    public String getResetExp(){
        if(getConfig().contains("messages.admin.reset-exp"))
            return getConfig().getString("messages.admin.reset-exp");
        else return "%prefix% &aYou reset the experience of &2%player%&a.";
    }

    public String getGiveAllExp(){
        if(getConfig().contains("messages.admin.give-all-exp"))
            return getConfig().getString("messages.admin.give-all-exp");
        else return "%prefix% &aYou gave &2%amount% &aexperience points to everyone.";
    }

    public String getTakeAllExp(){
        if(getConfig().contains("messages.admin.take-all-exp"))
            return getConfig().getString("messages.admin.take-all-exp");
        else return "%prefix% &aYou take &2%amount% &aexperience points to everyone.";
    }

    public String getSetAllExp(){
        if(getConfig().contains("messages.admin.set-all-exp"))
            return getConfig().getString("messages.admin.set-all-exp");
        else return "%prefix% &aYou set the experience of everyone to &2%amount% &apoints.";
    }

    public String getResetAllExp(){
        if(getConfig().contains("messages.admin.reset-all-exp"))
            return getConfig().getString("messages.admin.reset-all-exp");
        else return "%prefix% &aYou reset the experience of everyone.";
    }

    public String getExpNowPlayer(){
        if(getConfig().contains("messages.admin.now-player-exp"))
            return getConfig().getString("messages.admin.now-player-exp");
        else return "%prefix% &2%player% &anow has &2%amount% &aexperience points.";
    }

    public String getSetFirstSpawn(){
        if(getConfig().contains("messages.admin.set-spawn-1"))
            return getConfig().getString("messages.admin.set-spawn-1");
        else return "%prefix% &aThe first spawn has been set to your location.";
    }

    public String getSetSecondSpawn(){
        if(getConfig().contains("messages.admin.set-spawn-2"))
            return getConfig().getString("messages.admin.set-spawn-2");
        else return "%prefix% &aThe second spawn has been set to your location.";
    }

    public String getSetLobby(){
        if(getConfig().contains("messages.admin.set-lobby"))
            return getConfig().getString("messages.admin.set-lobby");
        else return "%prefix% &aThe lobby has been set to your location.";
    }

    public String getSetSpectate(){
        if(getConfig().contains("messages.admin.set-spectate"))
            return getConfig().getString("messages.admin.set-spectate");
        else return "%prefix% &aThe spectate spawn has been set to your location.";
    }

    public String getReloaded(){
        if(getConfig().contains("messages.admin.reloaded"))
            return getConfig().getString("messages.admin.reloaded");
        else return "%prefix% &aSuccessfully reloaded.";
    }

}
