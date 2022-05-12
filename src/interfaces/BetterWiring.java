package interfaces;

/**
 * Can function as surrounding every element in Enigma machine.  Requires an input method (electrical flow from keyboard
 * toward reflector) and an output method (flow from reflector toward lampboard).
 *
 * @author Eliezer Meth
 * @version 1.2
 * Start Date: 2022-02-23
 */
public interface BetterWiring
{
    /**
     * Simulates electrical flow into the component in the direction of keyboard toward reflector.
     * @param letter 'A'->0 input to component.
     * @return 'A'->0 output from component.
     */
    int input(int letter);

    /**
     * Simulates electrical flow into the component in the direction of reflector toward keyboard.
     * @param letter 'A'->0 input to component.
     * @return 'A'->0 output from component.
     */
    int output(int letter);
}
