package fr.enzias.easyduels.utils;

import org.bukkit.entity.Player;

public class DuelPlayerCache {

    private Player opponent;
    private int bet;
    public DuelPlayerCache(Player opponent, int bet){

        this.opponent = opponent;
        this.bet = bet;
    }
    public Player getOpponent() {
        return opponent;
    }

    public boolean hasOpponent(){
        return opponent != null;
    }

    public boolean isOpponent(Player player){
        if(hasOpponent())
            return player.getName().equalsIgnoreCase(opponent.getName());
        else
            return false;
    }

    public boolean hasBet(){
        return bet > 0;
    }

    public int getBet() {
        if(hasBet())
            return bet;
        else
            return 0;
    }
}
