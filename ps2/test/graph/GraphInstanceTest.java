/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   emptyInstance()
    //     - returns a new empty graph of the particular implementation being tested
    //   testInitialVerticesAddVertex()
    //     - add a vertex to an empty graph
    //     - if the vertex is added,the graph should have one vertex
    //   int set(L source, L target, int weight)
    //     set has 2 behaviors:
    //       1. add new vertex if weight is non-zero
    //          if vertex(source or target) is not in graph ,add it
    //          if vertex(source or target) is in graph, update its weight
    //       2. remove vertex if weight is zero
    //          remove edge if it exists
    //    boolean remove(L vertex)
    //     remove vertex and also remove all its edges
    //    Map<L, Integer> sources(L target)
    //     pay attention that the graph is directed
    //     return all sources directed to  target vertex and their weights
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }



    // TODO other tests for instance methods of Graph
    @Test
    public void testInitialVerticesAddVertex(){
        Graph<String> g = emptyInstance();
        g.add("A");
        assertEquals("excpect a graph with one vertex",1, g.vertices().size());
        assertTrue("excpect a graph with vertex A",g.vertices().contains("A"));
    }

    @Test
    public void testVertexAdded_And_WeightUpdateThoughSet(){
        Graph<String> g = emptyInstance();
        //set will return previous weight,if no such edge,return 0
        assertEquals("excpect edge A-B with weight 1",0,g.set("A","B",1));
        assertTrue("excepct a graph with vertex A",g.vertices().contains("A"));
        assertTrue("excepct a graph with vertex B",g.vertices().contains("B"));
        assertEquals("excpect a graph with two vertex",2, g.vertices().size());
        assertEquals("excpect edge A-B with weight 2 after update",1,g.set("A","B",2));
    }

    @Test
    public  void testRemoveEdgeThoughSet(){
        Graph<String> g = emptyInstance();
        g.set("A","B",1);
        assertEquals("excpect 1",1,g.set("A","B",0));
        assertEquals("excepct 0",0,g.set("A","B",0));
    }

    @Test
    public void testRemove(){
        Graph<String> g = emptyInstance();
        g.set("A","B",1);
        g.set("A","C",2);
        g.set("B","C",3);
        assertFalse("except false",g.remove("D"));
        assertTrue("except true",g.remove("A"));
        assertEquals("there should be 2 vertices ",2,g.vertices().size());
        assertFalse("vertex A should not exist",g.vertices().contains("A"));
        assertTrue("vertex B should exist",g.vertices().contains("B"));
        assertTrue("vertex C should exist",g.vertices().contains("C"));

        System.out.println(g);
        assertEquals("edge B-C should exist ",3,g.set("B","C",1));
        assertEquals("edge A-C should not exist",0,g.set("A","C",0));
        assertEquals("edge A-B should not exist",0,g.set("A","C",0));
    }

    @Test
    public void testSources(){
        Graph<String> g = emptyInstance();
        g.set("A","B",1);
        g.set("A","C",2);
        g.set("B","C",3);
        Map<String,Integer> mp = g.sources("B");
        assertEquals("expect 1 sources",1,mp.size());
        assertTrue("expect source A",mp.containsKey("A"));
        assertFalse("C is not a source" ,mp.containsKey("C"));
        assertEquals("expect weight 2",1,mp.get("A").intValue());
    }

    @Test
    public void testTargets(){
        Graph<String> g = emptyInstance();
        g.set("A","B",1);
        g.set("A","C",2);
        g.set("B","C",3);
        Map<String,Integer> mp = g.targets("B");
        assertEquals("expect 1 targets",1,mp.size());
        assertTrue("expect target C",mp.containsKey("C"));
        assertFalse("A is not a target" ,mp.containsKey("A"));
        assertEquals("expect weight 3",3,mp.get("C").intValue());
    }
}
