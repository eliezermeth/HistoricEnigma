package machine_pieces;

import org.junit.jupiter.api.Test;
import resources.Utilities;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/***
 * @author Eliezer Meth
 * @version 1<br>
 * Start Date: 2024-04-04
 */
class GearConstructionTest
{

    GearConstruction rotor = new GearConstruction("ZYXWVUTSRQPONMLKJIHGFEDCBA", "TCD");
    // rotor can be used for most tests, since both use rotor and reflector constructors use private method
    // populateWiring() for the first parameter
    GearConstruction ref1 = new GearConstruction("ZYXWVUTSRQPONMLKJIHGFEDCBA", true, true, true);
    GearConstruction ref2 = new GearConstruction("ZYXWVUTSRQPONMLKJIHGFEDCBA", false, false, false);
    GearConstruction ref3 = new GearConstruction("ZYXWVUTSRQPONMLKJIHGFEDCBA", true, false, true);



    @Test
    void getWirings()
    {
        ArrayList<Character>[] wiringList = rotor.getWirings();
        assertEquals(ArrayList.class, wiringList[0].getClass()); // test class (can't test that it is ArrayList<Character>)
        assertEquals(Character.class, wiringList[0].get(0).getClass()); // assert that ArrayLists contain characters

        // test actual contents of rotor wirings
        assertEquals(Utilities.getAzArrayList(), wiringList[0]); // letters
        LinkedList<Character> list = new LinkedList<>();
        for (char letter : "ZYXWVUTSRQPONMLKJIHGFEDCBA".toCharArray())
            list.add(letter);
        assertEquals(list, wiringList[1]); // wirings
    }

    @Test
    void getTurnoverPositions()
    {
        assertArrayEquals(new char[] {'T', 'C', 'D'}, rotor.getTurnoverPositions());
    }

    @Test
    void isReflectorRotatable()
    {
        assertTrue(ref1.isReflectorRotatable());
        assertFalse(ref2.isReflectorRotatable());
        assertTrue(ref3.isReflectorRotatable());
    }

    @Test
    void isReflectorRewirable()
    {
        assertTrue(ref1.isReflectorRewirable());
        assertFalse(ref2.isReflectorRewirable());
        assertTrue(ref3.isReflectorRewirable());
    }

    @Test
    void isReflectorStepping()
    {
        assertTrue(ref1.isReflectorStepping());
        assertFalse(ref2.isReflectorStepping());
        assertFalse(ref3.isReflectorStepping());
    }
}