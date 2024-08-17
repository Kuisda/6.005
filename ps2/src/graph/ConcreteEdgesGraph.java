/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   public boolean add(String vertex)
    //   public int set(L source, L target, int weight);
    //   public boolean remove(L vertex);
    //   public Set<L> vertices();
    //   public Map<L, Integer> sources(L target);
    //   public Map<L, Integer> targets(L source);
    // Representation invariant:
    //   private final Set<String> vertices = new HashSet<>();
    //   private final List<Edge> edges = new ArrayList<>();
    // Safety from rep exposure:
    //   all field is private and final
    //   in function vertices(), return a new set to avoid modification of the original set
    
    //constructor
    /**
     * Create an empty graph
     * as vertices and edges are already initialized,constructor don't have to do anything
     */
    public ConcreteEdgesGraph(){}
    // TODO checkRep
    
    @Override public boolean add(String vertex) {
        if(this.vertices.contains(vertex)){
            return false;
        }else{
            this.vertices.add(vertex);
            return true;
        }
    }
    
    @Override public int set(String source, String target, int weight) {
        if(weight < 0|| source.equals(target))return -1;
        else if(weight == 0){
            for(Edge edge : this.edges){
                //edge will exist only once, so the count of remove operation will at most be 1
                //so remove at iterating will not cause problem
                int oldWeight = edge.getWeight();
                if(edge.findEdge(source,target)){
                    edges.remove(edge);
                    return oldWeight;
                }
            }
            return 0;
        }
        else{
            for(Edge edge : this.edges){
                if(edge.findEdge(source,target)){
                    int oldWeight = edge.getWeight();
                    edges.remove(edge);
                    edges.add(new Edge(source,target,weight));
                    return oldWeight;
                }
            }
            vertices.add(source);
            vertices.add(target);
            edges.add(new Edge(source,target,weight));
            return 0;
        }

    }
    
    @Override public boolean remove(String vertex) {
        boolean findVertex = false;
        //reverse_remove will not cause index problem while removing in iteration
        for(int i=edges.size()-1;i>=0;i--){
            if(edges.get(i).getSource().equals(vertex) || edges.get(i).getTarget().equals(vertex)){
                findVertex = true;
                edges.remove(i);
            }
        }
        if(findVertex){
            vertices.remove(vertex);
        }
        return findVertex;
    }
    
    @Override public Set<String> vertices() {
        return new HashSet<>(vertices);
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String,Integer> mp = new HashMap<>();
        for(Edge edge:this.edges){
            if(edge.getTarget().equals(target)){
                mp.put(edge.getSource(),edge.getWeight());
            }
        }
        return mp;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String,Integer> mp = new HashMap<>();
        for(Edge edge:this.edges){
            if(edge.getSource().equals(source)) {
                mp.put(edge.getTarget(), edge.getWeight());
            }
        }
        return mp;
    }
    
    //toString()
    @Override
    public String toString(){
        return "vertices: " + vertices + "\nedges: " + edges;
    }
    
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge {
    //fields
    private final String source;
    private final String target;
    private final int weight;

    // Abstraction function:
    //    represents an edge in a graph,with source, target, and weight
    //     check if edge is equal thought all fields are equal
    //     find edge by source,target
    // Representation invariant:
    //   source is the source of an edge, String,not empty
    //   target is the target of an edge, String,not empty
    //   weight is the weight of an edge. int, >=0
    // Safety from rep exposure:
    //   all the fields are private and final
    //   no mutator to change the fields
    //   String is immutable and if change the value of int , error will occur

    /**constructor: build an edge with source, target, and weight
     *
     *Edge would be created in ConcreteEdgesGraph set() method
     *
     */
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }
    // TODO checkRep


    //methods
    public String getSource() {
        return source;
    }
    public String getTarget() {
        return target;
    }
    public int getWeight() {
        return weight;
    }

    public boolean findEdge(String source,String target){
        return this.source.equals(source) && this.target.equals(target);
    }

    //toString()
    @Override
    public String toString(){
        return source + "->" + target + ":" + weight;
    }

}
