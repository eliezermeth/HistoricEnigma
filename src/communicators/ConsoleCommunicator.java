package communicators;

import interfaces.Communicator;

import java.util.Scanner;

/**
 * @author Eliezer Meth
 * Start Date: 2020-12-08
 *
 * Class to get input from console.
 */

public class ConsoleCommunicator implements Communicator
{
    // Scanner to read from console
    private Scanner input;

    public ConsoleCommunicator()
    {
        input = new Scanner(System.in);
    }

    @Override
    public String nextLine()
    {
        return input.nextLine();
    }

    @Override
    public void send(String str)
    {
        // TODO return string
        System.out.println(str);
    }
}
