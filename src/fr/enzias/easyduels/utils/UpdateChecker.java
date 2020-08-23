package fr.enzias.easyduels.utils;

import fr.enzias.easyduels.EasyDuels;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    private final EasyDuels plugin;
    private int ressourceID;
    public UpdateChecker(EasyDuels plugin, int ressourceID) {
        this.plugin = plugin;
        this.ressourceID = ressourceID;
    }

    public void getVersion(final Consumer<String> consumer){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try(InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + ressourceID).openStream();
                    Scanner scanner = new Scanner(inputStream)) {
                if(scanner.hasNext())
                    consumer.accept(scanner.next());
            } catch (IOException e){
                plugin.getLogger().warning("Cannot look for updates: " + e.getMessage());
            }
        });
    }

}
