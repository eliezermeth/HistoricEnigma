package machine_pieces;

import java.util.LinkedList;

/**
 * @author Eliezer Meth
 * Start Date: 2020-09-17
 * Last Modified: 2020-09-24
 *
 * This class is instantiated to create an unmodified rotor or reflector in its most basic sense.
 */

public class GearConstruction
{
    // interfaces.Wiring (for either rotor or reflector)
    private final int[] wirings = new int[26]; // will be 26 letters

    // For rotor
    private char[] turnoverPositions; // one or more

    // For reflector
    private boolean reflectorRotatable;
    private boolean reflectorRewirable;

    /**
     * Constructor for rotor.
     * @param wirings String in order of scrambled letter output.
     * @param turnover String of letter(s) on top when it causes the next rotor to step.
     */
    public GearConstruction(String wirings, String turnover)
    {
        populateWiringsArray(wirings);

        turnoverPositions = new char[turnover.length()];
        for (int i = 0; i < turnover.length(); i++)
            turnoverPositions[i] = turnover.charAt(i);

    }

    /**
     * Constructor for reflector.
     * @param wirings String in order of scrambled letter output.
     * @param reflectorRotatable If reflector can rotate position and setting.
     * @param reflectorRewirable If reflector can be rewired.
     */
    public GearConstruction(String wirings, boolean reflectorRotatable, boolean reflectorRewirable)
    {
        populateWiringsArray(wirings);
        this.reflectorRotatable = reflectorRotatable;
        this.reflectorRewirable = reflectorRewirable;
    }

    /**
     * Method to take string of wiring instructions and convert them into the wirings array.
     * @param wirings String of wiring instructions.
     */
    private void populateWiringsArray(String wirings)
    {
        for (int i = 0; i < 26; i++)
            this.wirings[i] = wirings.charAt(i) - 'A'; // to make A = 0
    }

    // Getter method for rotors for the wiring

    /**
     * Method to get rotor wiring.
     * @return LinkedList<Integer> containing number representation of the letters.
     */
    public LinkedList<Integer> getRotorWiring()
    {
        LinkedList<Integer> linkedList = new LinkedList<>();

        for (int num : wirings)
            linkedList.add(num);

        return linkedList;
    }

    /**
     * Method to get rotor turnover positions (what letter is on top at that point).
     * @return char[] of letter turnover positions.
     */
    public char[] getTurnoverPositions()
    {
        return turnoverPositions;
    }

    /**
     * Method to get reflector wiring.
     * @return int[] containing number representation of the letters.
     */
    public int[] getReflectorWiring()
    {
        return wirings;
    }

    /**
     * Method to get if the reflector is rotatable.
     * @return Boolean if reflector is rotatable.
     */
    public boolean getReflectorRotatable()
    {
        return reflectorRotatable;
    }

    /**
     * Method to get if the reflector is rewirable.
     * @return Boolean if reflector is rewirable.
     */
    public boolean getReflectorRewirable()
    {
        return reflectorRewirable;
    }
}
