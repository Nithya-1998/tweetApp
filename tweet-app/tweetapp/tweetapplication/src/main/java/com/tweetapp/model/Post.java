package com.tweetapp.model;

public class Post {

	private String tweet;
	private User userDetails;

	public Post() {
		super();
	}

	public Post(String tweet, User userDetails) {
		super();
		this.tweet = tweet;
		this.userDetails = userDetails;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public User getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(User userDetails) {
		this.userDetails = userDetails;
	}

	@Override
	public String toString() {
		return "[tweet=" + tweet + ", userDetails=" + userDetails + "]";
	}

}