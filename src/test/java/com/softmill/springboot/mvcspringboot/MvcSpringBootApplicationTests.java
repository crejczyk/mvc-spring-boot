package com.softmill.springboot.mvcspringboot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.softmill.springboot.mvcspringboot.model.Tweet;
import com.softmill.springboot.mvcspringboot.repository.TweetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MvcSpringBootApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	TweetRepository tweetRepository;
	
	private String getUrl() {
		return new StringBuilder("http://localhost:").append(port).toString();
	}

	@Test
	public void testGetAllTweets() {
		assertThat(this.restTemplate.getForObject(getUrl()+ "/tweets",
				new ArrayList<Tweet>().getClass())).hasSameClassAs(new ArrayList<Tweet>());
	}
	
	@Test
	public void testDeleteTweet() throws Exception {
		Tweet tweet = tweetRepository.save(new Tweet("To be deleted"));
		this.restTemplate.delete(getUrl()+"/tweets/{id}",tweet.getId());
	}

    @Test
    public void testGetSingleTweet() {
        Tweet tweet = tweetRepository.save(new Tweet("Hello !"));
		assertThat(this.restTemplate.getForObject(getUrl()+ "/tweets/{id}",
				Tweet.class,tweet.getId())).hasSameHashCodeAs(tweet);
    }
}
