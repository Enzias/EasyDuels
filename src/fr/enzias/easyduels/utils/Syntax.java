package fr.enzias.easyduels.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Syntax {

    //Utils
    private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public Location stringToLoc(String stringLocation){
        String[] locations = stringLocation.split("/");
        double x = Double.parseDouble(locations[0]);
        double y = Double.parseDouble(locations[1]);
        double z = Double.parseDouble(locations[2]);

        return new Location(Bukkit.getWorld(""), x, y, z);
    }

    public String locToString(Location location){
        return location.getX()+ "/" + location.getY() + "/" + location.getZ();
    }

    public String coloredMessage(String message){
        if(Bukkit.getVersion().contains("1.16")){
            Matcher match = pattern.matcher(message);
            while(match.find()){
                String color = message.substring(match.start(), match.end());
                message = message.replace(color, net.md_5.bungee.api.ChatColor.valueOf(color) + "");
                match = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
