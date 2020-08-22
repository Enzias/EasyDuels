package fr.enzias.easyduels.arena;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.managers.TimerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Arena {

    private final EasyDuels plugin;
    private TimerManager timer;
    private Location firstLocation;
    private Location secondLocation;
    private int lobbyTime;
    private int playingTime;
    private int reloadingTime;
    private ArrayList<Player> players = new ArrayList<Player>();
    private HashMap<Player, Location> lastLocations = new HashMap<>();
    private ArenaStatuts statut;
    private Player winner = null;


    public Arena(EasyDuels plugin, Location firstLocation, Location secondLocation, int lobbyTime, int playingTime, int reloadingTime) {
        this.plugin = plugin;
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
        this.lobbyTime = lobbyTime;
        this.playingTime = playingTime;
        this.reloadingTime = reloadingTime;
        this.statut = ArenaStatuts.IDLE;
    }

    public void addToArena(Player firstPlayer, Player secondPlayer){
        getPlayers().clear();
        getPlayers().add(0, firstPlayer);
        getPlayers().add(1, secondPlayer);
    }

    public void deleteFromArena(Player player){
        getPlayers().remove(player);
    }

    public void resetArena(){
        setFirstLocation(plugin.getArenaFile().getFirstLocation());
        setSecondLocation(plugin.getArenaFile().getSecondLocation());
        setWinner(null);
        setLobbyTime(plugin.getSettingsFile().getLobbyTime());
        setPlayingTime(plugin.getSettingsFile().getFightTime());
        setReloadingTime(plugin.getSettingsFile().getEndTime());
    }

    public void teleportToLocation(Player firstPlayer, Player secondPlayer, boolean sync){
        if(sync) {
            firstPlayer.teleport(firstLocation);
            secondPlayer.teleport(secondLocation);
        }
        else {
            Bukkit.getScheduler().runTask(plugin, () -> {
                firstPlayer.teleport(firstLocation);
                secondPlayer.teleport(secondLocation);
            });
        }
    }

    public void teleportToLastLocation(Player player) {
            player.teleport(lastLocations.get(player));
    }

    public void startMatch(){

        setStatut(ArenaStatuts.LOBBY);

        timer = new TimerManager(plugin);
        if(plugin.getSettingsFile().getSyncTimer())
            timer.runTaskTimer(plugin, 0, 20);
        else
            timer.runTaskTimerAsynchronously(plugin, 0, 20);
    }

    public Location getFirstLocation(){
        return firstLocation;
    }
    public Location getSecondLocation(){
        return secondLocation;
    }
    public Location getLastLocation(Player player){
        return lastLocations.get(player);
    }
    public int getLobbyTime(){
        return lobbyTime;
    }
    public int getPlayingTime(){
        return playingTime;
    }
    public int getReloadingTime(){
        return reloadingTime;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public UUID getFirstPlayerUUID(){
        return players.get(0).getUniqueId();
    }
    public UUID getSecondPlayerUUID(){
        return players.get(1).getUniqueId();
    }
    public Player getFirstPlayer(){
        return players.get(0);
    }
    public Player getSecondPlayer(){
        return players.get(1);
    }

    public Player getWinner(){
        return winner;
    }
    public void setWinner(Player winner){
        this.winner = winner;
    }

    public void setFirstLocation(Location firstLocation) {
        this.firstLocation = firstLocation;
    }

    public void setSecondLocation(Location secondLocation) {
        this.secondLocation = secondLocation;
    }

    public void setLobbyTime(int lobbyTime) {
        this.lobbyTime = lobbyTime;
    }

    public void setPlayingTime(int playingTime) {
        this.playingTime = playingTime;
    }

    public void setReloadingTime(int reloadingTime) {
        this.reloadingTime = reloadingTime;
    }

    public void setLastLocation(Player... players){
        for(Player player : players)
            lastLocations.put(player, player.getLocation());
    }

    public void setStatut(ArenaStatuts statut){
        this.statut = statut;
    }
    public boolean isStatut(ArenaStatuts statut){
        return this.statut == statut;
    }


}
