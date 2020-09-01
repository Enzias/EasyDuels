package fr.enzias.easyduels.commands.SubCommands.AdminCommand.SubCommands;

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
        if(args.length == 2) {
            sender.sendMessage(messageFile.getAdminHelpMessages(), player);
        } else
            sender.sendMessage(messageFile.getAdminUnknown(), player);
    }

    @Override
    public String getName() {
        return "help";
    }
}
