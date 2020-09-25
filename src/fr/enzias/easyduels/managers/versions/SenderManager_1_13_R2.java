package fr.enzias.easyduels.managers.versions;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.managers.SenderManager;
import fr.enzias.easyduels.utils.Syntax;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_13_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_13_R2.PacketPlayOutExplosion;
import net.minecraft.server.v1_13_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_13_R2.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_13_R2.Vec3D;
import org.bukkit.*;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

public class SenderManager_1_13_R2 implements SenderManager {

    private final EasyDuels plugin;

    Syntax syntax = new Syntax();

    public SenderManager_1_13_R2(EasyDuels plugin) {
        this.plugin = plugin;
    }

    public void sendMessage(String message, Player... players){
        if(message.isEmpty())
            return;
        for(Player player : players)
        player.sendMessage(syntax.coloredMessage(message));
    }

    public void sendMessage(List<String> list, Player... players){
        for(Player player : players) {
            for (String message : list)
                player.sendMessage(syntax.coloredMessage(message));
        }
    }

    public void sendTitle(List<String> messages, int fadeIn, int stay, int fadeOut, Player... players){

        String title = syntax.coloredMessage(messages.get(0));
        String subtitle = syntax.coloredMessage(messages.get(1));

        for(Player player : players)
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);

    }

    public void sendTitlePlaceHolders(List<String> messages, int fadeIn, int stay, int fadeOut, Player player, String... replace){

        String title = syntax.coloredMessage(messages.get(0));
        String subtitle = syntax.coloredMessage(messages.get(1));

        for (int i = 0; i <= (replace.length - 2); i++) {
            title = title.replaceAll(replace[i], replace[i+1]);
            subtitle = subtitle.replaceAll(replace[i], replace[i+1]);
            i++;
        }

        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);

    }

    public void sendSound(String sound, int volume, float pitch, Player... players){
        for(Player player : players)
        player.playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
    }

    public void sendActionbar(String message, int fadeIn, int stay, int fadeOut, Player... players){

        PacketPlayOutTitle actionbar = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, ChatSerializer.a("{\"text\":\""+
                syntax.coloredMessage(message) + "\"}"), fadeIn, stay, fadeOut);

        for(Player player : players)
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(actionbar);
    }

    public void sendPlayerCommand(String command, Player... players) {
        for(Player player : players)
        player.performCommand(command);
    }

    public void sendConsoleCommand(String command, boolean sync){
        if(sync)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        else
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
    }

    public void sendConsoleCommand(List<String> commands, boolean sync, String... replace) {
        if(sync) {
            for (String command : commands) {
                for (int i = 0; i <= (replace.length - 2); i++) {
                    command = command.replaceAll(replace[i], replace[i+1]);
                    i++;
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }else {
            Bukkit.getScheduler().runTask(plugin, () -> {
                for (String command : commands) {
                    for (int i = 0; i <= (replace.length - 2); i++) {
                        command = command.replaceAll(replace[i], replace[i+1]);
                        i++;
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            });
        }
    }

    public void sendBroadcast(String message, boolean sync){
        if(sync)
            Bukkit.broadcastMessage(syntax.coloredMessage(message));
        else
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.broadcastMessage(syntax.coloredMessage(message)));
    }

    public void sendFirework (Player player, boolean sync){
        if(sync) {
            Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            Builder builder = FireworkEffect.builder();

            fireworkMeta.addEffect(builder.withColor(Color.YELLOW).build());
            fireworkMeta.addEffect(builder.withFade(Color.ORANGE).build());
            fireworkMeta.addEffect(builder.withTrail().build());
            fireworkMeta.addEffect(builder.with(FireworkEffect.Type.STAR).build());
            fireworkMeta.setPower(1);
            firework.setFireworkMeta(fireworkMeta);
        }
        else
            Bukkit.getScheduler().runTask(plugin, () -> {
                Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();
                Builder builder = FireworkEffect.builder();

                fireworkMeta.addEffect(builder.withColor(Color.YELLOW).build());
                fireworkMeta.addEffect(builder.withFade(Color.ORANGE).build());
                fireworkMeta.addEffect(builder.withTrail().build());
                fireworkMeta.addEffect(builder.with(FireworkEffect.Type.STAR).build());
                fireworkMeta.setPower(1);
                firework.setFireworkMeta(fireworkMeta);
            });
    }

    public void sendExplosion(Player player){
        Location location = player.getLocation();
        PacketPlayOutExplosion explosion = new PacketPlayOutExplosion(location.getX(), location.getY(), location.getZ(), 5f, new ArrayList<>(), new Vec3D(0, 0, 0));
        for(Player players : Bukkit.getOnlinePlayers()) {
            players.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
            ((CraftPlayer) players).getHandle().playerConnection.sendPacket(explosion);
        }
    }

    public void sendHover(String acceptMessage, String denyMessage, String acceptHover, String denyHover, String before, String target, Player player){
        TextComponent base = new TextComponent("");
        if(!before.isEmpty()) {
            String[] split = before.split(" ");
            for (int i = 0; i < split.length; i++) {
                TextComponent a = new TextComponent(syntax.coloredMessage(split[i]));
                base.addExtra(a);
                base.addExtra(" ");
            }
        }
        TextComponent accept = new TextComponent(syntax.coloredMessage(acceptMessage));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(syntax.coloredMessage(acceptHover)).create()));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/easyduels accept " + target));

        TextComponent deny = new TextComponent(" " +syntax.coloredMessage(denyMessage));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(syntax.coloredMessage(denyHover)).create()));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/easyduels deny " + target));


        base.addExtra(accept);
        base.addExtra(deny);

        player.spigot().sendMessage(base);
    }

    public void sendGameMode(String gameMode, boolean sync, Player... players){
        if(sync) {
            for (Player player : players) {
                if (player.getGameMode() != GameMode.valueOf(gameMode))
                    player.setGameMode(GameMode.valueOf(gameMode));
            }
        } else {
            Bukkit.getScheduler().runTask(plugin, () -> {
                for (Player player : players) {
                    if (player.getGameMode() != GameMode.valueOf(gameMode))
                        player.setGameMode(GameMode.valueOf(gameMode));
                }
            });
        }
    }

}
