package com.fdmgroup.DionMangaReader.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fdmgroup.DionMangaReader.dal.UserRepository;
import com.fdmgroup.DionMangaReader.dal.BookRepository;
import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.Favourite;
import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.exceptions.BadRequestException;
@Service
public class UserService
{
	private UserRepository userRepository;
	private BookRepository bookRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService(
			UserRepository userRepository, 
			BookRepository bookRepository, 
			PasswordEncoder passwordEncoder)
	{
		super();
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
		this.passwordEncoder = passwordEncoder;;
		
	}
	
	public List<User> findAll(){
		return this.userRepository.findAll();
	}
	
	public User findById(int userId) {
		return this.userRepository.findById(userId)
				.orElseThrow(()->new BadRequestException("User with Id: " + userId + "cannot be found."));
	}
	
	public User findByExactUsername(String username) {
		return this.userRepository.findByUsername(username)
				.orElseThrow(()->new BadRequestException("User with username: "+  username + "cannot be found."));
	}
	public boolean existsByUsername(String username, int userId) {
		boolean found = false;
		try {
			User user = userRepository.findByUsername(username)
			.orElseThrow(()->new BadRequestException("User with username: "+  username + "cannot be found."));
			
			if(user.getUserId() != userId)
			{
				found = true;
			}
			else
				found = false;
			
		} catch(BadRequestException e){
			found = false;
		}
		return found;
	}
	public boolean existsByEmail(String email, int userId) {
		boolean found = false;
		try {
			User user = userRepository.findByEmail(email)
			.orElseThrow(()->new BadRequestException("User with email: "+  email + "cannot be found."));
			
			if(user.getUserId() != userId) {
				found = true;
			}
			else
				found = false;
			
		} catch(BadRequestException e){
			found = false;
		}
		return found;
	}

	public void update(User newUser)
	{
		if(userRepository.existsById(newUser.getUserId()))
		{
			User oldUser = findById(newUser.getUserId());
			if (newUser.getPassword() != null && !oldUser.getPassword().contentEquals(newUser.getPassword())) {
				System.out.println("Hashing Password: " + newUser.getPassword());
				oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
			}
			if (newUser.getUsername() != null) {
				if(!existsByUsername(newUser.getUsername(), newUser.getUserId())) {
					oldUser.setUsername(newUser.getUsername());
				}else {
					throw new BadRequestException("Username '" + newUser.getUsername() + "' already exists");
				}
				
				
			}
			if (newUser.getEmail() != null) {
				if(!existsByEmail(newUser.getEmail(), newUser.getUserId())){
					oldUser.setEmail(newUser.getEmail());
					
				} else
				{
					throw new BadRequestException("Email '" + newUser.getEmail() + "' already exists");
				}
				
			}
			if (newUser.getBookmarkedBooks()!= null && !newUser.getBookmarkedBooks().isEmpty()) {
				oldUser.setBookmarkedBooks(newUser.getBookmarkedBooks());
			}
			if (newUser.getFavourites() != null && !newUser.getFavourites().isEmpty()) {
				oldUser.setFavourites(newUser.getFavourites());
			}
			
			userRepository.save(oldUser);
			return;
		}
		
		throw new BadRequestException("Must provide a user id for 'put' mapping");
		
		
		
	}
	public void deleteById(int userId) {
		delete(findById(userId));
	}

	public void delete(User user)
	{
		int userId = user.getUserId();
		for (BookmarkedBook bookmarkBook: user.getBookmarkedBooks()) {
			int bookId = bookmarkBook.getBookId();
			Book book = bookRepository.findById(bookId)
					.orElseThrow(()->new BadRequestException("Book with Id: "+ bookId + " cannot be found."));
			book.removeBookmarkedBookByUser(userId);
		}
		user.setBookmarkedBooks(new ArrayList<BookmarkedBook>());
		
		for (Favourite favourite: user.getFavourites()) {
			int bookId = favourite.getBookId();
			Book book = bookRepository.findById(bookId)
					.orElseThrow(()->new BadRequestException("Book with Id: "+ bookId + " cannot be found."));
			book.removeFavouriteByUser(userId);
		}
		user.setFavourites(new ArrayList<Favourite>());
		update(user);
		userRepository.delete(user);
	}

	public User findByEmail(String email) {
		User User = userRepository.findByEmail(email)
				.orElseThrow(()->new BadRequestException("User with email: " + email + " cannot be found."));
		return User;
	}
	
	public void register(User user) {
	    if(existsByEmail(user.getEmail(), 0)) {
	        throw new BadRequestException("Email Already Exists");
	    }else if (existsByUsername(user.getUsername(), 0)){
	        throw new BadRequestException("Username Already Exists");
	    }
	    	System.out.println("Hashing password: " + user.getPassword());
	        String hashedPassword = passwordEncoder.encode(user.getPassword());
	        user.setPassword(hashedPassword);
	        this.userRepository.save(user);
	}
	
	public List<User> findPartialMatch(String q)
	{
		
		return userRepository.findPartialMatch(q);
	}
}
