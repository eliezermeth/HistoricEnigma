package machine;

import com.sun.istack.internal.NotNull;
import interfaces.Model;

/**
 * @author Eliezer Meth
 * Start Date: 2020-12-08
 *
 * Class to hold a fully constructed Enigma machine, with its plugboard, rotors, and reflector.  It is to simulate the
 * electrical impulse that would occur in an actual machine.  A key would be pressed (in this case, a character input)
 * and it would illuminate one of the lamps (output the character).
 */

public class ConstructedMilitaryModel implements Model
{
    // Hardware pieces
    private final Rotor[] rotors;
    private final Reflector reflector;
    private final Plugboard plugboard;

    /**
     * Constructor for model.  Must contain the rotors, reflector, and plugboard.
     * @param rotors Array of rotors to be inserted into the machine.
     * @param reflector machine.Reflector to be inserted into the machine.
     * @param plugboard machine.Plugboard to be inserted into the machine.
     */
    public ConstructedMilitaryModel(@NotNull Rotor[] rotors, @NotNull Reflector reflector, @NotNull Plugboard plugboard)
    {
        this.rotors = rotors;
        this.reflector = reflector;
        this.plugboard = plugboard;
    }

    /**
     * Method to type a letter into the Enigma machine.
     * @param plaintextLetter Character to type into Enigma.
     * @return Output letter of the Enigma.
     */
    @Override
    public char type(char plaintextLetter)
    {
        int letter = convertChar(plaintextLetter); // convert to 0-based int
        int cipherLetter = plugboard.indexOf(rotor321(reflector.get(rotor123(plugboard.get(letter))))); // type letter into machine
        stepping();
        return convertChar(cipherLetter);
    }

    /**
     * Method to pass letter forward through rotors, from plugboard to reflector.
     * @param letter Int of letter to go into rotors from plugboard to reflector.
     * @return Output of letter from rotors from plugboard to reflector.
     */
    private int rotor123(int letter)
    {
        for (int i = 0; i < rotors.length; i++)
            letter = rotors[i].get(letter);
        return letter;
    }

    /**
     * Method to pass letter backward through rotors, from reflector to plugboard.
     * @param letter Int of letter to go into rotors from reflector to plugboard.
     * @return Output of letter from rotors from reflector to plugboard.
     */
    private int rotor321(int letter)
    {
        for (int i = rotors.length - 1; i > -1; i--)
            letter = rotors[i].indexOf(letter);
        return letter;
    }

    /**
     * Method to control the stepping of the rotors.
     */
    private void stepping()
    {
        boolean propagating; // if next rotor should step
        int i = 0;

        do {
            propagating = rotors[i++].step(); // step individual rotor and increase i
        } while (propagating && i < rotors.length);
    }

    /**
     * Method to return 0-based int value for letter.
     * @param letter Char of letter to type.
     * @return 0-based int of uppercase letter.
     */
    private int convertChar(char letter)
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
    private char convertChar(int number)
    {
        char letter = (char) (number + 'A'); // set 0 to 'A'

        // test that valid output number; should be impossible to get error
        if (letter < 'A' || letter > 'Z')
            throw new InvalidKeyException(Character.toString(letter));

        return letter; // capital letter returned by Enigma
    }
}

/**
 * @author Eliezer Meth
 * Start Date: 2020-12-08
 *
 * Class for invalid letter typed into Enigma.
 */
class InvalidKeyException extends RuntimeException
{
    /**
     * Constructor for error with message.
     * @param errorMessage String to be output with error.
     * @param err Throwable error.
     */
    public InvalidKeyException(String errorMessage, Throwable err)
    {
        super(errorMessage, err);
    }

    /**
     * Constructor for error of invalid key.
     * @param rotorKey Key attempted to be typed into machine.
     */
    public InvalidKeyException(String rotorKey) {
        super (rotorKey + " is not a valid key: ");
    }
}