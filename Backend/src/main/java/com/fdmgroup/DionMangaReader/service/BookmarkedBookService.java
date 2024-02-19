package com.fdmgroup.DionMangaReader.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.DionMangaReader.dal.BookRepository;
import com.fdmgroup.DionMangaReader.dal.BookmarkedBookRepository;
import com.fdmgroup.DionMangaReader.dal.UserRepository;
import com.fdmgroup.DionMangaReader.exceptions.BadRequestException;
import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.User;

import jakarta.persistence.EntityManager;

@Service
public class BookmarkedBookService
{
	private BookmarkedBookRepository bookmarkedBookRepository;
	private BookRepository bookRepository;
	private UserRepository userRepository;
	
	@Autowired
	private BookmarkedBookService(
			BookmarkedBookRepository bookmarkedBookRepository,
			BookRepository bookRepository,
			UserRepository userRepository
			) 
	{
		super();
		this.bookmarkedBookRepository = bookmarkedBookRepository;
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}
	
	public List<BookmarkedBook> findAll(){
		return this.bookmarkedBookRepository.findAll();
	}
	
	public void save(BookmarkedBook newBookmarkedBook)
	{
		this.bookmarkedBookRepository.save(newBookmarkedBook);
	}

	public void update(BookmarkedBook newBookmarkedBook)
	{
		if(bookmarkedBookRepository.existsById(newBookmarkedBook.getBookmarkedBookId()))
		{
			bookmarkedBookRepository.save(newBookmarkedBook);
			return;
		}
		
		throw new RuntimeException("Must provide a user id for 'put' mapping");		
	}
	public void deleteById(int bookmarkedBookId)
	{
		delete(findById(bookmarkedBookId));
	}
	
	public List<Integer> getOrderedByBookmarkCount() {
		List<BookmarkedBook> allBookmarks = findAll();
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        
        for (BookmarkedBook bookmarks : allBookmarks) {
            int bookId = bookmarks.getBookId(); // Assuming getBookId() retrieves the book_id from each object
            frequencyMap.put(bookId, frequencyMap.getOrDefault(bookId, 0) + 1);
        }
        List<Integer> sortedBookIds = frequencyMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return sortedBookIds;

        
    }
	public void delete(BookmarkedBook bookmarkedBook)
	{
		int bookId = bookmarkedBook.getBookId();
		int userId = bookmarkedBook.getUserId();
		
		Book book = bookRepository.findById(bookId)
				.orElseThrow(()->new RuntimeException("Book with Id: "+ bookId + "cannot be found."));
		User user = userRepository.findById(userId)
				.orElseThrow(()->new RuntimeException("User with Id: "+ userId + "cannot be found."));
		
		List<BookmarkedBook> newBookmarkedBookBookList = book.getBookmarkedBooks();
		for (int i = 0; i<newBookmarkedBookBookList.size(); i++) {
			if(book.getBookmarkedBooks().get(i).getBookmarkedBookId() == bookmarkedBook.getBookmarkedBookId()) {
				newBookmarkedBookBookList.remove(i);
				break;
			}
		}
		book.setBookmarkedBooks(newBookmarkedBookBookList);
		bookRepository.save(book);

		List<BookmarkedBook> newBookmarkedBookUserList = user.getBookmarkedBooks();
		for (int i = 0; i<newBookmarkedBookUserList.size(); i++) {
			if(user.getBookmarkedBooks().get(i).getBookmarkedBookId() == bookmarkedBook.getBookmarkedBookId()) {
				newBookmarkedBookUserList.remove(i);
				break;
			}
		}
		user.setBookmarkedBooks(newBookmarkedBookUserList);
		userRepository.save(user);
		bookmarkedBookRepository.delete(bookmarkedBook);
		
		
	}
	public void deleteByBookIdUserId(int bookId, int userId)
	{
		delete(findExactBookmark(bookId, userId));
		
	}
	public BookmarkedBook findExactBookmark(int bookId, int userId) {
		return bookmarkedBookRepository.findExactBookmark(bookId, userId);
	}

	public BookmarkedBook findById(int bookmarkedBookId)
	{
		return bookmarkedBookRepository.findById(bookmarkedBookId)
				.orElseThrow(()->new RuntimeException("BookmarkedBook with Id: "+ bookmarkedBookId + "cannot be found."));
	}




	

}
