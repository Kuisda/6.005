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
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   use private and final fields to ensure that the rep is not exposed.
    //   Vertex is Safety from rep exposure
    //   use copy of vertices to avoid exposing the rep of ConcreteVerticesGraph.
    
    //constructor
    public ConcreteVerticesGraph(){}
    // TODO checkRep
    
    @Override public boolean add(String vertex) {
        for(Vertex v:vertices){
            if(v.getName().equals(vertex)){
                return false;
            }
        }
        vertices.add(new Vertex(vertex));
        return true;
    }
    
    @Override public int set(String source, String target, int weight) {
        if(weight < 0 || source.equals(target))return -1;
        else if(weight == 0){
            int returnWeight = 0;
            for(Vertex v:vertices){
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
            Vertex v1 = null,v2 = null;
            for(Vertex v:vertices){
                if(v.getName().equals(source)){
                    v1 = v;
                }
                if(v.getName().equals(target)){
                    v2 = v;
                }
            }
            if(v1!= null && v2!= null){
                returnWeight = v1.addEdgeFromThisVertex(target,weight);
                returnWeight = v2.addEdgeToThisVertex(source,weight);
                return returnWeight;
            } else if(v1 == null && v2 != null){
                v2.addEdgeToThisVertex(source,weight);
                vertices.add(new Vertex(source));
                vertices.get(vertices.size()-1).addEdgeFromThisVertex(target,weight);
            }else if(v1 !=null && v2 == null){
                v1.addEdgeFromThisVertex(target,weight);
                vertices.add(new Vertex(target));
                vertices.get(vertices.size()-1).addEdgeToThisVertex(source,weight);
            }else{
                vertices.add(new Vertex(source));
                vertices.get(vertices.size()-1).addEdgeFromThisVertex(target,weight);
                vertices.add(new Vertex(target));
                vertices.get(vertices.size()-1).addEdgeToThisVertex(source,weight);
            }
            return returnWeight;
        }
    }
    
    @Override public boolean remove(String vertex) {
        boolean returnValue = false;
        for(Vertex v : vertices){
            if(v.getName().equals(vertex)){
                returnValue = true;
                vertices.remove(v);
                break;
            }
        }
        if(!returnValue) return false;
        else{
            for(Vertex v:vertices){
                v.deleteVertex(vertex);
            }
        }
        return true;
    }
    
    @Override public Set<String> vertices() {
        return vertices.stream().map(Vertex::getName).collect(Collectors.toSet());
    }
    
    @Override public Map<String, Integer> sources(String target) {
        for(Vertex v:vertices){
            if(v.getName().equals(target)){
                return new HashMap<>(v.getTarget());
            }
        }
        return new HashMap<>();
    }
    
    @Override public Map<String, Integer> targets(String source) {
       for(Vertex v:vertices){
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
        for(Vertex v: vertices){
            sb.append(v.getName()).append(" ");
        }
        sb.append("\nedges:\n");
        for(Vertex v: vertices){
            for(String k:v.getSource().keySet()){
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
class Vertex {
    
    // TODO fields
    private final String name;
    private final Map<String,Integer> sourceMap;
    private final Map<String,Integer> targetMap;
    
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
    public Vertex(String name){
        this.name = name;
        this.sourceMap = new HashMap<>();
        this.targetMap = new HashMap<>();
    }
    // TODO checkRep
    
    // TODO methods
    public String getName(){
        return name;
    }

    /**get source Map which this vertex is as the source of the edge
     *
     * @return a copy of the edges map,to avoid exposing the rep of ConcreteVerticesGraph.
     *
     */
    public Map<String,Integer> getSource(){
        return new HashMap<>(sourceMap);
    }
    /**get target Map which this vertex is as the target of the edge
     *
     * @return a copy of the edges map,to avoid exposing the rep of ConcreteVerticesGraph.
     */
    public Map<String,Integer> getTarget(){
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
    public int addEdgeFromThisVertex(String target,int weight){
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
    public int addEdgeToThisVertex(String source,int weight){
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
    public int deleteEdgeFromThisVertex(String target){
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
    public int deleteEdgeToThisVertex(String source){
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
    public void deleteVertex(String vertex){
        sourceMap.remove(vertex);
        targetMap.remove(vertex);
    }
    // TODO toString()
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vertex name:").append(this.name);
        sb.append("\nFromThisVertex:\n");
        for(String target:sourceMap.keySet()){
            sb.append(target).append(":").append(sourceMap.get(target)).append("\n");
        }
        sb.append("\nToThisVertex:\n");
        for(String source:targetMap.keySet()){
            sb.append(source).append(":").append(targetMap.get(source)).append("\n");
        }
        return sb.toString();
    }
    
}
