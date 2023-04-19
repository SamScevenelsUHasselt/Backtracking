package org.example;

public class Main {
    public static void main(String[] args) {
        /* working/interesting configs:
            -6 3 0 3 -- geen backtrack
            -8 4 0 4 -- veel backtrack en veel calls
            -6 3 0 2 -- geen oplossing -> voegt een ronde toe
            -4 2 0 4 -- zeer kort met backtracking
            -4 2 0 2 -- 2x rondes verhogen
            -4 2 1 2 -- dubbels
            -6 3 1 3 -- dubbels zorgt voor backtracking
            -6 3 1 2 -- dubbels en rondes verhogen
        */

        int ploegen = 6;
        int spelletjes = 3;
        int dubbels = 1;
        int rondes = 2;

        Spelverdeling.toonVerdeling(ploegen, spelletjes, dubbels, rondes);

    }
}