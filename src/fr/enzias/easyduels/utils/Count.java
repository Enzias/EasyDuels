package fr.enzias.easyduels.utils;

public class Count {

    public String secondToString(int time){

        if(time == 0)
            return Integer.toString(time);

        String timer;

        int minutes = time/60;
        int seconds = time%60;

        if(minutes > 0)
            timer = String.format("%02d:%02d", minutes, seconds);
        else
            timer =Integer.toString(time);

        return timer;

    }

}
