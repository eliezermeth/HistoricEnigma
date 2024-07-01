package machines;

import com.sun.istack.internal.NotNull;

import interfaces.MachineModel;
import machine_pieces.EntryWheel;
import machine_pieces.Plugboard;
import machine_pieces.Reflector;
import machine_pieces.Rotor;
import utilities.AlphabetConverter;

/**
 * Class to hold a fully constructed Enigma model, be it civilian or military.  Contains all pieces that can be used in
 * Enigma machine; depending on the model, some will remain in the base state and not have any effect.  The model
 * contains an entry wheel, plugboard, rotors, and a reflector.
 * <br>
 * The model simulates the electrical impulse that would occur in an actual machine.  A key would be pressed (in this
 * case, a character input) and it would illuminate one of the lamps (output the character).
 * <br>
 * This class is a rewrite of ConstructedMilitaryModel.
 * <br>
 * @author Eliezer Meth
 * @version 2<br>
 * Start Date: 2024-06-24<br>
 * Last Modified: 2024-06-25
 */
public class ConstructedFullModel implements MachineModel
{
    private final AlphabetConverter ac;

    // Hardware pieces
    private final EntryWheel entryWheel;
    private final Plugboard plugboard;
    private final Rotor[] rotorAssembly;
    // rotors are stored as they physically would exist in the machine, with the first rotor in the rightmost slot
    private final Reflector reflector;

    /**
     * Constructor for an Enigma model.  Requires existing entry wheel, plugboard, rotors, and reflector.
     *
     * @param entryWheel Entry wheel to transform keyboard to machine pulse.
     * @param plugboard Enigma plugboard.
     * @param rotorAssembly Rotors to be inserted into the machine, in a left-right pattern.
     * @param reflector Enigma reflector
     * @throws IllegalStateException thrown if AlphabetConverter is not instantiated.
     */
    public ConstructedFullModel(@NotNull EntryWheel entryWheel, @NotNull Plugboard plugboard,
                                @NotNull Rotor[] rotorAssembly, @NotNull Reflector reflector)
    {
        this.entryWheel = entryWheel;
        this.plugboard = plugboard;
        this.rotorAssembly = rotorAssembly;
        this.reflector = reflector;

        // if AlphabetConstructor has not been instantiated, destroy the machine
        if (!AlphabetConverter.exists())
            throw new IllegalStateException("AlphabetConstructor must first be instantiated.");
        ac = AlphabetConverter.getAlphabetConverter();
    }

    /**
     * Depress a letter on the keyboard of the Enigma machine.  Returns the lamp that is lit up.
     *
     * @param letter Character to type into Enigma.
     * @return Output letter of the Enigma.
     */
    @Override
    public char type(char letter)
    {
        int position = ac.convert(letter); // throw exception if invalid letter?

        stepping(); // machine steps before electrical signal passes through rotor assembly
        int cipherLetter = entryWheel.output(plugboard.output(rotorAssemblyLR(
                reflector.input(rotorAssemblyRL(plugboard.input(entryWheel.input(position)))))));
        return ac.convert(cipherLetter);
    }

    /**
     * Method to pass a letter forward through the rotors, from the plugboard to the reflector.  In a physical machine,
     * this would send an electrical signal from the right side of the machine to the left.
     *
     * @param contactSignal int of electrical impulse position to go into the rotor assembly.
     * @return position of electrical impulse exiting the rotor assembly.
     */
    private int rotorAssemblyRL(int contactSignal)
    {
        for (int i = rotorAssembly.length - 1; i > -1; i--) // iterate backward over rotorAssembly array
            contactSignal = rotorAssembly[i].input(contactSignal);
        return contactSignal;
    }

    /**
     * Method to pass a letter backward through the rotors, from the reflector to the plugboard.  In a physical machine,
     * this would send an electrical signal from the left side of the machine to the right.
     *
     * @param contactSignal int of electrical impulse position to go into the rotor assembly.
     * @return position of electrical impulse exiting the rotor assembly.
     */
    private int rotorAssemblyLR(int contactSignal)
    {
        for (int i = 0; i < rotorAssembly.length; i++) // iterate forward over rotorAssembly array
            contactSignal = rotorAssembly[i].output(contactSignal);
        return contactSignal;
    }

    /**
     * Step the necessary rotors.
     * <br><br>
     * After a key press (and signal return), the rightmost rotor would step.  If the pawl engaged, it would step the
     * next rotor, etc.
     */
    private void stepping()
    {
        boolean propogate; // if next rotor should step
        int i = rotorAssembly.length - 1; // first (rightmost) rotor

        do {
            propogate = rotorAssembly[i--].step(); // step individual rotor and shift i to new rotor
        } while (propogate && i > -1); // restrain to array
    }

    /**
     * Returns reference to the EntryWheel object.
     * @return EntryWheel object.
     */
    public EntryWheel getEntryWheel()
    {
        return entryWheel;
    }

    /**
     * Returns reference to the Plugboard object.
     * @return Plugboard object.
     */
    public Plugboard getPlugboard()
    {
        return plugboard;
    }

    /**
     * Returns reference to the Rotor assembly array.  Rotors are arranged in the LR order, with the first rotor on the
     * right side.
     * @return <code>Rotor[]</code> array in LR order.
     */
    public Rotor[] getRotorAssembly()
    {
        return rotorAssembly;
    }

    /**
     * Returns reference to the Reflector object.
     * @return Reflector object.
     */
    public Reflector getReflector()
    {
        return reflector;
    }
}
