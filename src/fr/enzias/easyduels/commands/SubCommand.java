package fr.enzias.easyduels.commands;

import fr.enzias.easyduels.EasyDuels;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    private final EasyDuels plugin;

    public SubCommand(EasyDuels plugin){
        this.plugin = plugin;

    }

    public abstract void onCommand(Player player, String[] args);

    public abstract String getName();
}
