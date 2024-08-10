/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.*;

import static twitter.Extract.getMentionedUsers;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        if(tweets == null || tweets.isEmpty()){
            return new HashMap<String, Set<String>>();
        }else{
            Map<String,Set<String>> socialMap = new HashMap<String, Set<String>>();
            Map<String, List<Tweet>> mp = GroupTweetsByAuthor(tweets);
            for(String author : mp.keySet()){
                Set<String> mentionedUsers = getMentionedUsers(mp.get(author));
                if(!mentionedUsers.isEmpty()){
                    socialMap.put(author,mentionedUsers);
                }
            }
            return socialMap;
        }
    }
    /**
     * GroupTweetsByAuthor
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     *
     * @return a map of author to list of tweets.
     * the author is lowcased.
    * */
    public static Map<String, List<Tweet>> GroupTweetsByAuthor(List<Tweet> tweets) {
        Map<String, List<Tweet>> mp = new HashMap<String, List<Tweet>>();
        for(Tweet tweet : tweets){
            if(!mp.containsKey(tweet.getAuthor())){
                mp.put(tweet.getAuthor().toLowerCase(), new ArrayList<Tweet>());
            }
            mp.get(tweet.getAuthor().toLowerCase()).add(tweet);
        }
        return mp;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Set<String> MaxIinfluenceSet = new HashSet<String>();
        Map<String,Set<String>> befollowed = new HashMap<String, Set<String>>();
        for(String user : followsGraph.keySet()){
            for(String follow : followsGraph.get(user)){
                if(!befollowed.containsKey(follow)){
                    befollowed.put(follow, new HashSet<String>());
                }
                befollowed.get(follow).add(user);
            }
        }
        Map<String,Integer> Counter = new HashMap<String, Integer>();
        for(String user : befollowed.keySet()){
            Counter.put(user, befollowed.get(user).size());
        }

        int maxCount = 0;
        for(String user : Counter.keySet()){
            if(Counter.get(user) > maxCount){
                maxCount = Counter.get(user);
                MaxIinfluenceSet.clear();
                MaxIinfluenceSet.add(user);
            }else if(Counter.get(user) == maxCount){
                MaxIinfluenceSet.add(user);
            }
        }
        return new ArrayList<>(MaxIinfluenceSet);
    }

}
