package fr.enzias.easyduels.files;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.utils.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ArenaFile {

    private final EasyDuels plugin;
    private File arenaFile;
    private FileConfiguration arenaConfig;
    Syntax syntax = new Syntax();

    public ArenaFile(EasyDuels plugin) {
        this.plugin = plugin;
    }

    public void setup(){
        arenaFile = new File(plugin.getDataFolder(), "arena.yml");

        if(!arenaFile.exists()) {
            plugin.saveResource("arena.yml", false);
        }

        arenaConfig = new YamlConfiguration();
        try {
            arenaConfig.load(arenaFile);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public void save(){
        try{
            arenaConfig.save(arenaFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        save();
        arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
    }

    public FileConfiguration getConfig(){
        return arenaConfig;
    }


    //Arena Settings --> Getters
    public Location getFirstLocation(){
        return new Location(Bukkit.createWorld(new WorldCreator(getWorldName())), syntax.stringToLoc(getCoordinates1()).getX(), syntax.stringToLoc(getCoordinates1()).getY(),
                syntax.stringToLoc(getCoordinates1()).getZ(), getYaw1(), getPitch1());
    }

    public String getWorldName(){
        return getConfig().getString("arena.world");
    }

    public String getCoordinates1(){
        return getConfig().getString("arena.spawnpoints.spawn1.xyz");
    }

    public float getYaw1(){
        return getConfig().getInt("arena.spawnpoints.spawn1.yaw");
    }

    public float getPitch1(){
        return getConfig().getInt("arena.spawnpoints.spawn1.pitch");
    }

    public Location getSecondLocation(){
        return new Location(Bukkit.createWorld(new WorldCreator(getWorldName())), syntax.stringToLoc(getCoordinates2()).getX(), syntax.stringToLoc(getCoordinates2()).getY(),
                syntax.stringToLoc(getCoordinates2()).getZ(), getYaw2(), getPitch2());
    }

    public String getCoordinates2(){
        return getConfig().getString("arena.spawnpoints.spawn2.xyz");
    }

    public float getYaw2(){
        return getConfig().getInt("arena.spawnpoints.spawn2.yaw");
    }

    public float getPitch2(){
        return getConfig().getInt("arena.spawnpoints.spawn2.pitch");
    }

    //Arena Settings --> Setters

    public void setFirstLocation(Location location){

        setWorldName(location.getWorld().getName());
        setLocation1(syntax.locToString(location));
        setYaw1(location.getYaw());
        setPitch1(location.getPitch());

    }

    public void setSecondLocation(Location location){

        setWorldName(location.getWorld().getName());
        setLocation2(syntax.locToString(location));
        setYaw2(location.getYaw());
        setPitch2(location.getPitch());

    }

    public void setWorldName(String worldName){
        getConfig().set("arena.world", worldName);
    }

    public void setLocation1(String location1){
        getConfig().set("arena.spawnpoints.spawn1.xyz", location1);
    }

    public void setYaw1(Float yaw1){
        getConfig().set("arena.spawnpoints.spawn1.yaw", yaw1);
    }

    public void setPitch1(Float pitch1){
        getConfig().set("arena.spawnpoints.spawn1.pitch", pitch1);
    }

    public void setLocation2(String location2){
        getConfig().set("arena.spawnpoints.spawn2.xyz", location2);
    }

    public void setYaw2(Float yaw2){
        getConfig().set("arena.spawnpoints.spawn2.yaw", yaw2);
    }

    public void setPitch2(Float pitch2){
        getConfig().set("arena.spawnpoints.spawn2.pitch", pitch2);
    }


}
