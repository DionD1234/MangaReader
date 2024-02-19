package com.fdmgroup.DionMangaReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.DionMangaReader.service.BookService;
import com.fdmgroup.DionMangaReader.service.BookmarkedBookService;
import com.fdmgroup.DionMangaReader.service.FavouriteService;
import com.fdmgroup.DionMangaReader.service.UserService;
import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.Favourite;

@SpringBootTest
@Transactional
class DionMangaReaderApplicationTests {

	private UserService userService;
	private BookService bookService;
	private BookmarkedBookService bookmarkedBookService;
	private FavouriteService favouriteService;
	
	@Autowired
    public DionMangaReaderApplicationTests(UserService userService, 
                      BookService bookService, 
                      BookmarkedBookService bookmarkedBookService, 
                      FavouriteService favouriteService) 
	{
        super();
        this.userService = userService;
        this.bookService = bookService;
        this.bookmarkedBookService= bookmarkedBookService;
        this.favouriteService = favouriteService;
    }

	/*
	 * 
	 * Had issues with saved entities not being assigned to the next id,
	 * eg. being assigned 27 when the last id was 25, so created a for loop
	 * to find the max id
	 * 
	 */
	
	
	
	/*
	 * User Entity Tests
	 */
	
	// Can become false due to userSave test, check SQL for new total
	@Test
	void userFindAllTest() {
		List<User> users = userService.findAll();
		
		assertEquals(6, users.size());
		
	}
	// Can become false due to userSave test, check SQL for new total
	@Test
	void userPartialMatchTest() {
		List<User> users = userService.findPartialMatch("username");
		assertEquals(6, users.size());
		users = userService.findPartialMatch("1");
		assertEquals(1, users.size());
	}
	@Test
	void testUserFindById() {
		User user = userService.findById(2);
		assertEquals(2, user.getUserId());
	}
	@Test
	void testUserSave() {
		User user = new User("fakeemail7@mail.com","username7","password7");
		userService.register(user);
		
		List<User> users = userService.findAll();
		User maxUser = users.get(0); // Assume the first book has the maximum ID initially
	    
	    for (User savedUser : users) {
	        if (savedUser.getUserId() > maxUser.getUserId()) {
	            maxUser = savedUser; // Update maxBook if a book with higher ID is found
	        }
	    }
	    
		String stringRepresentation = user + "";
		assertTrue(stringRepresentation.contentEquals(maxUser+ ""));
	}
	@Test
	void testUserCreate() {
		User user = new User("fakemail@mail.com","user1","newpassword");
		int oldSize = userService.findAll().size();
		userService.register(user);		
		int newSize = userService.findAll().size();
		assertTrue(oldSize + 1 == newSize);
	}
	
	@Test
	void testUserUpdate() {
		User user = userService.findById(1);
		user.setUsername("user1");
		userService.update(user);
		User updatedUser = userService.findById(1);
		System.out.println(updatedUser.getBookmarkedBooks());
		assertTrue(userService.findById(1).getUsername().contentEquals("user1"));
	}
	@Test
	void testUserDelete() {
		List<User> users = userService.findAll();
		int oldSize = users.size();
		userService.deleteById(6);
		users = userService.findAll();
		int newSize = users.size();
		assertTrue(oldSize == newSize+1);
	}
	/*
	 * Book Entity Test
	 */
	
	@Test
	void bookFindAllTest() {
		List<Book> books = bookService.findAll();
		
		assertEquals(25, books.size());
		
	}
	@Test
	void bookPartialMatchTest() {
		List<Book> books = bookService.findPartialMatch("spy");
		assertEquals(1, books.size());
	}
	@Test
	void bookFindById() {
		Book book = bookService.findById(6);
		assertEquals(6, book.getBookId());
	}
	@Test
	void bookSave() {
		Book book = new Book(27,"https://example.com/cover26.jpg","Intriguing mystery with unexpected twists and turns.","Unexpected Mystery");
		bookService.save(book);
		
		List<Book> books = bookService.findAll();
		Book maxBook = books.get(0); // Assume the first book has the maximum ID initially
	    
	    for (Book savedBook : books) {
	        if (savedBook.getBookId() > maxBook.getBookId()) {
	            maxBook = savedBook; // Update maxBook if a book with higher ID is found
	        }
	    }
	    
		String stringRepresentation = book + "";
		assertTrue(stringRepresentation.contentEquals(maxBook+ ""));
	}
	@Test
	void bookUpdate() {
		Book book = bookService.findById(1);
		book.setTitle("Adventure in a Mysterious World");
		bookService.update(book);
		assertTrue(bookService.findById(1).getTitle().contentEquals("Adventure in a Mysterious World"));
	}
	@Test
	void bookDelete() {
		List<Book> books = bookService.findAll();
		int oldSize = books.size();
		bookService.deleteById(25);
		books = bookService.findAll();
		int newSize = books.size();
		assertTrue(oldSize == newSize+1);
	}
	
