package com.tweetapp.service;

import java.util.List;

import com.tweetapp.dao.TweetDaoSqlImpl;
import com.tweetapp.model.Post;

public class TweetService {

	TweetDaoSqlImpl repo = new TweetDaoSqlImpl();

	public void saveTweet(String message, String username) throws Exception {
		repo.savePost(message, username);
	}

	public List<Post> getUsertweets(String username) throws Exception {
		return repo.getPostsByUser(username);
	}

	public List<Post> getAlltweets() {
		return repo.getPosts();
	}

}