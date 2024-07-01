package utilities;

import machine_pieces.GearConstruction;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the wiring data for rotors (with their turnovers) and reflectors.
 * <br><br>
 * Historic rotor configurations can be found at:<br>
 * <a href="https://en.wikipedia.org/wiki/Enigma_rotor_details">https://en.wikipedia.org/wiki/Enigma_rotor_details</a><br>
 * <a href="https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10">https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10</a><br>
 * <br>
 * @author Eliezer Meth
 * @version 2<br>
 * Start Date: 2020-09-17<br>
 * Last Modified: 2024-05-08
 */

public class WiringData
{
    /**
     * Enum of Enigma versions.<br>
     * <b>Enigma 1</b> <br>
     * <b>Enigma M3</b>
     */
    public enum enimgaVersion
    {
            ENIGMA_1, ENIGMA_M3
    }

    /**
     * This method contains the information for: Enigma I
     * This version was used by: Army, Luftwaffe (Air Force)<br>
     * <br>
     * Data is returned in a double-layered map.  First level is rotor/reflector, and second level is the name of the
     * specific part.
     * <br>
     * @return Map&lt;String, Map&lt;String, machine.GearConstruction&gt;&gt; of gear data
     */
    //public static Map<String, GearConstruction[]> Enigma1()
    public static Map<String, Map<String, GearConstruction>> Enigma1()
    {
        Map<String, Map<String, GearConstruction>> selection = new HashMap<>();

        // rotors
        Map<String, GearConstruction> rotors = new HashMap<>();
        rotors.put("I", new GearConstruction("EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q"));
        rotors.put("II", new GearConstruction("AJDKSIRUXBLHWTMCQGZNPYFVOE", "E"));
        rotors.put("III", new GearConstruction("BDFHJLCPRTXVZNYEIWGAKMUSQO", "V"));
        rotors.put("IV", new GearConstruction("ESOVPZJAYQUIRHXLNFTGKDCMWB", "J"));
        rotors.put("V", new GearConstruction("VZBRGITYUPSDNHLXAWMJQOFECK", "Z"));

        // reflectors (UKW)
        Map<String, GearConstruction> reflectors = new HashMap<>();
        reflectors.put("A", new GearConstruction("EJMZALYXVBWFCRQUONTSPIKHGD", false, false, false));
        reflectors.put("B", new GearConstruction("YRUHQSLDPXNGOKMIEBFZCWVJAT", false, false, false));
        reflectors.put("C", new GearConstruction("FVPJIAOYEDRZXWGCTKUQSBNMHL", false, false, false));

        // Place rotors and reflectors into map
        selection.put("rotor", rotors);
        selection.put("reflector", reflectors);

        return selection;
    }

    /**
     * This method contains the information for: Enigma M3
     * This version was used by: Kriegsmarine (Navy)
     * <br>
     * Data is returned in a double-layered map.  First level is rotor/reflector, and second level is the name of the
     * specific part.
     * <br>
     * @return Map&lt;String, Map&lt;String, machine.GearConstruction&gt;&gt; of gear data
     */
    public static Map<String, Map<String, GearConstruction>> EnigmaM3()
    {
        Map<String, Map<String, GearConstruction>> selection = new HashMap<>();

        // rotors
        Map<String, GearConstruction> rotors = new HashMap<>();
        rotors.put("I", new GearConstruction("EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q"));
        rotors.put("II", new GearConstruction("AJDKSIRUXBLHWTMCQGZNPYFVOE", "E"));
        rotors.put("III", new GearConstruction("BDFHJLCPRTXVZNYEIWGAKMUSQO", "V"));
        rotors.put("IV", new GearConstruction("ESOVPZJAYQUIRHXLNFTGKDCMWB", "J"));
        rotors.put("V", new GearConstruction("VZBRGITYUPSDNHLXAWMJQOFECK", "Z"));
        rotors.put("VI", new GearConstruction("JPGVOUMFYQBENHZRDKASXLICTW", "ZM"));
        rotors.put("VII", new GearConstruction("NZJHGRCXMYSWBOUFAIVLPEKQDT", "ZM"));
        rotors.put("VIII", new GearConstruction("FKQHTLXOCBJSPDZRAMEWNIUYGV", "ZM"));

        // reflectors (UKW)
        Map<String, GearConstruction> reflectors = new HashMap<>();
        reflectors.put("B", new GearConstruction("YRUHQSLDPXNGOKMIEBFZCWVJAT", false, false, false));
        reflectors.put("C", new GearConstruction("FVPJIAOYEDRZXWGCTKUQSBNMHL", false, false, false));

        // Place rotors and reflectors into map
        selection.put("rotor", rotors);
        selection.put("reflector", reflectors);

        return selection;
    }
}
