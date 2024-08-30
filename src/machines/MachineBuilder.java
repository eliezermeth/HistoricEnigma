package machines;

import machine_pieces.*;
import resources.AlphabetConverter;
import resources.WiringData;

import java.util.Map;
import java.util.Set;

/**
 * Builder class for create Enigma machine.
 *
 * @author Eliezer Meth
 * @version 2<br>
 * Start Date: 2024-06-25<br>
 * Last Modified: 2024-06-27
 */
public class MachineBuilder
{
    private AlphabetConverter ac;

    // Machine
    private WiringData.enimgaVersion machineVersion;
    private Map<String, Map<String, GearConstruction>> machineComponents;

    // Machine parts
    private EntryWheel entryWheel;

    private Plugboard plugboard;
    private boolean plugboardModifiable;

    private Rotor[] rotors; // holds rotors in LR configuration; .length = number of rotor slots
    private Set<String> availableRotors;

    private Reflector reflector;
    // TODO

    private MachineBuilder(WiringData.enimgaVersion version)
    {
        setMachineVersion(version);
        // TODO
    }

    public static MachineBuilder builder(WiringData.enimgaVersion version)
    {
        return new MachineBuilder(version);
    }

    private void setMachineVersion(WiringData.enimgaVersion version)
    {
        // TODO program metadata for machine variables
        machineVersion = version;

        switch (machineVersion)
        {
            case ENIGMA_1:
                machineComponents = WiringData.Enigma1();
                entryWheel = new EntryWheel(ac, EntryWheel.ETWsequence.ABCDE);
                plugboardModifiable = true;
                rotors = new Rotor[3]; // initialize array to proper length; TODO
                availableRotors = machineComponents.get("rotor").keySet();
                break;
            case ENIGMA_M3:
                machineComponents = WiringData.EnigmaM3();
                entryWheel = new EntryWheel(ac, EntryWheel.ETWsequence.ABCDE);
                plugboardModifiable = true;
                rotors = new Rotor[3]; // initialize array to proper length, 3 or 4?; TODO
                availableRotors = machineComponents.get("rotor").keySet();
                break;
        }
    }

    public MachineBuilder setEntryWheel(EntryWheel entryWheel)
    {
        if (entryWheel != null)
            this.entryWheel = entryWheel;

        return this;
    }

    /**
     * Add letter connections to the plugboard.
     * @param letters String of characters to be connected in the plugboard, with pairs separated by spaces.
     * @return self
     */
    public MachineBuilder addPlugboardConnection(String letters)
    {
        if (!plugboardModifiable) // not version cannot modify plugboard
            return this;

        if (plugboard == null) // implement elsewhere, such as constructor?
            plugboard = new Plugboard();

        String[] connections = letters.split(" ");
        for (String c : connections)
            plugboard.insertWire(letters); // does not return if successful

        return this;
    }

    /**
     * Choose a rotor position for a rotor.  Rotors are arranged with the rightmost as rotor 1, etc.
     * @param num Number of rotor slot.
     * @param name Name of rotor.
     * @return self
     */
    public MachineBuilder setRotor(int num, String name)
    {
        num = rotors.length - num;
        if (num < 1 || num > rotors.length - 1) // invalid slot selection
            return this;
        if (rotors[num] != null || !availableRotors.contains(name)) // slot occupied or rotor unavailable (nonexistent/used)
            return this;

        rotors[num] = new Rotor(name, machineComponents);
        availableRotors.remove(name); // remove rotor from available pool

        return this;
    }

    public MachineBuilder setRotorRingSetting(int num, int letter)
    {
        num = rotors.length - num;
        if (num < 1 || num > rotors.length - 1) // invalid slot selection
            return this;
        if (rotors[num] == null) // rotor not yet selected
            return this;

        rotors[num].setRingSetting(letter); // does not use return value

        return this;
    }

    public MachineBuilder setRotorGroundPosition(int num, char letter)
    {
        num = rotors.length - num;
        if (num < 1 || num > rotors.length - 1) // invalid slot selection
            return this;
        if (rotors[num] == null) // rotor not yet selected
            return this;

        rotors[num].setGroundPosition(letter);

        return this;
    }

    public MachineBuilder setReflector(String name)
    {
        if (machineComponents.get("reflector").containsKey(name))
            reflector = new Reflector(name, machineComponents);

        return this;
    }

    public ConstructedFullModel build()
    {
        // Entry wheel
        if (entryWheel == null)
            entryWheel = new EntryWheel(ac, EntryWheel.ETWsequence.ABCDE);

        // Plugboard
        if (plugboard == null)
            plugboard = new Plugboard(); // have letters return themselves

        // Rotors
        for (Rotor r : rotors)
            if (r == null)
                throw new IllegalStateException("All rotor slots must be filled before machine is built.");

        // Reflector
        if (reflector == null)
            throw new IllegalStateException("Reflector must be set before machine is built.");

        return new ConstructedFullModel(entryWheel, plugboard, rotors, reflector);
    }
}
