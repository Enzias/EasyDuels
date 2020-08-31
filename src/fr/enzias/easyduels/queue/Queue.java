package fr.enzias.easyduels.queue;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.utils.DuelPlayerCache;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;

public class Queue {

    private final EasyDuels plugin;
    private LinkedList<Player> queue = new LinkedList<>();
    private HashMap<Player, DuelPlayerCache> cache = new HashMap<>();
    public Queue(EasyDuels plugin) {
        this.plugin = plugin;
    }

    public void add(Player player, DuelPlayerCache cache){
        queue.addLast(player);
        this.cache.put(player, cache);
    }

    public Player getFirst(){
        return queue.getFirst();
    }

    public Player getAndRemoveFirst(){
        return queue.removeFirst();
    }

    public Player get(int i){
        return queue.get(i);
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public boolean isInQueue(Player player){
        return queue.contains(player);
    }

    public void delete(Player player){
        queue.remove(player);
    }

    public void clear(){
        queue.clear();
    }

    public int getPosition(Player player){
        return queue.indexOf(player) + 1;
    }

    public int getQueueLength(){
        return queue.size();
    }

    public DuelPlayerCache getCache(Player player) {
        return cache.get(player);
    }

    public LinkedList<Player> getQueue() {
        return queue;
    }
}
