package fr.enzias.easyduels.utils;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.filemanager.files.SettingsFile;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.List;

public class VaultHook {

    private final EasyDuels plugin;
    private Economy economy = null;
    private Permission perm = null;
    private boolean has = false;
    SettingsFile settings;
    public VaultHook(EasyDuels plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettingsFile();
    }

    public void hasVault(){
        if(Bukkit.getServer().getPluginManager().getPlugin("Vault") == null){
            Bukkit.getLogger().info("[EasyDuels] Vault plugin not found.");
            this.has = false;
        }
        this.has = true;
    }

    public boolean setupEconomy(){
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            Bukkit.getLogger().info("[EasyDuels] No Economy plugin found.");
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if(rsp == null){
            Bukkit.getLogger().info("[EasyDuels] No Permission plugin found.");
            return false;
        }
        perm = rsp.getProvider();
        return true;
    }

    //economy

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
        if(isEconNotNull()){
            return economy.has(player, amount);
        }
        return false;
    }

    public void giveBackBoth(int amount, Player player, Player player1){
        if(isEconNotNull()){
            EconomyResponse a = economy.depositPlayer(player, amount);
            EconomyResponse b = economy.depositPlayer(player1, amount);
        }
    }

    public void give(int amount, Player player){
        if(isEconNotNull()){
            EconomyResponse a = economy.depositPlayer(player, amount);
        }
    }

    public void takeBoth(int amount, Player player, Player player1){
        if(isEconNotNull()){
            EconomyResponse a = economy.withdrawPlayer(player, amount);
            EconomyResponse b = economy.withdrawPlayer(player1, amount);
        }
    }

    public void take(int amount, Player player){
        if(isEconNotNull()){
            EconomyResponse a = economy.withdrawPlayer(player, amount);
        }
    }

    //permission

    private boolean hasPermission(Player player, String perm){
        return this.perm.has(player, perm);
    }

    public boolean hasPermission(Player player, List<String> perms){
        for(String perm : perms){
            if(!hasPermission(player, perm))
                return false;
        }
        return true;
    }

    public void hasAndAddPermission(Player player, List<String> perms){
        if(isPermNotNull())
            for(String perm : perms){
                addPermission(player, perm);
            }
    }

    public void hasAndAddPermission(Player player, String perm){
        if(isPermNotNull())
            addPermission(player, perm);
    }

    private void addPermission(Player player, String perm){
        if(isPermNotNull())
            this.perm.playerAdd(null, player, perm);
    }

    public void hasAndRemovePermission(Player player, List<String> perms){
        if(isPermNotNull())
            for(String perm : perms){
                if(hasPermission(player, perm))
                    removePermission(player, perm);
            }
    }

    public void hasAndRemovePermission(Player player, String perm){
        if(isPermNotNull())
            if(hasPermission(player, perm))
                removePermission(player, perm);
    }

    private void removePermission(Player player, String perm){
        if(isPermNotNull())
            if(hasPermission(player, perm))
                this.perm.playerRemove(null, player, perm);
    }

    //checker

    public boolean isEconNotNull(){
        return economy != null;
    }

    public boolean isPermNotNull(){
        return perm != null;
    }

    public Economy getEconomy() {
        return economy;
    }

    public Permission getPermission(){
        return perm;
    }

    public boolean getHas() {
        return has;
    }

    public void setHas(boolean has) {
        this.has = has;
    }
}
