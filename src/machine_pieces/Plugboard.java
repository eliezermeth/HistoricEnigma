package machine_pieces;

import exceptions.BadKeyException;
import interfaces.Wiring;
import utilities.AlphabetConverter;
import utilities.Utilities;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * The plugboard mimics the performance of an Enigma plugboard (steckerbrett), in that it can connect one letter to a
 * different letter on its travel to the rotor, and a letter to the lamps.  It can be primed with the set of characters
 * to be used.  If no set is provided, it defaults to the 26-letter English alphabet.  The plugboard can enough letter
 * pairings to pair up to the number of letters in the set.  For a 26-letter alphabet, it can accept up to 13 letter
 * pairs, but provides the most complexity at 10 pairs.
 * <br>
 * In the event that a letter is entered for switching twice, an error will be thrown and the new pair will not be
 * inserted.
 * <br>
 * @author Eliezer Meth
 * @version 2.1<br>
 * Start Date: 2024-05-09<br>
 * Last Modified: 2024-05-21
 */
public class Plugboard implements Wiring
{
    private final char EMPTY_CHAR = '\0';

    private char[] alphabet;
    private int[] wiring; // to hold wiring connections for plugboard
    private LinkedList<String> connections = new LinkedList<>(); // list of connections in the plugboard

    private AlphabetConverter ac;

    /**
     * Default constructor; initialized plugboard with 26-letter English alphabet and no connections.
     */
    public Plugboard()
    {
        alphabet = Utilities.getAzArray();

        // get alphabet converter
        if (!AlphabetConverter.exists())
            AlphabetConverter.createAlphabetConverter(alphabet);
        ac = AlphabetConverter.getAlphabetConverter();

        resetPlugboard();
    }

    /**
     * Constructor to create plugboard based on alphabet listed in the string.
     * @param alphabet String of all characters in the alphabet.
     * @throws BadKeyException if duplicate characters are present in the alphabet.
     */
    public Plugboard(String alphabet) throws Exception
    {
        this(alphabet.toCharArray());
    }

    /**
     * Constructor to create plugboard based on alphabet listed in the string.
     * @param alphabet character array of all characters in the alphabet.
     * @throws BadKeyException if duplicate characters are present in the alphabet.
     */
    public Plugboard (char[] alphabet) throws Exception
    {
        // test if duplicate letters
        Set<Character> temp = new HashSet<>();
        for (char c : alphabet)
            if (!temp.add(c)) // letter already present
                throw new BadKeyException("Duplicate characters are present in the alphabet.", new Exception());

        this.alphabet = alphabet; // set plugboard alphabet

        // get alphabet converter
        if (!AlphabetConverter.exists())
            AlphabetConverter.createAlphabetConverter(alphabet);
        ac = AlphabetConverter.getAlphabetConverter();

        resetPlugboard();
    }

    /**
     * Remove all connections in the plugboard and link letters to themselves.
     */
    public void resetPlugboard()
    {
        wiring = new int[alphabet.length];

        for (int i = 0; i < wiring.length; i++) // set each letter to point to itself
            wiring[i] = i;

        // clear connections list
        connections.clear();
    }

    /**
     * Insert a wire to swap the two letters in the letter pair.
     *
     * @param letterPair The two characters to be swapped; no modifications will be made to the characters
     * @return if a connection was made.  True if successful, false if a character was already swapped or invalid.
     */
    public boolean insertWire(String letterPair)
    {
        return insertWire(letterPair.toCharArray());
    }

    /**
     * Insert a wire between the two letters to form a letter pair.
     *
     * @param letter1 One of the characters to be swapped; no modifications will be made to the character
     * @param letter2 The other character to be swapped; no modifications will be made to the character
     * @return if a connection was made.  True if successful, false if character was already swapped or invalid.
     */
    public boolean insertWire(char letter1, char letter2)
    {
        return insertWire(new char[]{letter1, letter2});
    }

    /**
     * Insert a wire to swap the two letters in the letter pair.
     *
     * @param letterPair The two characters to be swapped; no modifications will be made to the characters
     * @return if a connection was made.  True if successful, false if a character was already swapped or invalid.
     */
    public boolean insertWire(char[] letterPair)
    {
        if (letterPair.length != 2) // invalid number to pair
            return false;
        if (letterPair[0] == letterPair[1]) // verify letters are not the same
            return false;

        // attempt to find both characters in alphabet
        int let1pos = ac.convert(letterPair[0]);
        int let2pos = ac.convert(letterPair[1]);
        if (let1pos == -1 || let2pos == -1) // verify both characters in alphabet
            return false;
        if (wiring[let1pos] != let1pos || wiring[let2pos] != let2pos) // either letter already swapped
            return false;

        // insert wire
        wiring[let1pos] = let2pos;
        wiring[let2pos] = let1pos;

        connections.add(new String(letterPair));

        return true;
    }

