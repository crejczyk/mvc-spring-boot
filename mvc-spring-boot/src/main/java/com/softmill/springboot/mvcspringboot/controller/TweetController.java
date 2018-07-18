package com.softmill.springboot.mvcspringboot.controller;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.softmill.springboot.mvcspringboot.model.Tweet;
import com.softmill.springboot.mvcspringboot.repository.TweetRepository;


@RestController
public class TweetController {

	@Autowired
	private TweetRepository tweetRepository;

	@GetMapping("/tweets")
	public Iterable<Tweet> getAllTweets() {
		return tweetRepository.findAll();
	}

	@PostMapping("/tweets")
	public Tweet createTweets(@Valid @RequestBody Tweet tweet) {
		return tweetRepository.save(tweet);
	}
	
	@GetMapping("/tweets/{id}")
	public ResponseEntity<Tweet> getTweetById(@PathVariable(value = "id") String tweetId) {
		Optional<Tweet> existingTweet = tweetRepository.findById(tweetId);
		if (existingTweet.isPresent()) {
			return ResponseEntity.ok(existingTweet.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/tweets/{id}")
	public ResponseEntity<Tweet> updateTweet(@PathVariable(value = "id") String tweetId,
			@Valid @RequestBody Tweet tweet) {
		Optional<Tweet> existingTweet = tweetRepository.findById(tweetId);
		if (existingTweet.isPresent()) {
			Tweet tweetDB = existingTweet.get();
			tweetDB.setText(tweet.getText());
			tweetRepository.save(tweetDB);
			return new ResponseEntity<>(tweetDB, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/tweets/{id}")
	public ResponseEntity<Void> deleteTweet(@PathVariable(value = "id") String tweetId) {
		return tweetRepository.findById(tweetId)
			.map(temp -> {
			tweetRepository.delete(temp);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}


/*	@GetMapping(value = "/stream/tweets", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Tweet streamAllTweets() {
		return tweetRepository.findAll().delayElements(Duration.ofMillis(100));
	}*/
}