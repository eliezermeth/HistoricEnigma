package resources;

import java.util.ArrayList;

/**
 * Class to receive and hold a custom alphabet.  Characters can then be converted to their index in the alphabet (int),
 * and from their index back to their character.  Particularly helpful with non-sequential alphabets in Unicode.
 *
 * This class works as a parameterized singleton, in that it takes an alphabet at instantiation, and uses that alphabet
 * during its execution.
 *
 * @author Eliezer Meth
 * @version 1.0.1<br>
 * Start Date: 2024-05-15<br>
 * Last Modified: 2024-06-03
 */
public class AlphabetConverter
{
    private static AlphabetConverter alphabetConverter;

    private final String sAlphabet;
    private final char[] alphabet;

    /**
     * Create an Alphabet Converter based on the passed parameter.
     * @param alphabet Alphabet to use for conversions.
     */
    private AlphabetConverter(char[] alphabet)
    {
        // remove all duplicates from alphabet
        ArrayList<Character> temp = new ArrayList<>();
        for (char c : alphabet)
            if (!temp.contains(c))
                temp.add(c);
        char[] aTemp = new char[temp.size()];
        for (int i = 0; i < aTemp.length; i++)
            aTemp[i] = temp.get(i);

        this.alphabet = aTemp;
        this.sAlphabet = new String(this.alphabet);
    }

    /**
     * Create an Alphabet Converter singleton.  The Alphabet Converter will be based on the alphabet parameter,
     * unmodified.  If an instance was already created, throws an IllegalStateException.  Duplicate characters will be
     * reduced to the first occurrence.
     * @param alphabet String of all characters in the alphabet.
     * @throws IllegalStateException if singleton already exists
     */
    public static synchronized void createAlphabetConverter(String alphabet) throws IllegalStateException
    {
        createAlphabetConverter(alphabet.toCharArray());
    }

    /**
     * Create an Alphabet Converter singleton.  The Alphabet Converter will be based on the alphabet parameter,
     * unmodified.  If an instance was already created, throws an IllegalStateException.  Duplicate characters will be
     * reduced to the first occurrence.
     * @param alphabet char array of all characters in the alphabet.
     * @throws IllegalStateException if singleton already exists
     */
    public static synchronized void createAlphabetConverter(char[] alphabet) throws IllegalStateException
    {
        if (alphabetConverter == null)
            alphabetConverter = new AlphabetConverter(alphabet);
        else
            throw new IllegalStateException("Instance has already been created with alphabet: " +
                    alphabetConverter.sAlphabet);
    }

    /**
     * Get existing AlphabetConverter singleton.  If not yet instantiated, throws IllegalStateException.
     * @return reference to AlphabetConverter singleton.
     * @throws IllegalStateException if no AlphabetConverter has been created.
     */
    public static synchronized AlphabetConverter getAlphabetConverter()
    {
        if (alphabetConverter == null)
            throw new IllegalStateException("Instance has not yet been created.  Call createAlphabetConverter first.");

        return alphabetConverter;
    }

    /**
     * Return if an AlphabetConverter has already been instantiated.
     * @return if in instance of AlphabetConverter has been created.
     */
    public static synchronized boolean exists()
    {
        return alphabetConverter != null;
    }

    /**
     * Get a string containing all characters in the implemented alphabet.
     * @return String of characters in alphabet.
     */
    public String getAlphabetString()
    {
        return sAlphabet;
    }

    /**
     * Get a char array containing all characters in the implemented alphabet.
     * @return char[] of characters in alphabet.
     */
    public char[] getAlphabet()
    {
        return alphabet.clone();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Running methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Convert a character into its index of the alphabet.
     * @param letter character to find its index in the alphabet.
     * @return int index if the character is in the alphabet, -1 if not.
     */
    public int convert(char letter)
    {
        for (int i = 0; i < alphabet.length; i++)
            if (alphabet[i] == letter)
                return i;

        return -1; // if letter not in alphabet
    }

    /**
     * Convert a number into a character based on its index in the alphabet.
     * @param number index of character in alphabet.
     * @return character in alphabet; '\0' if out of bounds.
     */
    public char convert(int number) throws ArrayIndexOutOfBoundsException
    {
        try {
            return alphabet[number];
        } catch (ArrayIndexOutOfBoundsException e) {
            return '\0';
        }
    }
}
