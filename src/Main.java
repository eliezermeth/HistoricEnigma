import communicators.ConsoleCommunicator;
import machines.HistoricEnigmaMachine;

public class Main
{
    public static void main(String[] args)
    {
        ConsoleCommunicator communicator = new ConsoleCommunicator();
        HistoricEnigmaMachine machine = new HistoricEnigmaMachine(communicator);

        machine.setupEnigma();
        System.out.println(machine.type("HELLOWORLD"));

    }
}
