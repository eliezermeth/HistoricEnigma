package machines;

import machine_pieces.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import resources.AlphabetConverter;
import resources.WiringData;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test ConstructedFullModel.
 *
 * @author Eliezer Meth
 * @version 1<br>
 * Start Date: 2024-07-01
 */
class ConstructedFullModelTest
{
    static ConstructedFullModel model;
    static AlphabetConverter ac;

    /**
     * Create model of machine for testing
     */
    @BeforeAll
    static void createModel()
    {
        // ensure AlphabetConverter exists
        if (!AlphabetConverter.exists())
            AlphabetConverter.createAlphabetConverter("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        ac = AlphabetConverter.getAlphabetConverter();

        EntryWheel entryWheel = new EntryWheel(EntryWheel.ETWsequence.ABCDE); // A -> A

        Plugboard plugboard = new Plugboard(); // no connections

        Map<String, Map<String, GearConstruction>> components = WiringData.Enigma1();

        Rotor[] rotors = new Rotor[3];
        rotors[0] = new Rotor("III", components);
        rotors[1] = new Rotor("II", components);
        rotors[2] = new Rotor("I", components);

        Reflector reflector = new Reflector("B", components);

        model = new ConstructedFullModel(entryWheel, plugboard, rotors, reflector);
    }

    @Test
    void type()
    {
        assertEquals('F', model.type('A'));
        assertEquals('U', model.type('B'));

        model.getRotorAssembly()[2].setGroundPosition('O');
        assertEquals('A', model.getRotorAssembly()[1].getWindow());

        assertEquals('N', model.type('A'));
        assertEquals('J', model.type('A'));
        assertEquals('Q', model.getRotorAssembly()[2].getWindow());
        assertEquals('N', model.type('B')); // engage turnover
        assertEquals('R', model.getRotorAssembly()[2].getWindow());
        assertEquals('B', model.getRotorAssembly()[1].getWindow());
    }

    @Test
    void getEntryWheel()
    {
    }

    @Test
    void getPlugboard()
    {
    }

    @Test
    void getRotorAssembly()
    {
    }

    @Test
    void getReflector()
    {
    }
}