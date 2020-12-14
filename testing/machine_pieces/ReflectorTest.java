package machine_pieces;

import machine_pieces.Reflector;
import org.junit.jupiter.api.Test;
import utilities.WiringData;

import static org.junit.jupiter.api.Assertions.*;

class ReflectorTest
{

    @Test
    void getReflectorSelected()
    {
        Reflector reflector = new Reflector("1", WiringData.Enigma1());
        assertEquals("1", reflector.getReflectorSelected());
        reflector = new Reflector("2", WiringData.Enigma1());
        assertEquals("2", reflector.getReflectorSelected());
    }

    @Test
    void get()
    {
        Reflector reflector = new Reflector("1", WiringData.Enigma1());
        assertEquals(4, reflector.get(0));
        assertEquals(3, reflector.get(25));
    }

    @Test
    void indexOf()
    {
        Reflector reflector = new Reflector("1", WiringData.Enigma1());
        assertEquals(0, reflector.indexOf(4));
        assertEquals(25, reflector.indexOf(3));
    }
}