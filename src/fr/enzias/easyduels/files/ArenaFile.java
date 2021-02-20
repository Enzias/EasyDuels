package fr.enzias.easyduels.filemanager.files;

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
    private File file;
    private FileConfiguration config;
    Syntax syntax = new Syntax();

    public ArenaFile(EasyDuels plugin) {
        this.plugin = plugin;
    }

    public void setup(){
        file = new File(plugin.getDataFolder(), "arena.yml");

        if(!file.exists()) {
            plugin.saveResource("arena.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(file);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public void save(){
        try{
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        save();
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig(){
        return config;
    }


    //Arena Settings --> Getters
    public Location getFirstLocation(){
        return new Location(Bukkit.createWorld(new WorldCreator(getWorldName())), syntax.stringToLoc(getCoordinates1()).getX(), syntax.stringToLoc(getCoordinates1()).getY(),
                syntax.stringToLoc(getCoordinates1()).getZ(), getYaw1(), getPitch1());
    }

    public String getWorldName(){
        if(getConfig().contains("arena.world"))
            return getConfig().getString("arena.world");
        return "world";
    }

    public String getCoordinates1(){
        if(getConfig().contains("arena.spawnpoints.spawn1.xyz"))
            return getConfig().getString("arena.spawnpoints.spawn1.xyz");
        return "0/0/0";
    }

    public float getYaw1(){
        if(getConfig().contains("arena.spawnpoints.spawn1.yaw"))
            return getConfig().getInt("arena.spawnpoints.spawn1.yaw");
        return 90.0f;
    }

    public float getPitch1(){
        if(getConfig().contains("arena.spawnpoints.spawn1.pitch"))
            return getConfig().getInt("arena.spawnpoints.spawn1.pitch");
        return 0;
    }

    public Location getSecondLocation(){
        return new Location(Bukkit.createWorld(new WorldCreator(getWorldName())), syntax.stringToLoc(getCoordinates2()).getX(), syntax.stringToLoc(getCoordinates2()).getY(),
                syntax.stringToLoc(getCoordinates2()).getZ(), getYaw2(), getPitch2());
    }

    public String getCoordinates2(){
        if(getConfig().contains("arena.spawnpoints.spawn2.xyz"))
            return getConfig().getString("arena.spawnpoints.spawn2.xyz");
        return "0/0/0";
    }

    public float getYaw2(){
        if(getConfig().contains("arena.spawnpoints.spawn2.yaw"))
            return getConfig().getInt("arena.spawnpoints.spawn2.yaw");
        return 90.0f;
    }

    public float getPitch2(){
        if(getConfig().contains("arena.spawnpoints.spawn2.pitch"))
            return getConfig().getInt("arena.spawnpoints.spawn2.pitch");
        return 0;
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

    //spectate

    public String getCoordinatesSpectate(){
        if(getConfig().contains("arena.spectate.xyz"))
            return getConfig().getString("arena.spectate.xyz");
        return "0/0/0";
    }

    public float getYawSpectate(){
        if(getConfig().contains("arena.spectate.yaw"))
            return getConfig().getInt("arena.spectate.yaw");
        return 90.0f;
    }

    public float getPitchSpectate(){
        if(getConfig().contains("arena.spectate.pitch"))
            return getConfig().getInt("arena.spectate.pitch");
        return 0;
    }

    public Location getSpectateLocation(){
        return new Location(Bukkit.createWorld(new WorldCreator(getWorldName()))
                , syntax.stringToLoc(getCoordinatesSpectate()).getX()
                , syntax.stringToLoc(getCoordinatesSpectate()).getY()
                , syntax.stringToLoc(getCoordinatesSpectate()).getZ()
                , getYawSpectate(), getPitchSpectate());
    }

    public void setSpectateLocation(Location location){

        setWorldName(location.getWorld().getName());
        setSpectateLocation1(syntax.locToString(location));
        setSpectateYaw(location.getYaw());
        setSpectatePitch(location.getPitch());

    }

    public void setSpectateLocation1(String location2){
        getConfig().set("arena.spectate.xyz", location2);
    }

    public void setSpectateYaw(Float yaw2){
        getConfig().set("arena.spectate.yaw", yaw2);
    }

    public void setSpectatePitch(Float pitch2){
        getConfig().set("arena.spectate.pitch", pitch2);
    }

    //lobby

    public String getLobbyWorldName(){
        if(getConfig().contains("lobby.world"))
            return getConfig().getString("lobby.world");
        return "world";
    }

    public String getCoordinatesLobby(){
        if(getConfig().contains("lobby.xyz"))
            return getConfig().getString("lobby.xyz");
        return "0/0/0";
    }

    public float getYawLobby(){
        if(getConfig().contains("lobby.yaw"))
            return getConfig().getInt("lobby.yaw");
        return 90.0f;
    }

    public float getPitchLobby(){
        if(getConfig().contains("lobby.pitch"))
            return getConfig().getInt("lobby.pitch");
        return 0;
    }

    public Location getLobbyLocation(){
        return new Location(Bukkit.createWorld(new WorldCreator(getLobbyWorldName()))
                , syntax.stringToLoc(getCoordinatesLobby()).getX()
                , syntax.stringToLoc(getCoordinatesLobby()).getY()
                , syntax.stringToLoc(getCoordinatesLobby()).getZ()
                , getYawLobby(), getPitchLobby());
    }

    public void setLobbyLocation(Location location){

        setLobbyWorldName(location.getWorld().getName());
        setLobbyLocation1(syntax.locToString(location));
        setLobbyYaw(location.getYaw());
        setLobbyPitch(location.getPitch());

    }

    public void setLobbyWorldName(String worldName){
        getConfig().set("lobby.world", worldName);
    }

    public void setLobbyLocation1(String location1){
        getConfig().set("lobby.xyz", location1);
    }

    public void setLobbyYaw(Float yaw1){
        getConfig().set("lobby.yaw", yaw1);
    }

    public void setLobbyPitch(Float pitch1){
        getConfig().set("lobby.pitch", pitch1);
    }
}
