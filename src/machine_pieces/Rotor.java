package machine_pieces;

import interfaces.Wiring;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class is to simulate a complete rotor that could be inserted into the machine.
 * <br><br>
 * Historic rotor configurations can be found at:<br>
 * <a href="https://en.wikipedia.org/wiki/Enigma_rotor_details">https://en.wikipedia.org/wiki/Enigma_rotor_details</a><br>
 * <a href="https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10">https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10</a><br>
 * <br>
 * To see an explanation of rotor settings:<br>
 * <a href="https://crypto.stackexchange.com/questions/29315/how-does-the-ring-settings-of-enigma-change-wiring-tables">https://crypto.stackexchange.com/questions/29315/how-does-the-ring-settings-of-enigma-change-wiring-tables</a><br>
 * <br>
 * @author Eliezer Meth
 * @version 3<br>
 * Start Date: 2024-04-05<br>
 * Last Modified: 2024-04-17
 */
public class Rotor implements Wiring
{
    // Initial rotor information
    private final GearConstruction gear; // factory setting of rotor
    private final String rotorSelected; // rotor selected
    private int ringSetting = 0; // ringstellung (ring setting) of rotor; defaults to A (0 is computer number)
    private char groundPosition = 'A'; // position of letter in window at start; defaults to A

    // Functioning rotor information
    private ArrayList<Character> letters; // letters printed on reflector
    private ArrayList<Character> wirings; // wiring connection from the visible letters of rotor
    /* Letters (front) and wirings (back) have a one-to-one relation.  The letter in front element 0 links directly to
    wiring back element 0.  However, to find where the signal is output from the gear, the letter from the back must
    have its position found in the front.
    */
    private final char[] turnovers; // letter(s) on top when the notch is engaged to turn over the next rotor

    /**
     * Constructor to allow the rotor to be selected at its default settings.
     * @param rotorSelected name of rotor selected for this instance.
     * @param selection Map for rotors and reflectors for a version of Enigma.
     */
    public Rotor (String rotorSelected, Map<String, Map<String, GearConstruction>> selection)
    {
        this.rotorSelected = rotorSelected;
        gear = selection.get("rotor").get(this.rotorSelected); // select rotor information for this rotor
        letters = gear.getWirings()[0]; // get letters wheel
        wirings = gear.getWirings()[1]; // get internal rotor wiring
        turnovers = gear.getTurnoverPositions(); // get turnover positions
    }

    /**
     * Constructor to allow the rotor to be constructed and fully primed with custom settings upon creation.
     * @param rotorSelected name of rotor selected for this instance.
     * @param ringSetting Number for ring setting of rotor; 01 -> A.
     * @param groundPosition Letter on top of rotor at start.
     * @param selection Map for rotors and reflectors for a version of Enigma.
     */
    public Rotor(String rotorSelected, int ringSetting, char groundPosition, Map<String, Map<String, GearConstruction>> selection)
    {
        this(rotorSelected, selection); // get rotor by calling other constructor
        setRingSetting(ringSetting); // set ring setting
        setGroundPosition(groundPosition); // set ground position
    }

    /**
     * Get the name of the selected rotor.
     * @return name designation of rotor.
     */
    public String getRotorSelected()
    {
        return rotorSelected;
    }

    /**
     * Set ring setting of rotor.
     * <br>
     * Restores a default (unmodified) version of the rotor and applies the proper ring setting.  The ground setting of
     * the rotor will need to be set after, as it defaults to A.
     * <br>
     * Note: Absolute values will be taken from negative numbers, and 00 -> Z.
     *
     * @param ringSetting int of letter to change wiring of rotor; 01 -> A
     */
    public void setRingSetting(int ringSetting)
    {
        this.ringSetting = restrictNumberToLength(Math.abs(ringSetting) - 1); // update ring setting; subtract 1 for computer number

        // restore to default
        letters = gear.getWirings()[0];
        wirings = gear.getWirings()[1];

        // Rotate connections in rotor (wirings)
        // 1. Advance each wiring connection by the specified ring setting
        // can use letters since it is in order
        for (int i = 0; i < letters.size(); i++)
        {
            int num = letters.indexOf(wirings.get(i)); // only used to find the wiring letter in the alphabet
            num = (num + this.ringSetting) % letters.size(); // advance letter, but wrap it to the alphabet
            wirings.set(i, letters.get(num));
        }
        // 2. Retreat internal wiring ring to new position relative to outside letters ring
        for (int i = 0; i < this.ringSetting; i++) // since computer number
            advanceRing(-1, wirings);
    }

