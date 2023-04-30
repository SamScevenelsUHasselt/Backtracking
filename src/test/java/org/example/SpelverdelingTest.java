package org.example;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class SpelverdelingTest {

    @Test
    public void testEvenNumberOfTeams() {
        // Arrange
        int ploegen = 4;
        int spellen = 6;
        int dubbels = 1;
        int rondes = 3;

        // Act
        Optional<String[][]> result = Spelverdeling.spelverdeling(ploegen, spellen, dubbels, rondes);

        // Assert
        assertTrue(result.isPresent());
        String[][] verdeling = result.get();
        assertEquals(verdeling.length, rondes);
        assertEquals(verdeling[0].length, spellen);
    }

    @Test
    public void testOddNumberOfTeams() {
        // Arrange
        int ploegen = 3;
        int spellen = 6;
        int dubbels = 1;
        int rondes = 3;

        // Act
        Optional<String[][]> result = Spelverdeling.spelverdeling(ploegen, spellen, dubbels, rondes);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void testTooManyGames() {
        // Arrange
        int ploegen = 4;
        int spellen = 10;
        int dubbels = 1;
        int rondes = 3;

        // Act
        Optional<String[][]> result = Spelverdeling.spelverdeling(ploegen, spellen, dubbels, rondes);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIncreasingRounds() {
        // Arrange
        int ploegen = 6;
        int spellen = 6;
        int dubbels = 1;
        int rondes = 1;

        // Act
        Optional<String[][]> result = Spelverdeling.spelverdeling(ploegen, spellen, dubbels, rondes);

        // Assert
        assertTrue(result.isPresent());
        String[][] verdeling = result.get();
        assertEquals(verdeling.length, rondes);
        assertEquals(verdeling[0].length, spellen);
    }
}
