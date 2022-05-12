package machine_pieces;

import interfaces.BetterWiring;
import interfaces.Wiring;
import utilities.Utilities;

/**
 * Takes the function of the eintrittswalze (ETW), or entry stator, connecting the plugboard to the rotor assembly.  If
 * there is no plugboard, the ETW instead connects the keyboard and lampboard to the rotor assembly.  It can be wired
 * any way, but  most fall into two camps: the commercial Enigma connects them in the QWERTY format (Q->A, W-B, etc.)
 * while the military Enigma connects them in straight alphabetical order (A->A, B->B, etc.).  The entry wheel did not
 * rotate or step.
 *
 * @author Eliezer Meth
 * @version 1
 * Start Date: 2022-02-21
 */
public class EntryWheel implements BetterWiring
{
    public enum ETWsequence { QWERTY, ABCDE, CUSTOM }
    private ETWsequence setting;

    private char[] letters = new char[26]; // human-readable letters
    private int[] etw = new int[26]; // 0-based numbers; for use by machine

    /**
     * Constructor.
     *
     * @param setting ETWsequence
     */
    public EntryWheel(ETWsequence setting)
    {
        setSequence(setting);
    }

    /**
     * Set sequence and letter wheel array.
     * @param setting ETWsequence
     * @return successful
     */
    public boolean setSequence(ETWsequence setting)
    {
        this.setting = setting;

        // set letter wheel
        switch (setting)
        {
            case QWERTY:
                letters = Utilities.getQwertyArray();
                break;
            case ABCDE:
                letters = Utilities.getAzArray();
                break;
            case CUSTOM:
                // TODO implement
                throw new NullPointerException();
                //break;
        }

        // convert letter wheel into number for ETW
        etw = Utilities.intAdjustArray(letters);

        return (etw[0] + etw[1] != 0); // if ETW assigned, two letters cannot equal 0 and assignment was successful
    }

    /**
     * Signal transfer from keyboard/plugboard to rotor assembly.
     * @param num 0-based A letter.
     * @return 0-based A letter from ETW.
     */
    public int input(int num)
    {
        // if sequence is A -> A, there is no need to compute output
        if (setting == ETWsequence.ABCDE)
            return num;

        // else, perform indexOf() on etw[]
        for (int i = 0; i < etw.length; i++)
            if (etw[i] == num) // letter match found
                return i;

        // should never happen
        return -1;
        // TODO error checking?
    }

    /**
     * Signal transfer from rotor assembly to keyboard/plugboard.
     * @param num 0-based A letter.
     * @return 0-based A letter from ETW.
     */
    public int output(int num)
    {
        // if sequence is A -> A, there is no need to compute output
        if (setting == ETWsequence.ABCDE)
            return num;

        // else
        return etw[num];

        // TODO error checking?
        // For optimization: compare lookup time in array to comparing equality of setting; possibly remove if statement.
    }
}

// TODO allow custom input
// TODO make sure all letters are capitalized
// TODO remove dependency on 26-letter alphabet