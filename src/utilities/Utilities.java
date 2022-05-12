package utilities;

import java.util.LinkedList;

/**
 * @author Eliezer Meth
 * Start Date: 2020-09-21
 * Last Modified: 2022-02-23
 *
 * This class is to provide methods that will be used by other classes; methods that do not logically fit in the class,
 * or methods that are used by more than one class.
 */

public class Utilities
{
    /**
     * Method to get LinkedList<Character> of the alphabet (capital letters).
     * @return LinkedList<Character> of the alphabet (capital letters).
     */
    public static LinkedList<Character> getAzList()
    {
        char[] array = Utilities.getAzArray();

        LinkedList<Character> list = new LinkedList<>();
        for (char letter : array)
            list.add(letter);
        return list;
    }

    /**
     * Method to get char[] of the alphabet (capital letters).
     * @return char[] of the alphabet (capital letters).
     */
    public static char[] getAzArray()
    {
        return new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                          'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    }

    /**
     * Returns a char[] of the alphabet (capital letters) in QWERTY format.
     * @return char[]
     */
    public static char[] getQwertyArray()
    {
        return new char[]{'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D',
                          'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
    }

    /**
     * Takes an array of letters and adjusts them, using ASCII values, to A -> 0.  All letters will be capitalized prior
     * to conversion.
     *
     * @param array char[] containing letters from the English alphabet
     * @return 0-based int[]
     */
    public static int[] intAdjustArray(char[] array)
    {
        int[] temp = new int[array.length];

        for (int i = 0; i < array.length; i++)
        {
            temp[i] = convertChar(array[i]);
        }

        return temp;
    }
    // TODO try/catch for InvalidKeyException?

    /**
     * Takes an array of ints and adjusts them, using ASCII values, to 0 -> A.  Only works with letters in the English
     * language.
     *
     * @param array int[] containing letters from the English alphabet
     * @return 'A'-based array
     */
    public static char[] charAdjustArray(int[] array)
    {
        char[] temp = new char[array.length];

        for (int i = 0; i < array.length; i++)
        {
            temp[i] = convertChar(array[i]);
        }

        return temp;
    }
    // TODO try/catch for InvalidKeyException?

    /**
     * Method to return 0-based int value for letter.
     * @param letter Char of letter to type.
     * @return 0-based int of uppercase letter.
     */
    private static int convertChar(char letter)
    {
        int number = ((int) Character.toUpperCase(letter)) - 'A'; // set 'A' to 0

        // test that valid input letter
        if (number < 0 || number > 25) // outside letter range
            throw new InvalidKeyException(Character.toString(letter));

        return number; // return 0-based int value for letter
    }

    /**
     * Method to return character returned from Enigma.  It will be a capital letter.
     * @param number 0-based int of letter returned from Enigma.
     * @return Character equivalent in letters.
     */
    private static char convertChar(int number)
    {
        char letter = (char) (number + 'A'); // set 0 to 'A'

        // test that valid output number; should be impossible to get error
        if (letter < 'A' || letter > 'Z')
            throw new InvalidKeyException(Character.toString(letter));

        return letter; // capital letter returned by Enigma
    }

    /**
     * Acts like indexOf() method for array.
     * @param array Array to be searched.
     * @param letter Number to find.
     * @return Index where found.
     */
    public static int indexOf(int[] array, int letter)
    {
        for (int i = 0; i < array.length; i++)
            if (array[i] == letter)
                return i;

        return -1; // should never be reached
    }
}
