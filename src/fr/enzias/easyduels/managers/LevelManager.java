package fr.enzias.easyduels.managers;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.filemanager.files.RankFile;
import fr.enzias.easyduels.ranks.RankManager;
import fr.enzias.easyduels.storage.JsonFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

public class LevelManager {

    private final EasyDuels plugin;
    private HashMap<String, Long> cooldowns;
    private LinkedHashMap<Integer, Long> requiredExp;
    private ExperienceManager experienceManager;
    private RankManager rankManager;
    private JsonFile storage;
    private RankFile rankFile;

    public LevelManager(EasyDuels plugin) {
        this.plugin = plugin;
        this.cooldowns = new HashMap<>();
        this.requiredExp = new LinkedHashMap<>();
        this.experienceManager = new ExperienceManager(plugin);
        this.rankManager = new RankManager(plugin);
        this.storage = new JsonFile(plugin);
        this.rankFile = plugin.getRankFile();
    }

    public boolean getActive(){
        return rankFile.getRank();
    }

    public boolean setup(){
        if(getActive()){

            rankManager.initiateRanks();
            initiateExpLevel();
            setExperienceFromStorage();

            Bukkit.getLogger().info("[EasyDuels] " + (this.requiredExp.size()-1) + " levels successfully calculated.");
            Bukkit.getLogger().info("[EasyDuels] " + rankManager.getRanks().size() + " ranks successfully saved.");

            return true;
        }
            return false;
    }

    public String getMaxLevel(){
        return String.valueOf(rankFile.getMaxLevel());
    }

    private void initiateExpLevel(){

        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("JavaScript");

        this.requiredExp.put(0, 0L);

        for(int level = 1; level <= rankFile.getMaxLevel(); level++){
            String formula = rankManager.getFormula(level);
            long exp = 0L;
            try {
                exp = Math.round((Double) engine.eval(formula));
            } catch (ScriptException e) {
                e.printStackTrace();
            }
            this.requiredExp.put(level, exp);
        }
    }

    /**
    * Get player's experience from his data file
    * @param player The player
    */
    private long getExpFromStorage(Player player){
        storage.setFile(player);
        return storage.getExperience();
    }

    /**
     * Get player's experience from the exp manager
     * @param player The player
     */
    public long getExpFromManager(Player player){
        return experienceManager.getExperience(player);
    }

    /**
     * Set player's experience in the exp manager from data file
     * @param player The player
     */
    public void setExperienceFromStorage(Player player){
        long exp = getExpFromStorage(player);
        experienceManager.putInExperience(player, exp);
    }

    /**
     * Set player's experience in the exp manager from data file for every connected players
     */
    public void setExperienceFromStorage(){
        for(Player player : Bukkit.getOnlinePlayers()) {
            setExperienceFromStorage(player);
        }
    }

    /**
     * Set player's experience in the exp manager from amount
     * @param player The player
     * @param exp The amount of experience
     */
    private void setExperienceFromAmount(Player player, long exp){
        experienceManager.putInExperience(player, exp);
    }

    /**
     * Add amount of experience in the manager
     * @param player The player
     * @param exp The amount to add
     * @return The player's experience after adding
     */
    private Long addAndGetExperience(Player player, long exp){
        return experienceManager.addAndGetExperience(player, exp);
    }

    /**
     * Remove amount of experience in the manager
     * @param player The player
     * @param exp The amount to remove
     * @return The player's experience after removing
     */
    private Long removeAndGetExperience(Player player, long exp){
        return experienceManager.removeAndGetExperience(player, exp);
    }

    /**
     * Get the required exp needed for a level
     * @param level The level
     */
    private long getRequiredExperience(int level){
        return this.requiredExp.get(level);
    }

    /**
     * Get the player level depending on his experience
     * @param player The player
     */
    public int getLevel(Player player){
        long experience = getExpFromManager(player);
        return getLevelAtExp(experience);
    }

    /**
     * Get the level according to the experience
     * @param exp the given experience
     * @return the according level
     */
    private int getLevelAtExp(long exp){
        for(int i = 1; i <= rankFile.getMaxLevel(); i++){
            long requiredExperience = getRequiredExperience(i);
            if(exp < requiredExperience)
                return i-1;
        }
        return rankFile.getMaxLevel();
    }

    /**
     * Get the experience required to level up
     * @param player The player
     */
    public long getExpToNextLevel(Player player){

        long playerExperience = getExpFromManager(player);
        int level = getLevel(player);
        if(level >= rankFile.getMaxLevel())
            return 0L;
        return getRequiredExperience(level+1)-playerExperience;
    }

    /**
     * Get the level of the rank of the player
     * @param player The player
     * @return The rank level
     */
    private int getRankLevel(Player player){
        return rankManager.getRankLevelOnLevel(getLevel(player));
    }

