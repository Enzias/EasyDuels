package fr.enzias.easyduels.commands.subcommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ProfileCommand extends SubCommand {

    public ProfileCommand(EasyDuels plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length == 1){

            if(player.hasPermission("easyduels.myprofile"))
                sender.sendMessage(getProfile(player), player);
            else
                sender.sendMessage(messageFile.getNoPermission(), player);

        }else if(args.length == 2){

            if(player.hasPermission("easyduels.othersprofile")) {

                if (Bukkit.getPlayer(args[1]) != null) {

                    Player target = Bukkit.getPlayer(args[1]);
                    sender.sendMessage(getProfile(target), player);

                } else
                    sender.sendMessage(messageFile.getOfflinePlayer().replaceAll("%player%", args[1]), player);

            }else
                sender.sendMessage(messageFile.getNoPermission(), player);
        }else
            sender.sendMessage(messageFile.getUnknown(), player);

    }

    private List<String> getProfile(Player player){
        int i = 0;
        List<String> list = messageFile.getProfile();
        for(String msg : list){
            list.set(i, msg
                    .replaceAll("%player%", player.getName())
                    .replaceAll("%rank%", levelManager.getRank(player))
                    .replaceAll("%level%", String.valueOf(levelManager.getLevel(player)))
                    .replaceAll("%max_level%", levelManager.getMaxLevel())
                    .replaceAll("%experience%", String.valueOf(levelManager.getExpFromManager(player)))
                    .replaceAll("%needed%", String.valueOf(levelManager.getExpToNextLevel(player)))
                    .replaceAll("%cooldown%", String.valueOf(levelManager.getCoolDown(player))));
            i++;
        }
        return list;
    }

    @Override
    public String getName() {
        return "profile";
    }
}
