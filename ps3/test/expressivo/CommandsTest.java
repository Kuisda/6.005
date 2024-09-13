/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashMap;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // TODO tests for Commands.differentiate() and Commands.simplify()

    @Test
    public  void testDifferentiate() {
        assertEquals(Commands.differentiate("1+2*3+2*(1+2*5*x)","x"),"(0.0 + ((0.0 * 3.0 + 2.0 * 0.0) + ((0.0 + (0.0 * 5.0 * x + 2.0 * (0.0 * x + 5.0 * 1.0))) * 2.0 + (1.0 + 2.0 * 5.0 * x) * 0.0)))");
    }

    @Test
    public void testSimplify() {
        HashMap<String, Double> mp = new HashMap<String, Double>();
        mp.put("x", 2.0);
        assertEquals(Commands.simplify("1+2*3+2*(1+2*5*x)",mp),"49.0");
    }
    
}
