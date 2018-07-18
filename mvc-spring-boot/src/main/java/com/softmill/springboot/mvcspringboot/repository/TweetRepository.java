package com.softmill.springboot.mvcspringboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softmill.springboot.mvcspringboot.model.Tweet;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, String> {

}