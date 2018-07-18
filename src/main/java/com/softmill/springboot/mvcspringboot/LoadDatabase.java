package com.softmill.springboot.mvcspringboot;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.softmill.springboot.mvcspringboot.model.Tweet;
import com.softmill.springboot.mvcspringboot.repository.TweetRepository;

@Configuration
public class LoadDatabase {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoadDatabase.class);

	private final TweetRepository tweetRepository;

	public LoadDatabase(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}

	@EventListener(value = ContextRefreshedEvent.class)
	void init() {
		initTweets();
	}

	private void initTweets() {
		log.info("Start data initialization...");
		this.tweetRepository.deleteAll();

		ArrayList<String> tweets = new ArrayList<String>(Arrays.asList("Tweet one", "Tweet two"));
		for (String tweet : tweets) {
			tweetRepository.save(new Tweet(tweet));
		}
		log.info("done initialization...");
	}
}