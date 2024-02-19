package com.fdmgroup.DionMangaReader.controller;

import java.util.Arrays;
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

import com.fdmgroup.DionMangaReader.exceptions.BadRequestException;
import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController
{
private BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}
	
	@GetMapping
	public List<Book> firstEndpoint() {
		return bookService.findAll();
	}
	@GetMapping("/{bookId}")
	public Book findById(@PathVariable int bookId) {
		return bookService.findById(bookId);
	}
	@GetMapping("/find")
	public List<Book> findByExactTitle(@RequestParam String title)
	{
		return bookService.findByTitle(title);
	}

	@GetMapping("/search")
	public List<Book> findByBookIdOrTitle(
			@RequestParam(required = false, defaultValue = "-1") int bookId,
	        @RequestParam(required = false, defaultValue = "") String title) 
	{
		if (bookId==-1 && !title.isEmpty())
		{
			return bookService.findPartialMatch(title);
		} else if (bookId != -1 && title.isEmpty()) {
			return Arrays.asList(bookService.findById(bookId));
		}else if (bookId == -1 && title.isEmpty()) {
			throw new BadRequestException("Must provide either a Book Id or Title");
		}else {
			throw new BadRequestException("Cannot provide both a Book Id and Title");
		}
	}
	@PostMapping
	public void createNew(@RequestBody Book newBook)
	{
		System.out.println(newBook);
		bookService.save(newBook);
	}
	@PutMapping
	public void updateBook(@RequestBody Book newBook)
	{
		System.out.println(newBook);
		bookService.update(newBook);
	}
	@DeleteMapping("/{bookId}")
	public void deleteBook(@PathVariable int bookId) {
		bookService.deleteById(bookId);
	}
}
