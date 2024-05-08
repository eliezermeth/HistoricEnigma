package interfaces;

/**
 * Can function as surrounding every element in Enigma machine.  Requires an input method (electrical flow from keyboard
 * toward reflector) and an output method (flow from reflector toward lampboard).
 *
 * @author Eliezer Meth
 * @version 2.1
 * Start Date: 2024-03-03
 * Last Modified: 2024-04-11
 */
public interface Wiring
{
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
    int input(int contactSignal);

    /**
     * Simulates electrical flow into the component in the direction of reflector toward keyboard.
     * <br>
     * Data is transmitted in the form of a contact signal, an electrical impulse at a contact.  Contacts start from
     * contact 00, which is at the top of the rotor.  This physical position is by the letter in the window <b>at that
     * time</b>.  Contact 00 is the unmoving designation at the top, and all other contacts circle from there.
     * @param contactSignal position of the electrical signal when entering the rotor.
     * @return position of the electrical signal exiting the rotor.
     */
    int output(int contactSignal);
}
