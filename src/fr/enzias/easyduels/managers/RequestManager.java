package fr.enzias.easyduels.managers;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.tasks.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class RequestManager {

    private final EasyDuels plugin;
    private HashMap<UUID, UUID> requests = new HashMap<UUID, UUID>();
    private HashMap<UUID, BukkitTask> tasks = new HashMap<UUID, org.bukkit.scheduler.BukkitTask>();
    private HashMap<UUID, Integer> money = new HashMap<UUID, Integer>();
    SettingsFile settings;
    public RequestManager(EasyDuels plugin) {
        this.plugin = plugin;
        settings = plugin.getSettingsFile();
    }

    public void addRequest(Player firstPlayer, Player secondPlayer){
        requests.put(firstPlayer.getUniqueId(), secondPlayer.getUniqueId());

        if(settings.getSyncTimer()){
            tasks.put(firstPlayer.getUniqueId(), Bukkit.getScheduler().runTaskTimer(plugin, new Cooldown(plugin, firstPlayer), 0,20));
        }
        else{
            tasks.put(firstPlayer.getUniqueId(), Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Cooldown(plugin, firstPlayer), 0, 20));
        }
    }

    public void addRequest(Player firstPlayer, Player secondPlayer, int money){
        requests.put(firstPlayer.getUniqueId(), secondPlayer.getUniqueId());
        this.money.put(firstPlayer.getUniqueId(), money);

        if(settings.getSyncTimer()){
            tasks.put(firstPlayer.getUniqueId(), Bukkit.getScheduler().runTaskTimer(plugin, new Cooldown(plugin, firstPlayer), 0,20));
        }
        else{
            tasks.put(firstPlayer.getUniqueId(), Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Cooldown(plugin, firstPlayer), 0, 20));
        }
    }

    public void deleteRequest(Player player){
        requests.remove(player.getUniqueId());
        tasks.get(player.getUniqueId()).cancel();
        tasks.remove(player.getUniqueId());
        money.remove(player.getUniqueId());
    }

    public boolean hasRequest(Player player){
        return  requests.containsKey(player.getUniqueId());
    }

    public boolean hasBet(Player player){
        return money.containsKey(player.getUniqueId());
    }

    public int getBet(Player player){
        return money.get(player.getUniqueId());
    }

    public boolean hasRequestFrom(Player player, Player target){
        return requests.get(player.getUniqueId()).equals(target.getUniqueId());
    }

}
