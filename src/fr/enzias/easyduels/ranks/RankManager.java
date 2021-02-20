package fr.enzias.easyduels.ranks;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.filemanager.files.RankFile;
import fr.enzias.easyduels.managers.versions.SenderManager;
import fr.enzias.easyduels.utils.VaultHook;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class RankManager {

    private final EasyDuels plugin;
    private HashMap<Integer, Rank> ranks;
    private RankFile rankFile;
    private VaultHook vaultHook;
    private SenderManager sender;
    public RankManager(EasyDuels plugin) {
        this.plugin = plugin;
        this.rankFile = plugin.getRankFile();
        this.vaultHook = plugin.getVaultHook();
        this.sender = plugin.getSender();
    }

    /**
     * Add all ranks in config to the rank list
     */
    public void initiateRanks(){
        ranks = new HashMap<>();
        if(rankFile.getLevelRank() != null)
            for(String s : rankFile.getLevelRank()) {
                addRank(Integer.parseInt(s));
            }
    }

    public String getFormula(int level){
        return rankFile.getFormula().replaceAll("%level%", String.valueOf(level));
    }

    /**
     * Get the level of the rank associate to a level
     * @param level The level
     * @return The level of the rank
     */
    public int getRankLevelOnLevel(int level){
        int rankLevel = 0;
        for(Integer i : ranks.keySet()){
            if(level >= i && i >= rankLevel)
                rankLevel = i;
        }
        return rankLevel;
    }

    /**
     * Check if the level doesn't correspond to the rank 0
     * @param level The level
     * @return False if the rank associate to the level is the rank 0
     */
    public boolean isUpperZRank(int level){
        return getRankLevelOnLevel(level) != 0;
    }

    /**
     * Send messages of rank up
     * @param rank The rank
     * @param player The player
     */
    public void sendRankUp(int rank, Player player){
        if(isUpperZRank(rank)) {
            if (rankFile.checkRankUp()) {
                if (rankFile.getRankMessageToPlayers() != null)
                    sender.sendMessage(rankFile.getRankMessageToPlayers()
                            .replaceAll("%rank%", getRankOnLevel(rank).getName()), player);
                if (rankFile.getRankTitleToPlayers() != null)
                    sender.sendTitlePlaceHolders(rankFile.getRankTitleToPlayers(), 20, 100, 20, player,
                            "%rank%", getRankOnLevel(rank).getName());
                if (rankFile.getRankSoundToPlayers() != null)
                    sender.sendSound(rankFile.getRankSoundToPlayers(), rankFile.getRankVolumeToPlayers(), rankFile.getRankPitchToPlayers(), player);
                if (rankFile.getRankActionbarToPlayers() != null)
                    sender.sendActionbar(rankFile.getRankActionbarToPlayers()
                            .replaceAll("%rank%", getRankOnLevel(rank).getName()), 20, 100, 20, player);
                if (rankFile.getRankBroadcast() != null)
                    sender.sendBroadcast(rankFile.getRankBroadcast()
                            .replaceAll("%rank%", getRankOnLevel(rank).getName())
                            .replaceAll("%player%", player.getName()), plugin.getSettingsFile().getSyncTimer());
            }
        }
    }

    /**
     * Send messages of level up
     * @param level The level
     * @param player The player
     */
    public void sendLevelUp(int level, Player player){
        if(rankFile.checkLevelUp()) {
            if (rankFile.getLevelMessageToPlayers() != null)
                sender.sendMessage(rankFile.getLevelMessageToPlayers()
                        .replaceAll("%level%", String.valueOf(level)), player);
            if (rankFile.getLevelTitleToPlayers() != null)
                sender.sendTitlePlaceHolders(rankFile.getLevelTitleToPlayers(), 20, 100, 20, player,
                        "%level%", String.valueOf(level));
            if (rankFile.getLevelSoundToPlayers() != null)
                sender.sendSound(rankFile.getLevelSoundToPlayers(), rankFile.getLevelVolumeToPlayers(), rankFile.getLevelPitchToPlayers(), player);
            if (rankFile.getLevelActionbarToPlayers() != null)
                sender.sendActionbar(rankFile.getLevelActionbarToPlayers()
                        .replaceAll("%level%", String.valueOf(level)), 20, 100, 20, player);
            if (rankFile.getLevelBroadcast() != null)
                sender.sendBroadcast(rankFile.getLevelBroadcast()
                        .replaceAll("%level%", String.valueOf(level))
                        .replaceAll("%player%", player.getName()), plugin.getSettingsFile().getSyncTimer());
        }
    }

    /**
     * Send rank up command rewards
     * @param rank The rank
     * @param player The player
     */
    public void sendRankUpReward(int rank, Player player){
        if(isUpperZRank(rank) && getRankOnLevel(rank).getRewardCommands() != null)
            sender.sendConsoleCommand(getRankOnLevel(rank).getRewardCommands(), plugin.getSettingsFile().getSyncTimer(),
                "%player%", player.getName());
    }

    /**
     * Send level up command rewards
     * @param player The player
     */
    public void sendLevelUpReward(Player player, int rep){
        if(rankFile.getLevelUpRewardCommands() != null)
            sender.sendConsoleCommand(rankFile.getLevelUpRewardCommands(), plugin.getSettingsFile().getSyncTimer(),
                    "%player%", player.getName(), "%amount%", String.valueOf(rep));
    }

    /**
     * Check if a player has all permissions of a rank
     * @param rank The rank
     * @param player The player
     * @return True if he has
     */
    //Not useful at the moment
    public boolean hasPermission(int rank, Player player){
        return vaultHook.hasPermission(player, getRankOnLevel(rank).getPermissionNode());
    }

    /**
     * Send permission of a rank to a player
     * @param rank The rank
     * @param player The player
     */
    public void sendPermission(int rank, Player player){
        if(isUpperZRank(rank) && getRankOnLevel(rank).getPermissionNode() != null)
            vaultHook.hasAndAddPermission(player, getRankOnLevel(rank).getPermissionNode());
    }

    public void removePermission(int rank, Player player){
        if(isUpperZRank(rank) && getRankOnLevel(rank).getPermissionNode() != null)
            vaultHook.hasAndRemovePermission(player, getRankOnLevel(rank).getPermissionNode());
    }

    /**
     * Get the Rank depending on the level
     * @param level The level
     * @return The rank in the rank list
     */
    public Rank getRankOnLevel(int level){
        return this.ranks.get(level);
    }

    /**
     * Add a rank to the rank list
     * @param level The level of the rank
     */
    public void addRank(int level){
        if(rankFile.getRankName(level) != null)
            this.ranks.put(level, new Rank(rankFile.getRankName(level), rankFile.getRankPermissionNode(level),
                    rankFile.getRankRewardCommands(level)));
    }

    public HashMap<Integer, Rank> getRanks() {
        return ranks;
    }
}
