package com.fdmgroup.DionMangaReader.controller;

import java.util.ArrayList;
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

import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.service.BookmarkedBookService;
import com.fdmgroup.DionMangaReader.service.UserService;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkedBookController
{
private BookmarkedBookService bookmarkedBookService;
private UserService userService;
	@Autowired
	public BookmarkedBookController(
			BookmarkedBookService bookmarkedBookService,
			UserService userService) {
		super();
		this.bookmarkedBookService = bookmarkedBookService;
		this.userService = userService;
	}
	
	@GetMapping
	public List<BookmarkedBook> firstEndpoint() {
		return bookmarkedBookService.findAll();
	}
	@GetMapping("/user/{userId}")
	public List<Integer> bookmarksByUserId(@PathVariable int userId){
		List<BookmarkedBook> bookmarkList = firstEndpoint();
		List<Integer> resultList = new ArrayList<>();
		for(BookmarkedBook bookmark : bookmarkList) {
			if (bookmark.getUserId() == userId) {
				resultList.add(bookmark.getBookId());
			}
		}
		return resultList;
	}

	@GetMapping("/{bookmarkedBookId}")
	public BookmarkedBook findById(@PathVariable int bookmarkedBookId) {
		return bookmarkedBookService.findById(bookmarkedBookId);
	}
	
	@GetMapping("/users")
	public BookmarkedBook findExactByBookIdUserId(@RequestParam("bookId") int bookId, @RequestParam("userId") int userId) {
		return bookmarkedBookService.findExactBookmark(bookId, userId);
	}
	@GetMapping("/top5")
	public List<Integer> findTop5(){
		List<Integer> bookIdList = bookmarkedBookService.getOrderedByBookmarkCount();
		List<Integer> returnList = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			returnList.add(bookIdList.get(i));
		}
		//
		return returnList;
	}
	
	@GetMapping("/desc")
	public List<Integer> orderByDesc(){
		List<Integer> bookIdList = bookmarkedBookService.getOrderedByBookmarkCount();
		List<Integer> fullList = new ArrayList<>();
		
		for (int bookId : bookIdList) {
			fullList.add(bookId);
		}
		
		return fullList;

	}
	
	@PostMapping
	public void createNew(@RequestBody BookmarkedBook newBookmarkedBook)
	{
		System.out.println(newBookmarkedBook);
		bookmarkedBookService.save(newBookmarkedBook);
		User user = userService.findById(newBookmarkedBook.getUserId());
		user.addBookmarkedBook(bookmarkedBookService.findExactBookmark(
				newBookmarkedBook.getBookId(),
				newBookmarkedBook.getUserId()
				));
		userService.update(user);
	}
	@PutMapping
	public void updateBookmark(@RequestBody BookmarkedBook newBookmarkedBook)
	{
		System.out.println(newBookmarkedBook);
		bookmarkedBookService.update(newBookmarkedBook);
	}
	@DeleteMapping("/remove")
	public void deleteBookmarkByIds(@RequestParam("bookId") int bookId, @RequestParam("userId") int userId) {
		bookmarkedBookService.deleteByBookIdUserId(bookId, userId);
		
		
	}
	@DeleteMapping("/{bookmarkBookId}")
	public void deleteBookmark(@PathVariable int bookmarkBookId) {
		bookmarkedBookService.deleteById(bookmarkBookId);
	}
}
