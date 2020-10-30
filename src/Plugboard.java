import java.util.LinkedList;

/**
 * @author Eliezer Meth
 * Start Date: 2020-10-01
 * Last Modified: 2020-10-18
 *
 * The plugboard mimics the performance of an Enigma plugboard, in that it can connect one letter to a different letter
 * on its travel to the rotor, and a letter to the lamps.  It can accept up to 13 letter pairs, but provides the most
 * complexity at 10 pairs.
 *
 * In the event that a letter is entered for switching twice, an error will be thrown and the new pair will not be
 * inserted.
 */

public class Plugboard implements Wiring
{
    private final char[] visibleLetters = Utilities.getAzArray(); // get array of letters where they would appear on surface
    private int[] plugboard = new int[26]; // switched letter in letter position
    private LinkedList<String> connections = new LinkedList<>(); // list of connections in the plugboard

    /**
     * Default constructor, only initializes plugboard array with no connections.
     */
    public Plugboard()
    {
        resetPlugboard();
    }

    /**
     * Constructor to initialize plugboard with connections.
     * @param wireList String in form of "AB CD" with connected letters in pairs.
     */
    public Plugboard(String wireList)
    {
        this();

        // convert string into possible connections
        String[] possibleConnections = wireList.split(" ");

        // test if too many connections
        if (possibleConnections.length >= 13)
            throw new IllegalArgumentException("Invalid plugboard connections"); //TODO

        // insert wires into correct letters & check if already swapped
        for (String conn : possibleConnections)
            insertWire(conn);
    }

    /**
     * Method to remove any connections on the plugboard and link letters to themselves.
     */
    public void resetPlugboard()
    {
        // link letters in plugboard[] to themselves
        for (int i = 0; i < plugboard.length; i++)
            plugboard[i] = i;
    }

    /**
     * Method to insert wire to swap the two letters in the letter pair.
     * @param letterPair Two letters to be swapped.
     * @return Boolean if connection was made.  True if yes, false if one of the letters was already swapped or invalid.
     */
    public boolean insertWire(String letterPair) throws IllegalArgumentException
    {
        // make both letters uppercase so calculations work
        letterPair = letterPair.toUpperCase();

        int let1 = letterPair.charAt(0) - 'A'; // get first letter and store it with 'A' as 0
        int let2 = letterPair.charAt(1) - 'A'; // get second letter and store it with 'A' as 0

        // test if entered letters are in the alphabet
        if (let1 < 0 || let1 > 25 || let2 < 0 || let2 > 25) // not in the alphabet
        {
            System.out.println("\"" + letterPair + "\" is an invalid entry for the plugboard.");
            return false;
        }

        // test if one of the letters was already swapped in the plugboard
        boolean alreadyConnected = false;
        if (hasConnection(let1))
        {
            System.out.println(let1 + " is already connected to " + (char) (plugboard[let1] + 'A')); // display previous connection
            alreadyConnected = true;
        }
        if (hasConnection(let2))
        {
            System.out.println(let2 + " is already connected to " + (char) (plugboard[let2] + 'A')); // display previous connection
            alreadyConnected = true;
        }
        if (alreadyConnected)
            return false;

        // insert wire
        plugboard[let1] = let2;
        plugboard[let2] = let1;

        // add inserted connection to connections list
        connections.add(letterPair);

        return true;
    }

    /**
     * Method to remove wire connection based on one letter of connection.
     * @param letter One of the letters of the connection that is to be removed.
     * @return True if connection was removed; false if there was no connection.
     */
    public boolean removeWire(char letter)
    {
        String conn = findConnection(letter);

        return removeWire(conn);
    }

    /**
     * Method to remove wire connection.
     * @param conn String of wire connection ends.
     * @return True if connection was removed; false if there was no connection.
     */
    public boolean removeWire(String conn)
    {
        // test if string is empty or too long
        if (conn.length() == 0 || conn.length() > 2)
            return false;

        // get internal representation of connection
        String conn1 = findConnection(Character.toUpperCase(conn.charAt(0)));
        String conn2 = findConnection(Character.toUpperCase(conn.charAt(1)));

        // make sure that connection exits in plugboard by comparing the two results
        if (!conn1.equals(conn2))
            return false;

        // link exists; remove connections
        plugboard[conn1.charAt(0) - 'A'] = conn1.charAt(0) - 'A'; // reset individual letter
        plugboard[conn1.charAt(1) - 'A'] = conn1.charAt(1) - 'A';
        connections.remove(conn1); // remove connection from LinkedList
        return true;
    }

    /**
     * Method to return LinkedList of all connections in the plugboard.
     * @return LinkedList of String of all connections in the plugboard.
     */
    public LinkedList<String> getConnections()
    {
        // create and return deep copy of connections
        return new LinkedList<>(connections);
    }

    /**
     * Method to find connected letter of provided letter.
     * @param letter Char to find swapped letter.
     * @return Char of swapped letter.
     */
    public char findConnectedLetter(char letter)
    {
        // exit if letter does not have connection
        if (!hasConnection(letter))
            return '\0'; // empty char; should this be something else?

        return (char) (plugboard[Character.toUpperCase(letter) - 'A'] + 'A');
    }

    /**
     * Method to find connection group of provided letter.
     * @param letter Char to find group of.
     * @return String of swapped group that the letter is in.
     */
    public String findConnection(char letter)
    {
        if (!hasConnection(letter))
            return ""; // empty string; should this be something else?

        // loop through connections to find if has connection; return if found
        for (String conn : connections)
        {
            int present = conn.indexOf(Character.toUpperCase(letter));

            if (present != -1) // found connection
                return conn;
        }

        // if no connection
        return "";
    }

    /**
     * Method to test if char has a swapped letter.
     * @param letter To test if swapped.
     * @return If letter is swapped.
     */
    public boolean hasConnection(char letter)
    {
        return hasConnection(Character.toUpperCase(letter) - 'A');
    }

    /**
     * Method to test if int of letter (0-based) has a swapped letter.
     * @param letter 0-based letter to test if swapped.
     * @return If letter is swapped.
     */
    private boolean hasConnection(int letter)
    {
        return plugboard[letter] != letter;
    }

    /**
     * Method to get output of letter (from keyboard toward reflector).
     * @param letter 0-based letter to get output.
     * @return 0-based output letter toward reflector.
     */
    public int get(int letter)
    {
        return plugboard[letter];
    }

    /**
     * Method to get "input" of letter (from rotors toward lampboard).
     * @param letter 0-based letter to get "input".
     * @return 0-based "input" letter toward lampboard.
     */
    public int indexOf(int letter)
    {
        // iterate through plugboard to find "input"
        for (int i = 0; i < plugboard.length; i++)
            if (plugboard[i] == letter)
                return i; // "input"

        // if not found; must have been invalid letter entered into method; return after loop for certain return
        return -1;
    }
}
