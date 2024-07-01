package interfaces;

/**
 * Interface for containing a constructed model of the Enigma machine.
 *
 * @author Eliezer Meth
 * @version 1.0.1<br>
 * Start Date: 2020-12-08<br>
 * Last Modified: 2024-06-24
 */

public interface MachineModel
{
    /**
     * Depress a letter on the keyboard of the Enigma machine.  Returns the lamp that is lit up.
     *
     * @param letter Character to type into Enigma.
     * @return Output letter of the Enigma.
     */
    char type(char letter);
}
