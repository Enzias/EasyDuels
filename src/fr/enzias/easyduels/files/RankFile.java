package fr.enzias.easyduels.filemanager.files;

import fr.enzias.easyduels.EasyDuels;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RankFile {

    private final EasyDuels plugin;
    private File file;
    private FileConfiguration config;

    public RankFile(EasyDuels plugin) {
        this.plugin = plugin;
    }

    public void setup(){
        file = new File(plugin.getDataFolder(), "rank.yml");

        if(!file.exists()) {
            plugin.saveResource("rank.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(file);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public void save(){
        try{
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        save();
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig(){
        return config;
    }

    //setings

    public boolean getRank(){
        if(getConfig().contains("settings.enable"))
            return getConfig().getBoolean("settings.enable");
        else
            return false;
    }

    public boolean getInheritance(){
        if(getConfig().contains("settings.rank-perms-inheritance"))
            return getConfig().getBoolean("settings.rank-perms-inheritance");
        else
            return false;
    }

    public boolean getChecker(){
        if(getConfig().contains("settings.rank-perms-checker"))
            return getConfig().getBoolean("settings.rank-perms-checker");
        else
            return false;
    }

    public String getNoRankName(){
        if (getConfig().contains("settings.no-rank-name")
                && !getConfig().getString("settings.no-rank-name").equalsIgnoreCase("")) {
            return getConfig().getString("settings.no-rank-name");
        } return "&7None";
    }

    //progression

    public String getFormula(){
        if (getConfig().contains("settings.progression.level-formula")
                && !getConfig().getString("settings.progression.level-formula").equalsIgnoreCase("")) {
            return getConfig().getString("settings.progression.level-formula");
        } return "3000*Math.pow(1.025, level)-3000";
    }

    //rank up actions

    public boolean checkRankUp(){
        return getConfig().get("settings.progression.rank-up-actions") instanceof ConfigurationSection;
    }

    public String getRankMessageToPlayers(){
        if (getConfig().contains("settings.progression.rank-up-actions.message-to-players")
                && !getConfig().getString("settings.progression.rank-up-actions.message-to-players").equalsIgnoreCase("")) {
            return getConfig().getString("settings.progression.rank-up-actions.message-to-players");
        } return null;
    }

    public List<String> getRankTitleToPlayers(){
        if (getConfig().contains("settings.progression.rank-up-actions.title-to-players")) {
            return getConfig().getStringList("settings.progression.rank-up-actions.title-to-players");
        } return null;
    }

    public String getRankSoundToPlayers(){
        if (getConfig().contains("settings.progression.rank-up-actions.sound-to-players")
                && !getConfig().getString("settings.progression.rank-up-actions.sound-to-players.sound-id").equalsIgnoreCase("")) {
            return getConfig().getString("settings.progression.rank-up-actions.sound-to-players.sound-id");
        } return null;
    }

    public int getRankVolumeToPlayers(){
        if (getConfig().contains("settings.progression.rank-up-actions.sound-to-players.volume")) {
            return getConfig().getInt("settings.progression.rank-up-actions.sound-to-players.volume");
        } return 1;
    }

    public float getRankPitchToPlayers(){
        if (getConfig().contains("settings.progression.rank-up-actions.sound-to-players.pitch")) {
            return getConfig().getInt("settings.progression.rank-up-actions.sound-to-players.pitch");
        } return 1;
    }

    public String getRankActionbarToPlayers(){
        if (getConfig().contains("settings.progression.rank-up-actions.actionbar-to-players")
                && !getConfig().getString("settings.progression.rank-up-actions.actionbar-to-players").equalsIgnoreCase("")) {
            return getConfig().getString("settings.progression.rank-up-actions.actionbar-to-players");
        } return null;
    }

    public String getRankBroadcast(){
        if (getConfig().contains("settings.progression.rank-up-actions.broadcast")
                && !getConfig().getString("settings.progression.rank-up-actions.broadcast").equalsIgnoreCase("")) {
            return getConfig().getString("settings.progression.rank-up-actions.broadcast");
        } return null;
    }

    //level up actions

    public boolean checkLevelUp(){
        return getConfig().get("settings.progression.level-up-actions") instanceof ConfigurationSection;
    }

    public String getLevelMessageToPlayers(){
        if (getConfig().contains("settings.progression.level-up-actions.message-to-players")
                && !getConfig().getString("settings.progression.level-up-actions.message-to-players").equalsIgnoreCase("")) {
            return getConfig().getString("settings.progression.level-up-actions.message-to-players");
        } return null;
    }

    public List<String> getLevelTitleToPlayers(){
        if (getConfig().contains("settings.progression.level-up-actions.title-to-players")) {
            return getConfig().getStringList("settings.progression.level-up-actions.title-to-players");
        } return null;
    }

    public String getLevelSoundToPlayers(){
        if (getConfig().contains("settings.progression.level-up-actions.sound-to-players")
                && !getConfig().getString("settings.progression.level-up-actions.sound-to-players.sound-id").equalsIgnoreCase("")) {
            return getConfig().getString("settings.progression.level-up-actions.sound-to-players.sound-id");
        } return null;
    }

    public int getLevelVolumeToPlayers(){
        if (getConfig().contains("settings.progression.level-up-actions.sound-to-players.volume")) {
            return getConfig().getInt("settings.progression.level-up-actions.sound-to-players.volume");
        } return 1;
    }

    public float getLevelPitchToPlayers(){
        if (getConfig().contains("settings.progression.level-up-actions.sound-to-players.pitch")) {
            return getConfig().getInt("settings.progression.level-up-actions.sound-to-players.pitch");
        } return 1;
    }

    public String getLevelActionbarToPlayers(){
        if (getConfig().contains("settings.progression.level-up-actions.actionbar-to-players")
                && !getConfig().getString("settings.progression.level-up-actions.actionbar-to-players").equalsIgnoreCase("")) {
            return getConfig().getString("settings.progression.level-up-actions.actionbar-to-players");
        } return null;
    }

    public String getLevelBroadcast(){
        if (getConfig().contains("settings.progression.level-up-actions.broadcast")
                && !getConfig().getString("settings.progression.level-up-actions.broadcast").equalsIgnoreCase("")) {
            return getConfig().getString("settings.progression.level-up-actions.broadcast");
        } return null;
    }

    //other settings

    public List<String> getLevelUpRewardCommands(){
        if (getConfig().contains("settings.progression.level-up-reward-commands")) {
            return getConfig().getStringList("settings.progression.level-up-reward-commands");
        } return null;
    }

    public int getMaxLevel(){
        if(getConfig().contains("settings.progression.maximum-level"))
            return getConfig().getInt("settings.progression.maximum-level");
        else return -1;
    }

    public int getLevelCoolDown(){
        if(getConfig().contains("settings.progression.cooldown"))
            return getConfig().getInt("settings.progression.cooldown");
        else return 0;
    }

    //duel reward

    public List<Long> getWinnerRange(){
        if(getConfig().contains("settings.progression.duel-reward.winner-range")){
            String s = getConfig().getString("settings.progression.duel-reward.winner-range");
            if(s == null)
                return Collections.singletonList(0L);
            if(s.contains("-"))
                return Arrays.asList(Long.valueOf(s.split("-")[0]), Long.valueOf(s.split("-")[1]));
        }
        return Collections.singletonList(0L);
    }

    public List<Long> getNoWinnerRange(){
        if(getConfig().contains("settings.progression.duel-reward.no-winner-range")){
            String s = getConfig().getString("settings.progression.duel-reward.no-winner-range");
            if(s == null)
                return Collections.singletonList(0L);
            if(s.contains("-"))
                return Arrays.asList(Long.valueOf(s.split("-")[0]), Long.valueOf(s.split("-")[1]));
        }
        return Collections.singletonList(0L);
    }
    public List<Long> getLoserRange(){
        if(getConfig().contains("settings.progression.duel-reward.loser-range")){
            String s = getConfig().getString("settings.progression.duel-reward.loser-range");
            if(s == null)
                return Collections.singletonList(0L);
            if(s.contains("-"))
                return Arrays.asList(Long.valueOf(s.split("-")[0]), Long.valueOf(s.split("-")[1]));
        }
            return Collections.singletonList(0L);
    }

    //ranks

    public List<String> getLevelRank(){
        List<String> list = new ArrayList<>();
        if(getConfig().contains("ranks"))
            list.addAll(getConfig().getConfigurationSection("ranks").getKeys(false));
        return list;
    }

    public String getRankName(int i){
        if (getConfig().contains("ranks." + i + ".name")
                && !getConfig().getString("ranks." + i + ".name").equalsIgnoreCase("")) {
            return getConfig().getString("ranks." + i + ".name");
        } return null;
    }

    public List<String> getRankPermissionNode(int i){
        if (getConfig().contains("ranks." + i + ".permission")) {
            return getConfig().getStringList("ranks." + i + ".permission");
        } return null;
    }

    public List<String> getRankRewardCommands(int i){
        if (getConfig().contains("ranks." + i + ".reward-commands")) {
            return getConfig().getStringList("ranks." + i + ".reward-commands");
        } return null;
    }

}
