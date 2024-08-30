package machine_pieces;

import exceptions.BadKeyException;
import interfaces.Wiring;
import resources.AlphabetConverter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * EntryWheel takes the function of the eintrittswalze (ETW), or entry stator, connecting the plugboard to the rotor
 * assembly.  If there is no plugboard, the entry wheel instead connects the keyboard and lampboard to the rotor
 * assembly.  It can be wired any way, but most machines fall under two camps:  The commercial Enigma connects them in
 * the QWERTY format (Q->A, W->B, etc.), while the military Enigma connects them in alphabetical order (A->A, B->B,
 * etc.).  The entry wheel did not rotate or step.  The wiring does not necessarily mirror; while a QWERTY input of Q
 * produces output A, input Q does not produce output A.<br>
 *
 * @author Eliezer Meth
 * @version 2<br>
 * Start Date: 2024-06-04
 * Last Modified: 2024-06-06
 */
public class EntryWheel implements Wiring
{
    /**
     * Options for wiring connections in the entry wheel:<br>
     * <b>QWERTY</b>: The keyboard key Q connects to the rotor assembly contact A, W -> B, etc.  <br>
     * <b>ABCDE</b>: A -> A, B -> B, etc.<br>
     * <b>CUSTOM</b>: Wiring keyboard and rotor contacts must be passed to EntryWheel.set().
     */
    public enum ETWsequence { QWERTY, ABCDE, CUSTOM } // can be used to set sequence of entry wheel
    private ETWsequence setting = ETWsequence.CUSTOM; // default to CUSTOM; used as flag and may be changed later

    private AlphabetConverter ac;

    // There are two options on how to hold the data: two arrays or a HashMap.  Although a HashMap requires more
    // overhead space due to the items being saved as objects, it has time complexities for input / output functions of
    // O(1) / O(n), faster than the array's input / output of O(n) / O(n).
    private HashMap<Integer, Integer> wiring = new HashMap<>();
    private String front; // wiring for front
    private String back; // wiring for back

    // utility strings
    private final String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String qwe = "QWERTYUIOPASDFGHJKLZXCVBNM";

    /**
     * Constructor for Entry Wheel.  Requires AlphabetConverter and ETWsequence.  ETWsequence defines how the wirings
     * are connected from the keyboard to the rotor assembly.
     * @param ac AlphabetConverter.
     * @param seq ETWsequence of QWERTY, ABCDEF, or CUSTOM.  CUSTOM requires the wiring to be set later.
     * @throws IllegalStateException if AlphabetConverter has not been instantiated.
     * @throws BadKeyException if wiring sequences do not match AlphabetConverter alphabet; only occurs if
     * AlphabetConverter contains nonstandard 26-letter English alphabet (uppercase) and non-CUSTOM selected
     */
    public EntryWheel(AlphabetConverter ac, ETWsequence seq) throws IllegalStateException, BadKeyException
    {
        if (!AlphabetConverter.exists())
            throw new IllegalStateException("AlphabetConverter must first be instantiated.");
        this.ac = ac;

        if (seq == ETWsequence.ABCDE)
            set(abc, abc);
        else if (seq == ETWsequence.QWERTY)
            set(qwe, abc);

        // save setting for later
        setting = seq;
    }

    /**
     * Method to set wiring sequence in entry wheel from keyboard to rotor assembly.  Public calls should only be done
     * when entry wheel is set to <code>CUSTOM</code>.<br>
     * The first element of <code>key</code> directionally points to the first element of <code>signal</code>, etc.
     * @param key String of wiring order from keyboard.
     * @param signal String in positional reference of wiring connections from keyboard connections.
     * @return if <code>EntryWheel</code> could be set properly; fails if already set
     * @throws BadKeyException alphabets do not match length with <code>AlphabetConverter</code> or contain internal
     * duplicate or invalid letters.
     */
    public boolean set(String key, String signal) throws BadKeyException
    {
        // check that no wirings have yet been set
        if (setting != ETWsequence.CUSTOM || !wiring.isEmpty())
            return false;

        // verify both strings are the proper length
        if (key.length() != ac.getAlphabet().length || signal.length() != ac.getAlphabet().length)
            throw new BadKeyException("Alphabet lengths must match with AlphabetConverter.");

        // transform potentials into char[], then Set.  Check against containsAll()
        // transform AlphabetConverter's alphabet into set
        char[] tempAlphabet = ac.getAlphabet();
        Set<Character> setAlphabet = IntStream.range(0, tempAlphabet.length)
                .mapToObj(i -> tempAlphabet[i])
                .collect(Collectors.toCollection(HashSet::new));
        // transform key into set
        char[] tempKey = key.toCharArray();
        Set<Character> setKey = IntStream.range(0, tempKey.length)
                .mapToObj(i -> tempKey[i])
                .collect(Collectors.toCollection(HashSet::new));
        // transform signal into set
        char[] tempSignal = signal.toCharArray();
        Set<Character> setSignal = IntStream.range(0, tempSignal.length)
                .mapToObj(i -> tempSignal[i])
                .collect(Collectors.toCollection(HashSet::new));

        // verify neither string contains duplicates nor invalid letters
        if (!setAlphabet.containsAll(setKey) || !setAlphabet.containsAll(setSignal))
            throw new BadKeyException("Alphabets may not contain duplicate or invalid letters.");

        // valid; add wirings to hashmap
        for (int i = 0; i < tempAlphabet.length; i++)
        {
            wiring.put(ac.convert(tempKey[i]), ac.convert(tempSignal[i]));
        }

        // save alphabets
        front = key;
        back = signal;

        return true;
    }

    /**
     * Get the setting of the entry wheel.
     * @return <code>ETWsequence</code>
     */
    public ETWsequence getSetting()
    {
        return setting;
    }

    /**
     * Get wiring order for keyboard and rotor assembly contacts.
     * @return <code>String[keyboard side, rotor assembly side]</code>
     */
    public String[] getWirings()
    {
        return new String[] {front, back};
    }

    /**
     * Simulates electrical flow into the component in the direction of keyboard toward reflector.
     * <br>
     * Data is transmitted in the form of a contact signal, an electrical impulse at a contact.  Contacts are based on
     * the alphabet position of a given letter within the alphabet, not the letter's position on the keyboard, which
     * is set during construction of the entry wheel.
     *
     * @param contactSignal position of the electrical signal when entering the entry wheel from the keyboard
     * @return position of the electrical signal exiting the entry wheel or -1 if contactSignal not found
     */
    @Override
    public int input(int contactSignal)
    {
        return wiring.getOrDefault(contactSignal, -1);
    }

    /**
     * Simulates electrical flow into the component in the direction of reflector toward keyboard.
     * <br>
     * Data is transmitted in the form of a contact signal, an electrical impulse at a contact.  Contacts are based on
     * the alphabet position of a given letter within the alphabet, not the letter's position on the keyboard, which
     * is set during construction of the entry wheel.
     *
     * @param contactSignal position of the electrical signal when entering the entry wheel from the rotor assembly
     * @return position of the electrical signal exiting the entry wheel or -1 if contactSignal not found
     */
    @Override
    public int output(int contactSignal)
    {
        for (Map.Entry<Integer, Integer> entry : wiring.entrySet())
            if (entry.getValue().equals(contactSignal))
                return entry.getKey();

        return -1; // if contactSignal not in map
    }
}