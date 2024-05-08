package machine_pieces;

import org.junit.jupiter.api.Test;
import utilities.WiringData;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/***
 * @author Eliezer Meth
 * @version 1<br>
 * Start Date: 2024-04-11<br>
 * Last Modified: 2024-04-17
 */
class RotorTest
{
    // set up for rotors to be used by tests
    //Map<String, GearConstruction[]> enigma1 = WiringData.Enigma1();
    Map<String, Map<String, GearConstruction>> enigma1 = WiringData.Enigma1();

    @Test
    void getRotorSelected()
    {
        Rotor rotor1 = new Rotor("I", enigma1);
        assertEquals("I", rotor1.getRotorSelected());

        Rotor rotor2 = new Rotor("II", 1, 'B', enigma1);
        assertEquals("II", rotor2.getRotorSelected());
    }

    @Test
    void setRingSetting()
    {
        // test what happens when a ring setting is modified on an existing rotor
        Rotor rotor1 = new Rotor("I", enigma1);
        rotor1.setRingSetting(13);
        assertEquals(13, rotor1.getRingSetting());
        // test end
        rotor1.setRingSetting(26); // Z
        assertEquals(26, rotor1.getRingSetting());
        // give overflowing number; should wrap around
        rotor1.setRingSetting(27);
        assertEquals(1, rotor1.getRingSetting());
        // zero and negative
        rotor1.setRingSetting(0);
        assertEquals(26, rotor1.getRingSetting());
        rotor1.setRingSetting(-1);
        assertEquals(1, rotor1.getRingSetting());
    }

    @Test
    void getRingSetting()
    {
        // at creation of rotor
        Rotor rotor1 = new Rotor("I", enigma1);
        assertEquals(1, rotor1.getRingSetting());

        Rotor rotor2 = new Rotor("II", 5, 'B', enigma1);
        assertEquals(5, rotor2.getRingSetting());
    }

    @Test
    void setGroundPosition()
    {
        Rotor rotor1 = new Rotor("I", enigma1);
        assertEquals('A', rotor1.getGroundPosition());
        assertTrue(rotor1.setGroundPosition('M'));
        assertEquals('M', rotor1.getGroundPosition());
        assertEquals('M', rotor1.getWindow());
        assertFalse(rotor1.setGroundPosition('&')); // invalid character
    }

    @Test
    void getGroundPosition()
    {
        Rotor rotor1 = new Rotor("I", enigma1);
        assertEquals('A', rotor1.getGroundPosition());

        Rotor rotor2 = new Rotor("II", 1, 'B', enigma1);
        assertEquals('B', rotor2.getGroundPosition());
    }

    @Test
    void getWindow()
    {
        Rotor rotor1 = new Rotor("I", enigma1);
        assertEquals('A', rotor1.getWindow());
        rotor1.step();
        assertEquals('B', rotor1.getWindow());
    }

    @Test
    void getWirings()
    {
        // only test the first few elements of each ArrayList; assume all others correct if those are correct
        Rotor rotor1 = new Rotor("I", enigma1);

        ArrayList<Character>[] temp = rotor1.getWirings(); // basic
        ArrayList<Character> testLetters = temp[0];
        ArrayList<Character> testWirings = temp[1];
        char[] expectedLetters = {'A', 'B', 'C', 'D', 'E'};
        char[] expectedWirings = {'E', 'K', 'M', 'F', 'L'};
        for (char i = 0; i < expectedLetters.length; i++)
            assertEquals(expectedLetters[i], testLetters.get(i));
        for (char i = 0; i < expectedWirings.length; i++)
            assertEquals(expectedWirings[i], testWirings.get(i));

        rotor1.step(); // step and test
        temp = rotor1.getWirings();
        testLetters = temp[0];
        testWirings = temp[1];
        expectedLetters = new char[]{'B', 'C', 'D', 'E', 'F'};
        expectedWirings = new char[]{'K', 'M', 'F', 'L', 'G'};
        for (char i = 0; i < expectedLetters.length; i++)
            assertEquals(expectedLetters[i], testLetters.get(i));
        for (char i = 0; i < expectedWirings.length; i++)
            assertEquals(expectedWirings[i], testWirings.get(i));

        rotor1.setRingSetting(1); // set to A; will reset rotor
        temp = rotor1.getWirings();
        testLetters = temp[0];
        testWirings = temp[1];
        expectedLetters = new char[]{'A', 'B', 'C', 'D', 'E'};
        expectedWirings = new char[]{'E', 'K', 'M', 'F', 'L'};
        for (char i = 0; i < expectedLetters.length; i++)
            assertEquals(expectedLetters[i], testLetters.get(i));
        for (char i = 0; i < expectedWirings.length; i++)
            assertEquals(expectedWirings[i], testWirings.get(i));

        rotor1.setRingSetting(2); // set to B; will reset rotor
        temp = rotor1.getWirings();
        testLetters = temp[0];
        testWirings = temp[1];
        expectedLetters = new char[]{'A', 'B', 'C', 'D', 'E'};
        expectedWirings = new char[]{'K', 'F', 'L', 'N', 'G'};
        for (char i = 0; i < expectedLetters.length; i++)
            assertEquals(expectedLetters[i], testLetters.get(i));
        System.out.println();
        for (char i = 0; i < expectedWirings.length; i++)
            assertEquals(expectedWirings[i], testWirings.get(i));
    }

