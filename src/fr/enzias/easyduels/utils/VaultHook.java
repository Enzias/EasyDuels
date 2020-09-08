package fr.enzias.easyduels.utils;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.files.SettingsFile;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private final EasyDuels plugin;
    private Economy economy = null;
    SettingsFile settings;
    public VaultHook(EasyDuels plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettingsFile();
    }

    public boolean setupEconomy(){
        if(Bukkit.getServer().getPluginManager().getPlugin("Vault") == null){
            Bukkit.getLogger().info("[EasyDuels] Vault plugin not found.");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            Bukkit.getLogger().info("[EasyDuels] No Economy plugin found.");
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public boolean isAbove(int i){
        return i >= settings.getMinAmount();
    }

    public boolean isUnder(int i){
        if(settings.getMaxAmount() <= 0)
            return true;
        return i <= settings.getMaxAmount();
    }

    public boolean isValidAmount(String amount){
        try{
            int i = Integer.parseInt(amount);
            return (i > 0);
        }catch (NumberFormatException e){
            return false;
        }
    }

    public int getValidAmount(String amount){
        return Integer.parseInt(amount);
    }

    public boolean hasEnough(int amount, Player player){
        if(isNotNull()){
            return economy.has(player, amount);
        }
        return false;
    }

    public void giveBackBoth(int amount, Player player, Player player1){
        if(isNotNull()){
            EconomyResponse a = economy.depositPlayer(player, amount);
            EconomyResponse b = economy.depositPlayer(player1, amount);
        }
    }

    public void give(int amount, Player player){
        if(isNotNull()){
            EconomyResponse a = economy.depositPlayer(player, amount);
        }
    }

    public void takeBoth(int amount, Player player, Player player1){
        if(isNotNull()){
            EconomyResponse a = economy.withdrawPlayer(player, amount);
            EconomyResponse b = economy.withdrawPlayer(player1, amount);
        }
    }

    public void take(int amount, Player player){
        if(isNotNull()){
            EconomyResponse a = economy.withdrawPlayer(player, amount);
        }
    }

    public boolean isNotNull(){
        return economy != null;
    }

    public Economy getEconomy() {
        return economy;
    }
}
