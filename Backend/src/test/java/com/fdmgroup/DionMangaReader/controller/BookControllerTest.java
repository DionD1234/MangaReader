package com.fdmgroup.DionMangaReader.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.DionMangaReader.exceptions.BadRequestException;
import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.service.BookService;

@Transactional
@ExtendWith(MockitoExtension.class)
class BookControllerTest
{
	
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFirstEndpoint() {
        // Mock data
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "coverUrl1", "title1", "description1"));
        bookList.add(new Book(2, "coverUrl2", "title2", "description2"));
        when(bookService.findAll()).thenReturn(bookList);

        // Call the controller method directly
        List<Book> result = bookController.firstEndpoint();

        // Verify the result
        assertEquals(bookList, result);
    }

    @Test
    void testFindById() {
        // Mock data
        int bookId = 1;
        Book book = new Book(1, "coverUrl1", "title1", "description1");
        when(bookService.findById(bookId)).thenReturn(book);

        // Call the controller method directly
        Book result = bookController.findById(bookId);

        // Verify the result
        assertEquals(book, result);
    }

    @Test
    void testFindByBookIdOrTitle() {
        // Mock data
        int bookId = 1;
        String title = "title";
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "coverUrl1", "title1", "description1"));
        bookList.add(new Book(2, "coverUrl2", "title2", "description2"));

        // Mock service method calls
        when(bookService.findPartialMatch(title)).thenReturn(bookList);
        when(bookService.findById(bookId)).thenReturn(new Book(1, "coverUrl1", "title1", "description1"));

        // Call the controller method directly
        List<Book> resultByTitle = bookController.findByBookIdOrTitle(-1, title);
        List<Book> resultById = bookController.findByBookIdOrTitle(bookId, "");

        // Verify the result
        assertEquals(bookList, resultByTitle);
        String bookAsString = bookList.get(0) + "";
        assertTrue(bookAsString.contentEquals(resultById.get(0) + ""));
    }

    @Test
    void testFindByBookIdOrTitle_Exceptions() {
        // Call the controller method directly with invalid input
        assertThrows(BadRequestException.class, () -> bookController.findByBookIdOrTitle(-1, ""));
        assertThrows(BadRequestException.class, () -> bookController.findByBookIdOrTitle(1, "title"));
    }

    @Test
    void testCreateNew() {
        // Mock data
        Book newBook = new Book(1, "coverUrl1", "title1", "description1");

        // Call the controller method directly
        bookController.createNew(newBook);

        // Verify that the service method is called
        verify(bookService, times(1)).save(newBook);
    }

    @Test
    void testUpdateBook() {
        // Mock data
        Book updatedBook = new Book(1, "coverUrl1", "title1", "description1");

        // Call the controller method directly
        bookController.updateBook(updatedBook);

        // Verify that the service method is called
        verify(bookService, times(1)).update(updatedBook);
    }

    @Test
    void testDeleteBook() {
        // Mock data
        int bookId = 1;

        // Call the controller method directly
        bookController.deleteBook(bookId);

        // Verify that the service method is called
        verify(bookService, times(1)).deleteById(bookId);
    }
}


