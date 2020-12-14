package machine_pieces;

import org.junit.jupiter.api.Test;
import utilities.WiringData;

import java.sql.Ref;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConstructedMilitaryModelTest
{

    @Test
    void type()
    {
        Map<String, GearConstruction[]> selection = WiringData.Enigma1();

        Rotor[] rotors = new Rotor[3];
        rotors[0] = new Rotor("1", "A", "A", selection);
        rotors[1] = new Rotor("2", "A", "A", selection);
        rotors[2] = new Rotor("3", "A", "A", selection);

        Reflector reflector = new Reflector("2", selection);

        Plugboard plugboard = new Plugboard();

        ConstructedMilitaryModel model = new ConstructedMilitaryModel(rotors, reflector, plugboard);

        String plaintext = "ABC";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++)
        {
            sb.append(model.type(plaintext.charAt(i)));
        }
        String ciphertext = sb.toString();

        System.out.println(ciphertext);
    }

    @Test
    void incrementalTest()
    {
        Map<String, GearConstruction[]> selection = WiringData.Enigma1();
        Rotor r1 = new Rotor("1", "A", "A", selection);
        Rotor r2 = new Rotor("2", "A", "A", selection);
        Rotor r3 = new Rotor("3", "A", "A", selection);
        Reflector reflector = new Reflector("2", selection);
        Plugboard plugboard = new Plugboard();

        // step
        r1.step();

        // make letter
        int num = 'A' - 'A'; // convert to 0-based int
        int cipherLetter = type(num, plugboard, r1, r2, r3, reflector); // type letter into machine

        char letter = (char) (cipherLetter + 'A');
        System.out.println(letter);

        r1.step();

        // make letter
        num = 'B' - 'A'; // convert to 0-based int
        cipherLetter = type(num, plugboard, r1, r2, r3, reflector); // type letter into machine

        letter = (char) (cipherLetter + 'A');
        System.out.println(letter);

        r1.step();

        // make letter
        num = 'C' - 'A'; // convert to 0-based int
        cipherLetter = type(num, plugboard, r1, r2, r3, reflector); // type letter into machine

        letter = (char) (cipherLetter + 'A');
        System.out.println(letter);
        //printDetails(plugboard, r1, r2, r3, reflector);
    }

    private int type(int letter, Plugboard plugboard, Rotor r1, Rotor r2, Rotor r3, Reflector reflector)
    {
        Rotor[] rotors = {r1, r2, r3};
        return plugboard.indexOf
                (rotor321(
                        reflector.get(
                                rotor123(
                                        plugboard.get(letter)
                                , rotors)
                        )
                , rotors));
    }

    private int rotor123(int letter, Rotor[] rotors)
    {
        for (int i = 0; i < rotors.length; i++)
        {
            System.out.print("Rotor " + i + ": " + (char)(letter + 'A'));
            letter = rotors[i].get(letter);
            System.out.println(" -> " + (char)(letter + 'A'));
        }
        return letter;
    }

    private int rotor321(int letter, Rotor[] rotors)
    {
        for (int i = rotors.length - 1; i > -1; i--)
            letter = rotors[i].indexOf(letter);
        return letter;
    }

    private void printDetails(Plugboard plugboard, Rotor r1, Rotor r2, Rotor r3, Reflector reflector)
    {
        //System.out.println(plugboard);
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(reflector);

    }
}