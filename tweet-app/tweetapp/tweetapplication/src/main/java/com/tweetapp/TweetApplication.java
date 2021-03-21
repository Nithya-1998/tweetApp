package com.tweetapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.tweetapp.constants.BatchConstants;
import com.tweetapp.model.Post;
import com.tweetapp.service.TweetService;
import com.tweetapp.service.UserService;
import com.tweetapp.util.EmailUtil;
import com.tweetapp.util.PasswordUtil;

public class TweetApplication {
	public static void main(String[] args) {
		try {
			UserService userService = new UserService();
			TweetService tweetService = new TweetService();
			boolean islogin = false;
			boolean isexit = true;
			String username = null;
			String email = null;

			while (isexit) {
				System.out.println("\nSelect Choice :  ");
				if (!islogin) {
					System.out.println("1. Signin");
					System.out.println("2. Register");
					System.out.println("3. Forgot Password");
				} else {
					System.out.println("4. Post a Tweet");
					System.out.println("5. View My Tweets");
					System.out.println("6. View All Tweets");
					System.out.println("7. View All Users");
					System.out.println("8. Reset Password");
					System.out.println("9. Signout");
					System.out.println("Press any other key to close the TweetApp.");
				}
				BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
				String choice;
				try {
					choice = sc.readLine().trim();
					switch (choice) {
					case "1": {
						if (islogin) {
							System.err.println("User Already SignedIn...");
						} else if (!islogin) {
							System.out.println("Enter Email Id");
							email = sc.readLine().trim();
							if (EmailUtil.emailValidate(email)) {
								System.out.println("Enter password");
								String password = sc.readLine();
								try {
									if (userService.validateUser(email, password)) {
										username = email;
										islogin = true;
										userService.setStatus(islogin, email);
										System.out.println("SignedIn successfully");
									} else {
										System.err.println("Incorrect credentials");
									}
								} catch (Exception e) {
									System.err.println("User not found ! Kindly Register before Signin");
								}
							} else {
								System.err.println("Invalid Email Id");
							}
						}
						break;
					}

					case "2": {
						if (!islogin) {
							System.out.println("New User Registration form...");
							System.out.println("Enter firstname");
							String fname = sc.readLine().trim();
							System.out.println("Enter lastname");
							String lname = sc.readLine().trim();
							System.out.println("Enter gender (Male/Female)");
							String gender = sc.readLine().trim();
							System.out.println("Enter DateOfBirth " + BatchConstants.DOB_FORMAT);
							String dob = sc.readLine().trim();
							System.out.println("Enter email");
							email = sc.readLine().trim();
							System.out.println(
									"Enter password (Min 8, Atleast 1 Uppercase, 1 Lowercase, 1 Special Character, 1 Number)");
							String pwd = sc.readLine();
							if (!EmailUtil.emailValidate(email)) {
								System.err.println("Check Mail Format");
								break;
							}
							if (!PasswordUtil.pwdValidate(pwd)) {
								System.err.println("Check Password Format");
								break;
							}
							try {
								userService.saveUser(fname, lname, gender, dob, email, pwd, false);
							} catch (Exception e) {
								System.err.println("Please Check User Details...");
							}
						} else if (islogin) {
							System.err.println("User Already SignedIn...");
						}
						break;
					}

					case "3": {
						if (!islogin) {
							System.out.println("Enter EmailID to change the password");
							email = sc.readLine().trim();
							try {
								if (userService.validateEmail(email)) {
									System.out.println("Email is validated");
									System.out.println(
											"Enter password (Min 8, Atleast 1 Uppercase, 1 Lowercase, 1 Special Character, 1 Number)");
									String password = sc.readLine();
									if (!PasswordUtil.pwdValidate(password)) {
										System.err.println("Please Check Password Format...!");
										break;
									}
									userService.resetPassword(email, password);
									System.out.println("Password Updated...!");

								}
							} catch (Exception e) {
								System.err.println("Enter a valid email..!");
							}
						} else if (islogin) {
							System.err.println("Signout to change password..!");
						}
						break;
					}
					case "4": {
						if (!islogin) {
							System.err.println("Please Signin first to post a tweet");
						} else if (islogin) {
							System.out.println("Enter tweet message...!");
							try {
								String tweetMessage = sc.readLine().trim();
								if (tweetMessage != "" && tweetMessage != null && !tweetMessage.isEmpty()
										&& !tweetMessage.isBlank()) {
									tweetService.saveTweet(tweetMessage, username);
									System.out.println("Message saved successfully.....!");
								}else {
									System.err.println("Message should not be empty...!");
									break;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						break;
					}

					case "5": {
						if (islogin) {
							try {
								List<Post> userTweets = tweetService.getUsertweets(username);
								if (userTweets != null && !userTweets.isEmpty()) {
									System.out.println("Your tweets are : ");
									userTweets.forEach(tweet -> {
										System.out.println(tweet.getTweet());
									});
								}
							} catch (Exception e) {
								System.err.println("No tweets found under " + username + "....!");
							}
						}
						break;
					}

					case "6": {
						if (islogin) {
							List<Post> userTweets = tweetService.getAlltweets();
							try {
								if(userTweets != null && !userTweets.isEmpty()) {
									System.out.println("All Tweets : ");
									userTweets.forEach(tweet -> {
										System.out.println(tweet.getTweet());
									});
								}
							} catch (Exception e) {
								System.err.println("No tweets yet...!");
							}
						}
						break;
					}

					case "7": {
						if (islogin) {
							List<String> userList = userService.getAllUsers();
							if (userList != null && !userList.isEmpty()) {
								System.out.println("Users List: ");
								userList.forEach(name -> {
									System.out.println(name);
								});
							}
						}
						break;
					}

					case "8": {
						System.out.println(
								"Enter the password to reset (Min 8, Atleast 1 Uppercase, 1 Lowercase, 1 Special Character, 1 Number)");
						String password = sc.readLine();
						if (!PasswordUtil.pwdValidate(password)) {
							System.err.println("Please Check Password Format");
							break;
						}
						try {
							if (!userService.validateUser(username, password)) {
								userService.resetPassword(username, password);
								System.out.println("Password Updated");
							} else {
								System.err.println("New Password cannot be same as old password");
							}
						} catch (Exception e) {
							System.err.println("Exception!");
						}
						break;
					}

					case "9": {
						if (!islogin) {
							System.err.println("Please Signin first...");
						} else if (islogin) {
							username = email;
							islogin = false;
							userService.setStatus(islogin, username);
							System.out.println("SignedOut successfully");
						}
						break;
					}

					default: {
						isexit = false;
						sc.close();
						break;
					}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.err.println("Connection Terminated");
		}
	}
}
