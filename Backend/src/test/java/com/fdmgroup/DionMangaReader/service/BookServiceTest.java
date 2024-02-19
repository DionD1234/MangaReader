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

import com.fdmgroup.DionMangaReader.dal.BookRepository;
import com.fdmgroup.DionMangaReader.dal.UserRepository;
import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.User;


@ExtendWith(MockitoExtension.class)
class BookServiceTest
{

    @Mock
    private BookRepository bookRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testFindAll() {
    	Book b1 = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
    	Book b2 = new Book(25, "https://example.com/cover2.jpg", "Quirky High School Romance", "Romantic comedy featuring quirky high school students.");
        		
    	List<Book> bookList = new ArrayList<>();
    	bookList.add(b1);
    	bookList.add(b2);
    	when(bookRepositoryMock.findAll()).thenReturn(bookList);
    	List<Book> resultList = bookService.findAll();
    	assertTrue(bookList.equals(resultList));
    }
    @Test
    void testUpdate() {
        // Mock data
    	Book existingBook = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        existingBook.setTitle("Mysterious Adventure");
        existingBook.setBookId(1);
        
        // Mock behavior of existsById method
        when(bookRepositoryMock.existsById(existingBook.getBookId())).thenReturn(true);

        // Test update method
        bookService.update(existingBook);

        // Verify that save method of repository is called
        verify(bookRepositoryMock, times(1)).save(existingBook);
    }

    @Test
    void testDeleteById() {
        // Mock data
    	int bookId = 1;
        Book book = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        book.setBookId(bookId);
        
        // Mock behavior of methods
        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepositoryMock.existsById(book.getBookId())).thenReturn(true);
        // Test deleteById method
        bookService.deleteById(bookId);

     // Verify that delete method of repository is called
        verify(bookRepositoryMock, times(1)).delete(book);
    }

    @Test
    void testDelete() {
        // Mock data
        Book book = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        book.setBookId(1);
        when(bookRepositoryMock.existsById(1)).thenReturn(true);
        
        // Test delete method
        bookService.delete(book);

        // Verify that delete method of repository is called
        verify(bookRepositoryMock, times(1)).delete(book);
    }

    @Test
    void testFindById() {
        // Mock data
        int bookId = 1;

        Book book = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        
        // Mock behavior of findById method
        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));

        // Test findById method
        Book result = bookService.findById(bookId);

        // Verify the result
        assertNotNull(result);
    }
}
