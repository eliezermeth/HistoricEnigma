package machine_pieces;

import utilities.Utilities;

/**
 * This class is instantiated to create an unmodified rotor or reflector in its most basic sense.
 *
 * @author Eliezer Meth
 * @version 2
 * Start Date: 2022-02-22
 * Last Modified: 2022-02-23
 */
public class BetterGearConstruction
{
    // interfaces.Wiring (for either rotor or reflector)
    private int[] wiring;

    // for rotor
    private int[] turnoverPositions; // letter(s) visible in window at step when ringstellung (ring setting) is A
    // notches (to physically cause turnover) are 8 positions further on from visible in window

    // for reflector
    private boolean reflectorRotatable;
    private boolean reflectorRewirable;
    private boolean reflectorStepping;

    /**
     * Constructor for rotor.
     * @param wirings String in order of scrambled letter output.
     * @param turnover String of letter(s) in window when notch causes next rotor to step.
     */
    public BetterGearConstruction(String wirings, String turnover)
    {
        populateWiring(wirings); // set wiring
        turnoverPositions = Utilities.intAdjustArray(turnover.toCharArray()); // set turnover
    }

    /**
     * Constructor for reflector.
     * @param wirings String in order of scrambled letter output.
     * @param reflectorRotatable If reflector can rotate position and setting.
     * @param reflectorRewirable If reflector can be rewired.
     * @param reflectorStepping If reflector steps.
     */
    public BetterGearConstruction(String wirings, boolean reflectorRotatable, boolean reflectorRewirable, boolean reflectorStepping)
    {
        populateWiring(wirings);
        this.reflectorRotatable = reflectorRotatable;
        this.reflectorRewirable = reflectorRewirable;
        this.reflectorStepping = reflectorStepping;
    }

    /**
     * Takes String of wiring instructions and converts it into wiring array.
     * @param wiring String of wiring instructions.
     */
    private void populateWiring(String wiring)
    {
        this.wiring = Utilities.intAdjustArray(wiring.toCharArray());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Getter methods for rotor and reflector

    /**
     * Get wiring of machine component.
     * @return 'A'->0 int[].
     */
    public int[] getWiring()
    {
        return wiring.clone(); // returns copy to prevent multiple components using same array
    }

    /**
     * Get turnover positions on rotor.
     * @return 'A'->0 int[].
     */
    public int[] getTurnoverPositions()
    {
        return turnoverPositions.clone();// returns copy to prevent multiple components using same array
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
