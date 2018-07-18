package com.softmill.springboot.mvcspringboot.event;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.softmill.springboot.mvcspringboot.model.Tweet;

public class RefreshTweetsEvent {

	private final Iterable<Tweet> tweets;

	public RefreshTweetsEvent(Iterable<Tweet> tweets) {
		this.tweets = tweets;
	}
	
    public List<Tweet> getList() {
    	 return StreamSupport.
    			 stream(tweets.spliterator(), false).
    			 collect(Collectors.toList());
    }
	
}
