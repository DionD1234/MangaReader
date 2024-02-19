package com.fdmgroup.DionMangaReader.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.DionMangaReader.dal.BookRepository;
import com.fdmgroup.DionMangaReader.dal.UserRepository;
import com.fdmgroup.DionMangaReader.exceptions.BadRequestException;
import com.fdmgroup.DionMangaReader.exceptions.NotFoundException;
import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.Favourite;
import com.fdmgroup.DionMangaReader.model.User;

@Service
public class BookService
{
	private BookRepository bookRepository;
	private UserRepository userRepository;
	
	@Autowired
	private BookService(BookRepository bookRepository, UserRepository userRepository) {
		super();
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}
	
	public List<Book> findAll(){
		return this.bookRepository.findAll();
	}
	
	public Book findById(int bookId) {
		return this.bookRepository.findById(bookId)
				.orElseThrow(()->new NotFoundException("Book with Id: "+ bookId + " cannot be found."));
				
	}
	public void save(Book newBook)
	{
		this.bookRepository.save(newBook);
	}

	public void update(Book newBook)
	{
		if(newBook.getBookId() == 0) 
		{
			throw new BadRequestException("Must provide a book id for 'put' mapping");
		}
		else if(bookRepository.existsById(newBook.getBookId()))
		{
			bookRepository.save(newBook);
			return;
		}
		throw new NotFoundException("Provided book id does not exist");
		
		
	}
	public void deleteById(int bookId)
	{
		delete(findById(bookId));
	}
	public void delete(Book book)
	{
		int bookId = book.getBookId();
		for (BookmarkedBook bookmarkBook: book.getBookmarkedBooks()) {
			int userId = bookmarkBook.getUserId();
			User user = userRepository.findById(userId)
					.orElseThrow(()->new NotFoundException("User with Id: "+ userId + " cannot be found."));
			user.removeBookmarkedBookByBook(bookId);
		}
		book.setBookmarkedBooks(new ArrayList<BookmarkedBook>());

		for (Favourite favourite: book.getFavourites()) {
			int userId = favourite.getUserId();
			User user = userRepository.findById(userId)
					.orElseThrow(()->new NotFoundException("User with Id: "+ userId + " cannot be found."));
			user.removeFavouriteByBook(userId);
		}
		book.setFavourites(new ArrayList<Favourite>());
		update(book);
		bookRepository.delete(book);
		
	}
	public List<Book> findByTitle(String title) {
		return bookRepository.findByTitle(title);
	}
	
	public List<Book> findPartialMatch(String title) {
		return bookRepository.findPartialMatch(title);
	}
	

}
