package org.example;
import java.util.*;

public class Spelverdeling {
    static long calls;
    static long backtracks;

    public static void toonVerdeling(int ploegen, int spellen, int dubbels, int rondes) {

        Optional<String[][]> oplossing = spelverdeling(ploegen, spellen, dubbels, rondes);

        if(oplossing.isPresent()){
            String[][] verdeling = oplossing.get();
            System.out.println("took " + calls + " calls to solve");
            System.out.println("used " + backtracks + " backtracks");
            StringBuilder concat = new StringBuilder();
            concat.append("Verdeling:\n");
            for (int r = 0; r < verdeling.length; r++) {
                concat.append("Ronde ").append(r + 1);
                for (int s = 0; s < verdeling[r].length; s++) {
                    concat.append("| ").append(verdeling[r][s]).append(" ");
                    if(!verdeling[r][s].equals("NULL")) concat.append(" ");
                }
                concat.append("|\n");
            }
            System.out.println(concat);
        }
        else {//door toevoegen van rondes zal dit nooit het geval zijn
            System.out.println("Geen oplossing");
        }
    }

    public static Optional<String[][]> spelverdeling(int ploegen, int spellen, int dubbels, int rondes) {
        calls = 0;
        if (ploegen % 2 != 0) {
            // Oneven aantal ploegen is niet geldig
            System.out.println("Oneven aantal ploegen!");
            return Optional.empty();
        }

        int maxMatches = (ploegen * (ploegen - 1) / 2) * (dubbels+1); // maximum aantal wedstrijden per spelletje
        if (spellen > maxMatches) {
            // Te veel spelletjes
            System.out.print("Teveel spellen");
            return Optional.empty();
        }

        //Maak arraylist van all ploegen
        ArrayList<Ploeg> ploegArray = new ArrayList<>();
        for (int i = 0; i < ploegen; i++) {
            ploegArray.add(new Ploeg(Character.toString(65 + i)));
        }

        //Maak arraylist van alle mogelijke matches
        ArrayList<Ploeg[]> matches = new ArrayList<>();
        for(int i = 0; i < ploegArray.size() ;i++){
            for(int j = i + 1; j < ploegArray.size(); j++){
                matches.add(new Ploeg[]{ploegArray.get(i), ploegArray.get(j)});
            }
        }
        matches.add(null); //Geen match spelen is ook een mogelijkheid. Dit wordt gerepresenteerd met null

        //backtracking algoritme
        Match[][] oplossingMatches = solveLoop(new Match[rondes][spellen],new Match(matches),matches,ploegArray,0,0,spellen,dubbels,rondes,false);
        while(oplossingMatches==null){//blijf rondes toevoegen tot een oplossing gevonden is
            System.out.println("Rondes Verhogen!!");
            rondes++;
            oplossingMatches = solveLoop(new Match[rondes][spellen],new Match(matches),matches,ploegArray,0,0,spellen,dubbels,rondes,false);
        }

        //Match[][] -> String [][]
            String[][] oplossingString = new String[rondes][spellen];
            for(int i = 0; i < rondes;i++){
                for (int j = 0; j < spellen;j++){
                    if(oplossingMatches[i][j] != null) {
                        oplossingString[i][j] = oplossingMatches[i][j].getMatchString();
                    }
                    else{
                        oplossingString[i][j] = "NULL";
                    }
                }
            }
            return Optional.of(oplossingString);
    }

    //Backtracking Algorithm
    public static Match[][] solveLoop(Match[][] rooster,Match huidigeMatch,ArrayList<Ploeg[]> matches,ArrayList<Ploeg> ploegArray,int spelIndex, int rondeIndex, int spellen, int dubbels, int rondes,boolean backtracking){
        while(!allePloegenAllesGespeeld(ploegArray,spellen)){
            calls++;

            //spelindex = -1 -> terug naar vorige ronde
            if(spelIndex < 0){
                rondeIndex--;
                spelIndex = spellen-1;

                //rondeIndex -1 -> geen oplossing gevonden
                if(rondeIndex < 0) return null;
            }

            //Backtracking (indexes zijn niet negatief door vorig if statement)
            if(backtracking){
                backtracks++; //wordt bijgehouden voor interesse
                huidigeMatch = rooster[rondeIndex][spelIndex];
                rooster[rondeIndex][spelIndex] = null;
                huidigeMatch.backTrack(spelIndex,rondeIndex);
                huidigeMatch.volgendeMatch();
                backtracking = false;
            }

            //Alle spellen gevuld, naar volgende ronde
            if(spelIndex >= spellen){
                spelIndex = 0;
                rondeIndex++;
            }


            //start mutex clauses
            //Alle rondes gevuld, maar geen oplossing → backtracken naar vorig spel
            if(rondeIndex >= rondes){
                spelIndex--;
                backtracking = true;
            }
            //alle mogelijke matchen doorlopen → backtracken naar vorig spel
            else if(huidigeMatch.getMatchTestIndex()>huidigeMatch.getAmountOfMatches()){
                spelIndex--;
                backtracking = true;
            }
            else {
                //Match mag gespeeld worden → naar volgend spel
                if (huidigeMatch.matchgeldig(dubbels, spelIndex, rondeIndex)) {
                    huidigeMatch.speelMatch(spelIndex, rondeIndex);
                    rooster[rondeIndex][spelIndex] = huidigeMatch;
                    huidigeMatch = new Match(matches);
                    spelIndex++;
                }
                //Match mag niet gespeeld worden → probeer de volgende match
                else {
                    huidigeMatch.volgendeMatch();
                }
            }
        }
        return rooster;
    }

    public static boolean allePloegenAllesGespeeld(ArrayList<Ploeg> ploegArray,int spellen){
        for (Ploeg ploeg: ploegArray){
            if (ploeg != null){
                if(!ploeg.allesGespeeld(spellen)) return false;
            }
        }
        return true;
    }
}
