package utilities;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class is to provide methods that will be used by other classes; methods that do not logically fit in the class,
 * or methods that are used by more than one class.
 *
 * @author Eliezer Meth
 * @version 1.2
 * Start Date: 2020-09-21
 * Last Modified: 2024-04-04
 */

public class Utilities
{
    /**
     * Method to get LinkedList<Character> of the alphabet (capital letters).
     * @return LinkedList<Character> of the alphabet (capital letters).
     */
    public static LinkedList<Character> getAzLinkedList()
    {
        char[] array = Utilities.getAzArray();

        LinkedList<Character> list = new LinkedList<>();
        for (char letter : array)
            list.add(letter);
        return list;
    }

    /**
     * Method to get ArrayList<Character> of the alphabet (capital letters).
     * @return LinkedList<Character> of the alphabet (capital letters).
     */
    public static ArrayList<Character> getAzArrayList()
    {
        char[] array = Utilities.getAzArray();

        ArrayList<Character> list = new ArrayList<>(array.length);
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
}
