package fr.enzias.easyduels.utils;

import org.bukkit.entity.Player;

public class DuelPlayerCache {

    private Player opponent;
    public DuelPlayerCache(Player opponent){

        this.opponent = opponent;
    }
    public Player getOpponent() {
        return opponent;
    }

    public boolean hasOpponent(){
        return opponent != null;
    }

    public boolean isOpponent(Player player){
        return player.getName().equalsIgnoreCase(opponent.getName());
    }
}
