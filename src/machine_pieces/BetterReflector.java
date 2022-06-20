package machine_pieces;

import interfaces.BetterWiring;
import utilities.Utilities;

import java.util.Map;

/**
 * This class is to simulate a reflector that could be inserted into the machine.
 * <br><br>
 * Historic reflector configurations can be found at:
 * https://en.wikipedia.org/wiki/Enigma_rotor_details
 * https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10
 *
 * @author Eliezer Meth
 * @version 2 <br>
 * Start Date: 2022-06-20
 */
public class BetterReflector implements BetterWiring
{
    // External reflector information
    private final int reflectorSelected; // reflector selected

    // Internal reflector configurations
    private final BetterGearConstruction gear; // factory setting for reflector
    private final char[] visibleLetters = Utilities.getAzArray(); // get  array of letters where they would be on disk
    private final int[] reflector; // internal reflector wiring

    private boolean rotatable;
    private boolean stepping;
    private boolean rewirable;

    /**
     * Constructor to select correct rotor.
     * @param reflectorSelected String (A+) representing the selected reflector.
     * @param selection Map{@literal <}String, machine.BetterGearConstruction[]> for rotors and reflectors for a version of the Enigma.
     */
    public BetterReflector(String reflectorSelected, Map<String, BetterGearConstruction[]> selection)
    {
        this.reflectorSelected = Integer.parseInt(reflectorSelected) - 1; // subtract 1 to get computer terms

        gear = selection.get("reflector")[this.reflectorSelected];
        reflector = gear.getWiring();
        rotatable = gear.isReflectorRotatable();
        stepping = gear.isReflectorStepping();
        rewirable = gear.isReflectorRewirable();
    }

    /**
     * Get the name of the reflector selected.
     * @return String of selected reflector.
     */
    public String getReflectorSelected()
    {
        return Integer.toString(reflectorSelected + 1); // return to human terms
    } // TODO standardize?

    /**
     * Simulates electrical flow into the component in the direction of keyboard toward reflector.
     * @param letter 'A'->0 input to component.
     * @return 'A'->0 output from component.
     */
    @Override
    public int input(int letter)
    {
        return reflector[letter];
    }

    /**
     * Simulates electrical flow into the component in the direction of reflector toward keyboard.<br>
     * NOTE: The reflector only allows flow in one direction.  This method calls input().
     * @param letter 'A'->0 input to component.
     * @return 'A'->0 output from component.
     */
    @Override
    public int output(int letter)
    {
        return input(letter);
    }

    public String toString()
    {
        return new String(Utilities.charAdjustArray(reflector)); // change to char[] then convert to string
    } // TODO only uses 26 letter alphabet
}
// TODO use rotatable, stepping, and rewirable