package machine_pieces;

import interfaces.BetterWiring;
import utilities.Utilities;

import java.util.Map;

/**
 * This class is to simulate a complete rotor that could be inserted into the machine.
 *
 * Historic rotor configurations can be found at:
 * https://en.wikipedia.org/wiki/Enigma_rotor_details
 * https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10
 *
 * To see an explanation of rotor settings:
 * https://crypto.stackexchange.com/questions/29315/how-does-the-ring-settings-of-enigma-change-wiring-tables
 *
 * @author ELiezer Meth
 * @version 2
 * Start Date: 2022-02-22
 * Last Modified: 2022-02-27
 */
public class BetterRotor implements BetterWiring
{
    // Initial rotor information
    private final BetterGearConstruction gear; // factory setting of rotor
    private final int rotorSelected; // rotor selected
    private int ringSetting = 0; // ringstellung (ring setting) of rotor; defaults to A
    private int initialPosition = 0; // position of letter in window at start; defaults to A

    // Functioning rotor information
    private int window = 0; // letter in window
    private int[] wiring; // wire connections and positions inside the rotor
    private final int[] turnover; // letter(s) on top when the notch is engaged to turn over the next rotor

    /**
     * Constructor to allow the rotor to be selected at its default settings.
     * @param rotorSelected 0-based number of which rotor selected for this instance.
     * @param selection Map for rotors and reflectors for a version of Enigma.
     */
    public BetterRotor(int rotorSelected, Map<String, BetterGearConstruction[]> selection)
    {
        this.rotorSelected = rotorSelected;
        gear = selection.get("rotor")[this.rotorSelected]; // select rotor information for this rotor
        wiring = gear.getWiring(); // get rotor wiring
        turnover = gear.getTurnoverPositions(); // get turnover positions
    }

    /**
     * Constructor to allow the rotor to be constructed and fully primed with custom settings upon creation.
     * @param rotorSelected 0-based number of which rotor selected for this instance.
     * @param ringSetting 'A'->0 int representing ring setting for this rotor.
     * @param initialPosition 'A'->0 int representing letter on top of rotor at start.
     * @param selection Map for rotors and reflectors for a version of Enigma.
     */
    public BetterRotor(int rotorSelected, int ringSetting, int initialPosition, Map<String, BetterGearConstruction[]> selection)
    {
        this(rotorSelected, selection); // get rotor by calling other constructor
        setRingSetting(ringSetting); // set ring setting
        setInitialPosition(initialPosition); // set initial position
    }

    /**
     * Get 0-based number of selected rotor.
     * @return 0-based designation of rotor.
     */
    public int getRotorSelected()
    {
        return rotorSelected;
    }

    /**
     * Set ring setting of rotor.
     * @param ringSetting 0-based number to change wiring in rotor.
     */
    public void setRingSetting(int ringSetting)
    {
        this.ringSetting = restrictNumberToLength(ringSetting); // update ring setting

        // reset rotor to default wiring
        wiring = gear.getWiring();

        // rotate connections in rotor
        if (ringSetting != 0) // only do if not A
            for (int i = 0; i < wiring.length; i++)
                wiring[i] += this.ringSetting;
    }

    /**
     * Get 0-based ring setting for rotor.
     * @return 0-based ring setting.
     */
    public int getRingSetting()
    {
        return ringSetting;
    }

    /**
     * Set initial letter in window of rotor.
     * @param initialPosition 0-based letter in window at beginning of run.
     */
    public void setInitialPosition(int initialPosition)
    {
        this.initialPosition = restrictNumberToLength(initialPosition);
        window = this.initialPosition; // set calculating letter to correct letter
    }

    /**
     * Get letter in window at beginning of run.
     * @return 0-based letter.
     */
    public int getInitialPosition()
    {
        return initialPosition;
    }

    /**
     * Get letter currently in window.
     * @return 0-based letter.
     */
    public int getWindow()
    {
        return window;
    }

    /**
     * Shift a possible number into the range possible for the component.
     * @param number Number to restrict.
     * @return Valid number.
     */
    private int restrictNumberToLength(int number)
    {
        // if positive, drop to valid length
        // if negative, bring negative to within parameters and add to length
        return (number > -1) ? (number % wiring.length) : ((wiring.length + (number % wiring.length)) % wiring.length);
    }

    // Running methods -------------------------------------------------------------------------------------------------

    @Override
    public int input(int letter)
    {
        return internalSignal(wiring[externalSignal(letter)]);
    }

    @Override
    public int output(int letter)
    {
        return internalSignal(Utilities.indexOf(wiring, externalSignal(letter)));
    }

    /**
     * Returns if rotor in turnover position (to try to cause next rotor to step).  The notch (8 past the window) on the
     * rotor would be in contact with the pawl, causing turnover of  rotors.  In a 3-rotor Enigma, there is no pawl for
     * the third rotor and the notch never engages.
     * @return If turnover letter is in the window.
     */
    public boolean isNotchAtPawl()
    {
        for (int turn : turnover) // for each character in window that causes turnover
            if (turn == window) // if match
                return true; // only one will match at time
        // else
        return false;
    }

    /**
     * Increment the rotor by 1.
     */
    public void step()
    {
        window = ++window % wiring.length; // increment then modulus
    }

    /**
     * Used when an external signal interacts with the side of the rotor.
     * @param i Position of signal application.
     * @return Internal receipt of signal.
     */
    private int externalSignal(int i)
    {
        return (window + i) % 26; // allows wraparound
    }

    /**
     * Used when an internal signal interacts with the side of the rotor.
     * @param i Internal application of signal.
     * @return Position of signal receipt/transfer.
     */
    private int internalSignal(int i)
    {
        return (i >= window) ? (i - window) : (window - i); // uses difference to get external position
    }
}

// TODO does the turnover work correctly?
// TODO determine naming for rotors

// TODO visible wheel
// TODO notch, turnover