/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if(tweets.isEmpty()||tweets.size()==1) return  null;
        Instant begin = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();
        for(Tweet tweet:tweets){
            if(tweet.getTimestamp().isBefore(begin)) begin = tweet.getTimestamp();
            if(tweet.getTimestamp().isAfter(end)) end = tweet.getTimestamp();
        }
        return new Timespan(begin, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        if(tweets == null || tweets.isEmpty()){
            return new HashSet<>() ;
        }else{
            Set<String> mentionedUsers = new HashSet<>();
            for(Tweet tweet:tweets){
                String text = tweet.getText();
                List<String> atGroups = getAtMentions(text);
                for(String atGroup:atGroups) {
                    boolean containSpecial = false;
                    char[] SpecialChars = new char[]{'.', '*', '/', '+', '-'};
                    for(char c:SpecialChars){
                        if(atGroup.contains(String.valueOf(c))){
                            containSpecial = true;
                            break;
                        }
                    }
                    if(!containSpecial){
                        mentionedUsers.add(atGroup.substring(1).toLowerCase().trim());//remove the end space is very important
                    }
                }
            }
            return mentionedUsers;
        }
    }

    private static List<String> getAtMentions(String text){
        Pattern pattern  = Pattern.compile("@[a-zA-Z0-9]+.");
        Matcher matcher = pattern.matcher(text);
        List<String> atGroups = new ArrayList<>();
        while(matcher.find()){
            atGroups.add(matcher.group(0));
        }
        return atGroups;
    }

}
