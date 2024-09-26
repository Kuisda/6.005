/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO: Description
 */
public class BoardTest {
    
    // TODO: Testing strategy

    Board board = new Board(16,30,10);
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO: Tests
    @Test
    public void testBoardConstructor() {
        System.out.println(board.BoardMessage());
    }

    @Test
    public void TestDig(){
        System.out.println(board.HandleDigMessage(0, 0));
        System.out.println();
        System.out.println(board.HandleDigMessage(1, 2));
        System.out.println();
        System.out.println(board.HandleDigMessage(0,3));
        System.out.println();
        System.out.println(board.HandleFlagMessage(1,3));
        System.out.println();
        System.out.println(board.HandleDeflagMessage(1,3));
        assertEquals("BOOM!\r\n",board.HandleDigMessage(1,3));

    }
    
}
