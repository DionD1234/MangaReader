package com.fdmgroup.DionMangaReader.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController
{
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping
	public List<User> firstEndpoint() {
		return userService.findAll();
	}
	@GetMapping("/")
	public User findExactUsername(@RequestParam String username){
		return userService.findByExactUsername(username);
	}
	@GetMapping("/search")
	public List<User> searchByUsername(@RequestParam String username){
		return userService.findPartialMatch(username);
	}
	@GetMapping("/{userId}")
	public User findById(@PathVariable int userId) {
		return userService.findById(userId);
	}
	@PostMapping
	public void createNew(@RequestBody User newUser)
	{
		System.out.println(newUser);
		userService.register(newUser);
	}
	@PutMapping
	public void updateUser(@RequestBody User newUser)
	{
		System.out.println(newUser);
		userService.update(newUser);
	}
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable int userId) {
		userService.deleteById(userId);
	}
}
