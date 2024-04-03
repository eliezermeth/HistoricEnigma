package machine_pieces;

import utilities.InvalidKeyException;

/**
 * Class to change letter characters to integers and back again.  Standardizes to base 0 for use by machine.
 * <br>
 * Currently only works with a 26-letter alphabet.
 *
 * @author Eliezer Meth
 * @version 1 <br>
 * Start Date: 2022-06-21
 */
public class CharStandardization
{
    /**
     * Simulates electrical flow into the component in the direction of keyboard toward reflector.
     * @param letter Letter input into machine.
     * @return int for machine to operate on.
     */
    public int input(char letter)
    {
        int number = ((int) Character.toUpperCase(letter)) - 'A'; // set 'A' to 0

        // test that it is a valid input letter
        if (number < 0 || number > 25) // TODO: make it so that it works with a different character set
            throw new InvalidKeyException(Character.toString(letter));

        return number; // return 0-based int value for letter
    }

    /**
     * Simulates electrical flow into the component in the direction of reflector toward keyboard.
     * @param number int result from machine operations.
     * @return Human-readable output letter.
     */
    public int output(int number)
    {
        char letter = (char) (number + 'A'); // set 0 as 'A' ASCII value and convert

        // test that valid output number; should be impossible to get error
        if (letter < 'A' || letter > 'Z')
            throw new InvalidKeyException(Character.toString(letter));

        return letter; // capital letter returned by machine
    }
}
