package machines;

import machine_pieces.*;
import utilities.WiringData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author ELiezer Meth
 * Start Date: 2020-12-14
 *
 * Builder class to create Enigma machine.
 * TODO: currently made for 3-rotor Enigma with nonrotating reflector; modify to work with all
 */

public class MachineBuilder
{
    // Machine
    private WiringData.enimgaVersionsEnum machineVersion = WiringData.enimgaVersionsEnum.ENIGMA_1;
    private Map<String, GearConstruction[]> machineType;
    private int numRotorsAvailable; // internal logic
    private int numReflectorsAvailable; // internal logic

    // Machine settings

    // Plugboard
    private final List<String> connections = new LinkedList<>();

    // Rotors
    PreliminaryRotorInfo[] preliminaryRotors = new PreliminaryRotorInfo[3];
    boolean[] rotorSet = new boolean[3];

    // Reflector
    private int reflectorSelected = 1;

    /**
     * Constructor private to prevent unwanted construction
     */
    private MachineBuilder()
    {

    }

    /**
     * Constructor private to prevent unwanted construction.  Takes Enigma version as parameter.
     */
    private MachineBuilder(WiringData.enimgaVersionsEnum version)
    {
        setMachineVersion(version);
        for (int i = 0; i < preliminaryRotors.length; i++)
        {
            preliminaryRotors[i] = new PreliminaryRotorInfo();
            preliminaryRotors[i].rotorSelected = i;
            preliminaryRotors[i].rotorInitialPosition = 'A';
            preliminaryRotors[i].rotorRingSetting = 'A';
        }

    }

    /**
     * Method to return instance of builder class.
     * @param version Enum of Enigma version to be built.
     * @return Builder class.
     */
    public static MachineBuilder builder(WiringData.enimgaVersionsEnum version)
    {
        return new MachineBuilder(version);
    }

    /**
     * Method to set version of Enigma.
     * @param version Enigma version.
     */
    private void setMachineVersion(WiringData.enimgaVersionsEnum version)
    {
        // TODO use machine type to set other variables
        machineVersion = version;

        if (version == WiringData.enimgaVersionsEnum.ENIGMA_1)
        {
            machineType = WiringData.Enigma1();
            numRotorsAvailable = 5;
            numReflectorsAvailable = 3;
        }
        else if (version == WiringData.enimgaVersionsEnum.ENIGMA_M3)
        {
            machineType = WiringData.EnigmaM3();
            numRotorsAvailable = 8;
            numReflectorsAvailable = 2;
        }
    }

    /**
     * Method to add connection to plugboard.
     * @param letters Letters to be connected on plugboard.
     */
    public void addPlugboardConnection(String letters)
    {
        if (letters.length() < 2)
            return;

        letters = letters.toUpperCase();
        char letter1 = letters.charAt(0);
        char letter2 = letters.charAt(1);

        if (!Character.isAlphabetic(letter1) || !Character.isAlphabetic(letter2))
            return;

        for (String letterPair : connections)
            if (letterPair.charAt(0) == letter1 || letterPair.charAt(0) == letter2 ||
                letterPair.charAt(1) == letter1 || letterPair.charAt(1) == letter2)
                return; // if doubled letter, do not add

        connections.add("" + letter1 + letter2);
    }

    /**
     * Method to set the selected rotor for rotor 1.
     * @param num Number of rotor.
     */
    public void setRotor1(int num)
    {
        if (num > 0 && num <= numRotorsAvailable)
        {
            preliminaryRotors[0].rotorSelected = num;
            rotorSet[0] = true;
        }
    }

    /**
     * Method to set the initial position (letter on top) of rotor 1.
     * @param position Character of letter.
     */
    public void setRotor1InitialPosition(char position)
    {
        position = Character.toUpperCase(position);

        if (position >= 'A' && position <= 'Z')
            preliminaryRotors[0].rotorInitialPosition = position;
    }

    /**
     * Method to set the ring setting of rotor 1.
     * @param setting Character of setting letter.
     */
    public void setRotor1RingSetting(char setting)
    {
        setting = Character.toUpperCase(setting);

        if (setting >= 'A' && setting <= 'Z')
            preliminaryRotors[0].rotorRingSetting = setting;
    }

    /**
     * Method to set the selected rotor for rotor 2.
     * @param num Number of rotor.
     */
    public void setRotor2(int num)
    {
        if (num > 0 && num <= numRotorsAvailable)
        {
            preliminaryRotors[1].rotorSelected = num;
            rotorSet[0] = true;
        }
    }

    /**
     * Method to set the initial position (letter on top) of rotor 2.
     * @param position Character of letter.
     */
    public void setRotor2InitialPosition(char position)
    {
        position = Character.toUpperCase(position);

        if (position >= 'A' && position <= 'Z')
            preliminaryRotors[1].rotorInitialPosition = position;
    }

