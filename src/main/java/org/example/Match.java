package org.example;

import java.util.ArrayList;

public class Match {
    ArrayList<Ploeg[]> matches;
    int matchTestIndex;
    Match(ArrayList<Ploeg[]> ploegen){
        matches = ploegen;
        matchTestIndex = 0;
    }
    public int getMatchTestIndex(){
        return matchTestIndex;
    }

    public int getAmountOfMatches(){
        return matches.size() - 1;
    }

    public String getMatchString(){
        if(matchTestIndex<matches.size()) {
            if (matches.get(matchTestIndex) != null) {
                return matches.get(matchTestIndex)[0].getName() + "-" + matches.get(matchTestIndex)[1].getName();
            }
        }
        return "NULL";
    }

    public void volgendeMatch(){
        matchTestIndex++;
    }
    public boolean matchgeldig(int dubbels,int spel,int ronde){
        //Ploeg 0 mag tegen ploeg 1 spelen en ploeg 1 mag tegen ploeg 0 spelen
        if(matches.get(matchTestIndex) == null){
            return true; //no match will always be valid
        }
        else return matches.get(matchTestIndex)[0].magSpelen(matches.get(matchTestIndex)[1],dubbels,spel,ronde) && matches.get(matchTestIndex)[1].magSpelen(matches.get(matchTestIndex)[0],dubbels,spel,ronde);
    }

    public void speelMatch(int spel,int ronde){
        if(matches.get(matchTestIndex) != null){
            matches.get(matchTestIndex)[0].speel(matches.get(matchTestIndex)[1],spel,ronde);
            matches.get(matchTestIndex)[1].speel(matches.get(matchTestIndex)[0],spel,ronde);
        }
    }

    public void backTrack(int spel,int ronde){
        if(matches.get(matchTestIndex) != null) {
            matches.get(matchTestIndex)[0].backTrack(matches.get(matchTestIndex)[1], spel, ronde);
            matches.get(matchTestIndex)[1].backTrack(matches.get(matchTestIndex)[0], spel, ronde);
        }
    }
}