    @Test
    void getTurnoverPositions()
    {
        Rotor rotor1 = new Rotor("I", enigma1);
        assertArrayEquals(new char[]{'Q'}, rotor1.getTurnoverPositions());

        Rotor rotor2 = new Rotor("II", 1, 'B', enigma1);
        assertArrayEquals(new char[]{'E'}, rotor2.getTurnoverPositions());

        // test rotor with multiple turnovers
        Map<String, Map<String, GearConstruction>> enigmaM3 = WiringData.EnigmaM3();
        Rotor rotor3 = new Rotor("VI", enigmaM3);
        assertArrayEquals(new char[]{'Z', 'M'}, rotor3.getTurnoverPositions());
    }

    @Test
    void input()
    {
        // most tests on rotor 1
        Rotor rotor1 = new Rotor("I", enigma1);
        assertEquals(4, rotor1.input(0)); // rotor receives A, sends E
        assertEquals(14, rotor1.input(12)); // rotor receives M, sends O
        rotor1.step();
        assertEquals(9, rotor1.input(0)); // rotor receives B, sends K
        rotor1.setGroundPosition('X');
        assertEquals(5, rotor1.input(1)); // rotor receives Y, sends C
        rotor1.setRingSetting(2); // ring setting 02; resets ground to A
        assertEquals(10, rotor1.input(0)); // rotor receives A, sends K
        assertEquals(5, rotor1.input(1)); // rotor receives B, sends F

        // confirm initial tests on rotor 2
        Rotor rotor2 = new Rotor("II", 1, 'B', enigma1);
        assertEquals(8, rotor2.input(0)); // rotor receives B, sends J
        assertEquals(2, rotor2.input(1)); // rotor receives C, sends D
        rotor2.setGroundPosition('A');
        assertEquals(0, rotor2.input(0)); // rotor receives A, sends A
    }

    @Test
    void output()
    {
        // most tests on rotor 1
        Rotor rotor1 = new Rotor("I", enigma1);
        assertEquals(20, rotor1.output(0)); // rotor receives A, sends U
        assertEquals(22, rotor1.output(1)); // rotor receives B, sends W
        assertEquals(0, rotor1.output(4)); // rotor receives E, sends A
        rotor1.step();
        assertEquals(14, rotor1.output(6)); // rotor receives H, sends P
        rotor1.setGroundPosition('M');
        assertEquals(18, rotor1.output(25)); // rotor receives L, sends E
        rotor1.setRingSetting(3); // ring setting 03; resets ground to A
        assertEquals(25, rotor1.output(19)); // rotor receives T, sends Z
        assertEquals(22, rotor1.output(2)); // rotor receives C, sends W

        // confirm initial tests on rotor 2
        Rotor rotor2 = new Rotor("II", 1, 'B', enigma1);
        assertEquals(16, rotor2.output(5)); // rotor receives G, sends R
        assertEquals(17, rotor2.output(24)); // rotor receives Z, sends S
    }

    @Test
    void step()
    {
        Rotor rotor1 = new Rotor("I", enigma1);
        assertEquals('A', rotor1.getWindow());

        boolean propagate = rotor1.step();
        assertEquals('B', rotor1.getWindow());
        assertFalse(propagate); // stepping does not propagate; not at turnover position

        propagate = rotor1.step();
        assertEquals('C', rotor1.getWindow());
        assertFalse(propagate);

        rotor1.setGroundPosition('P');

        propagate = rotor1.step();
        assertEquals('Q', rotor1.getWindow());
        assertFalse(propagate); // does not propagate when Q turns into the window

        propagate = rotor1.step();
        assertEquals('R', rotor1.getWindow());
        assertTrue(propagate); // propagates when Q disappears from the window

        propagate = rotor1.step();
        assertEquals('S', rotor1.getWindow());
        assertFalse(propagate);
    }
}