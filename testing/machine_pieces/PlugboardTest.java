package machine_pieces;

import machine_pieces.Plugboard;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PlugboardTest
{

    @Test
    void resetPlugboard()
    {
        // Create plugboard with connection
        Plugboard plugboard = new Plugboard("ED");
        // Get list of connections
        LinkedList<String> conns = plugboard.getConnections();
        // Make certain connections are correct in the plugboard
        assertEquals(plugboard.numberOfConnections(), 1); // one connection
        assertTrue(conns.size() > 0); // there are connections returned from linked list
        assertEquals("DE", conns.get(0));

        // Clear plugboard
        plugboard.resetPlugboard();
        conns = plugboard.getConnections();
        // Make certain connections are wiped out
        assertEquals(0, plugboard.numberOfConnections());
        assertEquals(0, conns.size());
    }

    @Test
    void insertWire()
    {
        Plugboard plugboard;

        // Test adding invalid characters
        plugboard = new Plugboard();
        assertFalse(plugboard.insertWire("@F")); // directly before 'A'
        assertFalse(plugboard.insertWire("[F")); // directly after 'Z'
        assertFalse(plugboard.insertWire("`F")); // directly before 'a'
        assertFalse(plugboard.insertWire("{F")); // directly after 'z'
        assertFalse(plugboard.insertWire("\\F")); // escape character

        // Test adding to blank plugboard; includes uppercase and lowercase testing
        plugboard = new Plugboard();
        assertEquals(0, plugboard.numberOfConnections()); // assert empty
        assertTrue(plugboard.insertWire("AB")); // upper upper
        assertEquals(1, plugboard.numberOfConnections());
        assertEquals(1, plugboard.get(0)); // A returns B
        assertEquals(0, plugboard.get(1)); // B returns A
        assertEquals(2, plugboard.get(2)); // C returns C
        assertTrue(plugboard.insertWire("Cd")); // upper lower
        assertEquals(2, plugboard.numberOfConnections());
        assertEquals("CD", plugboard.getConnections().get(1));
        assertTrue(plugboard.insertWire("eF")); // lower upper
        assertEquals(3, plugboard.numberOfConnections());
        assertEquals("EF", plugboard.getConnections().get(2));
        assertTrue(plugboard.insertWire("gh")); // lower lower
        assertEquals(4, plugboard.numberOfConnections());
        assertEquals("GH", plugboard.getConnections().get(3));
        // test ordering of letters (switches to alphabetical order)
        assertTrue(plugboard.insertWire("JI"));
        assertEquals(5, plugboard.numberOfConnections());
        assertEquals("IJ", plugboard.getConnections().get(4));

        // Test adding to plugboard with existing connections
        plugboard = new Plugboard("AB"); // initialize plugboard
        assertEquals(1, plugboard.numberOfConnections());
        assertEquals("AB", plugboard.getConnections().get(0));
        assertTrue(plugboard.insertWire("CD")); // assert adding new connection returns true
        assertEquals(2, plugboard.numberOfConnections());
        assertEquals("CD", plugboard.getConnections().get(1));

        // Test adding connection when one letter already connected to different letter
        plugboard = new Plugboard("AB");
        assertFalse(plugboard.insertWire("AC")); // first letter already connected
        assertFalse(plugboard.insertWire("CB")); // first letter already connected

        // Test adding connection when both letters already connected to different letters; also tests initializing plugboard with multiple connections
        plugboard = new Plugboard("AB CD");
        assertEquals(2, plugboard.numberOfConnections());
        assertEquals("AB", plugboard.getConnections().get(0));
        assertEquals("CD", plugboard.getConnections().get(1));
        assertFalse(plugboard.insertWire("AC")); // test cases
        assertFalse(plugboard.insertWire("BC"));
        assertFalse(plugboard.insertWire("DA"));

        // Test adding to full plugboard (by extension also tries to to add connection when both letters already connected to other letters)
        plugboard = new Plugboard("QW ER TY UI OP AS DF GH JK LZ XC VB NM");
        assertEquals(13, plugboard.numberOfConnections());
        assertFalse(plugboard.insertWire("AB"));

        // Test adding letter connected to itself
        plugboard = new Plugboard(); // empty plugboard
        assertFalse(plugboard.insertWire("AA"));
        plugboard = new Plugboard("AB"); // when already has connection
        assertFalse(plugboard.insertWire("AA"));
        plugboard = new Plugboard("AA"); // on initialization; uses next line to test
        assertEquals(0, plugboard.numberOfConnections());

        // Test adding a connection that already exists
        plugboard = new Plugboard("AB");
        assertFalse(plugboard.insertWire("AB"));

        // Test creating a plugboard with too many connections
        assertThrows(IllegalArgumentException.class,
                () -> { new Plugboard("QW ER TY UI OP AS DF GH JK LZ XC VB NM QW"); });
    }

    @Test
    void removeWire() // removeWire(char letter)
    {
        // Uppercase
        Plugboard plugboard = new Plugboard("AB");
        assertEquals(1, plugboard.numberOfConnections());
        assertEquals("AB", plugboard.getConnections().get(0));
        assertTrue(plugboard.removeWire('A'));
        assertEquals(0, plugboard.numberOfConnections());

        // Lowercase
        plugboard.insertWire("AB");
        assertEquals(1, plugboard.numberOfConnections());
        assertTrue(plugboard.removeWire('b'));
        assertEquals(0, plugboard.numberOfConnections());

        // Letter not in plugboard
        plugboard.insertWire("AB");
        assertFalse(plugboard.removeWire('C'));
        assertEquals(1, plugboard.numberOfConnections());

        // Invalid character
        assertFalse(plugboard.removeWire('#'));
    }

    @Test
    void testRemoveWire() // removeWire(String conn)
    {
        Plugboard plugboard = new Plugboard("AB CD EF HG");
        assertEquals(4, plugboard.numberOfConnections());
        assertEquals(0, plugboard.get(1));

        // Remove second wire
        assertEquals(2, plugboard.get(3)); // D returns C
        assertTrue(plugboard.removeWire("CD"));
        assertEquals(2, plugboard.get(2)); // C returns C
        assertEquals(3, plugboard.get(3)); // D returns D
        String[] test1 = {"AB", "EF", "GH"}; // what connections should now be
        LinkedList<String> list = plugboard.getConnections();
        assertEquals(test1.length, list.size()); // returned connections list is the correct size
        for (int i = 0; i < test1.length; i++)
            assertEquals(test1[i], list.get(i));

        // Remove last wire
        assertTrue(plugboard.removeWire("hg")); // tests since plugboard will represent as "GH"
        String[] test2 = {"AB", "EF"};
        list = plugboard.getConnections();
        assertEquals(test2.length, list.size()); // returned connections list is the correct size
        for (int i = 0; i < test2.length; i++)
            assertEquals(test2[i], list.get(i));

        // Remove non-present wires
        assertFalse(plugboard.removeWire("AC"));
        assertFalse(plugboard.removeWire("^6"));
    }

    @Test
    void getConnections()
    {
        // Test for regular plugboard
        Plugboard plugboard = new Plugboard("AB CD FE");
        assertEquals(3, plugboard.numberOfConnections());
        LinkedList<String> list = plugboard.getConnections();
        String[] test1 = {"AB", "CD", "EF"};
        for (int i = 0; i < test1.length; i++)
        {
            assertEquals(test1[i], list.get(i));
        }

        // insert later letters
        plugboard.insertWire("JZ");
        assertEquals(4, plugboard.numberOfConnections());
        list = plugboard.getConnections();
        String[] test2 = {"AB", "CD", "EF", "JZ"};
        for (int i = 0; i < test2.length; i++)
        {
            assertEquals(test2[i], list.get(i));
        }

        // insert in middle
        plugboard.insertWire("GL");
        assertEquals(5, plugboard.numberOfConnections());
        list = plugboard.getConnections();
        String[] test3 = {"AB", "CD", "EF", "GL", "JZ"};
        for (int i = 0; i < test3.length; i++)
        {
            assertEquals(test3[i], list.get(i));
        }

        // Test with blank plugboard
        plugboard = new Plugboard();
        assertEquals(0, plugboard.numberOfConnections());
        list = plugboard.getConnections();
        assertEquals(0, list.size());
    }

    @Test
    void findConnectedLetter()
    {
        Plugboard plugboard = new Plugboard("AE DO NC GZ LQ");
        assertEquals('E', plugboard.findConnectedLetter('a'));
        assertEquals('A', plugboard.findConnectedLetter('E'));
        assertEquals('N', plugboard.findConnectedLetter('C'));
        assertEquals('\0', plugboard.findConnectedLetter('X')); // no connection
        assertEquals('\0', plugboard.findConnectedLetter('^')); // invalid character
    }

    @Test
    void findConnection()
    {
        Plugboard plugboard = new Plugboard("AE DO NC GZ LQ");
        assertEquals("AE", plugboard.findConnection('a'));
        assertEquals("AE", plugboard.findConnection('E'));
        assertEquals("CN", plugboard.findConnection('C'));
        assertEquals("", plugboard.findConnection('X')); // no connection
        assertEquals("", plugboard.findConnection('^')); // invalid character
    }

    @Test
    void hasConnection()
    {
        Plugboard plugboard = new Plugboard("EI XO FJ AL QP MB");
        assertTrue(plugboard.hasConnection('E'));
        assertTrue(plugboard.hasConnection('o'));
        assertTrue(plugboard.hasConnection('x'));
        assertFalse(plugboard.hasConnection('Z')); // no connection
        assertFalse(plugboard.hasConnection('n')); // no connection
        assertFalse(plugboard.hasConnection('3')); // number (invalid character)
        assertFalse(plugboard.hasConnection('.')); // invalid character
        assertFalse(plugboard.hasConnection('\\')); // invalid character
    }

    @Test
    void numberOfConnection()
    {
        Plugboard plugboard = new Plugboard("QW ER TY UI OP AS DF GH JK LZ XC VB"); // 12 connections
        assertEquals(12, plugboard.numberOfConnections());
        plugboard.insertWire("NM"); // 13th connection
        assertEquals(13, plugboard.numberOfConnections());
        plugboard.insertWire("QT"); // will return false since plugboard full and repeated connections
        assertEquals(13, plugboard.numberOfConnections()); // above insert did not happen
        plugboard.insertWire("34"); // false; no connections but invalid characters
        assertEquals(13, plugboard.numberOfConnections());
        // remove connection
        plugboard.removeWire("NM");
        assertEquals(12, plugboard.numberOfConnections());
        // attempt valid letter and letter already present
        plugboard.insertWire("NA");
        assertEquals(12, plugboard.numberOfConnections());
        // attempt valid letter and invalid character
        plugboard.insertWire("!M");
        assertEquals(12, plugboard.numberOfConnections());
    }

    @Test
    void get()
    {
        Plugboard plugboard = new Plugboard("AC DL BK MN");
        // input letter modified to 0-based number from 'A'
        assertEquals('A' - 'A', plugboard.get('C' - 'A')); // C -> A
        assertEquals('C' - 'A', plugboard.get('A' - 'A')); // A -> C
        assertEquals('N' - 'A', plugboard.get('M' - 'A')); // M -> N
        assertEquals('X' - 'A', plugboard.get('X' - 'A')); // X -> X
        assertEquals('Z' - 'A', plugboard.get('Z' - 'A')); // Z -> Z
        // invalid inputs
        assertEquals(-1, plugboard.get('@' - 'A'));
        assertEquals(-1, plugboard.get('.' - 'A'));
        assertEquals(-1, plugboard.get('a' - 'A'));
    }

    @Test
    void indexOf()
    {
        Plugboard plugboard = new Plugboard("AC DL BK MN");
        // input letter modified to 0-based number from 'A'
        assertEquals('A' - 'A', plugboard.indexOf('C' - 'A')); // A <- C
        assertEquals('C' - 'A', plugboard.indexOf('A' - 'A')); // C <- A
        assertEquals('N' - 'A', plugboard.indexOf('M' - 'A')); // N <- M
        assertEquals('X' - 'A', plugboard.indexOf('X' - 'A')); // X <- X
        assertEquals('Z' - 'A', plugboard.indexOf('Z' - 'A')); // Z <- Z
        // invalid inputs
        assertEquals(-1, plugboard.indexOf('@' - 'A'));
        assertEquals(-1, plugboard.indexOf('.' - 'A'));
        assertEquals(-1, plugboard.indexOf('a' - 'A'));
    }

    @Test
    void toStringTest()
    {
        // Blank plugboard
        Plugboard plugboard = new Plugboard();
        String testPlugboard0 = "[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]\n" +
                                "[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]";
        assertEquals(testPlugboard0, plugboard.toString());

        // Insert connections
        plugboard.insertWire("EL");
        plugboard.insertWire("AZ");
        plugboard.insertWire("OM");
        String testPlugboard1 = "[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]\n" +
                                "[Z, B, C, D, L, F, G, H, I, J, K, E, O, N, M, P, Q, R, S, T, U, V, W, X, Y, A]";
        assertEquals(testPlugboard1, plugboard.toString());

        // Remove connection using letter
        plugboard.removeWire('A');
        String testPlugboard2 = "[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]\n" +
                                "[A, B, C, D, L, F, G, H, I, J, K, E, O, N, M, P, Q, R, S, T, U, V, W, X, Y, Z]";
        assertEquals(testPlugboard2, plugboard.toString());

        // Remove connection using string
        plugboard.removeWire("EL");
        String testPlugboard3 = "[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]\n" +
                                "[A, B, C, D, E, F, G, H, I, J, K, L, O, N, M, P, Q, R, S, T, U, V, W, X, Y, Z]";
        assertEquals(testPlugboard3, plugboard.toString());
    }
}
//TODO test creating plugboard letter connected multiple ways