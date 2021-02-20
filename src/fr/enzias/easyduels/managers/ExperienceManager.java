package fr.enzias.easyduels.managers;

import fr.enzias.easyduels.EasyDuels;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ExperienceManager {

    private final EasyDuels plugin;
    private HashMap<UUID, Long> players;
    public ExperienceManager(EasyDuels plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
    }

    public boolean hasExperience(Player player){
        return this.players.containsKey(player.getUniqueId());
    }

    public boolean hasEnoughExperience(Player player, long amount){
        return hasExperience(player) && getExperience(player) >= amount;
    }

    public void putInExperience(Player player, Long exp){
        if(hasExperience(player))
            this.players.replace(player.getUniqueId(), exp);
        else
            this.players.put(player.getUniqueId(), exp);
    }

    public void deleteInExperience(Player player){
        long exp = this.players.get(player.getUniqueId());
        this.players.remove(player.getUniqueId());
    }

    public Long getExperience(Player player){
        return this.players.get(player.getUniqueId());
    }

    public void addExperience(Player player, Long amount){
        long oldExp = getExperience(player);
        if(hasExperience(player))
            this.players.replace(player.getUniqueId(), amount+oldExp);
        else
            this.players.put(player.getUniqueId(), amount);
    }

    public Long addAndGetExperience(Player player, Long amount){
        addExperience(player, amount);
        return getExperience(player);
    }

    public void removeExperience(Player player, Long amount){
        long oldExp = getExperience(player);
        if(hasEnoughExperience(player, amount))
            this.players.replace(player.getUniqueId(), oldExp-amount);
        else if(hasExperience(player))
            this.players.replace(player.getUniqueId(), 0L);
        else
            this.players.put(player.getUniqueId(), 0L);

    }

    public Long removeAndGetExperience(Player player, Long amount){
        removeExperience(player, amount);
        return getExperience(player);
    }

    public void clearExperienceList(){
        this.players.clear();
    }

    public HashMap<UUID, Long> getPlayers() {
        return players;
    }
}
