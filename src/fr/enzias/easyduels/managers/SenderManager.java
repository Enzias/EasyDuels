package fr.enzias.easyduels.managers;

import org.bukkit.entity.Player;

import java.util.List;

public interface SenderManager {

    public void sendMessage(String message, Player... player);

    public void sendMessage(List<String> list, Player... players);

    public void sendTitle(List<String> messages, int fadeIn, int stay, int fadeOut, Player... player);

    public void sendTitlePlaceHolders(List<String> messages, int fadeIn, int stay, int fadeOut, Player player, String... replace);

    public void sendSound(String sound, int volume, float pitch, Player... player);

    public void sendActionbar(String message, int fadeIn, int stay, int fadeOut, Player... player);

    public void sendPlayerCommand(String command, Player... player);

    public void sendConsoleCommand(String command, boolean sync);

    public void sendConsoleCommand(List<String> commands, boolean sync, String... replace);

    public void sendBroadcast(String message, boolean sync);

    public void sendFirework(Player player, boolean sync);

    public void sendExplosion(Player player);

    public void sendHover(String acceptMessage, String denyMessage, String acceptHover, String denyHover, String target, Player player);

    public void sendGameMode(String gameMode, boolean sync, Player... players);
}
