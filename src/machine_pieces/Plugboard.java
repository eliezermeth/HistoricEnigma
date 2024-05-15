package machine_pieces;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import interfaces.Wiring;
import utilities.InvalidKeyException;
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
 * @version 2<br>
 * Start Date: 2024-05-09<br>
 * Last Modified: 2024-05-15
 */
public class Plugboard implements Wiring
{
    private final char EMPTY_CHAR = '\0';
    private final String EMPTY_STRING = "";

    private char[] alphabet;
    private char[] in_side;
    private char[] out_side;
    private LinkedList<String> connections = new LinkedList<>(); // list of connections in the plugboard

    /**
     * Default constructor; initialized plugboard with 26-letter English alphabet and no connections.
     */
    public Plugboard()
    {
        alphabet = Utilities.getAzArray();
        resetPlugboard();
    }

    /**
     * Constructor to create plugboard based on alphabet listed in the string.
     * @param alphabet String of all characters in the alphabet.
     * @throws InvalidKeyException if duplicate characters are present in the alphabet.
     */
    public Plugboard(String alphabet) throws Exception
    {
        this(alphabet.toCharArray());
    }

    /**
     * Constructor to create plugboard based on alphabet listed in the string.
     * @param alphabet character array of all characters in the alphabet.
     * @throws InvalidKeyException if duplicate characters are present in the alphabet.
     */
    public Plugboard (char[] alphabet) throws Exception
    {
        // test if duplicate letters
        Set<Character> temp = new HashSet<>();
        for (char c : alphabet)
            if (!temp.add(c)) // letter already present
                throw new InvalidKeyException("Duplicate characters are present in the alphabet.", new Exception());

        this.alphabet = alphabet; // set plugboard alphabet
        resetPlugboard();
    }

    /**
     * Remove all connections in the plugboard and link letters to themselves.
     */
    public void resetPlugboard()
    {
        in_side = new char[alphabet.length];
        out_side = new char[alphabet.length];

        // link letters in in_side[] and out_side[] to themselves
        for (int i = 0; i < alphabet.length; i++)
        {
            in_side[i] = alphabet[i];
            out_side[i] = alphabet[i];
        }

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

        // attempt to find both characters in alphabet and save their positions
        int let1pos = -1, let2pos = -1;
        for (int i = 0; i < alphabet.length && (let1pos == -1 || let2pos == -1); i++)
        {
            if (alphabet[i] == letterPair[0])
                let1pos = i;
            else if (alphabet[i] == letterPair[1])
                let2pos = i;
        }
        if (let1pos == -1 || let2pos == -1) // verify both characters in alphabet
            return false;
        if (in_side[let1pos] != out_side[let1pos] || in_side[let2pos] != out_side[let2pos]) // letter already swapped
            return false;

        // insert wire
        out_side[let1pos] = in_side[let2pos];
        out_side[let2pos] = in_side[let1pos];

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
        int let1pos = -1, let2pos = -1;
        for (int i = 0; i < alphabet.length && (let1pos == -1 || let2pos == -1); i++)
        {
            if (alphabet[i] == letters[0])
                let1pos = i;
            else if (alphabet[i] == letters[1])
                let2pos = i;
        }
        // reset wires
        out_side[let1pos] = in_side[let1pos];
        out_side[let2pos] = in_side[let2pos];
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
        for (int i = 0; i < alphabet.length; i++)
            if (in_side[i] == letter && out_side[i] != letter)
                return true;

        return false; // default; falls to this if called letter not in the alphabet
    }

    /**
     * Method to find connected letter of provided letter.  Best if called after hasConnection(); should only be called
     * with a letter in a connection.
     * @param letter char to find swapped letter of.
     * @return char of swapped letter or empty char.
     */
    public char findConnectedLetter(char letter)
    {
        for (int i = 0; i < alphabet.length; i++)
            if (in_side[i] == letter)
                return in_side[i] != out_side[i] ? out_side[i] : EMPTY_CHAR; // if different letter, return; else empty

        return EMPTY_CHAR; // if letter not in alphabet
    }

    /**
     * Method to find the connection pair that contains a letter.  Best if called after hasConnection().
     * @param letter character within the pair to find
     * @return String of swapping group that contains the letter; empty string if no connection is found.
     */
    public String findConnection(char letter)
    {
        for (String c : connections)
            if (c.contains(Character.toString(letter)))
                return c;

        return EMPTY_STRING; // default; connection does not exist for letter
    }

    /**
     * Get the number of wire connections in the plugboard (letters that output a different letter).
     * @return number of wire connections
     */
    public int numberOfConnections()
    {
        return connections.size();
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
     * @return position of the electrical signal exiting the plugboard.
     */
    @Override
    public int input(int contactSignal)
    {
        return 0;
    }

    /**
     * Simulates electrical flow into the component in the direction of reflector toward keyboard.
     * <br>
     * Data is transmitted in the form of a contact signal, an electrical impulse at a contact.  Contacts start from
     * contact 00, which corresponds to the first letter of the alphabet used by the plugboard.
     *
     * @param contactSignal position of the electrical signal when entering plugboard; index of letter in alphabet
     * @return position of the electrical signal exiting the plugboard.
     */
    @Override
    public int output(int contactSignal)
    {
        return 0;
    }

    // TODO change plugboard to a single array where each index (letter) holds where it connects to
}