    /**
     * Remove a wire connection based on one letter of the connection
     * @param letter character of the pair to be removed
     * @return True if connection removed; false if there was no connection.
     */
    public boolean removeWire(char letter)
    {
        // test that letter was swapped
        for (String pair : connections)
            if (pair.contains(Character.toString(letter)))
                return removeWire(pair);

        return false; // if letter was never swapped
    }

    /**
     * Remove the wire connection between the two letters.
     * @param letter1 One of the letters in a connection.
     * @param letter2 The other letter in the connection.
     * @return True if connection removed; false if there was no connection.
     */
    public boolean removeWire(char letter1, char letter2)
    {
        return removeWire(Character.toString(letter1) + letter2);
    }

    /**
     * Remove the wire connection between the two characters in the array.
     * @param letterPair char[2] of the letters in the connection.
     * @return True if connection removed; false if there was no connection.
     */
    public boolean removeWire(char[] letterPair)
    {
        return removeWire(new String(letterPair));
    }

    /**
     * Remove a wire connection.
     * @param conn String of wire connection end; can be out of order.
     * @return True if connection removed; false if there was no connection.
     */
    public boolean removeWire(String conn)
    {
        if (conn.length() != 2) // not valid for a pair
            return false;

        String temp = Character.toString(conn.charAt(1)) + conn.charAt(0); // cheaper than StringBuffer's reverse
        String properOrder = null;
        if (connections.contains(conn))
            properOrder = conn;
        else if (connections.contains(temp))
            properOrder = temp;

        if (properOrder == null) // connection not found in connections list
            return false;

        // connection exists; remove from arrays and LinkedList
        char[] letters = properOrder.toCharArray();
        int let1pos = ac.convert(letters[0]);
        int let2pos = wiring[let1pos];
        // reset wires
        wiring[let1pos] = let1pos;
        wiring[let2pos] = let2pos;
        connections.remove(properOrder);

        return true;
    }

    /**
     * Return a LinkedList of all connections in the plugboard.
     * @return LinkedList of String of all connections in the plugboard.
     */
    public LinkedList<String> getConnections()
    {
        // create and return deap copy of connections
        return new LinkedList<>(connections);
    }

    /**
     * Test if a letter has a connection to another letter.
     * @param letter letter to test if a connection exists to any other letter
     * @return if connection exists
     */
    public boolean hasConnection(char letter)
    {
        int temp = ac.convert(letter);
        return (temp != -1 && wiring[temp] != temp); // if letter in alphabet and doesn't point to itself
    }

    /**
     * Method to find connected letter of provided letter.  Best if called after hasConnection(); should only be called
     * with a letter in a connection.
     * @param letter char to find swapped letter of.
     * @return char of swapped letter or empty char.
     */
    public char findConnectedLetter(char letter)
    {
        int index = ac.convert(letter);
        if (index == -1 || wiring[index] == index) // no connection or letter not in alphabet
            return EMPTY_CHAR;
        else // connection exists
            return ac.convert(wiring[index]);
    }

    /**
     * Method to find the connection pair that contains a letter.  Best if called after hasConnection().
     * @param letter character within the pair to find
     * @return String of swapping group that contains the letter; null if no connection is found.
     */
    public String findConnection(char letter)
    {
        for (String c : connections)
            if (c.contains(Character.toString(letter)))
                return c;

        return null; // default; connection does not exist for letter
    }

    /**
     * Get the number of wire connections in the plugboard (letters that output a different letter).
     * @return number of wire connections
     */
    public int numberOfConnections()
    {
        return connections.size();
    }

    /**
     * Get the alphabet used by the plugboard.
     * @return char array of alphabet.
     */
    public char[] getAlphabet()
    {
        return alphabet.clone(); // shallow copy of one-dimensional does not provide reference to original
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Running methods
    // -----------------------------------------------------------------------------------------------------------------


    /**
     * Simulates electrical flow into the component in the direction of keyboard toward reflector.
     * <br>
     * Data is transmitted in the form of a contact signal, an electrical impulse at a contact.  Contacts start from
     * contact 00, which corresponds to the first letter of the alphabet used by the plugboard.
     *
     * @param contactSignal position of the electrical signal when entering plugboard; index of letter in alphabet
     * @return position of the electrical signal exiting the plugboard; -1 if not valid
     */
    @Override
    public int input(int contactSignal)
    {
        try {
            return wiring[contactSignal];
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    /**
     * Simulates electrical flow into the component in the direction of reflector toward keyboard.
     * <br>
     * Data is transmitted in the form of a contact signal, an electrical impulse at a contact.  Contacts start from
     * contact 00, which corresponds to the first letter of the alphabet used by the plugboard.
     *
     * @param contactSignal position of the electrical signal when entering plugboard; index of letter in alphabet
     * @return position of the electrical signal exiting the plugboard; -1 if not valid
     */
    @Override
    public int output(int contactSignal)
    {
        return input(contactSignal); // since input/output contacts mirror each other
    }
}
