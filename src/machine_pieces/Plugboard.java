package machine_pieces;

import interfaces.Wiring;
import utilities.Utilities;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Eliezer Meth
 * Start Date: 2020-10-01
 * Last Modified: 2020-12-14
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
    private final char EMPTY_CHAR = '\0';
    private final String EMPTY_STRING = "";

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
        String[] possibleConnections = wireList.trim().split(" ");

        // test if too many connections
        if (possibleConnections.length > 13)
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

        // clear connections list
        connections.clear();
    }

    /**
     * Method to insert wire to swap the two letters in the letter pair.
     * @param letterPair Two letters to be swapped.
     * @return Boolean if connection was made.  True if yes, false if one of the letters was already swapped or invalid.
     */
    public boolean insertWire(String letterPair)
    {
        // make both letters uppercase so calculations work
        letterPair = alphabetize(letterPair.toUpperCase());

        int let1 = letterPair.charAt(0) - 'A'; // get first letter and store it with 'A' as 0
        int let2 = letterPair.charAt(1) - 'A'; // get second letter and store it with 'A' as 0

        // test if entered letters are in the alphabet
        if (let1 < 0 || let1 > 25 || let2 < 0 || let2 > 25) // not in the alphabet
        {
            System.out.println("\"" + letterPair + "\" is an invalid entry for the plugboard.");
            return false;
        }

        // test if all plugboard connections have already been made; if true, will also trip pairAlreadyExists or alreadyConnected if statements
        if (numberOfConnections() >= 13)
            System.out.println("All plugboard connections have already been made.");

        // test if the connection already exists in the plugboard
        boolean pairAlreadyExists = false;
        if (findConnectedLetter(letterPair.charAt(0)) == letterPair.charAt(1))
        {
            System.out.println(letterPair.charAt(0) + " is already connected to " + letterPair.charAt(1));
            pairAlreadyExists = true;
        }

        // test if one of the letters was already swapped in the plugboard
        boolean alreadyConnected = false;
        if (!pairAlreadyExists && hasConnection(let1))
        {
            System.out.println(letterPair.charAt(0) + " is already connected to " + (char) (plugboard[let1] + 'A')); // display previous connection
            alreadyConnected = true;
        }
        if (!pairAlreadyExists && hasConnection(let2))
        {
            System.out.println(letterPair.charAt(1) + " is already connected to " + (char) (plugboard[let2] + 'A')); // display previous connection
            alreadyConnected = true;
        }

        // test if attempting to connect letter to itself
        if (let1 == let2)
        {
            System.out.println("A letter cannot be connected to itself.");
            return false;
        }

        if (pairAlreadyExists || alreadyConnected) // if at least one letter was already connected
            return false;

        // insert wire
        plugboard[let1] = let2;
        plugboard[let2] = let1;

        // add inserted connection to connections list in alphabetical position
        //TODO optimize
        for (int i = 0; i < connections.size(); i++)
        {
            if (letterPair.charAt(0) < connections.get(i).charAt(0)) // compare first letters
            {
                connections.add(i, letterPair);
                return true;
            }
        }
        // else
        connections.add(alphabetize(letterPair));
        return true;
    }

    /**
     * Method to assist adding a letter pair to the connections LinkedList by alphabetizing it.
     * @param letterPair Letters to be alphabetized.
     * @return Alphabetized letter string.
     */
    private String alphabetize(String letterPair)
    {
        char[] array = letterPair.toCharArray(); // make into char array
        Arrays.sort(array); // sort
        return new String(array); // convert to string and return
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

        // make sure that characters were in the alphabet
        boolean invalidConnection = false;
        if (conn1.equals(EMPTY_STRING))
        {
            System.out.println("\"" + conn.charAt(0) + "\" is an invalid character." );
            invalidConnection = true;
        }
        if (conn1.equals(EMPTY_STRING))
        {
            System.out.println("\"" + conn.charAt(1) + "\" is an invalid character." );
            invalidConnection = true;
        }
        // make sure that connection exists in plugboard by comparing the two results
        if (!conn1.equals(conn2))
        {
            System.out.println("There is no wire between " + Character.toUpperCase(conn.charAt(0)) +
                    " and " + Character.toUpperCase(conn.charAt(1)));
            invalidConnection = true;
        }
        // return now if invalid connection
        if (invalidConnection)
            return false;

        // else
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
     * Method to find connected letter of provided letter.  Best if called after hasConnection().
     * @param letter Char to find swapped letter.
     * @return Char of swapped letter.
     */
    public char findConnectedLetter(char letter)
    {
        // exit if letter does not have connection
        if (!hasConnection(letter))
            return EMPTY_CHAR; // empty char; should this be something else?

        return (char) (plugboard[Character.toUpperCase(letter) - 'A'] + 'A');
    }

    /**
     * Method to find connection group of provided letter.  Best if called after hasConnection().
     * @param letter Char to find group of.
     * @return String of swapped group that the letter is in.
     */
    public String findConnection(char letter)
    {
        if (!hasConnection(letter))
            return EMPTY_STRING; // empty string; should this be something else?

        // loop through connections to find if has connection
        String pair = "";
        for (String conn : connections)
        {
            int present = conn.indexOf(Character.toUpperCase(letter));

            if (present != -1) // found connection
                pair = conn;
        }

        return pair;
    }

    /**
     * Method to test if char has a swapped letter.
     * @param letter To test if swapped.
     * @return If letter is swapped.
     */
    public boolean hasConnection(char letter)
    {
        int numOfLetter = Character.toUpperCase(letter) - 'A';
        return numOfLetter > -1 && numOfLetter < 26 && hasConnection(numOfLetter); // check if given letter is actually a letter
        //TODO return that input letter is invalid
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
     * Method to return number of connection on plugboard.
     * @return Int of number of connections.
     */
    public int numberOfConnections()
    {
        return connections.size();
    }

    /**
     * Method to get output of letter (from keyboard toward reflector).
     * @param letter 0-based letter to get output.
     * @return 0-based output letter toward reflector.
     */
    public int get(int letter)
    {
        if (letter < 0 || letter > 25) // letter is invalid; outside of array
            return -1;

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

    /**
     * Method to return string of regular letters array and plugboard letters array of connections.
     * @return String of regular letters array and plugboard letters array of connections.
     */
    public String toString()
    {
        // Convert int[] plugboard to string array
        String[] plugboardArray = new String[plugboard.length];
        for (int i = 0; i < plugboardArray.length; i++)
        {
            plugboardArray[i] = Character.toString((char) (plugboard[i] + 65)); // num to ASCII, to char, to String
        }

        // Construct and return array string
        return Arrays.toString(visibleLetters) + "\n" + Arrays.toString(plugboardArray);
    }
}
//TODO code for input of "ADEF"