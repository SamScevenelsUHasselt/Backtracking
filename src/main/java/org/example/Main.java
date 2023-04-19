package org.example;

import java.util.Collections;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        /* working configs:
            -6 3 0 3 -- no backtracking needed
            -4 4 0 4 -- a lot of backtracking needed
            -6 3 0 2 -- geen oplossing -> voegt een ronde toe
            -4 2 0 4 -- zeer kort met backtracking
            -4 2 0 4 -- 2x rondes verhogen
            -4 2 1 2 -- dubbels
            -6 3 1 3 -- buddels zorgt voor backtracking
        */


        int ploegen = 4;
        int spelletjes = 2;
        int dubbels = 0;
        int rondes = 2;

        Optional<String[][]> oplossing = Spelverdeling.spelverdeling(ploegen, spelletjes, dubbels, rondes);
        if(oplossing.isPresent()){
            System.out.println("Oplossing:");
            Spelverdeling.toonVerdeling(oplossing.get());
        }else {
            System.out.println("Geen oplossing");
        }
    }
}