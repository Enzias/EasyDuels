package fr.enzias.easyduels.listeners.damager;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.filemanager.files.ArenaFile;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TridentHitEvent implements Listener {

    private final EasyDuels plugin;
    private ArenaFile arenaFile;
    private Arena arena;

    public TridentHitEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.arenaFile = plugin.getArenaFile();
        this.arena = plugin.getArena();
    }

    @EventHandler
    public void onTridentHit(EntityDamageByEntityEvent event) {
        if (!arena.isStatut(ArenaStatuts.PLAYING) && !arena.isStatut(ArenaStatuts.IDLE))
            if (event.getDamager() instanceof Trident && ((Trident) event.getDamager()).getShooter() instanceof Player && event.getEntity() instanceof Player &&
                    ((Player) ((Trident) event.getDamager()).getShooter()).getUniqueId() != event.getEntity().getUniqueId()) {

                Player shooter = (Player) ((Trident) event.getDamager()).getShooter();
                Player player = (Player) event.getEntity();

                if (shooter.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName()) && arena.getPlayers().contains(shooter)
                        && player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName()) && arena.getPlayers().contains(player))
                    event.setCancelled(true);
            }
    }

}

