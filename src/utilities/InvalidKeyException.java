package utilities;

/**
 * Class for invalid letter typed into Enigma.
 *
 * @author Eliezer Meth
 * @version 1
 * Start Date: 2020-12-08
 * Last Modified 2022-02-21
 */
public class InvalidKeyException extends RuntimeException
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
