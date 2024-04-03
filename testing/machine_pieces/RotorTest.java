package machine_pieces;

import com.sun.corba.se.impl.ior.WireObjectKeyTemplate;
import machine_pieces.Rotor;
import org.junit.jupiter.api.Test;
import utilities.WiringData;

import static org.junit.jupiter.api.Assertions.*;

class RotorTest
{
    // TODO tests on constructors

    @Test
    void getRotorSelected()
    {
        BetterRotor r1 = new BetterRotor(1, WiringData.betterEnigma1());
        BetterRotor r2 = new BetterRotor(2, WiringData.betterEnigma1());
        assertEquals(1, r1.getRotorSelected());
        assertEquals(2, r2.getRotorSelected());
    }

    @Test
    void setRingSetting()
    {
        BetterRotor r1 = new BetterRotor(1, WiringData.betterEnigma1());
        assertEquals(0, r1.getRingSetting()); // default setting, A
        r1.setRingSetting(4); // set to E
        assertEquals(4, r1.getRingSetting());
    }

    @Test
    void getRingSetting()
    {
        // see tests in setRingSetting()
    }

    @Test
    void setInitialPosition()
    {
        BetterRotor br1 = new BetterRotor(1, WiringData.betterEnigma1());
        assertEquals(0, br1.getInitialPosition()); // default
        br1.setInitialPosition(1); // set to B
        assertEquals(1, br1.getInitialPosition());
        br1.setInitialPosition(-1); // go backwards to Z
        assertEquals(25, br1.getInitialPosition());
    }

    @Test
    void getInitialPosition()
    {
        // see tests in setInitialPosition()
    }

    @Test
    void getWindow()
    {
        BetterRotor br1 = new BetterRotor(1, WiringData.betterEnigma1());
        assertEquals(0, br1.getWindow());
        br1.setInitialPosition(1);
        assertEquals(1, br1.getWindow());

        BetterRotor br2 = new BetterRotor(2, WiringData.betterEnigma1());
        assertEquals(0, br2.getWindow());
        br2.setInitialPosition(5);
        assertEquals(5, br2.getWindow());
    }

    @Test
    void step()
    {
    }

    @Test
    void get()
    {
        Rotor r1 = new Rotor("1", WiringData.Enigma1());
        assertEquals(4, r1.get(0)); // 4 = E; should start off as first element
        assertEquals(10, r1.get(1)); // 10 = K; should start off as second element
        r1.step(); // rotate rotor once
        assertEquals(10, r1.get(0)); // K should now be the first element

    }

    @Test
    void indexOf()
    {
        Rotor r1 = new Rotor("1", WiringData.Enigma1());
        assertEquals(0, r1.indexOf(4)); // 4 = E; should start off as first element
        assertEquals(1, r1.indexOf(10)); // 10 = K; start off as second element
        r1.step(); // rotate rotor once
        assertEquals(0, r1.indexOf(10)); // K should now be the first element
    }

    @Test
    void seeRotorRotation()
    {
        // setRingSetting and setInitialPosition work

        Rotor r1 = new Rotor("1", WiringData.Enigma1());
        Rotor r2 = new Rotor("2", WiringData.Enigma1());
        for (int i = 0; i < 30; i++)
        {
            boolean propagating = r1.step();
            System.out.println((i + 1)  + " " + propagating + "\n" + r1);
            if (propagating)
                r2.step();
            System.out.println(r2 + "\n");
        }
    }
}