	/*
	 * BookmarkedBook Entity Test
	 */
	
	@Test
	void bookmarkedBookFindAllTest() {
		List<BookmarkedBook> bookmarkedBooks = bookmarkedBookService.findAll();
		
		assertEquals(54, bookmarkedBooks.size());
		
	}
	@Test
	void bookmarkedBookFindByIdTest() {
		BookmarkedBook bookmarkedBook = bookmarkedBookService.findById(6);
		assertEquals(6, bookmarkedBook.getBookmarkedBookId());
	}
	@Test
	void bookbookmarkedBookSaveTest() {
		BookmarkedBook bookmarkedBook = new BookmarkedBook(6,16,1);
		bookmarkedBookService.save(bookmarkedBook);
		
		List<BookmarkedBook> bookmarkedBooks = bookmarkedBookService.findAll();
		BookmarkedBook maxBookmarkedBook = bookmarkedBooks.get(0); // Assume the first book has the maximum ID initially
	    
	    for (BookmarkedBook savedBookmarkedBook : bookmarkedBooks) {
	        if (savedBookmarkedBook.getBookmarkedBookId() > maxBookmarkedBook.getBookmarkedBookId()) {
	            maxBookmarkedBook = savedBookmarkedBook; // Update maxBook if a book with higher ID is found
	        }
	    }
		
		String stringRepresentation = bookmarkedBook + "";
		assertTrue(stringRepresentation.contentEquals(maxBookmarkedBook+ ""));
	}
	@Test
	void bookbookmarkedBookUpdate() {
		BookmarkedBook bookmarkedBook = bookmarkedBookService.findById(2);
		bookmarkedBook.setCurrentChapter(10);
		bookmarkedBookService.update(bookmarkedBook);
		assertTrue(bookmarkedBookService.findById(2).getCurrentChapter() == 10);
	}
	@Test
	void bookmarkedBookDelete() {
		List<BookmarkedBook> bookmarkedBooks = bookmarkedBookService.findAll();
		int oldSize = bookmarkedBooks.size();
		bookmarkedBookService.deleteById(20);
		bookmarkedBooks = bookmarkedBookService.findAll();
		int newSize = bookmarkedBooks.size();
		assertTrue(oldSize == newSize+1);
	}
	/*
	 * Favourite Entity Test
	 */
	@Test
	void favouriteFindAllTest() {
		List<Favourite> favourites = favouriteService.findAll();
		
		assertEquals(35, favourites.size());
		
	}
	@Test
	void favouriteFindByIdTest() {
		Favourite favourite = favouriteService.findById(3);
		assertEquals(3, favourite.getFavId());
	}
	@Test
	void favouriteSaveTest() {
		Favourite favourite = new Favourite(4,8);
		favouriteService.save(favourite);
		String stringRepresentation = favourite + "";
		
		List<Favourite> favourites = favouriteService.findAll();
		Favourite maxFavourite = favourites.get(0); // Assume the first book has the maximum ID initially
	    
	    for (Favourite savedFavourite : favourites) {
	        if (savedFavourite.getFavId() > maxFavourite.getFavId()) {
	            maxFavourite = savedFavourite; // Update maxBook if a book with higher ID is found
	        }
	    }
		
		assertTrue(stringRepresentation.contentEquals(maxFavourite+ ""));
	}
	@Test
	void favouriteUpdate() {
		Favourite favourite = favouriteService.findById(2);
		favourite.setUserId(2);
		favouriteService.update(favourite);
		assertTrue(favouriteService.findById(2).getUserId() == 2);
	}
	@Test
	void favouriteDelete() {
		List<Favourite> favourites = favouriteService.findAll();
		int oldSize = favourites.size();
		favouriteService.deleteById(20);
		favourites = favouriteService.findAll();
		int newSize = favourites.size();
		assertTrue(oldSize == newSize+1);
	}
	
	

}
