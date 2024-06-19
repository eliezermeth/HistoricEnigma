package utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Eliezer Meth
 * @version 1.0.1<br>
 * Start Date: 2024-05-18<br>
 * Last Modified: 2024-06-03
 */
class AlphabetConverterTest
{
    String testAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Test
    void createAlphabetConverterString()
    {
        assertDoesNotThrow(() -> AlphabetConverter.createAlphabetConverter(testAlphabet));
        assertThrows(IllegalStateException.class, () -> AlphabetConverter.createAlphabetConverter(testAlphabet));
    }

    @Test
    void testCreateAlphabetConverterCharArray()
    {
        // test run by createAlphabetConverterString suffices - the method that takes the string parameter calls the
        // method that takes the char array parameter
    }

    @Test
    void getAlphabetConverter()
    {
        // as of now, not able to test IllegalStateException

        assertNotNull(AlphabetConverter.getAlphabetConverter());
    }

    @Test
    void alphabetConverterExists()
    {
        // unable to test if does not exist

        assertTrue(AlphabetConverter.exists());
    }

    @Test
    void getAlphabetString()
    {
        AlphabetConverter ac = AlphabetConverter.getAlphabetConverter();
        assertEquals(testAlphabet, ac.getAlphabetString());
    }

    @Test
    void getAlphabet()
    {
        AlphabetConverter ac = AlphabetConverter.getAlphabetConverter();
        assertArrayEquals(testAlphabet.toCharArray(), ac.getAlphabet());
    }

    @Test
    void convert() // input char, output int
    {
        AlphabetConverter ac = AlphabetConverter.getAlphabetConverter();
        assertEquals(0, ac.convert('A'));
        assertEquals(25, ac.convert('Z'));
        assertEquals(-1, ac.convert('a'));
    }

    @Test
    void testConvert() // input char, output int
    {
        AlphabetConverter ac = AlphabetConverter.getAlphabetConverter();
        assertEquals('A', ac.convert(0));
        assertEquals('Z', ac.convert(25));
        assertEquals('\0', ac.convert(-1));
        assertEquals('\0', ac.convert(100));
    }
}