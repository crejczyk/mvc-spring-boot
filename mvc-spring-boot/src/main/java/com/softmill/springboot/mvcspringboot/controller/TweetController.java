package com.softmill.springboot.mvcspringboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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

import com.softmill.springboot.mvcspringboot.event.RefreshTweetsEvent;
import com.softmill.springboot.mvcspringboot.model.Tweet;
import com.softmill.springboot.mvcspringboot.repository.TweetRepository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
public class TweetController {

private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	 
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


	@GetMapping(value = "/stream/tweets")
	public SseEmitter streamAllTweets(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store");

		SseEmitter emitter = new SseEmitter();
		// SseEmitter emitter = new SseEmitter(180_000L);

		this.emitters.add(emitter);

		emitter.onCompletion(() -> this.emitters.remove(emitter));
		emitter.onTimeout(() -> this.emitters.remove(emitter));

		return emitter;
	}
	
	@EventListener
	public void onRefreshTweets(RefreshTweetsEvent refreshTweetsEvent) {
	    List<SseEmitter> deadEmitters = new ArrayList<>();
	    this.emitters.forEach(emitter -> {
	      try {
	        emitter.send(refreshTweetsEvent.getList());
	      }
	      catch (Exception e) {
	        deadEmitters.add(emitter);
	      }
	    });

	    this.emitters.removeAll(deadEmitters);
	  }
}