    public String getRank(Player player){
        if(rankManager.isUpperZRank(getLevel(player)))
            return rankManager.getRankOnLevel(rankManager.getRankLevelOnLevel(getLevel(player))).getName();
        else return rankFile.getNoRankName();
    }

    /**
     * Save all experience data in storage files
     */
    public void saveAllExpData(){
        for(Map.Entry<UUID, Long> entry : experienceManager.getPlayers().entrySet()){
            storage.setFile(entry.getKey());
            storage.setExperience(entry.getValue());
        }
    } //not useful at the moment

    /**
     * Save experience of a player in his storage file
     * @param player The player
     */
    public void saveExpData(Player player){
        storage.setFile(player);
        storage.setExperience(getExpFromManager(player));
    } //on modification TODO

    /**
     * Save all experience data in storage files
     * And delete all players in the manager list
     */
    public void saveAllExpDataAndDelete(){
        saveAllExpData();
        experienceManager.clearExperienceList();
    }

    /**
     * Save experience of a player in his storage file
     * And delete the player in the experience manager
     * @param player The player
     */
    public void saveExpDataAndDelete(Player player){
        saveExpData(player);
        experienceManager.deleteInExperience(player);
    }

    /**
     * Get a random number between range
     * @param min Minimum value
     * @param max Maximum value
     * @return Random value
     */
    private long getRandomInclusive(long min, long max){
        if (min >= max) {
            long temp = min;
            min = max;
            max = temp;
        }

        Random r = new Random();
        return (r.nextInt((int) ((max - min) + 1)) + min);
    }

    /**
     * Get random amount of exp to give to the winner
     * @return The random amount
     */
    public long getWinnerRewardExp(){
        if(rankFile.getWinnerRange().size() == 1)
            return rankFile.getWinnerRange().get(0);

        else if(rankFile.getWinnerRange().size() == 2)
            return getRandomInclusive(rankFile.getWinnerRange().get(0), rankFile.getWinnerRange().get(1));

        else
            return 0L;
    }

    /**
     * Get random amount of exp to give to players
     * @return The random amount
     */
    public long getNoWinnerRewardExp(){
        if(rankFile.getNoWinnerRange().size() == 1)
            return rankFile.getNoWinnerRange().get(0);

        else if(rankFile.getNoWinnerRange().size() == 2)
            return getRandomInclusive(rankFile.getNoWinnerRange().get(0), rankFile.getNoWinnerRange().get(1));

        else
            return 0L;
    }

    /**
     * Get random amount of exp to give to the loser
     * @return The random amount
     */
    public long getLoserRewardExp(){
        if(rankFile.getLoserRange().size() == 1)
            return rankFile.getLoserRange().get(0);

        else if(rankFile.getLoserRange().size() == 2)
            return getRandomInclusive(rankFile.getLoserRange().get(0), rankFile.getLoserRange().get(1));

        else
            return 0L;
    }

    /**
     * Give exp to a player with a cooldown
     * @param player The player
     * @param amount The amount to add
     */
    public void giveExpLegit(Player player, long amount){
        giveExpForce(player, amount);
        addCooldown(player);
    }

    /**
     * Give exp to a player without adding a cooldown
     * @param player The player
     * @param amount The amount to add
     */
    public void giveExpForce(Player player, long amount){

        long oldExp = getExpFromManager(player);
        long newExp = addAndGetExperience(player, amount);

        if(performRankUP(player, getLevelAtExp(oldExp), getLevel(player)))
            performLevelUPNoSend(player, oldExp, newExp);
        else
            performLevelUP(player, oldExp, newExp);
    }

    /**
     * Set the experience of a player
     * @param player The player
     * @param amount The amount to add
     */
    public void setExp(Player player, long amount){
        long oldExp = getExpFromManager(player);
        setExperienceFromAmount(player, amount);

        if(performRankUP(player, getLevelAtExp(oldExp), getLevel(player)))
            performLevelUPNoSend(player, oldExp, getExpFromManager(player));
        else
            performLevelUP(player, oldExp, getExpFromManager(player));

        checkPermission(player);
    }

    /**
     * Take exp to a player
     * @param player The player
     * @param amount The amount to take
     */
    public void takeExp(Player player, long amount){

        removeAndGetExperience(player, amount);
        checkPermission(player);
    }

    /**
     * Reset exp of a player
     * @param player The player
     */
    public void resetExp(Player player){
        setExperienceFromAmount(player, 0L);
        checkPermission(player);
    }

    /**
     * Give exp to all connected players
     * @param amount The amount
     */
    public void giveAllExp(long amount){
        for(Player player : Bukkit.getOnlinePlayers())
            giveExpForce(player, amount);
    }

    /**
     * Take exp to all connected players
     * @param amount The amount to take
     */
    public void takeAllExp(long amount){
        for(Player player : Bukkit.getOnlinePlayers())
            takeExp(player, amount);
    }

