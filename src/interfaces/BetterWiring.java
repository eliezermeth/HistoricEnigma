package interfaces;

/**
 * Can function as surrounding every element in Enigma machine.  Requires an input method (electrical flow from keyboard
 * toward reflector) and an output method (flow from reflector toward lampboard).
 *
 * @author Eliezer Meth
 * @version 2
 * Start Date: 2024-03-03
 */
public interface BetterWiring
{
    /**
     * Simulates electrical flow into the component in the direction of keyboard toward reflector.
     * @param letter Letter input to component.
     * @return Letter output from component.
     */
    char input(char letter);

    /**
     * Simulates electrical flow into the component in the direction of reflector toward keyboard.
     * @param letter Letter input to component.
     * @return Letter output from component
     */
    char output(char letter);
}
