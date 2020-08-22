package fr.enzias.easyduels.listeners.damager;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.files.ArenaFile;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ArrowHitEvent implements Listener {

    private final EasyDuels plugin;
    private ArenaFile arenaFile;
    private Arena arena;

    public ArrowHitEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.arenaFile = plugin.getArenaFile();
        this.arena = plugin.getArena();
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event) {
        if (!arena.isStatut(ArenaStatuts.PLAYING) && !arena.isStatut(ArenaStatuts.IDLE))
            if (event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player && event.getEntity() instanceof Player &&
                    ((Player) ((Arrow) event.getDamager()).getShooter()).getUniqueId() != event.getEntity().getUniqueId()) {

                Player shooter = (Player) ((Arrow) event.getDamager()).getShooter();
                Player player = (Player) event.getEntity();

                if (shooter.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName()) && arena.getPlayers().contains(shooter)
                        && player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName()) && arena.getPlayers().contains(player))
                    event.setCancelled(true);
            }
    }

}