    /**
     * Set the experience of all connected players
     * @param amount The amount to take
     */
    public void setAllExp(long amount){
        for(Player player : Bukkit.getOnlinePlayers())
            setExp(player, amount);
    }

    /**
     * Reset the experience of all connected players
     */
    public void resetAllExp(){
        for(Player player : Bukkit.getOnlinePlayers())
            resetExp(player);
    }

    /**
     * Check if a player has leveled up and send permissions, rewards and level up messages
     * @param player The player
     * @param oldExp Exp of the player before give
     * @param newExp Exp of the player after give
     */
    public void performLevelUP(Player player, long oldExp, long newExp){
        performLevelUPNoSend(player, oldExp, newExp);

        if(oldExp >= newExp)
            return;

        int oldLevel = getLevelAtExp(oldExp);
        int newLevel = getLevelAtExp(newExp);

        if(oldLevel >= newLevel)
            return;

        rankManager.sendLevelUp(newLevel, player);
    }

    private void performLevelUPNoSend(Player player, long oldExp, long newExp){
        if(oldExp >= newExp)
            return;

        int oldLevel = getLevelAtExp(oldExp);
        int newLevel = getLevelAtExp(newExp);

        if(oldLevel >= newLevel)
            return;

        rankManager.sendLevelUpReward(player, newLevel-oldLevel);
    }

    /**
     * Check if a player has ranked up and send permissions, rewards and rank up messages
     * @param player The player
     * @param oldLevel Level of the player before give
     * @param newLevel Level of the player after give
     */
    private boolean performRankUP(Player player, int oldLevel, int newLevel){
        if(oldLevel >= newLevel)
            return false;

        int oldRank = rankManager.getRankLevelOnLevel(oldLevel);
        int newRank = rankManager.getRankLevelOnLevel(newLevel);

        if(oldRank >= newRank)
            return false;

        List<Integer> done = Arrays.asList(oldRank);
        done = new ArrayList<>(done);

        for(int i = oldRank+1; i <= newRank; i++){
            int rank = rankManager.getRankLevelOnLevel(i);
            if(!done.contains(rank)){
                rankManager.sendRankUpReward(rank, player);
                done.add(rank);
            }
        }
        sendPermissionAndLower(player, newRank);
        deleteUpperPerms(player, newRank);
        rankManager.sendRankUp(newRank, player);
        return true;
    }

    /**
     * Check if the player has good set permissions
     * @param player The player
     */
    public void checkPermission(Player player){
        sendPermissionAndLower(player, getRankLevel(player));
        deleteUpperPerms(player, getRankLevel(player));
    }

    /**
     * Add the permissions of all lower ranks than the player's rank and player's rank permissions
     * @param player The player
     * @param rank The player's rank
     */
    private void sendPermissionAndLower(Player player, int rank){

        rankManager.sendPermission(rank, player);

        if(rankFile.getInheritance()) {
            List<Integer> done = Arrays.asList(rank);
            done = new ArrayList<>(done);

            for (int i = 0; i < rank; i++) {
                int node = rankManager.getRankLevelOnLevel(i);
                if (!done.contains(node) && node <= rank) {
                    rankManager.sendPermission(node, player);
                    done.add(node);
                }
            }
        }
    }

    /**
     * Delete the permissions of all upper ranks than the player's rank
     * @param player The player
     * @param rank The player's rank
     */
    private void deleteUpperPerms(Player player, int rank){
        if(rankFile.getChecker()) {
            List<Integer> done = Arrays.asList(rank);
            done = new ArrayList<>(done);

            for (int i = rank + 1; i <= rankFile.getMaxLevel(); i++) {
                int node = rankManager.getRankLevelOnLevel(i);
                if (!done.contains(node) && node > rank) {
                    rankManager.removePermission(node, player);
                    done.add(node);
                }
            }
        }
    }

    /**
     * Set the cooldown for a player
     * @param player The player
     */
    private void addCooldown(Player player){
        if(cooldowns.containsKey(player.getName()))
            this.cooldowns.replace(player.getName(), System.currentTimeMillis() + rankFile.getLevelCoolDown()* 1000L);
        else
            this.cooldowns.put(player.getName(), System.currentTimeMillis() + rankFile.getLevelCoolDown()*1000L);
    }

    public int getCoolDown(Player player){
        if(canGain(player))
            return 0;
        return (int) ((cooldowns.get(player.getName()) - System.currentTimeMillis())/1000);
    }

    /**
     * If a player can gain level - If he has cooldown
     * @param player The player
     */
    public boolean canGain(Player player){
        if(cooldowns.containsKey(player.getName()))
            return !(cooldowns.get(player.getName()) > System.currentTimeMillis());
        else return true;
    }

    public boolean isValidAmount(String amount){
        try{
            long i = Long.parseLong(amount);
            return (i > 0);
        }catch (NumberFormatException e){
            return false;
        }
    }

    public Long getValidAmount(String amount){
        return Long.parseLong(amount);
    }

}