    /**
     * Method to set the ring setting of rotor 2.
     * @param setting Character of setting letter.
     */
    public void setRotor2RingSetting(char setting)
    {
        setting = Character.toUpperCase(setting);

        if (setting >= 'A' && setting <= 'Z')
            preliminaryRotors[1].rotorRingSetting = setting;
    }

    /**
     * Method to set the selected rotor for rotor 3.
     * @param num Number of rotor.
     */
    public void setRotor3(int num)
    {
        if (num > 0 && num <= numRotorsAvailable)
        {
            preliminaryRotors[2].rotorSelected = num;
            rotorSet[0] = true;
        }
    }

    /**
     * Method to set the initial position (letter on top) of rotor 3.
     * @param position Character of letter.
     */
    public void setRotor3InitialPosition(char position)
    {
        position = Character.toUpperCase(position);

        if (position >= 'A' && position <= 'Z')
            preliminaryRotors[2].rotorInitialPosition = position;
    }

    /**
     * Method to set the ring setting of rotor 3.
     * @param setting Character of setting letter.
     */
    public void setRotor3RingSetting(char setting)
    {
        setting = Character.toUpperCase(setting);

        if (setting >= 'A' && setting <= 'Z')
            preliminaryRotors[2].rotorRingSetting = setting;
    }

    /**
     * Method to select reflector.
     * @param num Number of reflector.
     */
    public void setReflectorSelected(int num)
    {
        if (num > 0 && num <= numReflectorsAvailable)
            reflectorSelected = num;
    }

    public ConstructedMilitaryModel build()
    {
        // Plugboard
        // only do the first 13 (if more added)
        StringBuilder plugboardSb = new StringBuilder();
        for (int i = 0; i < connections.size() && i < 13; i++) // maximum 13 connections
        {
            plugboardSb.append(connections.get(i)).append(" ");
        }
        Plugboard plugboard = new Plugboard(plugboardSb.toString());

        // Rotors
        fixRotors();
        Rotor[] rotors = new Rotor[preliminaryRotors.length];
        for (int i = 0; i < preliminaryRotors.length; i++)
        {
            rotors[i] = new Rotor(Integer.toString(preliminaryRotors[i].rotorSelected), Character.toString(preliminaryRotors[i].rotorRingSetting),
                    Character.toString(preliminaryRotors[i].rotorInitialPosition), machineType);
        }

        // Reflector
        Reflector reflector = new Reflector(Integer.toString(reflectorSelected), machineType);

        return new ConstructedMilitaryModel(rotors, reflector, plugboard);
    }

    private void fixRotors()
    {
        List<Integer> numbers = new LinkedList<>();

        boolean duplicate = false;
        for (PreliminaryRotorInfo preliminaryRotor : preliminaryRotors)
        {
            if (numbers.contains(preliminaryRotor.rotorSelected))
                duplicate = true;
            numbers.add(preliminaryRotor.rotorSelected);
        }

        if (!duplicate)
            return;

        // duplicate
        List<Integer> takenNumbers = new LinkedList<>();
        List<Integer> needToFix = new LinkedList<>();

        // user-set rotor gets precedence
        for (int i = 0; i < preliminaryRotors.length; i++)
        {
            if (rotorSet[i] && !takenNumbers.contains(preliminaryRotors[i].rotorSelected))
                takenNumbers.add(preliminaryRotors[i].rotorSelected);
            else if (rotorSet[i] && takenNumbers.contains(preliminaryRotors[i].rotorSelected))
                needToFix.add(i);;
        }
        // non-set rotors
        for (int i = 0; i < preliminaryRotors.length; i++)
        {
            if (!rotorSet[i] && !takenNumbers.contains(preliminaryRotors[i].rotorSelected))
                takenNumbers.add(preliminaryRotors[i].rotorSelected);
            else if (!rotorSet[i] && takenNumbers.contains(preliminaryRotors[i].rotorSelected))
                needToFix.add(i);;
        }

        // make list of available rotors
        List<Integer> availableNumbers = new LinkedList<>(); // make into queue/stack?
        for (int i = 1; i <= numRotorsAvailable; i++)
        {
            if (!takenNumbers.contains(i))
                availableNumbers.add(i);
        }
        Collections.shuffle(availableNumbers);

        // fix doubled rotors by giving it random other
        for (Integer toFix : needToFix)
        {
            preliminaryRotors[toFix].rotorSelected = availableNumbers.get(0);
            availableNumbers.remove(0);
        }
    }
}

class PreliminaryRotorInfo
{
    public int rotorSelected = 1;
    public char rotorInitialPosition = 'A';
    public char rotorRingSetting = 'A';
}
