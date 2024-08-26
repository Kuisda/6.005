/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   represent a directed graph,this graph has a list of vertices.
    // Representation invariant:
    //   vertices represent the vertices of the directed graph.
    //   each vertex has a name,a source map and a target map.
    //   the source map represents the edges from this vertex to other vertices and their weights.
    // Safety from rep exposure:
    //   use private and final fields to ensure that the rep is not exposed.
    //   Vertex is Safety from rep exposure
    //   use copy of vertices to avoid exposing the rep of ConcreteVerticesGraph.
    
    //constructor
    public ConcreteVerticesGraph(){}
    // TODO checkRep
    
    @Override public boolean add(L vertex) {
        for(Vertex<L> v:vertices){
            if(v.getName().equals(vertex)){
                return false;
            }
        }
        vertices.add(new Vertex<L>(vertex));
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        if(weight < 0 || source.equals(target))return -1;
        else if(weight == 0){
            int returnWeight = 0;
            for(Vertex<L> v:vertices){
                if(v.getName().equals(source)){
                    returnWeight = v.deleteEdgeFromThisVertex(target);
                }
                if(v.getName().equals(target)) {
                    returnWeight = v.deleteEdgeToThisVertex(source);
                }
            }
            return returnWeight;
        }
        else{
            int returnWeight = 0;
            Vertex<L> v1 = null,v2 = null;
            for(Vertex<L> v:vertices){
                if(v.getName().equals(source)){
                    v1 = v;
                }
                if(v.getName().equals(target)){
                    v2 = v;
                }
            }
            if(v1!= null && v2!= null){
                v1.addEdgeFromThisVertex(target,weight);
                returnWeight = v2.addEdgeToThisVertex(source,weight);
                return returnWeight;
            } else if(v1 == null && v2 != null){
                v2.addEdgeToThisVertex(source,weight);
                vertices.add(new Vertex<L>(source));
                vertices.get(vertices.size()-1).addEdgeFromThisVertex(target,weight);
            }else if(v1 !=null && v2 == null){
                v1.addEdgeFromThisVertex(target,weight);
                vertices.add(new Vertex<L>(target));
                vertices.get(vertices.size()-1).addEdgeToThisVertex(source,weight);
            }else{
                vertices.add(new Vertex<L>(source));
                vertices.get(vertices.size()-1).addEdgeFromThisVertex(target,weight);
                vertices.add(new Vertex<L>(target));
                vertices.get(vertices.size()-1).addEdgeToThisVertex(source,weight);
            }
            return returnWeight;
        }
    }
    
    @Override public boolean remove(L vertex) {
        boolean returnValue = false;
        for(Vertex<L> v : vertices){
            if(v.getName().equals(vertex)){
                returnValue = true;
                vertices.remove(v);
                break;
            }
        }
        if(!returnValue) return false;
        else{
            for(Vertex<L> v:vertices){
                v.deleteVertex(vertex);
            }
        }
        return true;
    }
    
    @Override public Set<L> vertices() {
        return vertices.stream().map(Vertex::getName).collect(Collectors.toSet());
    }
    
    @Override public Map<L, Integer> sources(L target) {
        for(Vertex<L> v:vertices){
            if(v.getName().equals(target)){
                return new HashMap<>(v.getTarget());
            }
        }
        return new HashMap<>();
    }
    
    @Override public Map<L, Integer> targets(L source) {
       for(Vertex<L> v:vertices){
           if(v.getName().equals(source)){
               return new HashMap<>(v.getSource());
           }
       }
       return new HashMap<>();
    }
    
    // TODO toString()
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("vertices:\n");
        for(Vertex<L> v: vertices){
            sb.append(v.getName()).append(" ");
        }
        sb.append("\nedges:\n");
        for(Vertex<L> v: vertices){
            for(L k:v.getSource().keySet()){
                sb.append(v.getName()).append("-").append(k).append(":").append(v.getSource().get(k));
                sb.append("   ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // TODO fields
    private final L name;
    private final Map<L,Integer> sourceMap;
    private final Map<L,Integer> targetMap;
    
    // Abstraction function:
    //   represent a vertex in the directed graph,this vertex 's two maps as source and target
    // Representation invariant:
    //   name of this vertex,String,not null and not empty
    //   the edges from this vertex to other vertices and their weights
    //   the edges from other vertices to this vertex and their weights
    // Safety from rep exposure:
    //   name,as String type is immutable
    //   edges,as Map type is mutable,but the keys are immutable and the values are Integer type,which is immutable.
    //   use of HashMap to create a copy of edges to avoid exposing the rep of ConcreteVerticesGraph.
    
    // constructor
    public Vertex(L name){
        this.name = name;
        this.sourceMap = new HashMap<>();
        this.targetMap = new HashMap<>();
    }
    // TODO checkRep
    
    // TODO methods
    public L getName(){
        return name;
    }

    /**get source Map which this vertex is as the source of the edge
     *
     * @return a copy of the edges map,to avoid exposing the rep of ConcreteVerticesGraph.
     *
     */
    public Map<L,Integer> getSource(){
        return new HashMap<>(sourceMap);
    }
    /**get target Map which this vertex is as the target of the edge
     *
     * @return a copy of the edges map,to avoid exposing the rep of ConcreteVerticesGraph.
     */
    public Map<L,Integer> getTarget(){
        return new HashMap<>(targetMap);
    }

    /** add or update an edge from this vertex to the target vertex.
     * if the edge already exists,update its weight and return the old weight.
     * if the edge does not exist,add it to the edges map and return 0.
     *
     * @param target the target vertex of the edge
     * @param weight the weight of the edge
     * @return the old weight of the edge,or 0 if the edge is new.
     */
    public int addEdgeFromThisVertex(L target,int weight){
        if(sourceMap.containsKey(target)){
            int oldWeight = sourceMap.get(target);
            sourceMap.put(target,weight);
            return oldWeight;
        }else{
            sourceMap.put(target,weight);
            return 0;
        }
    }

    /**add or update an edge from the source vertex to this vertex.
     * if the edge already exists,update its weight and return the old weight.
     *if the edge does not exist,add it to the edges map and return 0.
     *
     * @param source the source vertex of the edge.
     * @param weight the weight of the edge.
     * @return the old weight of the edge,or 0 if the edge is new.
     */
    public int addEdgeToThisVertex(L source,int weight){
        if(this.targetMap.containsKey(source)){
            int oldWeight = this.targetMap.get(source);
            this.targetMap.put(source,weight);
            return oldWeight;
        }else{
            this.targetMap.put(source,weight);
            return 0;
        }
    }

    /**delete an edge from this vertex to the target vertex.
     *if the edge exist,delete it  and return the weight of the edge.
     *else return 0
     * @param target the target vertex of the edge to be deleted.
     * @return the weight of the edge,or 0 if the edge does not exist.
     */
    public int deleteEdgeFromThisVertex(L target){
        if(sourceMap.containsKey(target)){
            int weight = sourceMap.get(target);
            sourceMap.remove(target);
            return weight;
        }else{
            return 0;
        }
    }

    /**delete an edge from the source vertex to this vertex.
     *if the edge exist,delete it  and return the weight of the edge.
     *else return 0
     *
     * @param source the source vertex of the edge to be deleted.
     * @return the weight of the edge,or 0 if the edge does not exist.
     */
    public int deleteEdgeToThisVertex(L source){
        if(targetMap.containsKey(source)){
            int weight = targetMap.get(source);
            targetMap.remove(source);
            return weight;
        }else{
            return 0;
        }
    }


    /**delete an edge by a vertex
     *
     *
     * cause the function in ConcreteVerticesGraph will delete all the edges contained a vertex,
     * so I don't care about whether the edge is the source or target
     *
     *
     * @param vertex the vertex of the edge to be deleted.
     *
     */
    public void deleteVertex(L vertex){
        sourceMap.remove(vertex);
        targetMap.remove(vertex);
    }
    // TODO toString()
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vertex name:").append(this.name);
        sb.append("\nFromThisVertex:\n");
        for(L target:sourceMap.keySet()){
            sb.append(target).append(":").append(sourceMap.get(target)).append("\n");
        }
        sb.append("\nToThisVertex:\n");
        for(L source:targetMap.keySet()){
            sb.append(source).append(":").append(targetMap.get(source)).append("\n");
        }
        return sb.toString();
    }
    
}
