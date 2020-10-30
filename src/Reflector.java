import java.util.Arrays;
import java.util.Map;

/**
 * @author Eliezer Meth
 * Start Date: 2020-09-24
 *
 * This class is to simulate a reflector that could be inserted into the machine.
 *
 * Historic reflector configurations can be found at:
 * https://en.wikipedia.org/wiki/Enigma_rotor_details
 * https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10
 */

public class Reflector implements Wiring
{
    // External rotor information
    private final int reflectorSelected; // reflector selected

    // Internal rotor configurations
    private final char[] visibleLetters = Utilities.getAzArray(); // get array of letters where they would be on disk
    private final int[] reflector; // internal reflector wiring

    /**
     * Constructor to select correct reflector.
     * @param reflectorSelected String (A+) representing the selected reflector.
     * @param selection Map<String, GearConstruction[]> for rotors and reflectors for a version of the Enigma.
     */
    public Reflector(String reflectorSelected, Map<String, GearConstruction[]> selection)
    {
        this.reflectorSelected = Integer.parseInt(reflectorSelected) - 1; // subtract 1 to get to computer terms

        reflector = selection.get("reflector")[this.reflectorSelected].getReflectorWiring();
    }

    public String getReflectorSelected()
    {
        return Integer.toString(reflectorSelected + 1); // return to human terms }
    }

    /**
     * Method to get reflection of letter from direction of keyboard to lights.
     * @param letter Int (adjusted) of letter of the letter from direction of keyboard.
     * @return int (adjusted) of letter toward lights.
     */
    public int get(int letter)
    {
        return reflector[letter];
    }

    /**
     * Method to get reflection of letter from (on normal Enigma machine) direction of lights to keyboard.
     * WARNING:  This method should not be called for any version of the German Enigma machine.
     * Only versions such as the British TypeX machine should make use of this method.
     * @param letter Int (adjusted) of the letter from direction of the keyboard.
     * @return Int (adjusted) of letter toward lights.
     */
    public int indexOf(int letter)
    {
        int index = -1; // set to throw error

        // search array and terminate when found
        for (int i = 0; i < reflector.length; i++)
            if (reflector[i] == letter)
            {
                return i;
            }

        return index;
    }

    /**
     * Method to return string of regular letters array and reflected letters array.
     * @return String of regular letters array and reflected letters array.
     */
    public String toString()
    {
        // Convert int[] reflector to string array
        String[] reflectorArray = new String[reflector.length];
        for (int i = 0; i < reflectorArray.length; i++)
        {
            reflectorArray[i] = Character.toString((char) (reflector[i] + 65)); // num to ASCII, to char, to String
        }

        // Construct and return array string
        return Arrays.toString(visibleLetters) + "\n" + Arrays.toString(reflectorArray);
    }
}
// TODO methods and fields for if rotatable and rewirable