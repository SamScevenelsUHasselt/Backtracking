package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class MatchTest {

    @Test
    void testGetMatchTestIndex() {
        Ploeg ploeg1 = new Ploeg("Ploeg 1");
        Ploeg ploeg2 = new Ploeg("Ploeg 2");
        Ploeg[] ploegen = {ploeg1, ploeg2};
        ArrayList<Ploeg[]> matches = new ArrayList<>();
        matches.add(ploegen);
        Match match = new Match(matches);
        assertEquals(0, match.getMatchTestIndex());
    }

    @Test
    void testGetAmountOfMatches() {
        Ploeg ploeg1 = new Ploeg("Ploeg 1");
        Ploeg ploeg2 = new Ploeg("Ploeg 2");
        Ploeg[] ploegen = {ploeg1, ploeg2};
        ArrayList<Ploeg[]> matches = new ArrayList<>();
        matches.add(ploegen);
        Match match = new Match(matches);
        assertEquals(0, match.getAmountOfMatches());
    }

    @Test
    void testGetMatchString() {
        Ploeg ploeg1 = new Ploeg("Ploeg 1");
        Ploeg ploeg2 = new Ploeg("Ploeg 2");
        Ploeg[] ploegen = {ploeg1, ploeg2};
        ArrayList<Ploeg[]> matches = new ArrayList<>();
        matches.add(ploegen);
        Match match = new Match(matches);
        assertEquals("Ploeg 1-Ploeg 2", match.getMatchString());
    }

    @Test
    void testVolgendeMatch() {
        Ploeg ploeg1 = new Ploeg("Ploeg 1");
        Ploeg ploeg2 = new Ploeg("Ploeg 2");
        Ploeg[] ploegen = {ploeg1, ploeg2};
        ArrayList<Ploeg[]> matches = new ArrayList<>();
        matches.add(ploegen);
        Match match = new Match(matches);
        match.volgendeMatch();
        assertEquals(1, match.getMatchTestIndex());
    }

    @Test
    void testMatchgeldig() {
        Ploeg ploeg1 = new Ploeg("Ploeg 1");
        Ploeg ploeg2 = new Ploeg("Ploeg 2");
        Ploeg[] ploegen = {ploeg1, ploeg2};
        ArrayList<Ploeg[]> matches = new ArrayList<>();
        matches.add(ploegen);
        Match match = new Match(matches);
        assertTrue(match.matchgeldig(1, 1, 1));
    }

    @Test
    void testSpeelMatch() {
        Ploeg ploeg1 = new Ploeg("Ploeg 1");
        Ploeg ploeg2 = new Ploeg("Ploeg 2");
        Ploeg[] ploegen = {ploeg1, ploeg2};
        ArrayList<Ploeg[]> matches = new ArrayList<>();
        matches.add(ploegen);
        Match match = new Match(matches);
        match.speelMatch(1, 1);
        assertTrue(ploeg1.getTegenGespeeld().contains(ploeg2));
        assertTrue(ploeg2.getTegenGespeeld().contains(ploeg1));
    }
}
