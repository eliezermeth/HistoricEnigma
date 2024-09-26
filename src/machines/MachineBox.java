package machines;

import machine_pieces.EntryWheel;
import machine_pieces.Plugboard;
import machine_pieces.Reflector;
import machine_pieces.Rotor;
import resources.AlphabetConverter;

/**
 * Class to hold implemented versions of all components of a version of the Enigma machine.  This class does not run,
 * but rather holds all entities that could compose an Enigma, similar to a machine out of the factory.  Components can
 * be modified within this class, but must be transferred to a <code>ConstructedFullModel</code> to run as a full
 * machine.
 * <br>
 * @author Eliezer Meth
 * @verison 1<br>
 * Start Date: 2024-09-18
 */
public class MachineBox
{
    // Hardware pieces
    private EntryWheel entryWheel;
    private Plugboard plugboard;
    private Rotor[] rotorGroup;
    private Reflector[] reflectorGroup;

    // Information
    private String name;
    private String description;
    private String alphabet;
    private int rotorSlots;
    private String comments;

    public MachineBox()
    {
        alphabet = AlphabetConverter.getAlphabetConverter().getAlphabetString();
    }

    public boolean setEntryWheel(EntryWheel entryWheel)
    {
        if (entryWheel != null)
            return false;

        this.entryWheel = entryWheel;
        return true;
    }

    public boolean setPlugboard(Plugboard plugboard)
    {
        if (plugboard != null)
            return false;

        this.plugboard = plugboard;
        return true;
    }

    public void setRotorGroup(Rotor... args)
    {
        this.rotorGroup = args;
    }

    public void setReflectorGroup(Reflector... args)
    {
        this.reflectorGroup = args;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    // get alphabet

    public void setRotorSlots(int rotorSlots)
    {
        this.rotorSlots = rotorSlots;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

}
