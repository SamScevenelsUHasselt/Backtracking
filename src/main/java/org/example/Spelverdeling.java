package org.example;
import java.util.*;

public class Spelverdeling {
    static int calls;
    static int backtracks;
    static String meanwhile;
    static final boolean verbose = false;

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

        ArrayList<Ploeg> ploegArray = new ArrayList<>();
        for (int i = 0; i < ploegen; i++) {
            ploegArray.add(new Ploeg(Character.toString(65 + i)));
        }

        ArrayList<Ploeg[]> matches = new ArrayList<>();
        for(int i = 0; i < ploegArray.size() ;i++){
            for(int j = i + 1; j < ploegArray.size(); j++){
                matches.add(new Ploeg[]{ploegArray.get(i), ploegArray.get(j)});
            }
        }
        matches.add(null);


        Match[][] oplossingMatches = solve(new Match[rondes][spellen],new Match(matches),matches,ploegArray,0,0,spellen,dubbels,rondes,false);
        while(oplossingMatches==null){
            System.out.println("Rondes Verhogen!!");
            rondes++;
            oplossingMatches = solve(new Match[rondes][spellen],new Match(matches),matches,ploegArray,0,0,spellen,dubbels,rondes,false);
        }
        System.out.println("took " + calls + " calls to solve");
        System.out.println("used " + backtracks + " backtracks");

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
    private static Match[][] solve(Match[][] rooster,Match huidigeMatch,ArrayList<Ploeg[]> matches,ArrayList<Ploeg> ploegArray,int spelIndex, int rondeIndex, int spellen, int dubbels, int rondes,boolean backtracking){
        calls++;
        if(verbose){
            System.out.println("Call "+ calls);
            toonVerdelingMatch(rooster,rondes,spellen);
        }

        //spelindex = -1 -> terug naar vorige ronde
        if(spelIndex < 0){
            rondeIndex--;
            spelIndex = spellen-1;

            //rondeIndex -1 -> geen oplossing gevonden
            if(rondeIndex < 0) return null;

            return solve(rooster,huidigeMatch,matches,ploegArray,spelIndex, rondeIndex, spellen, dubbels, rondes,backtracking);
        }

        //Backtracking en indexes zijn niet negatief door vorig if statement (beter op deze manier door 2d array)
        if(backtracking){
            backtracks++;
            huidigeMatch = rooster[rondeIndex][spelIndex];
            rooster[rondeIndex][spelIndex] = null;
            huidigeMatch.backTrack(spelIndex,rondeIndex);
            huidigeMatch.volgendeMatch();
        }

        //Oplossing gevonden (Bottleneck)
        if(allePloegenAllesGespeeld(ploegArray,spellen)) return rooster;

        //Alle spellen gevuld, naar volgende ronde
        if(spelIndex >= spellen){
            return solve(rooster,huidigeMatch,matches,ploegArray,0, rondeIndex+1, spellen, dubbels, rondes,false);
        }

        //Alle rondes gevuld maar geen oplossing -> backtracken naar vorig spel
        if(rondeIndex >= rondes){
            return solve(rooster,huidigeMatch,matches,ploegArray,spelIndex-1, rondeIndex, spellen, dubbels, rondes,true);
        }

        //alle matchen doorlopen -> backtracken naar vorig spel
        if(huidigeMatch.getMatchTestIndex()>huidigeMatch.getAmountOfMatches()){
            return solve(rooster,huidigeMatch,matches,ploegArray,spelIndex-1, rondeIndex, spellen, dubbels, rondes,true);
        }

        //Match mag gespeeld worden -> naar volgend spel
        if(huidigeMatch.matchgeldig(dubbels,spelIndex,rondeIndex)){
            huidigeMatch.speelMatch(spelIndex,rondeIndex);
            rooster[rondeIndex][spelIndex] = huidigeMatch;
            huidigeMatch = new Match(matches);
            return solve(rooster,huidigeMatch,matches,ploegArray,spelIndex+1, rondeIndex, spellen, dubbels, rondes,false);
        }
        //Match mag niet gespeeld worden -> probeer de volgende match
        else{
            huidigeMatch.volgendeMatch();
            return solve(rooster,huidigeMatch,matches,ploegArray,spelIndex, rondeIndex, spellen, dubbels, rondes,false);
        }
    }

    private static boolean allePloegenAllesGespeeld(ArrayList<Ploeg> ploegArray,int spellen){
        for (Ploeg ploeg: ploegArray){
            if (ploeg != null){
                if(!ploeg.allesGespeeld(spellen)) return false;
            }
        }
        return true;
    }

    // Print de spelverdeling
    public static void toonVerdeling(String[][] verdeling) {
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

    private static void toonVerdelingMatch(Match[][] verdeling,int rondes,int spellen) {
        String[][] oplossingString = new String[rondes][spellen];
        for(int i = 0; i < rondes;i++){
            for (int j = 0; j < spellen;j++){
                if(verdeling[i][j] != null) {
                    oplossingString[i][j] = verdeling[i][j].getMatchString();
                }
                else{
                    oplossingString[i][j] = "NULL";
                }
            }
        }

        StringBuilder concat = new StringBuilder();
        concat.append("Verdeling:\n");
        for (int r = 0; r < oplossingString.length; r++) {
            concat.append("Ronde ").append(r + 1);
            for (int s = 0; s < oplossingString[r].length; s++) {
                concat.append("| ").append(oplossingString[r][s]).append(" ");
                if(!oplossingString[r][s].equals("NULL")) concat.append(" ");
            }
            concat.append("|\n").append("̲");
        }
        if(!concat.toString().equals(meanwhile)){
            System.out.println(concat);
            meanwhile = concat.toString();
        }
    }
}
