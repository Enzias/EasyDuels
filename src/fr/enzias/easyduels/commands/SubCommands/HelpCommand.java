package fr.enzias.easyduels.commands.SubCommands;

import fr.enzias.easyduels.EasyDuels;
import fr.enzias.easyduels.commands.SubCommand;
import fr.enzias.easyduels.files.MessageFile;
import fr.enzias.easyduels.managers.SenderManager;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {

    MessageFile messageFile;
    SenderManager sender;


    public HelpCommand(EasyDuels plugin) {
        super(plugin);
        this.messageFile = plugin.getMessageFile();
        this.sender = plugin.getSender();
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length == 1) {
            if (player.hasPermission("easyduels.help")) {
                sender.sendMessage(messageFile.getHelpMessages(), player);
            } else
                sender.sendMessage(messageFile.getNoPermission(), player);
        } else
            sender.sendMessage(messageFile.getUnknown(), player);
    }

    @Override
    public String getName() {
        return "help";
    }
}
