package com.tweetapp.service;

import java.util.List;

import com.tweetapp.dao.UserDaoSqlImpl;
import com.tweetapp.model.User;
import com.tweetapp.util.DateUtil;

public class UserService {

	UserDaoSqlImpl repo = new UserDaoSqlImpl();

	public boolean validateUser(String email, String password) throws Exception {
		User existedUser = repo.getUser(email);
		if (existedUser != null) {
			if (existedUser.getPassword().equals(password)) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new Exception();
		}
	}

	public boolean validateEmail(String email) throws Exception {
		User existedUser = repo.getUser(email);
		if (existedUser != null) {
			if (existedUser.getEmail().equals(email)) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new Exception();
		}
	}

	public void saveUser(String firstName, String lastName, String gender, String dob, String email, String pwd,
			Boolean isActive) throws Exception {
		User newUser = new User(firstName, lastName, gender, DateUtil.convertToDate(dob), email, pwd, false);
		repo.saveUser(newUser);
	}

	public void resetPassword(String email, String password) throws Exception {
		repo.resetPassword(email, password);
	}

	public void setStatus(Boolean isActive, String email) throws Exception {
		repo.setStatus(isActive, email);
	}

	public List<String> getAllUsers() {
		return repo.getAllUsers();
	}
}