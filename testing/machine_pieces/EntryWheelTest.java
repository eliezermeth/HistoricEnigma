package machine_pieces;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import resources.AlphabetConverter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Eliezer Meth
 * @version 1<br>
 * Start Date: 2024-06-06<br>
 * Last Modified: 2024-06-19
 */
class EntryWheelTest
{
    static AlphabetConverter ac;
    static EntryWheel abc;
    static EntryWheel qwe;

    static String stringABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String stringQWE = "QWERTYUIOPASDFGHJKLZXCVBNM";
    static String alternateAlphabet = "QAZWSXEDCRFVTGBYHNUJMIKOLP";


    @BeforeAll
    static void setup()
    {
        if (!AlphabetConverter.exists())
            AlphabetConverter.createAlphabetConverter("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        ac = AlphabetConverter.getAlphabetConverter();

        abc = new EntryWheel(EntryWheel.ETWsequence.ABCDE);
        qwe = new EntryWheel(EntryWheel.ETWsequence.QWERTY);

    }

    // Does not test constructor if ETWsequence is a preset.  Does not test if AlphabetConverter does not exist; that
    // throws an IllegalStateException.  Only tests with 26-letter alphabet.

    // set() method only to be used when EntryWheel is created with ETWsequence.CUSTOM.
    @Test
    void set()
    {
        // attempt set with constructor-preset wheel; will return false
        assertFalse(abc.set(stringQWE, stringABC));
        assertFalse(qwe.set(stringQWE, stringQWE));

        // custom
        EntryWheel custom = new EntryWheel(EntryWheel.ETWsequence.CUSTOM);
        assertTrue(custom.set(stringABC, stringQWE));
        // also test if wirings placed in correct place
        String[] actual = custom.getWirings();
        assertEquals(stringABC, actual[0]);
        assertEquals(stringQWE, actual[1]);
        // test that doesn't allow to be changed
        assertFalse(custom.set(stringQWE, stringABC));
    }

    @Test
    void getSetting()
    {
        assertEquals(EntryWheel.ETWsequence.ABCDE, abc.getSetting());
        assertEquals(EntryWheel.ETWsequence.QWERTY, qwe.getSetting());

        EntryWheel temp = new EntryWheel(EntryWheel.ETWsequence.CUSTOM);
        assertEquals(EntryWheel.ETWsequence.CUSTOM, temp.getSetting());
    }

    @Test
    void getWirings()
    {
        String[] temp = qwe.getWirings();
        assertEquals(stringQWE, temp[0]);
        assertEquals(stringABC, temp[1]);

        temp = abc.getWirings();
        assertEquals(stringABC, temp[0]);
        assertEquals(stringABC, temp[1]);
    }

    @Test
    void input()
    {
        assertEquals(ac.convert('A'), abc.input(ac.convert('A')));
        assertEquals(ac.convert('B'), abc.input(ac.convert('B')));
        assertEquals(ac.convert('Z'), abc.input(ac.convert('Z')));
        assertEquals(-1, abc.input(-10)); // invalid; below bound
        assertEquals(-1, abc.input(26)); // invalid; above bound

        assertEquals(ac.convert('A'), qwe.input(ac.convert('Q')));
        assertEquals(ac.convert('B'), qwe.input(ac.convert('W')));
        assertEquals(ac.convert('C'), qwe.input(ac.convert('E')));
        assertEquals(ac.convert('Z'), qwe.input(ac.convert('M')));
    }

    @Test
    void output()
    {
        assertEquals(ac.convert('A'), abc.output(ac.convert('A')));
        assertEquals(ac.convert('B'), abc.output(ac.convert('B')));
        assertEquals(ac.convert('Z'), abc.output(ac.convert('Z')));
        assertEquals(-1, abc.output(-10)); // invalid; below bound
        assertEquals(-1, abc.output(26)); // invalid; above bound

        assertEquals(ac.convert('Q'), qwe.output(ac.convert('A')));
        assertEquals(ac.convert('W'), qwe.output(ac.convert('B')));
        assertEquals(ac.convert('E'), qwe.output(ac.convert('C')));
        assertEquals(ac.convert('M'), qwe.output(ac.convert('Z')));
    }
}