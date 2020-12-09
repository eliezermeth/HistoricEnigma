import machine_pieces.Rotor;
import org.junit.jupiter.api.Test;
import utilities.WiringData;

import static org.junit.jupiter.api.Assertions.*;

class RotorTest
{

    @Test
    void getRotorSelected()
    {
        Rotor r1 = new Rotor("1", WiringData.Enigma1());
        Rotor r2 = new Rotor("2", WiringData.Enigma1());
        assertEquals("1", r1.getRotorSelected());
        assertEquals("2", r2.getRotorSelected());
    }

    @Test
    void setRingSetting()
    {
        Rotor r1 = new Rotor("1", WiringData.Enigma1());
        assertEquals("A", r1.getRingSetting());
        r1.setRingSetting("D");
        assertEquals("D", r1.getRingSetting());
    }

    @Test
    void getRingSetting()
    {
        Rotor r1 = new Rotor("1", WiringData.Enigma1());
        assertEquals("A", r1.getRingSetting());
    }

    @Test
    void setInitialPosition()
    {
        Rotor r1 = new Rotor("1", WiringData.Enigma1());
        r1.setInitialPosition("B");
        assertEquals("B", r1.getInitialPosition());
    }

    @Test
    void getInitialPosition()
    {
        Rotor r1 = new Rotor("1", WiringData.Enigma1());
        assertEquals("A", r1.getInitialPosition());
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