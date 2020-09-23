package fr.enzias.easyduels.arena;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.files.SettingsFile;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Spectate {

    private final EasyDuels plugin;
    SettingsFile settings;
    SenderManager sender;
    MessageFile message;
    Arena arena;
    Location spectate;
    private ArrayList<Player> spectators = new ArrayList<>();
    private HashMap<Player, Location> locations = new HashMap<>();

    public Spectate(EasyDuels plugin, Location spectate) {
        this.plugin = plugin;
        this.sender = plugin.getSender();
        this.message = plugin.getMessageFile();
        this.settings = plugin.getSettingsFile();
        this.spectate = spectate;
        this.arena = plugin.getArena();
    }

    public boolean isSpectating(Player player) {
        return spectators.contains(player);
    }

    public void setSpectating(Player player) {
        spectators.add(player);
        sender.sendMessage(message.getNowSpectating(), player);
        locations.put(player, player.getLocation());
        teleportToSpectate(player);
        sender.sendGameMode(settings.getSpectateGamemode(), settings.getSyncTimer(), player);
    }

    public void finishSpectating(Player player) {
        sender.sendGameMode(settings.getAfterSpectateGamemode(), settings.getSyncTimer(), player);
        sender.sendMessage(message.getNoSpectating(), player);
        spectators.remove(player);
        teleportToLastLocation(player);
    }

    public void endMatch() {
        for (Player player : spectators) {
            sender.sendGameMode(settings.getAfterSpectateGamemode(), settings.getSyncTimer(), player);
            sender.sendMessage(message.getNoSpectating(), player);
            spectators.remove(player);
            teleportToLastLocation(player);
        }
    }

    private void teleportToSpectate(Player player) {
        if(settings.getSyncTimer())
            player.teleport(spectate);
        else
            Bukkit.getScheduler().runTask(plugin, () -> player.teleport(spectate));
    }


    private void teleportToLastLocation(Player player) {
        if(settings.getSyncTimer())
            teleportCheck(player);
        else
            Bukkit.getScheduler().runTask(plugin, () -> teleportCheck(player));
    }

    private void teleportCheck(Player player) {
        switch(plugin.getSettingsFile().getSpectatorTeleport()){
            case 0:
                player.teleport(locations.get(player));
                break;
            case 1:
                player.teleport(arena.getLobby());
                break;
            default:
                break;
        }
    }

}
