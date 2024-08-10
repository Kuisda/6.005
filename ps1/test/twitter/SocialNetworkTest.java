/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it @Alobot to talk about @Risvs so much in bitdiddle@mit.edu?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes @Alobot", d2);
    private static final Tweet tweet3 = new Tweet(3,"kvmm","@Bobbo hello",d2);
    private static final Tweet tweet4 = new Tweet(3,"lvaaS","@Bobbo hello",d2);
    private static final Tweet tweet5 = new Tweet(1,"alyssa","@Bobbo hello",d1);
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    /*testGuessFollowsGraph()
    * first consider the tweets is empty or not(upper test has already checked this)
    * then use a case with plural user mentioned to check if the function can handle multiple users
    *I don't have to check if the user we extract is correct because I will use getMentionedUsers() which is checked already
    * I have to check if the function can handle multiple tweets and extract the mentioned users correctly
    * */
    @Test
    public void testGuessFollowsGraph() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2));
        assertTrue("excpected true",followsGraph.containsKey("alyssa"));
        assertTrue("excpected true",followsGraph.containsKey("bbitdiddle"));
        assertTrue("excpected true",followsGraph.get("alyssa").contains("risvs"));
        assertTrue("excpected true",followsGraph.get("alyssa").contains("alobot"));
        assertFalse("excpected false",followsGraph.get("alyssa").contains("bbitdiddle"));
        assertTrue("excpected true",followsGraph.get("bbitdiddle").contains("alobot"));
        Map<String, Set<String>> followsGraph1 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2,tweet3,tweet4,tweet5));
        assertTrue("excpected true",followsGraph1.get("alyssa").contains("bobbo"));
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    /**testInfluencers()
     * 1.the input is empty
     * 2.you have multiple users in the followsGraph,get the max
     *
     */
    @Test
    public void testInfluencers() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2,tweet3,tweet4));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("excepected size of 2",2,influencers.size());
        assertTrue("excpected true",influencers.contains("alobot"));
        assertTrue("excpected true",influencers.contains("bobbo"));
        assertFalse("excpected true",influencers.contains("risvs"));

        Map<String, Set<String>> followsGraph1 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2,tweet3,tweet4,tweet5));
        List<String> influencers1 = SocialNetwork.influencers(followsGraph1);
        assertTrue("excepected true",influencers1.contains("bobbo"));
        assertFalse("excepted false",influencers1.contains("alobot"));
        assertEquals("excpected size of 1",1,influencers1.size());

    }


    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
