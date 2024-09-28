/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

    @Test
    public void TestBuildFromFile(){
        String filename ="D:/javaProject/lab-6.005/ps4/src/test/java/minesweeper/boards/board.txt";
        File file = new File(filename);
        try{
            Scanner reader = new Scanner(file);
            //read first line to get sizeX and sizeY
            String line = reader.nextLine();
            String[] temp = line.split(" ");
            if(temp.length!= 2){
                throw new IllegalArgumentException("Invalid input file format.The first line should be sizeX sizeY." +
                        "Then the board should only contain 0 or 1.0 for empty cell and 1 for mine.");
            }
            int sizeX = Integer.parseInt(temp[0]);
            int sizeY = Integer.parseInt(temp[1]);
            ArrayList<Integer> boardData = new ArrayList<>();
            while(reader.hasNextLine()){
                line = reader.nextLine();
                temp = line.split(" ");
                for(String s:temp){
                    boardData.add(s.charAt(0)-'0');
                }
            }
            System.out.println(boardData.size());
            assert(boardData.size() == sizeX*sizeY);
            board = new Board(sizeX, sizeY, boardData);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals("BOOM!\r\n",board.HandleDigMessage(5,0));


    }
    
}
