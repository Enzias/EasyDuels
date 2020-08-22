package fr.enzias.easyduels.files;

import fr.enzias.easyduels.EasyDuels;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class SettingsFile {

    private final EasyDuels plugin;
    public SettingsFile(EasyDuels plugin) {
        this.plugin = plugin;
    }

    //Blocked Commands

    public boolean getAllCommands(){
        return plugin.getConfig().getBoolean("settings.blocked-commands.all");
    }

    public List<String> getBlacklistedCommands(){
        return plugin.getConfig().getStringList("settings.blocked-commands.blacklisted-commands");
    }

    public List<String> getWhitelistedCommands() {
        return plugin.getConfig().getStringList("settings.blocked-commands.whitelisted-commands");
    }

    //Timer

    public boolean getSyncTimer() {
       return plugin.getConfig().getBoolean("settings.timer.sync-timer");
    }


    //Request

    public int getExpirationRequest(){
            return plugin.getConfig().getInt("settings.timer.request.expiration");
    }

    //Lobby

    public int getLobbyTime(){
        return plugin.getConfig().getInt("settings.timer.lobby.duration");
    }

    public boolean getPreventMove(){
        return plugin.getConfig().getBoolean("settings.timer.lobby.prevent-move");
    }

    public boolean checkLobbyTime(int i){
        return plugin.getConfig().get("settings.timer.lobby.actions." + i) instanceof ConfigurationSection;
    }

    public String getLobbyMessageToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.lobby.actions." + i + ".message-to-players")
                    && !plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".message-to-players").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".message-to-players");
            } return null;
    }

    public List<String> getLobbyTitleToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.lobby.actions." + i + ".title-to-players")) {
                return plugin.getConfig().getStringList("settings.timer.lobby.actions." + i + ".title-to-players");
            } return null;
    }

    public String getLobbySoundToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.lobby.actions." + i + ".sound-to-players")
                    && !plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".sound-to-players.sound-id").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".sound-to-players.sound-id");
            } return null;
    }

    public int getLobbyVolumeToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.lobby.actions." + i + ".sound-to-players")) {
                return plugin.getConfig().getInt("settings.timer.lobby.actions." + i + ".sound-to-players.volume");
            } return 1;
    }

    public float getLobbyPitchToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.lobby.actions." + i + ".sound-to-players")) {
                return plugin.getConfig().getInt("settings.timer.lobby.actions." + i + ".sound-to-players.pitch");
            } return 1;
    }

    public String getLobbyActionbarToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.lobby.actions." + i + ".actionbar-to-players")
                    && !plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".actionbar-to-players").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".actionbar-to-players");
            } return null;
    }

    public String getLobbyPlayersCommand(int i){
            if (plugin.getConfig().contains("settings.timer.lobby.actions." + i + ".players-command")
                    && !plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".players-command").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".players-command");
            } return null;
    }

    public String getLobbyConsoleCommand(int i){
            if (plugin.getConfig().contains("settings.timer.lobby.actions." + i + ".console-command")
                    && !plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".console-command").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".console-command");
            } return null;
    }

    public String getLobbyBroadcast(int i){
            if (plugin.getConfig().contains("settings.timer.lobby.actions." + i + ".broadcast")
                    && !plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".broadcast").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.lobby.actions." + i + ".broadcast");
            } return null;
    }

    //Fight

    public int getFightTime(){
        return plugin.getConfig().getInt("settings.timer.fight.duration");
    }

    public String getFightGameMode(){
        return plugin.getConfig().getString("settings.timer.fight.gamemode");
    }

    public boolean checkFightTime(int i){
        return plugin.getConfig().get("settings.timer.fight.actions." + i) instanceof ConfigurationSection;
    }

    public boolean checkFightAll(){
        return plugin.getConfig().get("settings.timer.fight.actions.all") instanceof ConfigurationSection;
    }

    public String getFightAllActionbarToPlayers(){
            if (plugin.getConfig().contains("settings.timer.fight.actions.all.actionbar-to-players")
                    && !plugin.getConfig().getString("settings.timer.fight.actions.all.actionbar-to-players").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.fight.actions.all.actionbar-to-players");
            }return null;
    }

    public String getFightMessageToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.fight.actions." + i + ".message-to-players")
                    && !plugin.getConfig().getString("settings.timer.fight.actions." + i + ".message-to-players").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.fight.actions." + i + ".message-to-players");
            } return null;
    }

    public List<String> getFightTitleToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.fight.actions." + i + ".title-to-players")) {
                return plugin.getConfig().getStringList("settings.timer.fight.actions." + i + ".title-to-players");
            } return null;
    }

    public String getFightSoundToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.fight.actions." + i + ".sound-to-players")
                    && !plugin.getConfig().getString("settings.timer.fight.actions." + i + ".sound-to-players.sound-id").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.fight.actions." + i + ".sound-to-players.sound-id");
            } return null;
    }

    public int getFightVolumeToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.fight.actions." + i + ".sound-to-players")) {
                return plugin.getConfig().getInt("settings.timer.fight.actions." + i + ".sound-to-players.volume");
            } return 1;
    }

    public float getFightPitchToPlayers(int i){
            if (plugin.getConfig().contains("settings.timer.fight.actions." + i + ".sound-to-players")) {
                return plugin.getConfig().getInt("settings.timer.fight.actions." + i + ".sound-to-players.pitch");
            } return 1;
    }

    public String getFightPlayersCommand(int i){
            if (plugin.getConfig().contains("settings.timer.fight.actions." + i + ".players-command")
                    && !plugin.getConfig().getString("settings.timer.fight.actions." + i + ".players-command").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.fight.actions." + i + ".players-command");
            } return null;
    }

    public String getFightConsoleCommand(int i){
            if (plugin.getConfig().contains("settings.timer.fight.actions." + i + ".console-command")
                    && !plugin.getConfig().getString("settings.timer.fight.actions." + i + ".console-command").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.fight.actions." + i + ".console-command");
            } return null;
    }

    public String getFightBroadcast(int i) {
            if (plugin.getConfig().contains("settings.timer.fight.actions." + i + ".broadcast")
                    && !plugin.getConfig().getString("settings.timer.fight.actions." + i + ".broadcast").equalsIgnoreCase("")) {
                return plugin.getConfig().getString("settings.timer.fight.actions." + i + ".broadcast");
            } return null;
    }

    //End - Reloading

    public int getEndTime(){
        return plugin.getConfig().getInt("settings.timer.end.duration");
    }

    public String getEndGameMode(){
        return plugin.getConfig().getString("settings.timer.end.gamemode");
    }

    public boolean getResultMessage(){
        if(plugin.getConfig().contains("settings.timer.end.actions.results-message")){
            return plugin.getConfig().getBoolean("settings.timer.end.actions.results-message");
        } return false;
    }

    public boolean getFakeExplosion(){
        if(plugin.getConfig().contains("settings.timer.end.actions.fake-explosion")){
            return plugin.getConfig().getBoolean("settings.timer.end.actions.fake-explosion");
        } return false;
    }

    public boolean getFirework(){
        if(plugin.getConfig().contains("settings.timer.end.actions.firework")){
            return plugin.getConfig().getBoolean("settings.timer.end.actions.firework");
        } return false;
    }

    public List<String> getEndTitleToWinner(){
        if (plugin.getConfig().contains("settings.timer.end.actions.title-to-winner")) {
            return plugin.getConfig().getStringList("settings.timer.end.actions.title-to-winner");
        } return null;
    }

    public List<String> getEndTitleToLoser(){
        if (plugin.getConfig().contains("settings.timer.end.actions.title-to-loser")) {
            return plugin.getConfig().getStringList("settings.timer.end.actions.title-to-loser");
        } return null;
    }

    public List<String> getEndTitleDraw(){
        if (plugin.getConfig().contains("settings.timer.end.actions.title-no-winner")) {
            return plugin.getConfig().getStringList("settings.timer.end.actions.title-no-winner");
        } return null;
    }

    public String getEndSoundToWinner(){
        if (plugin.getConfig().contains("settings.timer.end.actions.sound-to-winner")
                && !plugin.getConfig().getString("settings.timer.end.actions.sound-to-winner.sound-id").equalsIgnoreCase("")) {
            return plugin.getConfig().getString("settings.timer.end.actions.sound-to-winner.sound-id");
        } return null;
    }

    public int getEndVolumeToWinner(){
        if (plugin.getConfig().contains("settings.timer.end.actions.sound-to-winner")) {
            return plugin.getConfig().getInt("settings.timer.end.actions.sound-to-winner.volume");
        } return 1;
    }

    public float getEndPitchToWinner(){
        if (plugin.getConfig().contains("settings.timer.end.actions.sound-to-winner")) {
            return plugin.getConfig().getInt("settings.timer.end.actions.sound-to-winner.pitch");
        } return 1;
    }

    public String getEndSoundToLoser(){
        if (plugin.getConfig().contains("settings.timer.end.actions.sound-to-loser")
                && !plugin.getConfig().getString("settings.timer.end.actions.sound-to-loser.sound-id").equalsIgnoreCase("")) {
            return plugin.getConfig().getString("settings.timer.end.actions.sound-to-loser.sound-id");
        } return null;
    }

    public int getEndVolumeToLoser(){
        if (plugin.getConfig().contains("settings.timer.end.actions.sound-to-loser")) {
            return plugin.getConfig().getInt("settings.timer.end.actions.sound-to-loser.volume");
        } return 1;
    }

    public float getEndPitchToLoser(){
        if (plugin.getConfig().contains("settings.timer.end.actions.sound-to-loser")) {
            return plugin.getConfig().getInt("settings.timer.end.actions.sound-to-loser.pitch");
        } return 1;
    }

    public String getEndSoundNoWinner(){
        if (plugin.getConfig().contains("settings.timer.end.actions.sound-no-winner")
                && !plugin.getConfig().getString("settings.timer.end.actions.sound-no-winner.sound-id").equalsIgnoreCase("")) {
            return plugin.getConfig().getString("settings.timer.end.actions.sound-no-winner.sound-id");
        } return null;

    }

    public int getEndVolumeNoWinner(){
        if (plugin.getConfig().contains("settings.timer.end.actions.sound-no-winner")) {
            return plugin.getConfig().getInt("settings.timer.end.actions.sound-no-winner.volume");
        } return 1;
    }

    public float getEndPitchNoWinner(){
        if (plugin.getConfig().contains("settings.timer.end.actions.sound-no-winner")) {
            return plugin.getConfig().getInt("settings.timer.end.actions.sound-no-winner.pitch");
        } return 1;
    }

    public String getEndActionbarToWinner(){
        if (plugin.getConfig().contains("settings.timer.end.actions.actionbar-to-winner")
                && !plugin.getConfig().getString("settings.timer.end.actions.actionbar-to-winner").equalsIgnoreCase("")) {
            return plugin.getConfig().getString("settings.timer.end.actions.actionbar-to-winner");
        } return null;
    }

    public String getEndActionbarToLoser(){
        if (plugin.getConfig().contains("settings.timer.end.actions.actionbar-to-loser")
                && !plugin.getConfig().getString("settings.timer.end.actions.actionbar-to-loser").equalsIgnoreCase("")) {
            return plugin.getConfig().getString("settings.timer.end.actions.actionbar-to-loser");
        } return null;
    }

    public String getEndActionbarDraw(){
        if (plugin.getConfig().contains("settings.timer.end.actions.actionbar-no-winner")
                && !plugin.getConfig().getString("settings.timer.end.actions.actionbar-no-winner").equalsIgnoreCase("")) {
            return plugin.getConfig().getString("settings.timer.end.actions.actionbar-no-winner");
        } return null;
    }

}
