package fr.enzias.easyduels.listeners.damager;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.arena.Arena;
import fr.enzias.easyduels.arena.ArenaStatuts;
import fr.enzias.easyduels.filemanager.files.ArenaFile;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class PotionHitEvent implements Listener {

    private final EasyDuels plugin;
    private ArenaFile arenaFile;
    private Arena arena;

    public PotionHitEvent(EasyDuels plugin) {
        this.plugin = plugin;
        this.arenaFile = plugin.getArenaFile();
        this.arena = plugin.getArena();
    }

    @EventHandler
    public void onArrowHit(PotionSplashEvent event) {
        if (!arena.isStatut(ArenaStatuts.PLAYING) && !arena.isStatut(ArenaStatuts.IDLE))
            if (event.getEntity().getShooter() instanceof Player) {
            Player shooter = (Player) event.getEntity().getShooter();
            if(shooter.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName()) && arena.getPlayers().contains(shooter))

            for (PotionEffect effect : event.getPotion().getEffects()) {
                if (Arrays.asList(PotionEffectType.LEVITATION, PotionEffectType.HARM, PotionEffectType.SLOW, PotionEffectType.WEAKNESS, PotionEffectType.POISON,
                        PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.HUNGER, PotionEffectType.WITHER)
                        .contains(effect.getType())) {

                    if (event.getAffectedEntities() != null) {

                        for (Entity entity : event.getAffectedEntities()) {
                            if (entity instanceof Player && shooter.getUniqueId() != entity.getUniqueId()
                                    && entity.getLocation().getWorld().getName().equalsIgnoreCase(arenaFile.getWorldName()) && arena.getPlayers().contains(entity))
                                event.setCancelled(true);
                        }
                    }

                }
            }
        }
    }
}
