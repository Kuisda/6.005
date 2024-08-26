/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   represent a poet graph...
    // Representation invariant:
    //   empty:an empty graph
    // Safety from rep exposure:
    //   the graph is private and final; and the graph structure is not safety from pre exposure
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(corpus));
        String line;
        while((line = br.readLine())!=null){
            String[] words = line.split("[\\s]+");
//            System.out.println(Arrays.toString(words));
            for(int i=1;i<words.length;i++){
                if(graph.targets(wordFormat(words[i-1])).get(wordFormat(words[i]))==null){
                    graph.set(wordFormat(words[i-1]),wordFormat(words[i]),1);
                }else{
                    graph.set(wordFormat(words[i-1]), wordFormat(words[i]), graph.targets(wordFormat(words[i-1])).get(wordFormat(words[i]))+1);
                }
            }
        }
//        System.out.println(graph.targets("this"));
//        System.out.println(graph.sources("be"));
    }
    
    // TODO checkRep
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] words = input.split("[\\s]+");
        //this split may contain the punctuation,like "hello ,world",then will get [hello, ,world]
        //by wordFormat,we will get correct format to match the word in graph
        List<String> wordsList = new ArrayList<>( Arrays.asList(words));
        int bias = 0;
        for(int i=1;i<words.length;i++){
            for(String k:graph.targets(wordFormat(words[i-1])).keySet()){
                if(graph.sources(wordFormat(words[i])).get(k)!=null){
//                    System.out.println(wordsList);
//                    System.out.println("k:"+k);
//                    System.out.println("i:"+i);
                    wordsList.add(i+bias,k);
                    bias+=1;
                    break;
                }
            }
        }
        return String.join(" ", wordsList);
    }

    /**
     * format the word which will put in the graph
    *  format it to lower case and remove the punctuation at the beginning and end of the word
     * @param input the word to be formatted
     * @return the formatted word
    * */
    public String wordFormat(String input){
        return input.replaceAll("^[\\p{Punct}]+|[\\p{Punct}]+$", "").toLowerCase();
    }
    
    // TODO toString()
    
}
