package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PloegTest {
    @Test
    public void testGetName() {
        Ploeg ploeg = new Ploeg("Team A");
        assertEquals("Team A", ploeg.getName());
    }
    @Test
    public void testSpeel() {
        Ploeg ploeg1 = new Ploeg("Team A");
        Ploeg ploeg2 = new Ploeg("Team B");
        ploeg1.speel(ploeg2, 0, 0);
        assertTrue(ploeg1.getTegenGespeeld().contains(ploeg2));
        assertTrue(ploeg1.getSpellenGespeeld().contains(0));
        assertTrue(ploeg1.getRondesGespeeld().contains(0));
    }
    @Test
    public void testBackTrack() {
        Ploeg ploeg1 = new Ploeg("Team A");
        Ploeg ploeg2 = new Ploeg("Team B");
        ploeg1.speel(ploeg2, 0, 0);
        ploeg1.backTrack(ploeg2, 0, 0);
        assertFalse(ploeg1.getTegenGespeeld().contains(ploeg2));
        assertFalse(ploeg1.getSpellenGespeeld().contains(0));
        assertFalse(ploeg1.getRondesGespeeld().contains(0));
    }
    @Test
    public void testAllesGespeeld() {
        Ploeg ploeg1 = new Ploeg("Team A");
        assertFalse(ploeg1.allesGespeeld(3));
        ploeg1.speel(new Ploeg("Team B"), 0, 0);
        assertFalse(ploeg1.allesGespeeld(3));
        ploeg1.speel(new Ploeg("Team C"), 1, 0);
        assertFalse(ploeg1.allesGespeeld(3));
        ploeg1.speel(new Ploeg("Team D"), 2, 0);
        assertTrue(ploeg1.allesGespeeld(3));
    }
    @Test
    public void testMagSpelen() {
        Ploeg ploeg1 = new Ploeg("Team A");
        Ploeg ploeg2 = new Ploeg("Team B");
        Ploeg ploeg3 = new Ploeg("Team C");
        assertTrue(ploeg1.magSpelen(ploeg2, 1, 0, 0));
        ploeg1.speel(ploeg2, 0, 0);
        assertFalse(ploeg1.magSpelen(ploeg2, 1, 1, 0));
        assertFalse(ploeg1.magSpelen(ploeg2, 1, 0, 0));
        assertTrue(ploeg1.magSpelen(ploeg2, 1, 1, 1));
        assertFalse(ploeg1.magSpelen(ploeg3, 1, 1, 0));
        assertFalse(ploeg1.magSpelen(ploeg3, 2, 1, 0));
    }
}