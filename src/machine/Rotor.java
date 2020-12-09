package machine;

import interfaces.Wiring;
import utilities.Utilities;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Eliezer Meth
 * Start Date: 2020-09-17
 * Last Modified: 2020-09-24
 *
 * This class is to simulate a comlete rotor that could be inserted into the machine.
 *
 * Historic rotor configurations can be found at:
 * https://en.wikipedia.org/wiki/Enigma_rotor_details
 * https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10
 *
 * To see an explanation of rotor settings:
 * https://crypto.stackexchange.com/questions/29315/how-does-the-ring-settings-of-enigma-change-wiring-tables
 */

public class Rotor implements Wiring
{
    // External rotor information
    private final GearConstruction gear; // initial setting of rotor
    private final int rotorSelected; // rotor selected
    private String ringSetting = "A"; // ring setting of rotor; defaults to A
    private char initialPosition = 'A'; // position of starting letter rotor; defaults to A

    // Internal rotor configurations
    private LinkedList<Character> visibleLetters = Utilities.getAzList(); // get LinkedList for letters printed on rotor
    private LinkedList<Integer> wiresConfig; // wire connections and positions inside the rotor
    private final char[] turnover; // letter(s) on top when the notch is engaged to turn over the next rotor

    /**
     * Constructor to allow the rotor to be constructed and fully primed with custom settings upon creation.
     * @param rotorSelected String representing which rotor was selected for this instance.
     * @param ringSetting String representing the ring setting for this rotor.
     * @param initialPosition String representing letter of rotor on top at the start.
     * @param selection Map<String, machine.GearConstruction[]> for rotors and reflectors for a version of the Enigma.
     */
    public Rotor(String rotorSelected, String ringSetting, String initialPosition, Map<String, GearConstruction[]> selection)
    {
        this(rotorSelected, selection); // get rotor by calling other constructor
        setRingSetting(ringSetting); // set ring settings
        setInitialPosition(initialPosition); // set initial ring position
    }

    /**
     * Constructor to allow the rotor to be selected at its default settings.
     * @param rotorSelected String representing which rotor was selected for this instance.
     * @param selection Map<String, machine.GearConstruction[]> for rotors and reflectors for a version of the Enigma.
     */
    public Rotor(String rotorSelected, Map<String, GearConstruction[]> selection)
    {
        this.rotorSelected = Integer.parseInt(rotorSelected) - 1; // subtract 1 to get to computer terms

        // Select rotor information for this rotor
        gear = selection.get("rotor")[this.rotorSelected];

        // Get rotor wiring
        wiresConfig = gear.getRotorWiring();

        // Get turnover positions
        turnover = gear.getTurnoverPositions();
    }

    /**
     * Method to get designation of rotor.
     * @return String of designation of rotor.
     */
    public String getRotorSelected()
    {
        return Integer.toString(rotorSelected + 1); // return to human terms
    }

    /**
     * Method to change ring setting for rotor.
     * @param ringSetting String representing new ring setting.
     */
    public void setRingSetting(String ringSetting)
    {
        this.ringSetting = ringSetting; // update current ring setting

        // Reset rotor to default settings
        wiresConfig = gear.getRotorWiring();
        visibleLetters = Utilities.getAzList();

        int toRingSetting = ringSetting.charAt(0) - 65; // get difference for how much to rotate

        // Rotate external ring
        for (int i = 0; i < 26; i++)
        {
            wiresConfig.set(i, (wiresConfig.get(i) + toRingSetting) % 26);
        }

        // Rotate rotor backward to correct position
        for (int i = 0; i < toRingSetting; i++)
        {
            wiresConfig.add(0, wiresConfig.get(wiresConfig.size() - 1)); // move last element to front of list
            wiresConfig.remove(wiresConfig.size() - 1);
        }
    }

    /**
     * Method to return current ring setting of rotor.
     * @return String of current ring setting of rotor.
     */
    public String getRingSetting()
    {
        return ringSetting;
    }

    /**
     * Method to rotate entire gear to correct position.
     * @param initialPosition String of letter to be on the top of the rotor at the beginning of the run.
     */
    public void setInitialPosition(String initialPosition)
    {
        this.initialPosition = initialPosition.charAt(0);

        // If ringSetting != A, then need to reinitialize the entire gear
        if (!ringSetting.equals("A"))
            setRingSetting(ringSetting);

        // Rotate rotor to correct position
        for (int i = 0; i < this.initialPosition - 'A'; i++)
            step();
    }

    /**
     * Method to return letter on top of gear at beginning of run.
     * @return String of letter on top of gear at beginning of run.
     */
    public String getInitialPosition()
    {
        return Character.toString(initialPosition);
    }

    /**
     * Method to provide stepping for rotor and check if the stepping propagates to the next rotor.
     * @return Boolean; if propagates.
     */
    public boolean step()
    {
        // Check if step produces a turnover
        boolean propagating = false;
        for (char letter : turnover)
            if (visibleLetters.get(0) == letter)
            {
                propagating = true;
                break;
            }

        // Step arrays
        stepUtil(visibleLetters);
        stepUtil(wiresConfig);

        return propagating;
    }

    /**
     * Method to step LinkedList.
     * TODO Check if raw use of parameterized class will throw error and how best to do this.
     * @param list LinkedList to have item removed from front and inserted onto back
     */
    private void stepUtil(LinkedList list)
    {
        list.add(list.get(0)); // add first object to end
        list.remove(0); // remove from beginning
    }

    /**
     * Method to get the output of the letter (from keyboard to reflector).
     * @param letter Int (adjusted) of the letter from direction of keyboard.
     * @return Int (adjusted) of letter toward reflector.
     */
    public int get(int letter)
    {
        return wiresConfig.get(letter);
    }

    /**
     * Method to get the input of the letter (from reflector to keyboard).
     * @param letter Int (adjusted) of the letter from direction of the reflector.
     * @return Int (adjusted) of the letter toward the keyboard.
     */
    public int indexOf(int letter)
    {
        return wiresConfig.indexOf(letter);
    }

    /**
     * Method to return string of visibleLetters array and wiringConfig array
     * @return String of visibleLetters array and wiringConfig array
     */
    public String toString()
    {
        // Convert LinkedList<Integer> wiresConfig to string array
        String[] wiredArray = new String[wiresConfig.size()];
        for (int i = 0; i < wiresConfig.size(); i++)
        {
            wiredArray[i] = Character.toString((char) (wiresConfig.get(i) + 65)); // num to ASCII, to char, to String
        }

        // Construct and return arrays string
        return Arrays.toString(visibleLetters.toArray()) +"\n" + Arrays.toString(wiredArray);
    }
}
// TODO does the turnover work correctly?