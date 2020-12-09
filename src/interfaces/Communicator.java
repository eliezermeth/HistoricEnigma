package interfaces;

/**
 * @author Eliezer Meth
 * Start Date: 2020-12-08
 *
 * Interface for a method of communication with an I/O system..
 */

public interface Communicator
{
    /**
     * Method to get next input line.
     * @return String of line.
     */
    String nextLine();

    /**
     * Method to send string.
     * @param str String
     */
    void send(String str);
}
