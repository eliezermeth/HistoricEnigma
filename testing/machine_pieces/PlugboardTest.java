package machine_pieces;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.AlphabetConverter;
import utilities.Utilities;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Eliezer Meth
 * @version 1.0.1<br>
 * Start Date: 2024-05-20<br>
 * Last Modified: 2024-06-03
 */
class PlugboardTest
{

    static AlphabetConverter ac;
    final char EMPTY_CHAR = '\0';

    @BeforeAll
    static void setup()
    {
        if (!AlphabetConverter.exists())
            AlphabetConverter.createAlphabetConverter("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        ac = AlphabetConverter.getAlphabetConverter();
    }

    // test blank constructor and char[] constructor (covers string)
    @Test
    void constructorBlank()
    {
        Plugboard p = new Plugboard();
        assertEquals(26, p.getAlphabet().length);
    }

    @Test
    void constructorString() throws Exception
    {
        Plugboard p = new Plugboard("ABC");
        assertEquals(3, p.getAlphabet().length);

        assertThrows(Exception.class, () -> new Plugboard("AAA"));
    }

    @Test
    void constructorCharArray() throws Exception
    {
        char[] array = {'a', 'b', 'c', 'd', 'e'};
        Plugboard p = new Plugboard(array);
        assertArrayEquals(array, p.getAlphabet());

        assertThrows(Exception.class, () -> new Plugboard(new char[] {'a', 'b', 'c', 'a'}));
    }

    @Test
    void resetPlugboard()
    {
        Plugboard p = new Plugboard();
        assertEquals(0, p.numberOfConnections());
        assertEquals(0, p.input(0));
        // change plugboard
        p.insertWire("AB");
        assertEquals(1, p.numberOfConnections());
        assertEquals(1, p.input(0));
        // revert and test
        p.resetPlugboard();
        assertEquals(0, p.numberOfConnections());
        assertEquals(0, p.input(0));
    }

    @Test
    void insertWireString()
    {
        String test = "AB";

        Plugboard p = new Plugboard();
        assertEquals(0, p.numberOfConnections());
        assertTrue(p.insertWire(test));
        assertFalse(p.insertWire("BC")); // not allowed since B already swapped
        assertEquals(1, p.numberOfConnections());
        assertTrue(p.getConnections().contains(test));
        assertEquals(1, p.input(0));
        assertFalse(p.insertWire("ZZ")); // wire cannot link letter to itself
    }

    @Test
    void insertWire2Chars()
    {
        Plugboard p = new Plugboard();
        assertEquals(0, p.numberOfConnections());
        assertTrue(p.insertWire('A', 'B'));
        assertFalse(p.insertWire('C', 'B'));
        assertEquals(1, p.numberOfConnections());
        assertTrue(p.getConnections().contains("AB"));
        assertEquals(1, p.input(0));
        assertFalse(p.insertWire('Z', 'Z'));
    }

    @Test
    void insertWireCharArray()
    {
        char[] temp = {'A', 'B'}, invalid = {'B', 'C'}, duplicate = {'Z', 'Z'};

        Plugboard p = new Plugboard();
        assertEquals(0, p.numberOfConnections());
        assertTrue(p.insertWire(temp));
        assertFalse(p.insertWire(invalid));
        assertEquals(1, p.numberOfConnections());
        assertTrue(p.getConnections().contains("AB"));
        assertEquals(1, p.input(0));
        assertFalse(p.insertWire(duplicate));
    }

    @Test
    void removeWire1Char()
    {
        String wire = "AB";

        Plugboard p = new Plugboard();
        assertEquals(0, p.numberOfConnections());
        assertTrue(p.insertWire(wire)); // wire successfully inserted
        assertEquals(1, p.numberOfConnections());
        assertTrue(p.getConnections().contains(wire));
        assertEquals(1, p.input(0));
        assertEquals(0, p.input(1));

        assertTrue(p.removeWire('A')); // should remove AB
        assertEquals(0, p.numberOfConnections());
        assertFalse(p.getConnections().contains(wire));
        assertEquals(0, p.input(0));
        assertEquals(1, p.input(1));

        assertFalse(p.removeWire('C')); // no connection existed
        assertFalse(p.removeWire('A')); // since no longer has connection
    }

    @Test
    void removeWire2Chars()
    {
        String wire = "AB";

        Plugboard p = new Plugboard();
        assertEquals(0, p.numberOfConnections());
        assertTrue(p.insertWire(wire)); // wire successfully inserted
        assertEquals(1, p.numberOfConnections());
        assertTrue(p.getConnections().contains(wire));
        assertEquals(1, p.input(0));
        assertEquals(0, p.input(1));

        assertTrue(p.removeWire('B', 'A')); // should remove AB; flipped to test if works backward
        assertEquals(0, p.numberOfConnections());
        assertFalse(p.getConnections().contains(wire));
        assertEquals(0, p.input(0));
        assertEquals(1, p.input(1));

        assertFalse(p.removeWire('C')); // no connection existed
        assertFalse(p.removeWire('A')); // since no longer has connection
    }

    @Test
    void removeWireCharArray()
    {
        String wire = "AB";

        Plugboard p = new Plugboard();
        assertEquals(0, p.numberOfConnections());
        assertTrue(p.insertWire(wire)); // wire successfully inserted
        assertEquals(1, p.numberOfConnections());
        assertTrue(p.getConnections().contains(wire));
        assertEquals(1, p.input(0));
        assertEquals(0, p.input(1));

        assertTrue(p.removeWire(wire.toCharArray())); // should remove AB
        assertEquals(0, p.numberOfConnections());
        assertFalse(p.getConnections().contains(wire));
        assertEquals(0, p.input(0));
        assertEquals(1, p.input(1));

        assertFalse(p.removeWire('C')); // no connection existed
        assertFalse(p.removeWire('A')); // since no longer has connection
    }

    @Test
    void removeWireString()
    {
        String wire = "AB";

        Plugboard p = new Plugboard();
        assertEquals(0, p.numberOfConnections());
        assertTrue(p.insertWire(wire)); // wire successfully inserted
        assertEquals(1, p.numberOfConnections());
        assertTrue(p.getConnections().contains(wire));
        assertEquals(1, p.input(0));
        assertEquals(0, p.input(1));

        assertTrue(p.removeWire("BA")); // should remove AB; flipped to test if works backward
        assertEquals(0, p.numberOfConnections());
        assertFalse(p.getConnections().contains(wire));
        assertEquals(0, p.input(0));
        assertEquals(1, p.input(1));

        assertFalse(p.removeWire('C')); // no connection existed
        assertFalse(p.removeWire('A')); // since no longer has connection
    }

    @Test
    void getConnections()
    {
        String[] wires = {"AD", "CF", "ZM", "XL", "WI", "OP", "BH", "GT"};

        LinkedList<String> temp;

        Plugboard p = new Plugboard();
        for (int i = 0; i < 1; i++)
            assertTrue(p.insertWire(wires[i]));
        temp = p.getConnections();
        for (int i = 0; i < 1; i++) // contains
            assertTrue(temp.contains(wires[i]));
        for (int i = 1; i < 8; i++) // does not contain
            assertFalse(temp.contains(wires[i]));

        for (int i = 1; i < 5; i++)
            assertTrue(p.insertWire(wires[i]));
        temp = p.getConnections();
        for (int i = 0; i < 5; i++)
            assertTrue(temp.contains(wires[i]));
        for (int i = 5; i < 8; i++)
            assertFalse(temp.contains(wires[i]));

        for (int i = 5; i < 8; i++)
            assertTrue(p.insertWire(wires[i]));
        temp = p.getConnections();
        for (int i = 0; i < 8; i++)
            assertTrue(temp.contains(wires[i]));
    }

    @Test
    void hasConnection()
    {
        Plugboard p = new Plugboard();
        assertFalse(p.hasConnection('A')); // no connection made
        assertFalse(p.hasConnection('a')); // letter not in alphabet
        assertFalse(p.hasConnection('B'));
        p.insertWire("AB");
        assertTrue(p.hasConnection('A'));
        assertTrue(p.hasConnection('B'));
    }

    @Test
    void findConnectedLetter()
    {
        Plugboard p = new Plugboard();
        assertEquals(EMPTY_CHAR, p.findConnectedLetter('A')); // no connection
        assertEquals(EMPTY_CHAR, p.findConnectedLetter('a')); // letter not in alphabet
        p.insertWire("XY");
        p.insertWire("ZA");
        assertEquals('X', p.findConnectedLetter('Y'));
        assertEquals('Y', p.findConnectedLetter('X'));
        assertEquals('A', p.findConnectedLetter('Z'));
        p.insertWire(EMPTY_CHAR, p.findConnectedLetter('B'));
    }

    @Test
    void findConnection()
    {
        Plugboard p = new Plugboard();
        assertNull(p.findConnection('A'));
        assertNull(p.findConnection('1')); // letter not in alphabet
        p.insertWire("XY");
        p.insertWire("ZA");
        assertEquals("XY", p.findConnection('X'));
        assertEquals("XY", p.findConnection('Y'));
        assertEquals("ZA", p.findConnection('Z'));
        assertEquals("ZA", p.findConnection('A'));
        assertNull(p.findConnection('B'));
    }

    @Test
    void numberOfConnections()
    {
        Plugboard p = new Plugboard();
        assertEquals(0, p.numberOfConnections());
        p.insertWire("AB");
        assertEquals(1, p.numberOfConnections());
        p.insertWire("CD");
        p.insertWire("EF");
        assertEquals(3, p.numberOfConnections());
        p.removeWire("AB");
        assertEquals(2, p.numberOfConnections());
        p.resetPlugboard();
        assertEquals(0, p.numberOfConnections());
    }

    @Test
    void getAlphabet() throws Exception
    {
        Plugboard p = new Plugboard();
        assertArrayEquals(Utilities.getAzArray(), p.getAlphabet());

        char[] temp = {'x', '>', '1', '+', '.', '"', '\''};
        Plugboard x = new Plugboard(temp);
        assertArrayEquals(temp, x.getAlphabet());
    }

    @Test
    void input()
    {
        Plugboard p = new Plugboard();

        // test with letters connected to themselves
        assertEquals(0, p.input(0)); // receive A, send A
        assertEquals(1, p.input(1)); // B
        assertEquals(25, p.input(25)); // Z
        assertEquals(-1, p.input(43)); // invalid

        // input connections and then test
        p.insertWire("BC");
        p.insertWire("FX");
        assertEquals(0, p.input(0));
        assertEquals(2, p.input(1));
        assertEquals(1, p.input(2));
        assertEquals(3, p.input(3));
        assertEquals(23, p.input(5));
        assertEquals(5, p.input(23));
        assertEquals(25, p.input(25));
    }

    @Test
    void output()
    {
        Plugboard p = new Plugboard();

        // test with letters connected to themselves
        assertEquals(0, p.output(0)); // receive A, send A
        assertEquals(1, p.output(1)); // B
        assertEquals(25, p.output(25)); // Z
        assertEquals(-1, p.output(43)); // invalid

        // input connections and then test
        p.insertWire("BC");
        p.insertWire("FX");
        assertEquals(0, p.output(0));
        assertEquals(2, p.output(1));
        assertEquals(1, p.output(2));
        assertEquals(3, p.output(3));
        assertEquals(23, p.output(5));
        assertEquals(5, p.output(23));
        assertEquals(25, p.output(25));
    }
}