/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteEdgesGraph.toString()
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
    
    // TODO tests for operations of Edge

    @Test
    public void testEdge(){
        Edge<String> e1 = new Edge<String>("a", "b",1);
        int weight = e1.getWeight();
        weight = 2;
        assertEquals(1, e1.getWeight());
    }

    @Test
    public void testListRemove(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
//        list.remove("b");
        for(String s : list){//remove element while iterating the list will cause error
            if(s.equals("b")){
                System.out.println("find b");
                list.remove(s);
                continue;
            }
            System.out.println(s);
        }
        for(String s : list){
            System.out.println(s);
        }
    }

    @Test
    public void testReverseRemove(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        for(int i = list.size()-1; i>=0; i--){
            if(list.get(i).equals("c")){
                list.remove(i);
                continue;
            }
            System.out.println(list.get(i));
        }
    }
}
