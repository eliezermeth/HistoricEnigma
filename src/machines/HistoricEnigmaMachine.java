package machines;

import interfaces.Communicator;
import interfaces.MachineModel;
import interfaces.Wiring;
import machine_pieces.*;
import utilities.WiringData;

import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eliezer Meth
 * Start Date: 2020-09-17
 * Last Modified: 2020-12-09
 *
 * This project is to simulate an Enigma machine such as the one used by the Germans in WWII.
 *
 * Flaw in Enigma:
 * https://www.youtube.com/watch?v=V4V2bpZlqx8
 * Enigma sample messages:
 * http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
 * Online Enigma machine:
 * http://people.physik.hu-berlin.de/~palloks/js/enigma/enigma-u_v25_en.html
 */

public class HistoricEnigmaMachine
{
    // Variables
    private Communicator input; // to communicate from UI and Enigma machine
    private MachineModel machine; // Enigma machine

    /**
     * Constructor for class.  Only takes in method of input.
     * @param input Communicator.
     */
    public HistoricEnigmaMachine(Communicator input)
    {
        this.input = input;
    }

    // TODO decouple from console and military version
    /**
     * Method to set up Enigma machine.
     */
    public void setupEnigma()
    {
        // Get Enigma version
        input.send("Select Enigma version: " + Arrays.toString(WiringData.enigmaVersions));
        String version = input.nextLine();

        // Select correct machine
        Map<String, GearConstruction[]> selection = null;
        int numRotors = 0;
        int numReflectors = 0;
        switch (version) // TODO make sure input allowed version
        {
            case "Enigma1":
                selection = WiringData.Enigma1();
                numRotors = 3;
                numReflectors = 3;
                break;
            case "EnigmaM3":
                selection = WiringData.EnigmaM3();
                numRotors = 4;
                numReflectors = 2;
                break;
            default:
                try
                {
                    throw new Exception("Unknown version");
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
        }

        // TODO test that only selecting from correct number of rotors and reflectors
        // TODO add logic to prevent rotor from being used more than once

        // Select rotors
        input.send(String.format("Input the rotor selected, ring setting, and initial position in the form 1AC " +
                "for %d rotors, separated by a space.", numRotors));
        String rotorChoices = input.nextLine();

        // Format rotors
        String[] splitChoices = rotorChoices.split(" ");
        Rotor[] rotors = new Rotor[numRotors];
        for (int i = 0; i < numRotors; i++)
        {
            String configuration = splitChoices[i];
            rotors[i] = new Rotor(Character.toString(configuration.charAt(0)), Character.toString(configuration.charAt(1)),
                    Character.toString(configuration.charAt(2)), selection);
        }

        // Select reflector
        input.send(String.format("Input the selected reflector from %d reflectors.", numReflectors));
        String selectedRotor = Character.toString(input.nextLine().charAt(0));
        Reflector reflector = new Reflector(selectedRotor, selection);


        // Create plugboard
        input.send("Input the plugboard connections in the form AB CD EF");
        Plugboard plugboard = new Plugboard(input.nextLine());

        // TODO make that not reliant on military version
        // make machine
        machine = new ConstructedMilitaryModel(rotors, reflector, plugboard);
    }

    /**
     * Method to type on machine.
     * @param text String of text to be put into Enigma machine.
     * @return Output text of machine.
     */
    public String type(String text)
    {
        StringBuilder sb = new StringBuilder();

        text = text.toUpperCase();
        int length = text.length();
        for (int i = 0; i < length; i++)
        {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') // change to Character.isLetter() ?
            {
                sb.append(machine.type(c));
            }
        }

        return sb.toString();
    }
}
