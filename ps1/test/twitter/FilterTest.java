/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T13:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "I have to go out for a walk", d3);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    /*testwrittenBy()
    * there are situations for tweets:
    * 1. the tweets is empty
    * 2. the tweets is not empty
    * the realtion between the tweets and the user is:
    * 1. there is no tweet written by the user
    * 2. there is one or more tweets written by the user
    * only have to consider above  situations:
    * then I can get cases:
    * 1.tweets is empty and username is freewill
    * 2.tweets have more than one tweet and consist of more than one of the user's tweets
    * 3.tweets have more than one tweet and not consist of the user's tweets
    * */
    @Test
    public void testWrittenBy(){
        List<Tweet> writtenBy1 = Filter.writtenBy(Arrays.asList(), "alyssa");
        assertEquals("except list size of 0",0,writtenBy1.size());
        List<Tweet> writtenBy2 = Filter.writtenBy(Arrays.asList(tweet1, tweet2,tweet3), "alyssa");
        assertEquals("except list size of 2",2,writtenBy2.size());
        assertTrue("expected list to contain tweet",writtenBy2.contains(tweet1));
        assertTrue("expected list to contain tweet",writtenBy2.contains(tweet3));
        List<Tweet> writtenBy3 = Filter.writtenBy(Arrays.asList(tweet1, tweet2,tweet3), "ppp");
        assertEquals("except list size of 0",0,writtenBy3.size());
    }

    @Test
    public void testList(){
        List<Tweet> list1 = Arrays.asList(tweet1,tweet3);
        List<Tweet> list2 = Arrays.asList(tweet1,tweet3);
        assertEquals("..",list1,list2);
        List<Tweet> list3 = Arrays.asList();
        assertEquals("..",0,list3.size());
    }


    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }

    /*testInTimespan()
     * there are situations for tweets:
     * 1. the tweets is empty
     * 2. the tweets is not empty
     * then consider about the relation between timespan and the tweets:
     * 1. the tweets is empty,then we don't need to consider about the timespan
     * 2. the tweets is not empty,but all of the tweert is not in the timespan
     * 3. the tweets is not empty,and there are some of the tweets in the timespan
     * */
    @Test
    public void testInTimespan(){
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");


        List<Tweet> inTimespan1 = Filter.inTimespan(Arrays.asList(), new Timespan(testStart, testEnd));
        assertEquals("except list size of 0",0,inTimespan1.size());
        List<Tweet> inTimespan2 = Filter.inTimespan(Arrays.asList(tweet3), new Timespan(testStart, testEnd));
        assertEquals("except list size of 0",0,inTimespan2.size());
        List<Tweet> inTimespan3 = Filter.inTimespan(Arrays.asList(tweet1, tweet2,tweet3), new Timespan(testStart, testEnd));
        assertEquals("except list size of 2",2,inTimespan3.size());
        assertTrue("expected list to contain tweet",inTimespan3.contains(tweet1));
        assertEquals("expected same order", 0, inTimespan3.indexOf(tweet1));
        assertTrue("expected list to contain tweet",inTimespan3.contains(tweet2));
    }
    /*testContaining()
     * there are situations for tweets:
     * 1. the tweets is empty
     * 2. the tweets is not empty
     *   then consider about the relation between the tweets and the words:
     *   1. the tweets is empty,then we don't need to consider about the words,return empty list
     *   2. the words  is empty,then we can return empty list also
     *   3. both the tweets and the words is not empty,I set a case for three tweets and two words;
    * */
    @Test
    public void testContaining() {
        List <Tweet> containing1 = Filter.containing(Arrays.asList(), Arrays.asList("talk"));
        assertEquals("except list size of 0",0,containing1.size());
        List <Tweet> containing2 = Filter.containing(Arrays.asList(tweet1, tweet2,tweet3), Arrays.asList());
        assertEquals("except list size of 0",0,containing2.size());

        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2,tweet3), Arrays.asList("tAlk","Walk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2,tweet3)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
        assertEquals("expected same order", 2, containing.indexOf(tweet3));
    }


    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
