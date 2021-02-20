package fr.enzias.easyduels.listeners.damager;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.filemanager.files.ArenaFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent implements Listener {

    private final EasyDuels plugin;
    private ArenaFile arenaFile;
    private Arena arena;
    public DamageEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.arenaFile = plugin.getArenaFile();
        this.arena = plugin.getArena();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!arena.isStatut(ArenaStatuts.PLAYING) && !arena.isStatut(ArenaStatuts.IDLE))
            if (event.getDamager() instanceof Player && event.getEntity() instanceof Player && event.getDamager().getUniqueId() != event.getEntity().getUniqueId()) {
            Player player = (Player) event.getDamager();
            Player entity = (Player) event.getEntity();
            if (entity.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName()) && arena.getPlayers().contains(entity)
                    && player.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName()) && arena.getPlayers().contains(player))
                event.setCancelled(true);
        }
    }
}
