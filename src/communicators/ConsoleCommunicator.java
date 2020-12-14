package communicators;

import interfaces.Communicator;

import java.util.Scanner;

/**
 * @author Eliezer Meth
 * Start Date: 2020-12-08
 * Last Modified: 2020-12-09
 *
 * Class to get input from console.
 */

public class ConsoleCommunicator implements Communicator
{
    // Scanner to read from console
    private Scanner input;

    /**
     * Constructor.  Creates new Scanner.
     */
    public ConsoleCommunicator()
    {
        input = new Scanner(System.in);
    }

    /**
     * Gets next line from console.
     * @return String.
     */
    @Override
    public String nextLine()
    {
        return input.nextLine();
    }

    /**
     * Gets next int from console.
     * @return Int.
     */
    @Override
    public int nextInt()
    {
        return input.nextInt();
    }

    /**
     * Prints string to console.
     * @param str String.
     */
    @Override
    public void send(String str)
    {
        // TODO return string
        System.out.println(str);
    }
}
