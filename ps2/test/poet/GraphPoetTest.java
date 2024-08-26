/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import graph.Graph;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   we  build some sample corpus file to mock the input for the constructor of GraphPoet
    //   to test poem, should test if the case-insensitive has been implemented correctly
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests
    // only test while the constructor can handle the input file correctly
    // I will test whether the constructor build graph correctly in poet function test
    @Test
    public void testConstructor() {
        File Sonnet_1 = new File("D:/javaProject/lab-6.005/ps2/test/poet/Sonnet_1.txt");
        try{
            GraphPoet graphpoet = new GraphPoet(Sonnet_1);
        }catch (IOException e){
            System.out.println(e);
        }
    }


    @Test
    public void testSplit(){
        String input = "FROM fairest creatures ,we desire !increase,";
        String[] words = input.split("[\\s]+");
        System.out.println(Arrays.toString(words));
    }

    @Test
    public void testRex(){
        String[] inputs = "Seek to explore new ,and churl synergies this be!".split("[\\s]+");
        System.out.println(Arrays.toString(inputs));
        List<String> result = new ArrayList<>();
        for(String input:inputs){
            result.add(input.replaceAll("^[\\p{Punct}]+|[\\p{Punct}]+$", ""));
        }
        System.out.println(result);
    }

    @Test
    public void testListAdd(){
        List<String> list = new ArrayList<>(Arrays.asList("hello", "world"));
        list.add(0,"hi");
        System.out.println(list);
    }

    @Test
    public void testPoem(){
        File Sonnet_1 = new File("D:/javaProject/lab-6.005/ps2/test/poet/Sonnet_1.txt");
        try{
            GraphPoet graphpoet = new GraphPoet(Sonnet_1);
            String input = "Seek to explore new and churl synergies this be!";
            assertEquals(graphpoet.poem("Seek to explore new and synergies this be!"),"Seek to explore new and synergies this glutton be!");
            assertEquals(graphpoet.poem(input),"Seek to explore new and tender churl synergies this glutton be!");
            assertEquals(graphpoet.poem("Seek to explore new ,and churl synergies this be!"),"Seek to explore new ,and tender churl synergies this glutton be!");
        }catch (IOException e){
            System.out.println(e);
        }
    }

}
