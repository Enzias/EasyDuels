package fr.enzias.easyduels.files;

import fr.enzias.easyduels.EasyDuels;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
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

    //Blocked Commands

    public boolean getAllCommands(){
        return getConfig().getBoolean("settings.blocked-commands.all");
    }

    public List<String> getBlacklistedCommands(){
        return getConfig().getStringList("settings.blocked-commands.blacklisted-commands");
    }

    public List<String> getWhitelistedCommands() {
        return getConfig().getStringList("settings.blocked-commands.whitelisted-commands");
    }

    //Timer

    public boolean getSyncTimer() {
       return getConfig().contains("settings.timer.sync-timer");
    }


    //Request

    public int getExpirationRequest(){
            return getConfig().getInt("settings.timer.request.expiration");
    }

    //Lobby

    public int getLobbyTime(){
        return getConfig().getInt("settings.timer.lobby.duration");
    }

    public boolean getPreventMove(){
        return getConfig().getBoolean("settings.timer.lobby.prevent-move");
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
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".sound-to-players")) {
                return getConfig().getInt("settings.timer.lobby.actions." + i + ".sound-to-players.volume");
            } return 1;
    }

    public float getLobbyPitchToPlayers(int i){
            if (getConfig().contains("settings.timer.lobby.actions." + i + ".sound-to-players")) {
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
        return getConfig().getInt("settings.timer.fight.duration");
    }

    public String getFightGameMode(){
        return getConfig().getString("settings.timer.fight.gamemode");
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
            if (getConfig().contains("settings.timer.fight.actions." + i + ".sound-to-players")) {
                return getConfig().getInt("settings.timer.fight.actions." + i + ".sound-to-players.volume");
            } return 1;
    }

    public float getFightPitchToPlayers(int i){
            if (getConfig().contains("settings.timer.fight.actions." + i + ".sound-to-players")) {
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
        return getConfig().getInt("settings.timer.end.duration");
    }

    public String getEndGameMode(){
        return getConfig().getString("settings.timer.end.gamemode");
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
        if (getConfig().contains("settings.timer.end.actions.sound-to-winner")) {
            return getConfig().getInt("settings.timer.end.actions.sound-to-winner.volume");
        } return 1;
    }

    public float getEndPitchToWinner(){
        if (getConfig().contains("settings.timer.end.actions.sound-to-winner")) {
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
        if (getConfig().contains("settings.timer.end.actions.sound-to-loser")) {
            return getConfig().getInt("settings.timer.end.actions.sound-to-loser.volume");
        } return 1;
    }

    public float getEndPitchToLoser(){
        if (getConfig().contains("settings.timer.end.actions.sound-to-loser")) {
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
        if (getConfig().contains("settings.timer.end.actions.sound-no-winner")) {
            return getConfig().getInt("settings.timer.end.actions.sound-no-winner.volume");
        } return 1;
    }

    public float getEndPitchNoWinner(){
        if (getConfig().contains("settings.timer.end.actions.sound-no-winner")) {
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

}