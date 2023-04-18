package org.example;

import java.util.Collections;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        int ploegen = 8;
        int spelletjes = 4;
        int dubbels = 0;
        int rondes = 4;

        Optional<String[][]> oplossing = Spelverdeling.spelverdeling(ploegen, spelletjes, dubbels, rondes);
        if(oplossing.isPresent()){
            System.out.println("Oplossing:");
            Spelverdeling.toonVerdeling(oplossing.get());
        }else {
            System.out.println("Geen oplossing");
        }
    }
}