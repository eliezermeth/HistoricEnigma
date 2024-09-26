package machine_pieces;

import resources.AlphabetConverter;
import resources.Utilities;

import java.util.ArrayList;

/**
 * This class is instantiated to create an unmodified rotor or reflector in its most basic sense.
 *
 * @author Eliezer Meth
 * @version 3.1<br>
 * Start Date: 2022-02-22<br>
 * Last Modified: 2024-09-03
 */
public class GearConstruction
{
    // interfaces.Wiring (for either rotor or reflector)
    private ArrayList<Character> letters; // letters visible on the rotor/reflector
    private ArrayList<Character> wirings; // wiring connection from the visible letters of rotor/reflector
    /* Letters (front) and wirings (back) have a one-to-one relation.  The letter in front element 0 links directly to
    wiring back element 0.  However, to find where the signal is output from the gear, the letter from the back must
    have its position found in the front.
    */

    // for rotor
    private char[] turnoverPositions; // letter(s) visible in window at step when ringstellung (ring setting) is A
    // notches (to physically cause turnover) are 8 positions further on from visible in window

    // for reflector
    private boolean reflectorRotatable;
    private boolean reflectorStepping;
    private boolean reflectorRewirable;

    /**
     * Constructor for rotor.
     * @param wirings String in order of scrambled letter output.
     * @param turnover String of letter(s) in window when notch causes next rotor to step.
     */
    public GearConstruction(String wirings, String turnover)
    {
        populateWiring(wirings); // set wiring
        turnoverPositions = turnover.toCharArray();
    }

    /**
     * Constructor for reflector.
     * @param wirings String in order of scrambled letter output.
     * @param reflectorRotatable If reflector can rotate position and setting.
     * @param reflectorStepping If reflector steps.
     * @param reflectorRewirable If reflector can be rewired.
     */
    public GearConstruction(String wirings, boolean reflectorRotatable, boolean reflectorStepping, boolean reflectorRewirable)
    {
        populateWiring(wirings);
        this.reflectorRotatable = reflectorRotatable;
        this.reflectorStepping = reflectorStepping;
        this.reflectorRewirable = reflectorRewirable;
    }

    /**
     * Takes String of wiring instructions and converts it into wiring array.
     * @param wiring String of wiring instructions.
     */
    private void populateWiring(String wiring)
    {
        // populate letters (front)
        char[] temp = AlphabetConverter.getAlphabetConverter().getAlphabet(); // TODO ensure AlphabetConverter instantiated?
        letters = new ArrayList<>(temp.length);
        for (char c : temp)
            letters.add(c);

        // populate wirings (back)
        char[] characters = wiring.toCharArray();
        wirings = new ArrayList<>(characters.length);
        for (char c : characters)
            wirings.add(c);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Getter methods for rotor and reflector

    /**
     * Get wiring of machine component.
     * @return array of two ArrayLists [letters, wirings]
     */
    public ArrayList<Character>[] getWirings()
    {
        return new ArrayList[] {new ArrayList<>(letters), new ArrayList<>(wirings)};
    }

    /**
     * Get turnover positions on rotor.
     * @return char[]
     */
    public char[] getTurnoverPositions()
    {
        return turnoverPositions.clone(); // returns copy to prevent multiple components using same array
    }

    /**
     * Reflector rotatable possibility.
     * @return True/false.
     */
    public boolean isReflectorRotatable()
    {
        return reflectorRotatable;
    }

    /**
     * Reflector rewirable possibility.
     * @return True/false.
     */
    public boolean isReflectorRewirable()
    {
        return reflectorRewirable;
    }

    /**
     * Reflector stepping possibility.
     * @return True/false.
     */
    public boolean isReflectorStepping()
    {
        return reflectorStepping;
    }
}
