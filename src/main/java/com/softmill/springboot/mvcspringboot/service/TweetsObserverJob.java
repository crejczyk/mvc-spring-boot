package com.softmill.springboot.mvcspringboot.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.softmill.springboot.mvcspringboot.model.Tweet;
import com.softmill.springboot.mvcspringboot.event.RefreshTweetsEvent;
import com.softmill.springboot.mvcspringboot.repository.TweetRepository;

@Service
public class TweetsObserverJob {

	public final ApplicationEventPublisher eventPublisher;
	public final TweetRepository tweetRepository;

	public TweetsObserverJob(ApplicationEventPublisher eventPublisher, TweetRepository tweetRepository) {
		this.eventPublisher = eventPublisher;
		this.tweetRepository = tweetRepository;
	}

	@Scheduled(fixedRate = 5000)
	public void refresh() {
		Iterable<Tweet> tweets = tweetRepository.findAll();
		this.eventPublisher.publishEvent(new RefreshTweetsEvent(tweets));
	}

}