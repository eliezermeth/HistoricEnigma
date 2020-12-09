package interfaces;

/**
 * @author Eliezer Meth
 * Start Date: 2020-12-08
 *
 * Interface for containing a constructed model of the Enigma machine.
 */

public interface Model
{
    /**
     * Method to type a letter into the Enigma machine.
     * @param plaintextLetter Character to type into Enigma.
     * @return Output letter of the Enigma.
     */
    char type(char plaintextLetter);
}
