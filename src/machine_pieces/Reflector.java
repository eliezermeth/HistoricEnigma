package machine_pieces;

import interfaces.Wiring;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class is to simulate a reflector that could be inserted into the machine.
 * <br><br>
 * Historic reflector configurations can be found at:<br>
 * <a href="https://en.wikipedia.org/wiki/Enigma_rotor_details">https://en.wikipedia.org/wiki/Enigma_rotor_details</a><br>
 * <a href="https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10">https://www.cryptomuseum.com/crypto/enigma/wiring.htm#10</a><br>
 * <br>
 * @author Eliezer Meth
 * @version 3<br>
 * Start Date: 2024-04-25<br>
 * Last Modified: 2024-05-08
 */
public class Reflector implements Wiring
{
    // External reflector information

    private final String reflectorSelected; // reflector selected

    // Internal reflector configurations
    private final GearConstruction gear; // factory setting of rotor
    private final ArrayList<Character> letters; // array of shown letters
    private ArrayList<Character> wirings; // internal reflector wiring
    /* Letters (front) and wirings (back) have a one-to-one relation.  The letter in front element 0 links directly to
    wiring back element 0.  However, to find where the signal is output from the gear, the letter from the back must
    have its position found in the front.

    While the default reflector top letter is B, signal input at spot 00 was still A, one element before the top.
    Therefore, the reflector can be treated similarly to the rotor, but the "window" would be the second position.
    */

    private boolean rotatable;
    private boolean stepping;
    private boolean rewirable;

    /**
     * Constructor to select the correct reflector.
     * @param reflectorSelected name of reflector.
     * @param selection Map for rotors and reflectors for a version of Enigma.
     */
    public Reflector(String reflectorSelected, Map<String, Map<String, GearConstruction>> selection)
    {
        this.reflectorSelected = reflectorSelected;

        gear = selection.get("reflector").get(reflectorSelected);
        letters = gear.getWirings()[0];
        wirings = gear.getWirings()[1];
        rotatable = gear.isReflectorRotatable();
        stepping = gear.isReflectorStepping();
        rewirable = gear.isReflectorRewirable();
    }

    /**
     * Get the name of the selected reflector.
     * @return String of selected reflector.
     */
    public String getReflectorSelected()
    {
        return reflectorSelected;
    }

    /**
     * Returns if the reflector is rotatable.
     * @return boolean if rotatable.
     */
    public boolean isRotatable()
    {
        return rotatable;
    }

    /**
     * Returns if the reflector steps.
     * @return boolean if steps.
     */
    public boolean isStepping()
    {
        return stepping;
    }

    /**
     * Returns if the reflector is rewirable.
     * @return boolean if rewirable.
     */
    public boolean isRewirable()
    {
        return rewirable;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Running methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Simulates electrical flow into the component in the direction of keyboard toward reflector.
     * <br>
     * Data is transmitted in the form of a contact signal, an electrical impulse at a contact.  Contacts start from
     * contact 00, which is at the top of the rotor.  This physical position is by the letter in the window <b>at that
     * time</b>.  Contact 00 is the unmoving designation at the top, and all other contacts circle from there.
     *
     * @param contactSignal position of the electrical signal when entering the rotor.
     * @return position of the electrical signal exiting the rotor.
     */
    @Override
    public int input(int contactSignal)
    {
        return letters.indexOf(wirings.get(contactSignal));
    }

    /**
     * Simulates electrical flow into the component in the direction of reflector toward keyboard.
     * <br>
     * Data is transmitted in the form of a contact signal, an electrical impulse at a contact.  Contacts start from
     * contact 00, which is at the top of the rotor.  This physical position is by the letter in the window <b>at that
     * time</b>.  Contact 00 is the unmoving designation at the top, and all other contacts circle from there.
     * <br>
     * Note: The <b>input</b> method should be used for a regular run of the Enigma machine; this method will produce
     * opposite effects.
     *
     * @param contactSignal position of the electrical signal when entering the rotor.
     * @return position of the electrical signal exiting the rotor.
     */
    @Override
    public int output(int contactSignal)
    {
        return wirings.indexOf(letters.get(contactSignal));
    }

    // TODO did not program rotatable, stepping, rewirable to check exact mechanics
    // TODO if rewirable, need methods to rewire rotor
}
