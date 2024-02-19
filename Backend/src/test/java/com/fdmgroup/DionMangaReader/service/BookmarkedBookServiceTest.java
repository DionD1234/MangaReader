package com.fdmgroup.DionMangaReader.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.DionMangaReader.dal.BookRepository;
import com.fdmgroup.DionMangaReader.dal.BookmarkedBookRepository;
import com.fdmgroup.DionMangaReader.dal.UserRepository;
import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.User;




@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class BookmarkedBookServiceTest
{
	
	@Mock
    private BookmarkedBookRepository bookmarkedBookRepositoryMock;

    @Mock
    private BookRepository bookRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private BookmarkedBookService bookmarkedBookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
    	BookmarkedBook bb1 = new BookmarkedBook(1,1,10);;
    	BookmarkedBook bb2 = new BookmarkedBook(2,2,20);
    	List<BookmarkedBook> bookmarkedBookList = new ArrayList<>();
    	bookmarkedBookList.add(bb1);
    	bookmarkedBookList.add(bb2);
    	when(bookmarkedBookRepositoryMock.findAll()).thenReturn(bookmarkedBookList);
    	List<BookmarkedBook> resultList = bookmarkedBookService.findAll();
    	assertTrue(bookmarkedBookList.equals(resultList));
    }
    @Test
    void testGetOrderedByBookmarkCount() {
    	BookmarkedBook[] bbArray = {
		new BookmarkedBook(1, 1, 10),
		new BookmarkedBook(2, 1, 10),
		new BookmarkedBook(3, 1, 10),
		new BookmarkedBook(4, 2, 10),
		new BookmarkedBook(4, 3, 10),
		new BookmarkedBook(4, 2, 10),
		new BookmarkedBook(4, 1, 10),
		new BookmarkedBook(5, 2, 10),
		new BookmarkedBook(5, 3, 10),
		new BookmarkedBook(5, 2, 10),
		new BookmarkedBook(6, 2, 10),
		new BookmarkedBook(6, 1, 10),
		new BookmarkedBook(7, 2, 10),
		new BookmarkedBook(7, 3, 10),
		new BookmarkedBook(7, 2, 10),
		new BookmarkedBook(7, 5, 10),
		new BookmarkedBook(7, 1, 10)
		};
		List<BookmarkedBook> bbList = new ArrayList<>();
		for(BookmarkedBook book : bbArray) {
			bbList.add(book);
		}
		when(bookmarkedBookRepositoryMock.findAll()).thenReturn(bbList);
		List<Integer> sortedBookIds = bookmarkedBookService.getOrderedByBookmarkCount();
		int[] expectedResults = {7, 4, 5, 6, 1, 2, 3};
		
		for(int i = 0; i < sortedBookIds.size(); i++) {
			assertEquals(expectedResults[i], sortedBookIds.get(i));
		}
		assertEquals(expectedResults.length, sortedBookIds.size());
	
		
		
		
		
		
    }
    @Test
    void testUpdate() {
        // Mock data
    	int bookId = 1;
        int userId = 1;
        int currentChapter = 10;
    	BookmarkedBook foundBookmarkedBook = new BookmarkedBook(bookId,userId,currentChapter);
    	when(bookmarkedBookRepositoryMock.findById(1)).thenReturn(Optional.of(foundBookmarkedBook));
        BookmarkedBook existingBookmarkedBook = bookmarkedBookService.findById(1);
        existingBookmarkedBook.setUserId(2);
        
        // Mock behavior of existsById method
        when(bookmarkedBookRepositoryMock.existsById(existingBookmarkedBook.getBookmarkedBookId())).thenReturn(true);

        // Test update method
        bookmarkedBookService.update(existingBookmarkedBook);

        // Verify that save method of repository is called
        verify(bookmarkedBookRepositoryMock, times(1)).save(existingBookmarkedBook);
    }

    @Test
    void testDeleteById() {
        // Mock data
        int bookmarkedBookId = 1;
    	int bookId = 1;
        int userId = 1;
        int currentChapter = 10;
        BookmarkedBook bookmarkedBook = new BookmarkedBook(bookId,userId,currentChapter);
        User user = new User("fakeemail1@mail.com","username1","password1");
        Book book = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        
        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        
        // Mock behavior of findById method
        when(bookmarkedBookRepositoryMock.findById(bookmarkedBookId)).thenReturn(Optional.of(bookmarkedBook));
        // Test deleteById method
        bookmarkedBookService.deleteById(bookmarkedBookId);

     // Verify that delete method of repository is called
        verify(bookmarkedBookRepositoryMock, times(1)).delete(bookmarkedBook);
    }

    @Test
    void testDelete() {
        // Mock data
    	int bookId = 1;
        int userId = 1;
        int currentChapter = 10;
        BookmarkedBook bookmarkedBook = new BookmarkedBook(bookId,userId,currentChapter);
        User user = new User("fakeemail1@mail.com","username1","password1");
        Book book = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        
        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        
        // Test delete method
        bookmarkedBookService.delete(bookmarkedBook);

        // Verify that delete method of repository is called
        verify(bookmarkedBookRepositoryMock, times(1)).delete(bookmarkedBook);
    }

    @Test
    void testDeleteByBookIdUserId() {
        // Mock data
        int bookId = 1;
        int userId = 1;
        int currentChapter = 10;
        BookmarkedBook bookmarkedBook = new BookmarkedBook(bookId,userId,currentChapter);
        
        User user = new User("fakeemail1@mail.com","username1","password1");
        Book book = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        
        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        
        // Mock behavior of findExactBookmark method
        when(bookmarkedBookRepositoryMock.findExactBookmark(bookId, userId)).thenReturn(bookmarkedBook); 
        
        // Test deleteByBookIdUserId method
        bookmarkedBookService.deleteByBookIdUserId(bookId, userId);

        // Verify that delete method of repository is called
        verify(bookmarkedBookRepositoryMock, times(1)).delete(bookmarkedBook);
    }

    @Test
    void testFindExactBookmark() {
        // Mock data
        int bookId = 1;
        int userId = 1;
        int currentChapter = 10;
        BookmarkedBook bookmarkedBook = new BookmarkedBook(bookId,userId,currentChapter);
        
        // Mock behavior of findExactBookmark method
        when(bookmarkedBookRepositoryMock.findExactBookmark(bookId, userId)).thenReturn(bookmarkedBook);

        // Test findExactBookmark method
        BookmarkedBook result = bookmarkedBookService.findExactBookmark(bookId, userId);

        // Verify the result
        assertNotNull(result);
    }

    @Test
    void testFindById() {
        // Mock data
        int bookmarkedBookId = 1;
        int bookId = 1;
        int userId = 1;
        int currentChapter = 10;
        BookmarkedBook bookmarkedBook = new BookmarkedBook(bookId,userId,currentChapter);
        
        // Mock behavior of findById method
        when(bookmarkedBookRepositoryMock.findById(bookmarkedBookId)).thenReturn(Optional.of(bookmarkedBook));

        // Test findById method
        BookmarkedBook result = bookmarkedBookService.findById(bookmarkedBookId);

        // Verify the result
        assertNotNull(result);
    }

}
