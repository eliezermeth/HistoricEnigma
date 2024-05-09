package machine_pieces;

import org.junit.jupiter.api.Test;
import utilities.WiringData;

import java.sql.Ref;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Eliezer Meth
 * @version 1<br>
 * Start Date: 2024-05-08<br>
 * Last Modified: 2024-05-09
 */
class ReflectorTest
{
    // set up reflectors to be used by tests
    Map<String, Map<String, GearConstruction>> enigma1 = WiringData.Enigma1();
    Reflector refB = new Reflector("B", enigma1);
    Reflector refC = new Reflector("C", enigma1);
    // TODO write rotatable, stepping, rewirable after code and methods are determined

    // temporary reflectors with different reflectors and settings; requires calling createTemporaryReflectors()
    Reflector tft;
    Reflector ftf;

    void createTemporaryReflectors()
    {
        // temporary reflectors with different reflectors and settings
        Map<String, GearConstruction> tempRef = new HashMap<>();
        tempRef.put("tft", new GearConstruction("ABC", true, false, true));
        tempRef.put("ftf", new GearConstruction("ABC", false, true, false));
        Map<String, Map<String, GearConstruction>> temporaryMap = new HashMap<>();
        temporaryMap.put("reflector", tempRef);

        tft = new Reflector("tft", temporaryMap);
        ftf = new Reflector("ftf", temporaryMap);
    }

    @Test
    void getReflectorSelected()
    {
        assertEquals("B", refB.getReflectorSelected());
        assertEquals("C", refC.getReflectorSelected());
    }

    @Test
    void isRotatable()
    {
        createTemporaryReflectors();

        assertFalse(refB.isRotatable());
        assertFalse(refC.isRotatable());

        assertTrue(tft.isRotatable());
        assertFalse(ftf.isRotatable());
    }

    @Test
    void isStepping()
    {
        createTemporaryReflectors();

        assertFalse(refB.isStepping());
        assertFalse(refC.isStepping());

        assertFalse(tft.isStepping());
        assertTrue(ftf.isStepping());
    }

    @Test
    void isRewirable()
    {
        createTemporaryReflectors();

        assertFalse(refB.isRewirable());
        assertFalse(refC.isRewirable());

        assertTrue(tft.isRewirable());
        assertFalse(ftf.isRewirable());
    }

    @Test
    void input()
    {
        assertEquals(11, refB.input(6)); // reflector receives G, sends L
        assertEquals(23, refB.input(9)); // reflector receives J, sends X
        assertEquals(15, refC.input(2)); // reflector receives C, sends P
        assertEquals(19, refC.input(16)); // reflector receives Q, sends T
    }

    @Test
    void output()
    {
        // NOTE: Not to be used by typical Enigma machine, as it produces opposite effects.
        assertEquals(6, refB.input(11)); // reflector receives L, sends G
        assertEquals(9, refB.input(23)); // reflector receives X, sends J
        assertEquals(2, refC.input(15)); // reflector receives P, sends C
        assertEquals(16, refC.input(19)); // reflector receives T, sends Q
    }
}