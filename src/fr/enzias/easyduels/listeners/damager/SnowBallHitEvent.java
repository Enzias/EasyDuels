package fr.enzias.easyduels.listeners.damager;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.files.ArenaFile;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SnowBallHitEvent implements Listener {

    private final EasyDuels plugin;
    private ArenaFile arenaFile;
    private Arena arena;

    public SnowBallHitEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.arenaFile = plugin.getArenaFile();
        this.arena = plugin.getArena();
    }

    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent event) {
        if (!arena.isStatut(ArenaStatuts.PLAYING) && !arena.isStatut(ArenaStatuts.IDLE))
            if (event.getDamager() instanceof Snowball && ((Snowball) event.getDamager()).getShooter() instanceof Player && event.getEntity() instanceof Player &&
                    ((Player) ((Snowball) event.getDamager()).getShooter()).getUniqueId() != event.getEntity().getUniqueId()) {

                Player shooter = (Player) ((Snowball) event.getDamager()).getShooter();
                Player player = (Player) event.getEntity();
                if (shooter.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName()) && arena.getPlayers().contains(shooter)
                        && player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName()) && arena.getPlayers().contains(player))
                    event.setCancelled(true);
            }
    }
}
