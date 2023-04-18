package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Ploeg {
    private final String name;
    private final ArrayList<Ploeg> tegenGespeeld;
    private final ArrayList<Integer> spellenGespeeld;
    private final ArrayList<Integer> rondesGespeeld;
    Ploeg(String name){
        this.name = name;
        tegenGespeeld = new ArrayList<>();
        spellenGespeeld = new ArrayList<>();
        rondesGespeeld = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void speel(Ploeg tegenstander,Integer spel,Integer ronde){
        tegenGespeeld.add(tegenstander);
        spellenGespeeld.add(spel);
        rondesGespeeld.add(ronde);
    }

    public void backTrack(Ploeg tegenstander,Integer spel,Integer ronde){
        tegenGespeeld.remove(tegenstander);
        spellenGespeeld.remove(spel);
        rondesGespeeld.remove(ronde);
    }

    public boolean allesGespeeld(int spellen){
        for(int i = 0; i < spellen; i++){
            if(!spellenGespeeld.contains(i)) return false;
        }
        return true;
    }

    //Als de ploeg het spel nog niet gespeeld heeft deze ronde nog niet gespeeld heeft en nog tegen de tegenstqnder mag spelen mag de ploeg de match spelen
    public boolean magSpelen(Ploeg tegenstander,int dubbels,int spel, int ronde){
        return Collections.frequency(tegenGespeeld,tegenstander)<=dubbels && !spellenGespeeld.contains(spel) && !rondesGespeeld.contains(ronde);
    }

}
