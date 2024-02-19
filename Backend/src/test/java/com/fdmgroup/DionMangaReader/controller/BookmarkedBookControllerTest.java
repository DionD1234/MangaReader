package com.fdmgroup.DionMangaReader.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.service.BookmarkedBookService;
import com.fdmgroup.DionMangaReader.service.UserService;

@Transactional
@ExtendWith(MockitoExtension.class)
class BookmarkedBookControllerTest
{
	@Mock
    private BookmarkedBookService bookmarkedBookServiceMock;
	
	@Mock
	private UserService userServiceMock;

    @InjectMocks
    private BookmarkedBookController bookmarkedBookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    } 
    @Test
    void testFindAll() {
    	BookmarkedBook bb1 = new BookmarkedBook(1, 1, 10);
    	BookmarkedBook bb2 = new BookmarkedBook(2, 2, 20);
    	List<BookmarkedBook> bookmarkList = new ArrayList<BookmarkedBook>();
    	
    	bookmarkList.add(bb1);
    	bookmarkList.add(bb2);
    	

		when(bookmarkedBookServiceMock.findAll()).thenReturn(bookmarkList);
    	
		List<BookmarkedBook> resultList = bookmarkedBookController.firstEndpoint();
		assertEquals(bookmarkList.size(), resultList.size());
    	
    }
    
    
	@Test
    void testFindById() {
        // Mock data
        int bookmarkedBookId = 1;
        BookmarkedBook bookmarkedBook = new BookmarkedBook(1, 1, 10);
        when(bookmarkedBookServiceMock.findById(bookmarkedBookId)).thenReturn(bookmarkedBook);

        // Call the controller method directly
        BookmarkedBook result = bookmarkedBookController.findById(bookmarkedBookId);

        // Verify the result
        assertEquals(bookmarkedBook, result);
    }
	@Test
	void testBookmarksByUserId(){
		//Mock data
		BookmarkedBook[] bbArray = {
		new BookmarkedBook(1, 1, 10),
		new BookmarkedBook(2, 1, 10),
		new BookmarkedBook(3, 1, 10),
		new BookmarkedBook(1, 2, 10),
		new BookmarkedBook(1, 3, 10),
		new BookmarkedBook(1, 2, 10)
		};
		List<BookmarkedBook> bbList = new ArrayList<>();
		for(BookmarkedBook book : bbArray) {
			bbList.add(book);
		}
		when(bookmarkedBookServiceMock.findAll()).thenReturn(bbList);
		
		List<Integer> extractedList = bookmarkedBookController.bookmarksByUserId(1);
		assertEquals(3, extractedList.size());
		
	}
	
	@Test
	void testFindExactByBookIdUserId() {
		BookmarkedBook bb1 = new BookmarkedBook(1,1,10);
		when(bookmarkedBookServiceMock.findExactBookmark(1, 1)).thenReturn(bb1);
		BookmarkedBook bb2 = bookmarkedBookController.findExactByBookIdUserId(1, 1);
		assertEquals(bb1+"",bb2+"");
	}
	@Test
	void testFindTop5() {
		int[] intArray = {5,6,3,1,2,4,9,7,8};
		List<Integer> intList = new ArrayList<>();
		for(int i : intArray) {
			intList.add(i);
		}
		when(bookmarkedBookServiceMock.getOrderedByBookmarkCount()).thenReturn(intList);
		List<Integer> returnList = bookmarkedBookController.findTop5();
		for (int i =0; i<returnList.size(); i++) {
			assertEquals(intList.get(i), returnList.get(i));
		}
		assertEquals(5, returnList.size());
		
	}

	
	@Test
	void testOrderByDesc() {
		int[] intArray = {5,6,3,1,2,4,9,7,8};
		List<Integer> intList = new ArrayList<>();
		for(int i : intArray) {
			intList.add(i);
		}
		when(bookmarkedBookServiceMock.getOrderedByBookmarkCount()).thenReturn(intList);
		List<Integer> returnList = bookmarkedBookController.orderByDesc();
		for (int i =0; i<returnList.size(); i++) {
			assertEquals(intList.get(i), returnList.get(i));
		}
		assertEquals(intList.size(), returnList.size());
		
	}

    @Test
    void testCreateNew() {
        // Mock data
        BookmarkedBook newBookmarkedBook = new BookmarkedBook(1, 1, 10);
        when(userServiceMock.findById(1)).thenReturn(new User("username","email","password"));
        when(bookmarkedBookServiceMock.findExactBookmark(1, 1)).thenReturn(newBookmarkedBook);

        // Call the controller method directly
        bookmarkedBookController.createNew(newBookmarkedBook);

        // Verify that the service method is called
        verify(bookmarkedBookServiceMock, times(1)).save(newBookmarkedBook);
    }

    @Test
    void testUpdateBookmark() {
        // Mock data
        BookmarkedBook updatedBookmarkedBook = new BookmarkedBook(1, 1, 10);
        updatedBookmarkedBook.setBookId(1);

        // Call the controller method directly
        bookmarkedBookController.updateBookmark(updatedBookmarkedBook);

        // Verify that the service method is called
        verify(bookmarkedBookServiceMock, times(1)).update(updatedBookmarkedBook);
    }

    @Test
    void testDeleteBookmarkByIds() {
        // Mock data
        int bookid = 1;
        int userId = 1;

        // Call the controller method directly
        bookmarkedBookController.deleteBookmarkByIds(bookid, userId);

        // Verify that the service method is called
        verify(bookmarkedBookServiceMock, times(1)).deleteByBookIdUserId(bookid, userId);
    }

    @Test
    void testDeleteBookmark() {
        // Mock data
        int bookmarkBookId = 1;

        // Call the controller method directly
        bookmarkedBookController.deleteBookmark(bookmarkBookId);

        // Verify that the service method is called
        verify(bookmarkedBookServiceMock, times(1)).deleteById(bookmarkBookId);
    }

}
