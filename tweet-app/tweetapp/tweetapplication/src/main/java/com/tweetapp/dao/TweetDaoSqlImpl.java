package com.tweetapp.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tweetapp.constants.BatchConstants;
import com.tweetapp.model.Post;
import com.tweetapp.util.ConnectionHandler;

public class TweetDaoSqlImpl {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public List<Post> getPosts() {
		connection = ConnectionHandler.getConnection();
		List<Post> postList = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(BatchConstants.GET_ALL_TWEETS);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					String message = resultSet.getString("message");
					Post post = new Post();
					post.setTweet(message);
					postList.add(post);
				} while (resultSet.next());
			} else {
				System.err.println("No posts found");
			}
			return postList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closedb();
			} catch (SQLException sqlException) {
				throw new RuntimeException("Connection is not closed properly");
			}
		}
		return postList;
	}

	public List<Post> getPostsByUser(String username) throws Exception {
		connection = ConnectionHandler.getConnection();
		List<Post> posts = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(BatchConstants.GET_USER_TWEETS);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					String message = resultSet.getString("message");
					if (message != null) {
						Post post = new Post();
						post.setTweet(message);
						posts.add(post);
					}
				} while (resultSet.next());
			} else {
				throw new Exception();
			}
			return posts;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closedb();
			} catch (SQLException sqlException) {
				throw new RuntimeException("Connection is not closed properly");
			}
		}
		return posts;
	}

	public void savePost(String tweetMessage, String username) throws Exception {
		connection = ConnectionHandler.getConnection();
		try {
			preparedStatement = connection.prepareStatement(BatchConstants.SAVE_TWEET);
			preparedStatement.setString(1, tweetMessage);
			preparedStatement.setString(2, username);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				closedb();
			} catch (SQLException sqlException) {
				throw new RuntimeException("Connection is not closed properly");
			}
		}

	}

	public void closedb() throws SQLException {

		if (resultSet != null) {
			resultSet.close();
		}
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
}
