/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    /* testGetTimespan()
    * all tweets is final so if modified have happened, it will raise an error
    * First:think about the Partition of the inputs:
    * we have to think about the length of the input,like it is empty or has one or two tweets or more.
    *then we have to consider that if the function get correct max timestamp and min timestamp:
    * to make sure that,we can get to situation :all of the input tweets have the same timestamp or different timestamp.
    *now I can get the test cases:
    * 1.empty
    * 2.only one tweet
    * 3.two tweets with the same timestamp
    * 4.two tweets with different timestamp
    * 5.three tweets with one different timestamp and two with the same timestamp
    *
    * */
    /*testGetMentionedUsers()
    *if without username-mention,return null
    *if with username-mention,return the username
    *if other used with '@' that not match with the spec,return null
    *also ,consider the case that username-mention appear in the end or at the beginning of the tweet text
    * */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d4 = Instant.parse("2016-03-17T13:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "cchow", "rivest talk in 1 hour @brother", d3);
    private static final Tweet tweet4 = new Tweet(4, "dferguson", "you can send email to  bitdiddle@mit.edu to ask for help", d4);
    private static final Tweet tweet5 = new Tweet(5, "ejohnson", "@brother rivest talk in 1 hour", d4);
    private static final Tweet tweet6 = new Tweet(6, "fgray", "rivest talk @brother in 1 hour ", d4);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetTimeSpan(){
        //1.empty
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        assertNull("expect null",timespan);
        //2.only one tweet
        Timespan timespan1 = Extract.getTimespan(Arrays.asList(tweet1));
        assertNull("expect null",timespan1);
        //3.two tweets with the same timestamp
        Timespan timespan2 = Extract.getTimespan(Arrays.asList(tweet2, tweet3));
        assertEquals("expect start has same val as same timestamp", d2, timespan2.getStart());
        assertEquals("expect end has same val as same timestamp",d2,timespan2.getEnd());
        //4.two tweets with different timestamp
        Timespan timespan3 = Extract.getTimespan(Arrays.asList(tweet2, tweet4));
        assertEquals("expect start", d2, timespan3.getStart());
        assertEquals("expect end", d4, timespan3.getEnd());
        //5.three tweets with different timestamp
        Timespan timespan4 = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
        assertEquals("expect start", d1, timespan4.getStart());
        assertEquals("expect end", d3, timespan4.getEnd());
        assertEquals("expect end",d2,timespan4.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
