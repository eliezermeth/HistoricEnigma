package utilities;

import machine.GearConstruction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eliezer Meth
 * Start Date: 2020-09-17
 * Last Modified: 2020-10-18
 *
 * This class contains the wiring data for rotors (with their turnovers) and reflectors.
 *
 * Historic rotor configurations can be found at:
 * https://en.wikipedia.org/wiki/Enigma_rotor_details
 * https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10
 */

public class WiringData
{
    /**
     * This method contains the information for: Enigma I
     * This version was used by: Army, Luftwaffe (Air Force)
     * @return Map<String, machine.GearConstruction[]> of gear data.
     */
    public static Map<String, GearConstruction[]> Enigma1()
    {
        Map<String, GearConstruction[]> selection = new HashMap<>();

        // rotors
        GearConstruction[] rotors = new GearConstruction[5];
        rotors[0] = new GearConstruction("EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q"); // I
        rotors[1] = new GearConstruction("AJDKSIRUXBLHWTMCQGZNPYFVOE", "E"); // II
        rotors[2] = new GearConstruction("BDFHJLCPRTXVZNYEIWGAKMUSQO", "V"); // III
        rotors[3] = new GearConstruction("ESOVPZJAYQUIRHXLNFTGKDCMWB", "J"); // IV
        rotors[4] = new GearConstruction("VZBRGITYUPSDNHLXAWMJQOFECK", "Z"); // V

        // reflectors
        GearConstruction[] reflectors = new GearConstruction[3];
        reflectors[0] = new GearConstruction("EJMZALYXVBWFCRQUONTSPIKHGD", false, false); // UKW-A
        reflectors[1] = new GearConstruction("YRUHQSLDPXNGOKMIEBFZCWVJAT", false, false); // UKW-B
        reflectors[2] = new GearConstruction("FVPJIAOYEDRZXWGCTKUQSBNMHL", false, false); // UKW-C

        // Place rotors and reflectors into map
        selection.put("rotor", rotors);
        selection.put("reflector", reflectors);

        return selection;
    }

    /**
     * This method contains the information for: Enigma M3
     * This version was used by: Kriegsmarine (Navy)
     * @return Map<String, machine.GearConstruction[]> of gear data.
     */
    public static Map<String, GearConstruction[]> EnigmaM3()
    {
        Map<String, GearConstruction[]> selection = new HashMap<>();

        // rotors
        GearConstruction[] rotors = new GearConstruction[8];
        rotors[0] = new GearConstruction("EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q"); // I
        rotors[1] = new GearConstruction("AJDKSIRUXBLHWTMCQGZNPYFVOE", "E"); // II
        rotors[2] = new GearConstruction("BDFHJLCPRTXVZNYEIWGAKMUSQO", "V"); // III
        rotors[3] = new GearConstruction("ESOVPZJAYQUIRHXLNFTGKDCMWB", "J"); // IV
        rotors[4] = new GearConstruction("VZBRGITYUPSDNHLXAWMJQOFECK", "Z"); // V
        rotors[5] = new GearConstruction("JPGVOUMFYQBENHZRDKASXLICTW", "ZM"); // VI
        rotors[6] = new GearConstruction("NZJHGRCXMYSWBOUFAIVLPEKQDT", "ZM"); // VII
        rotors[7] = new GearConstruction("FKQHTLXOCBJSPDZRAMEWNIUYGV", "ZM"); // VIII

        // reflectors
        GearConstruction[] reflectors = new GearConstruction[2];
        reflectors[0] = new GearConstruction("YRUHQSLDPXNGOKMIEBFZCWVJAT", false, false); // UKW-B
        reflectors[1] = new GearConstruction("FVPJIAOYEDRZXWGCTKUQSBNMHL", false, false); // UKW-C

        // Place rotors and reflectors into map
        selection.put("rotor", rotors);
        selection.put("reflector", reflectors);

        return selection;
    }
}
