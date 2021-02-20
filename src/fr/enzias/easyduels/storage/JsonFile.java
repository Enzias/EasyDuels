package fr.enzias.easyduels.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.enzias.easyduels.EasyDuels;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JsonFile {

    private final EasyDuels plugin;
    private File file;
    private JSONObject json;
    private JSONParser parser = new JSONParser();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Map<String, Object> makerMap = new HashMap<>();

    public JsonFile(EasyDuels plugin) {
        this.plugin = plugin;
    }

    public void setFile(UUID uuid){
        File directory = new File(plugin.getDataFolder() + File.separator + "users");
        if(!directory.exists())
            directory.mkdir();

        this.file = new File(directory.getPath() + File.separator + uuid.toString() + ".json");
        checkFile();
    }

    public void setFile(OfflinePlayer player) {
        File directory = new File(plugin.getDataFolder() + File.separator + "users");
        if(!directory.exists())
            directory.mkdir();

        this.file = new File(directory.getPath() + File.separator + player.getUniqueId().toString() + ".json");
        checkFile();
    }

    public void setFile(Player player) {
        File directory = new File(plugin.getDataFolder() + File.separator + "users");
        if(!directory.exists())
            directory.mkdir();

        this.file = new File(directory.getPath() + File.separator + player.getUniqueId().toString() + ".json");

        checkFile();
    }

    public void checkFile(){
        if (!file.exists()) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(file, "UTF-8");
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            pw.print("{");
            pw.print("}");
            pw.flush();
            pw.close();
            setExperience(0L);
        }
        try {
            this.json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean hasExperience(){
        return json.containsKey("experience");
    }

    public Long getExperience(){
        return hasExperience() ? (Long) json.get("experience") : 0L;
    }

    public void setExperience(long exp){
        setMap(exp);
        String jsonString = this.gson.toJson(makerMap);

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(jsonString);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void setMap(long exp){
        this.makerMap.clear();
        this.makerMap.put("experience", exp);
    }

}
