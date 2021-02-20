package fr.enzias.easyduels.managers.versions;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.utils.Syntax;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_16_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_16_R3.Vec3D;
import org.bukkit.*;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

public class SenderManager_1_16_R3 implements SenderManager {

    private final EasyDuels plugin;

    Syntax syntax = new Syntax();
    String prefix = "";

    public SenderManager_1_16_R3(EasyDuels plugin) {
        this.plugin = plugin;
        this.prefix = plugin.getMessageFile().getPrefix();
    }

    public void sendMessage(String message, Player... players){
        if(message.isEmpty())
            return;
        for(Player player : players)
            player.sendMessage(syntax.coloredMessage(message.replaceAll("%prefix%", prefix)));
    }

    public void sendMessage(List<String> list, Player... players){
        for(Player player : players) {
            for (String message : list)
                player.sendMessage(syntax.coloredMessage(message.replaceAll("%prefix%", prefix)));
        }
    }

    public void sendTitle(List<String> messages, int fadeIn, int stay, int fadeOut, Player... players){

        String title = syntax.coloredMessage(messages.get(0).replaceAll("%prefix%", prefix));
        String subtitle = syntax.coloredMessage(messages.get(1).replaceAll("%prefix%", prefix));

        for(Player player : players)
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);

    }

    public void sendTitlePlaceHolders(List<String> messages, int fadeIn, int stay, int fadeOut, Player player, String... replace){

        String title = syntax.coloredMessage(messages.get(0).replaceAll("%prefix%", prefix));
        String subtitle = syntax.coloredMessage(messages.get(1).replaceAll("%prefix%", prefix));

        for (int i = 0; i <= (replace.length - 2); i++) {
            title = title.replaceAll(replace[i], syntax.coloredMessage(replace[i+1]));
            subtitle = subtitle.replaceAll(replace[i], syntax.coloredMessage(replace[i+1]));
            i++;
        }

        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);

    }

    public void sendSound(String sound, int volume, float pitch, Player... players){
        for(Player player : players)
            try{
                player.playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
            }catch (IllegalArgumentException e){
                plugin.getLogger().severe("=====================================");
                plugin.getLogger().severe("[EasyDuels] Invalid sound in the config! (" + sound + ")");
                plugin.getLogger().severe("[EasyDuels] Please check your config file to fix the issue.");
                plugin.getLogger().severe("[EasyDuels] https://github.com/Enzias/EasyDuels/wiki/How-to-fix-common-issues-%3F");
                plugin.getLogger().severe("[EasyDuels] Read this post if you need help or go on Discord.");
                plugin.getLogger().severe("=====================================");
            }
    }

    public void sendActionbar(String message, int fadeIn, int stay, int fadeOut, Player... players){

        PacketPlayOutTitle actionbar = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, ChatSerializer.a("{\"text\":\""+
                syntax.coloredMessage(message.replaceAll("%prefix%", prefix)) + "\"}"), fadeIn, stay, fadeOut);

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
            Bukkit.broadcastMessage(syntax.coloredMessage(message.replaceAll("%prefix%", prefix)));
        else
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.broadcastMessage(syntax.coloredMessage(message.replaceAll("%prefix%", prefix))));
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
                TextComponent a = new TextComponent(syntax.coloredMessage(split[i].replaceAll("%prefix%", prefix)));
                base.addExtra(a);
                base.addExtra(" ");
            }
        }
        TextComponent accept = new TextComponent(syntax.coloredMessage(acceptMessage.replaceAll("%prefix%", prefix)));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(syntax.coloredMessage(acceptHover.replaceAll("%prefix%", prefix))).create()));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/easyduels accept " + target));

        TextComponent deny = new TextComponent(" " +syntax.coloredMessage(denyMessage.replaceAll("%prefix%", prefix)));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(syntax.coloredMessage(denyHover.replaceAll("%prefix%", prefix))).create()));
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
