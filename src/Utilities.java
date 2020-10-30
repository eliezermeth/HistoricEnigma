import java.util.LinkedList;

/**
 * @author Eliezer Meth
 * Start Date: 2020-09-21
 * Last Modified: 2020-09-24
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
}
