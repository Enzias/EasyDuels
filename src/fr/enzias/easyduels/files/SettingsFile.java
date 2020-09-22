package fr.enzias.easyduels.files;

import fr.enzias.easyduels.EasyDuels;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SettingsFile {

    private final EasyDuels plugin;
    private File settingsFile;
    private FileConfiguration settingsConfig;

    public SettingsFile(EasyDuels plugin) {
        this.plugin = plugin;
    }

    public void setup(){
        settingsFile = new File(plugin.getDataFolder(), "settings.yml");

        if(!settingsFile.exists()) {
            plugin.saveResource("settings.yml", false);
        }

        settingsConfig = new YamlConfiguration();
        try {
            settingsConfig.load(settingsFile);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public void save(){
        try{
            settingsConfig.save(settingsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        save();
        settingsConfig = YamlConfiguration.loadConfiguration(settingsFile);
    }

    public FileConfiguration getConfig(){
        return settingsConfig;
    }

    public boolean getCheckForUpdates(){
        if(getConfig().contains("settings.check-for-updates"))
            return getConfig().getBoolean("settings.check-for-updates");
        else
            return true;
    }

    public List<String> getAliases(){
        if(getConfig().contains("settings.command-aliases"))
            return getConfig().getStringList("settings.command-aliases");
        else return Arrays.asList("easyduel", "ed", "duel", "duels");
    }

    //Blocked Commands

    public boolean getAllCommands(){
        if(getConfig().contains("settings.blocked-commands.all"))
            return getConfig().getBoolean("settings.blocked-commands.all");
        else return false;
    }

    public boolean getInventory(){
        if(getConfig().contains("settings.blocked-commands.inventory"))
            return getConfig().getBoolean("settings.blocked-commands.inventory");
        else return false;
    }

    public List<String> getBlacklistedCommands(){
        return getConfig().getStringList("settings.blocked-commands.blacklisted-commands");
    }

    public List<String> getWhitelistedCommands() {
        return getConfig().getStringList("settings.blocked-commands.whitelisted-commands");
    }

    //queue

    public boolean getQueue(){
        if(getConfig().contains("settings.queue.enable"))
            return getConfig().getBoolean("settings.queue.enable");
        else
            return false;
    }

    public int getQueueMaxPlayers(){
        if(getConfig().contains("settings.queue.max-players"))
            return getConfig().getInt("settings.queue.max-players");
        else return 30;
    }

    public boolean checkQueueJoin(){
        return getConfig().get("settings.queue.queue-join") instanceof ConfigurationSection;
    }

    public String getQueueJoinMessageToPlayer(){
        if (getConfig().contains("settings.queue.queue-join.message-to-player")
                && !getConfig().getString("settings.queue.queue-join.message-to-player").equalsIgnoreCase("")) {
            return getConfig().getString("settings.queue.queue-join.message-to-player");
        } return null;
    }

    public List<String> getQueueJoinTitleToPlayer(){
        if (getConfig().contains("settings.queue.queue-join.title-to-player")) {
            return getConfig().getStringList("settings.queue.queue-join.title-to-player");
        } return null;
    }

    public String getQueueJoinSoundToPlayer(){
        if (getConfig().contains("settings.queue.queue-join.sound-to-player")
                && !getConfig().getString("settings.queue.queue-join.sound-to-player.sound-id").equalsIgnoreCase("")) {
            return getConfig().getString("settings.queue.queue-join.sound-to-player.sound-id");
        } return null;
    }

    public int getQueueJoinVolumeToPlayer(){
        if (getConfig().contains("settings.queue.queue-join.sound-to-player.volume")) {
            return getConfig().getInt("settings.queue.queue-join.sound-to-player.volume");
        } return 1;
    }

    public float getQueueJoinPitchToPlayer(){
        if (getConfig().contains("settings.queue.queue-join.sound-to-player.pitch")) {
            return getConfig().getInt("settings.queue.queue-join.sound-to-player.pitch");
        } return 1;
    }

    public String getQueueJoinActionbarToPlayer(){
        if (getConfig().contains("settings.queue.queue-join.actionbar-to-player")
                && !getConfig().getString("settings.queue.queue-join.actionbar-to-player").equalsIgnoreCase("")) {
            return getConfig().getString("settings.queue.queue-join.actionbar-to-player");
        } return null;
    }

    //queue leave

    public boolean checkQueueLeave(){
        return getConfig().get("settings.queue.queue-leave") instanceof ConfigurationSection;
    }

    public String getQueueLeaveMessageToPlayer(){
        if (getConfig().contains("settings.queue.queue-leave.message-to-player")
                && !getConfig().getString("settings.queue.queue-leave.message-to-player").equalsIgnoreCase("")) {
            return getConfig().getString("settings.queue.queue-leave.message-to-player");
        } return null;
    }

    public List<String> getQueueLeaveTitleToPlayer(){
        if (getConfig().contains("settings.queue.queue-leave.title-to-player")) {
            return getConfig().getStringList("settings.queue.queue-leave.title-to-player");
        } return null;
    }

    public String getQueueLeaveSoundToPlayer(){
        if (getConfig().contains("settings.queue.queue-leave.sound-to-player")
                && !getConfig().getString("settings.queue.queue-leave.sound-to-player.sound-id").equalsIgnoreCase("")) {
            return getConfig().getString("settings.queue.queue-leave.sound-to-player.sound-id");
        } return null;
    }

    public int getQueueLeaveVolumeToPlayer(){
        if (getConfig().contains("settings.queue.queue-leave.sound-to-player.volume")) {
            return getConfig().getInt("settings.queue.queue-leave.sound-to-player.volume");
        } return 1;
    }

    public float getQueueLeavePitchToPlayer(){
        if (getConfig().contains("settings.queue.queue-leave.sound-to-player.pitch")) {
            return getConfig().getInt("settings.queue.queue-leave.sound-to-player.pitch");
        } return 1;
    }

    public String getQueueLeaveActionbarToPlayer(){
        if (getConfig().contains("settings.queue.queue-leave.actionbar-to-player")
                && !getConfig().getString("settings.queue.queue-leave.actionbar-to-player").equalsIgnoreCase("")) {
            return getConfig().getString("settings.queue.queue-leave.actionbar-to-player");
        } return null;
    }

    //queue update

    public boolean checkQueueUpdate(){
        return getConfig().get("settings.queue.queue-update") instanceof ConfigurationSection;
    }

    public String getQueueUpdateMessageToPlayer(){
        if (getConfig().contains("settings.queue.queue-update.message-to-player")
                && !getConfig().getString("settings.queue.queue-update.message-to-player").equalsIgnoreCase("")) {
            return getConfig().getString("settings.queue.queue-update.message-to-player");
        } return null;
    }

    public List<String> getQueueUpdateTitleToPlayer(){
        if (getConfig().contains("settings.queue.queue-update.title-to-player")) {
            return getConfig().getStringList("settings.queue.queue-update.title-to-player");
        } return null;
    }

    public String getQueueUpdateSoundToPlayer(){
        if (getConfig().contains("settings.queue.queue-update.sound-to-player")
                && !getConfig().getString("settings.queue.queue-update.sound-to-player.sound-id").equalsIgnoreCase("")) {
            return getConfig().getString("settings.queue.queue-update.sound-to-player.sound-id");
        } return null;
    }

    public int getQueueUpdateVolumeToPlayer(){
        if (getConfig().contains("settings.queue.queue-update.sound-to-player.volume")) {
            return getConfig().getInt("settings.queue.queue-update.sound-to-player.volume");
        } return 1;
    }

    public float getQueueUpdatePitchToPlayer(){
        if (getConfig().contains("settings.queue.queue-update.sound-to-player.pitch")) {
            return getConfig().getInt("settings.queue.queue-update.sound-to-player.pitch");
        } return 1;
    }

    public String getQueueUpdateActionbarToPlayer(){
        if (getConfig().contains("settings.queue.queue-update.actionbar-to-player")
                && !getConfig().getString("settings.queue.queue-update.actionbar-to-player").equalsIgnoreCase("")) {
            return getConfig().getString("settings.queue.queue-update.actionbar-to-player");
        } return null;
    }

    //Money Bet

    public boolean getMoneyBet(){
        if(getConfig().contains("settings.money-bet.enable"))
            return getConfig().getBoolean("settings.money-bet.enable");
        else
            return false;
    }

    public int getMinAmount(){
        if(getConfig().contains("settings.money-bet.minimum-amount"))
            return Math.max(getConfig().getInt("settings.money-bet.minimum-amount"), 0);
        return 0;
    }

    public int getMaxAmount(){
        if (!getConfig().contains("settings.money-bet.maximum-amount")
                || getConfig().getInt("settings.money-bet.maximum-amount") <= 0)
            return -1;
        return getConfig().getInt("settings.money-bet.maximum-amount");
    }

    //Timer

    public boolean getSyncTimer() {
        if(getConfig().contains("settings.timer.sync-timer"))
            return getConfig().contains("settings.timer.sync-timer");
        else return true;
    }


    //Request

    public int getExpirationRequest(){
        if(getConfig().contains("settings.timer.request.expiration"))
            return getConfig().getInt("settings.timer.request.expiration");
        else return 30;
    }

    //Lobby

    public int getLobbyTime(){
        if(getConfig().contains("settings.timer.lobby.duration"))
            return getConfig().getInt("settings.timer.lobby.duration");
        else return 10;
    }

    public boolean getPreventMove(){
        if(getConfig().contains("settings.timer.lobby.prevent-move"))
            return getConfig().getBoolean("settings.timer.lobby.prevent-move");
        else return false;
    }

    public boolean checkLobbyTime(int i){
        return getConfig().get("settings.timer.lobby.actions." + i) instanceof ConfigurationSection;
    }

    public String getLobbyMessageToPlayers(int i){
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".message-to-players")
                    && !getConfig().getString("settings.timer.lobby.actions." + i + ".message-to-players").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.lobby.actions." + i + ".message-to-players");
            } return null;
    }

    public List<String> getLobbyTitleToPlayers(int i){
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".title-to-players")) {
                return getConfig().getStringList("settings.timer.lobby.actions." + i + ".title-to-players");
            } return null;
    }

    public String getLobbySoundToPlayers(int i){
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".sound-to-players")
                    && !getConfig().getString("settings.timer.lobby.actions." + i + ".sound-to-players.sound-id").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.lobby.actions." + i + ".sound-to-players.sound-id");
            } return null;
    }

    public int getLobbyVolumeToPlayers(int i){
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".sound-to-players.volume")) {
                return getConfig().getInt("settings.timer.lobby.actions." + i + ".sound-to-players.volume");
            } return 1;
    }

    public float getLobbyPitchToPlayers(int i){
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".sound-to-players.pitch")) {
                return getConfig().getInt("settings.timer.lobby.actions." + i + ".sound-to-players.pitch");
            } return 1;
    }

    public String getLobbyActionbarToPlayers(int i){
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".actionbar-to-players")
                    && !getConfig().getString("settings.timer.lobby.actions." + i + ".actionbar-to-players").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.lobby.actions." + i + ".actionbar-to-players");
            } return null;
    }

    public String getLobbyPlayersCommand(int i){
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".players-command")
                    && !getConfig().getString("settings.timer.lobby.actions." + i + ".players-command").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.lobby.actions." + i + ".players-command");
            } return null;
    }

    public String getLobbyConsoleCommand(int i){
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".console-command")
                    && !getConfig().getString("settings.timer.lobby.actions." + i + ".console-command").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.lobby.actions." + i + ".console-command");
            } return null;
    }

    public String getLobbyBroadcast(int i){
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".broadcast")
                    && !getConfig().getString("settings.timer.lobby.actions." + i + ".broadcast").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.lobby.actions." + i + ".broadcast");
            } return null;
    }

    //Fight

    public int getFightTime(){
        if(getConfig().contains("settings.timer.fight.duration"))
            return getConfig().getInt("settings.timer.fight.duration");
        else return 300;
    }

    public String getFightGameMode(){
        if(getConfig().contains("settings.timer.fight.gamemode"))
            return getConfig().getString("settings.timer.fight.gamemode");
        return "SURVIVAL";
    }

    public boolean checkFightTime(int i){
        return getConfig().get("settings.timer.fight.actions." + i) instanceof ConfigurationSection;
    }

    public boolean checkFightAll(){
        return getConfig().get("settings.timer.fight.actions.all") instanceof ConfigurationSection;
    }

    public String getFightAllActionbarToPlayers(){
            if (getConfig().contains("settings.timer.fight.actions.all.actionbar-to-players")
                    && !getConfig().getString("settings.timer.fight.actions.all.actionbar-to-players").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.fight.actions.all.actionbar-to-players");
            }return null;
    }

    public String getFightMessageToPlayers(int i){
            if (getConfig().contains("settings.timer.fight.actions." + i + ".message-to-players")
                    && !getConfig().getString("settings.timer.fight.actions." + i + ".message-to-players").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.fight.actions." + i + ".message-to-players");
            } return null;
    }

    public List<String> getFightTitleToPlayers(int i){
            if (getConfig().contains("settings.timer.fight.actions." + i + ".title-to-players")) {
                return getConfig().getStringList("settings.timer.fight.actions." + i + ".title-to-players");
            } return null;
    }

    public String getFightSoundToPlayers(int i){
            if (getConfig().contains("settings.timer.fight.actions." + i + ".sound-to-players")
                    && !getConfig().getString("settings.timer.fight.actions." + i + ".sound-to-players.sound-id").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.fight.actions." + i + ".sound-to-players.sound-id");
            } return null;
    }

    public int getFightVolumeToPlayers(int i){
            if (getConfig().contains("settings.timer.fight.actions." + i + ".sound-to-players.volume")) {
                return getConfig().getInt("settings.timer.fight.actions." + i + ".sound-to-players.volume");
            } return 1;
    }

    public float getFightPitchToPlayers(int i){
            if (getConfig().contains("settings.timer.fight.actions." + i + ".sound-to-players.pitch")) {
                return getConfig().getInt("settings.timer.fight.actions." + i + ".sound-to-players.pitch");
            } return 1;
    }

    public String getFightPlayersCommand(int i){
            if (getConfig().contains("settings.timer.fight.actions." + i + ".players-command")
                    && !getConfig().getString("settings.timer.fight.actions." + i + ".players-command").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.fight.actions." + i + ".players-command");
            } return null;
    }

    public String getFightConsoleCommand(int i){
            if (getConfig().contains("settings.timer.fight.actions." + i + ".console-command")
                    && !getConfig().getString("settings.timer.fight.actions." + i + ".console-command").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.fight.actions." + i + ".console-command");
            } return null;
    }

    public String getFightBroadcast(int i) {
            if (getConfig().contains("settings.timer.fight.actions." + i + ".broadcast")
                    && !getConfig().getString("settings.timer.fight.actions." + i + ".broadcast").equalsIgnoreCase("")) {
                return getConfig().getString("settings.timer.fight.actions." + i + ".broadcast");
            } return null;
    }

    //End - Reloading

    public int getEndTime(){
        if(getConfig().contains("settings.timer.end.duration"))
            return getConfig().getInt("settings.timer.end.duration");
        else return 10;
    }

    public String getEndGameMode(){
        if(getConfig().contains("settings.timer.end.gamemode"))
            return getConfig().getString("settings.timer.end.gamemode");
        else return "SURVIVAL";
    }

    public boolean getResultMessage(){
        if(getConfig().contains("settings.timer.end.actions.results-message")){
            return getConfig().getBoolean("settings.timer.end.actions.results-message");
        } return false;
    }

    public boolean getFakeExplosion(){
        if(getConfig().contains("settings.timer.end.actions.fake-explosion")){
            return getConfig().getBoolean("settings.timer.end.actions.fake-explosion");
        } return false;
    }

    public boolean getFirework(){
        if(getConfig().contains("settings.timer.end.actions.firework")){
            return getConfig().getBoolean("settings.timer.end.actions.firework");
        } return false;
    }

    public String getBetMessageToWinner(){
        if(getConfig().contains("settings.timer.end.actions.bet-message-to-winner")
            && !getConfig().getString("settings.timer.end.actions.bet-message-to-winner").equalsIgnoreCase(""))
            return getConfig().getString("settings.timer.end.actions.bet-message-to-winner");
        return null;
    }

    public String getBetMessageToLoser(){
        if(getConfig().contains("settings.timer.end.actions.bet-message-to-loser")
                && !getConfig().getString("settings.timer.end.actions.bet-message-to-loser").equalsIgnoreCase(""))
            return getConfig().getString("settings.timer.end.actions.bet-message-to-loser");
        return null;
    }

    public String getBetMessageNoWinner(){
        if(getConfig().contains("settings.timer.end.actions.bet-message-no-winner")
                && !getConfig().getString("settings.timer.end.actions.bet-message-no-winner").equalsIgnoreCase(""))
            return getConfig().getString("settings.timer.end.actions.bet-message-no-winner");
        return null;
    }

    public List<String> getEndTitleToWinner(){
        if (getConfig().contains("settings.timer.end.actions.title-to-winner")) {
            return getConfig().getStringList("settings.timer.end.actions.title-to-winner");
        } return null;
    }

    public List<String> getEndTitleToLoser(){
        if (getConfig().contains("settings.timer.end.actions.title-to-loser")) {
            return getConfig().getStringList("settings.timer.end.actions.title-to-loser");
        } return null;
    }

    public List<String> getEndTitleDraw(){
        if (getConfig().contains("settings.timer.end.actions.title-no-winner")) {
            return getConfig().getStringList("settings.timer.end.actions.title-no-winner");
        } return null;
    }

    public String getEndSoundToWinner(){
        if (getConfig().contains("settings.timer.end.actions.sound-to-winner")
                && !getConfig().getString("settings.timer.end.actions.sound-to-winner.sound-id").equalsIgnoreCase("")) {
            return getConfig().getString("settings.timer.end.actions.sound-to-winner.sound-id");
        } return null;
    }

    public int getEndVolumeToWinner(){
        if (getConfig().contains("settings.timer.end.actions.sound-to-winner.volume")) {
            return getConfig().getInt("settings.timer.end.actions.sound-to-winner.volume");
        } return 1;
    }

    public float getEndPitchToWinner(){
        if (getConfig().contains("settings.timer.end.actions.sound-to-winner.pitch")) {
            return getConfig().getInt("settings.timer.end.actions.sound-to-winner.pitch");
        } return 1;
    }

    public String getEndSoundToLoser(){
        if (getConfig().contains("settings.timer.end.actions.sound-to-loser")
                && !getConfig().getString("settings.timer.end.actions.sound-to-loser.sound-id").equalsIgnoreCase("")) {
            return getConfig().getString("settings.timer.end.actions.sound-to-loser.sound-id");
        } return null;
    }

    public int getEndVolumeToLoser(){
        if (getConfig().contains("settings.timer.end.actions.sound-to-loser.volume")) {
            return getConfig().getInt("settings.timer.end.actions.sound-to-loser.volume");
        } return 1;
    }

    public float getEndPitchToLoser(){
        if (getConfig().contains("settings.timer.end.actions.sound-to-loser.pitch")) {
            return getConfig().getInt("settings.timer.end.actions.sound-to-loser.pitch");
        } return 1;
    }

    public String getEndSoundNoWinner(){
        if (getConfig().contains("settings.timer.end.actions.sound-no-winner")
                && !getConfig().getString("settings.timer.end.actions.sound-no-winner.sound-id").equalsIgnoreCase("")) {
            return getConfig().getString("settings.timer.end.actions.sound-no-winner.sound-id");
        } return null;

    }

    public int getEndVolumeNoWinner(){
        if (getConfig().contains("settings.timer.end.actions.sound-no-winner.volume")) {
            return getConfig().getInt("settings.timer.end.actions.sound-no-winner.volume");
        } return 1;
    }

    public float getEndPitchNoWinner(){
        if (getConfig().contains("settings.timer.end.actions.sound-no-winner.pitch")) {
            return getConfig().getInt("settings.timer.end.actions.sound-no-winner.pitch");
        } return 1;
    }

    public String getEndActionbarToWinner(){
        if (getConfig().contains("settings.timer.end.actions.actionbar-to-winner")
                && !getConfig().getString("settings.timer.end.actions.actionbar-to-winner").equalsIgnoreCase("")) {
            return getConfig().getString("settings.timer.end.actions.actionbar-to-winner");
        } return null;
    }

    public String getEndActionbarToLoser(){
        if (getConfig().contains("settings.timer.end.actions.actionbar-to-loser")
                && !getConfig().getString("settings.timer.end.actions.actionbar-to-loser").equalsIgnoreCase("")) {
            return getConfig().getString("settings.timer.end.actions.actionbar-to-loser");
        } return null;
    }

    public String getEndActionbarDraw(){
        if (getConfig().contains("settings.timer.end.actions.actionbar-no-winner")
                && !getConfig().getString("settings.timer.end.actions.actionbar-no-winner").equalsIgnoreCase("")) {
            return getConfig().getString("settings.timer.end.actions.actionbar-no-winner");
        } return null;
    }

    public List<String> getEndRewardCommand(){
        if(getConfig().contains("settings.timer.end.actions.reward-commands"))
            return getConfig().getStringList("settings.timer.end.actions.reward-commands");
        return null;
    }

    public String getEndBroadcast(){
        if (getConfig().contains("settings.timer.end.actions.broadcast")
                && !getConfig().getString("settings.timer.end.actions.broadcast").equalsIgnoreCase("")) {
            return getConfig().getString("settings.timer.end.actions.broadcast");
        } return null;
    }

    public String getEndBroadcastNoWinner(){
        if (getConfig().contains("settings.timer.end.actions.broadcast-no-winner")
                && !getConfig().getString("settings.timer.end.actions.broadcast-no-winner").equalsIgnoreCase("")) {
            return getConfig().getString("settings.timer.end.actions.broadcast-no-winner");
        } return null;
    }

}