    /**
     * Get ring setting for rotor; 01 -> A.
     *
     * @return alphabet ring setting of rotor.
     */
    public int getRingSetting()
    {
        return this.ringSetting + 1; // add 1 to transform from computer to human number
    }

    /**
     * Set ground position of rotor (the letter in window at start of operation).
     * @param groundPosition Character to be in window.
     * @return If rotor successfully rotated to position.
     */
    public boolean setGroundPosition(char groundPosition)
    {
        // test that ground position is valid
        ArrayList<Character> temp = gear.getWirings()[0]; // get used alphabet for reference
        if (groundPosition < temp.get(0) || groundPosition > temp.get(temp.size() - 1)) // within first and last
            return false;

        // set information and rotor
        this.groundPosition = groundPosition;
        while (getWindow() != this.groundPosition)
            step();
        return true;
    }

    /**
     * Ground position of rotor (letter in window at start of operation).
     * @return Character.
     */
    public char getGroundPosition()
    {
        return this.groundPosition;
    }

    /**
     * Get the letter currently visible in the window (the letter on top of the rotor; the beginning of the ArrayList).
     * @return Character.
     */
    public Character getWindow()
    {
        return letters.get(0);
    }

    /**
     * Get letters and wirings of the rotor.
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
        return turnovers.clone();
    }

    /**
     * Shift a possible number into the range possible for the component.
     * @param number Number to restrict.
     * @return Valid number.
     */
    private int restrictNumberToLength(int number)
    {
        // if positive, drop to valid length; if negative, bring negative to within parameters and add to length
        return (number > -1) ? (number % wirings.size()) : ((wirings.size() + (number % wirings.size())) % wirings.size());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Running methods
    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public int input(int contactSignal)
    {
        return letters.indexOf(wirings.get(contactSignal));
    }

    @Override
    public int output(int contactSignal)
    {
        return wirings.indexOf(letters.get(contactSignal));
    }

    /**
     * Steps rotor forward one revolution.
     *
     * @return If the next rotor should also step (if the stepping propagates).
     */
    public boolean step()
    {
        // test if next rotor should step
        boolean propogate = false;
        for (char c : turnovers) // test against all possible turnover positions
            if (getWindow() == c)
                propogate = true;

        advanceRing(letters, wirings);
        return propogate;
    }

    /**
     * Advances a ring a single position.
     * <br>
     * Pops a Character from the beginning of the ArrayList and adds it to the end.
     * @param arrays Vararg of ArrayList&lt;Character&gt; to advance
     */
    @SuppressWarnings("unchecked")
    private void advanceRing(ArrayList<Character>... arrays)
    {
        advanceRing(1, arrays);
    }

    /**
     * Shifts a ring a specified number of positions.
     * <br>
     * If a positive number is provided, the ring advances the specifed number of positions; that is, it pops a Character
     * from the beginning of the ArrayList and adds it to the end.  If a negative number is passed, the ring retreats.
     * @param numSteps The number of steps for the ring.
     * @param arrays Vararg of ArrayList&lt;Character&gt; to advance
     */
    @SuppressWarnings("unchecked")
    private void advanceRing(int numSteps, ArrayList<Character>... arrays)
    {
        if (numSteps > 0) // positive
            for (ArrayList<Character> al : arrays)
                for (int i = 0; i < numSteps; i++)
                {
                    Character temp = al.get(0); // save first letter
                    al.remove(0); // remove from ArrayList
                    al.add(temp); // add to end
                }
        else if (numSteps < 0) // negative
            for (ArrayList<Character> al : arrays)
                for (int i = 0; i > numSteps; i--)
                {
                    Character temp = al.get(al.size() - 1); // save last letter
                    al.remove(al.size() - 1); // remove from ArrayList
                    al.add(0, temp); // add to beginning
                }
        // if 0, do nothing
    }
}
