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
import com.fdmgroup.DionMangaReader.dal.FavouriteRepository;
import com.fdmgroup.DionMangaReader.dal.UserRepository;
import com.fdmgroup.DionMangaReader.exceptions.NotFoundException;
import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.Favourite;
import com.fdmgroup.DionMangaReader.model.User;

@ExtendWith(MockitoExtension.class)
class FavouriteServiceTest
{

	@Mock
    private FavouriteRepository favouriteRepositoryMock;

    @Mock
    private BookRepository bookRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private FavouriteService favouriteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
    	Favourite f1 = new Favourite(1,1);
    	Favourite f2 = new Favourite(2,2);
    	List<Favourite> userList = new ArrayList<>();
    	userList.add(f1);
    	userList.add(f2);
    	when(favouriteRepositoryMock.findAll()).thenReturn(userList);
    	List<Favourite> resultList = favouriteService.findAll();
    	assertTrue(userList.equals(resultList));
    }
    @Test
    void testUpdate() {
        // Mock data
    	int bookId = 1;
        int userId = 1;
    	Favourite foundBookmarkedBook = new Favourite(bookId,userId);
    	when(favouriteRepositoryMock.findById(1)).thenReturn(Optional.of(foundBookmarkedBook));
        Favourite existingFavourite = favouriteService.findById(1);
        existingFavourite.setUserId(2);
        
        // Mock behavior of existsById method
        when(favouriteRepositoryMock.existsById(existingFavourite.getFavId())).thenReturn(true);

        // Test update method
        favouriteService.update(existingFavourite);

        // Verify that save method of repository is called
        verify(favouriteRepositoryMock, times(1)).save(existingFavourite);
    }

    @Test
    void testDeleteById() {
        // Mock data
        int favouriteId = 1;
    	int bookId = 1;
        int userId = 1;
        Favourite favourite = new Favourite(bookId,userId);
        User user = new User("fakeemail1@mail.com","username1","password1");
        Book book = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        
        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        
        // Mock behavior of findById method
        when(favouriteRepositoryMock.findById(favouriteId)).thenReturn(Optional.of(favourite));
        // Test deleteById method
        favouriteService.deleteById(favouriteId);

     // Verify that delete method of repository is called
        verify(favouriteRepositoryMock, times(1)).delete(favourite);
    }

    @Test
    void testDelete() {
        // Mock data
    	int bookId = 1;
        int userId = 1;
 
        Favourite favourite = new Favourite(bookId,userId);
        User user = new User("fakeemail1@mail.com","username1","password1");
        Book book = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        
        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        
        // Test delete method
        favouriteService.delete(favourite);

        // Verify that delete method of repository is called
        verify(favouriteRepositoryMock, times(1)).delete(favourite);
    }
    
    @Test
    void testDeleteThrowsNotFoundUser() {
        // Mock data
    	int bookId = 1;
        int userId = 1;
 
        Favourite favourite = new Favourite(bookId,userId);
        Book book = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        
        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.empty());
        
        // Test delete method
        assertThrows(NotFoundException.class, () -> favouriteService.delete(favourite));
    }
    
    @Test
    void testDeleteThrowsNotFoundBook() {
        // Mock data
    	int bookId = 1;
        int userId = 1;
 
        Favourite favourite = new Favourite(bookId,userId);
        User user = new User("fakeemail1@mail.com","username1","password1");
        
        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.empty());
        
        // Test delete method
        assertThrows(NotFoundException.class, () -> favouriteService.delete(favourite));
    }
    
    
    
    

    @Test
    void testDeleteByBookIdUserId() {
        // Mock data
        int bookId = 1;
        int userId = 1;
        Favourite favourite = new Favourite(bookId,userId);
        
        User user = new User("fakeemail1@mail.com","username1","password1");
        Book book = new Book(42, "https://example.com/cover1.jpg", "Adventure and Mystery", "A tale of adventure and mystery in a fantasy world.");
        
        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        when(favouriteRepositoryMock.findExactFavourite(bookId, userId)).thenReturn(favourite); 
        
        // Test deleteByBookIdUserId method
        favouriteService.deleteByBookIdUserId(bookId, userId);

        // Verify that delete method of repository is called
        verify(favouriteRepositoryMock, times(1)).delete(favourite);
    }

    @Test
    void testFindExactFavourite() {
        // Mock data
        int bookId = 1;
        int userId = 1;
        
        Favourite bookmarkedBook = new Favourite(bookId,userId);
        
        // Mock behavior of findExactBookmark method
        when(favouriteRepositoryMock.findExactFavourite(bookId, userId)).thenReturn(bookmarkedBook);

        // Test findExactBookmark method
        Favourite result = favouriteService.findExactFavourite(bookId, userId);

        // Verify the result
        assertNotNull(result);
    }

    @Test
    void testFindById() {
        // Mock data
        int favouriteId = 1;
        int bookId = 1;
        int userId = 1;

        Favourite favourite = new Favourite(bookId,userId);
        
        // Mock behavior of findById method
        when(favouriteRepositoryMock.findById(favouriteId)).thenReturn(Optional.of(favourite));

        // Test findById method
        Favourite result = favouriteService.findById(favouriteId);

        // Verify the result
        assertNotNull(result);
    }
    
    @Test
    void testGetByFavouriteCount() {
    	Favourite[] favouriteArray = {
    			new Favourite(1,1),
    			new Favourite(2,2),
    			new Favourite(3,3),
    			new Favourite(4,4),
    			new Favourite(4,5),
    			new Favourite(5,5),
    			new Favourite(5,4),
    			new Favourite(5,3),
    			new Favourite(6,2),
    			new Favourite(6,1),
    			new Favourite(6,2),
    			new Favourite(6,3),
    			new Favourite(7,4),
    			new Favourite(7,1),
    			new Favourite(7,2),
    			new Favourite(7,3),
    			new Favourite(7,4)
    	};
    	List<Favourite> favouriteList = new ArrayList<>();
    	for (Favourite favourite: favouriteArray) {
    		favouriteList.add(favourite);
    	}
    	int[] expectedArray = {7,6,5,4,1,2,3};
    	
    	
    	when(favouriteRepositoryMock.findAll()).thenReturn(favouriteList);
    	
    	List<Integer> returnList = favouriteService.getByFavouriteCount();
    	
    	for (int i = 0; i < returnList.size();i++) {
    		assertEquals(expectedArray[i], returnList.get(i));
    	}
    	assertEquals(expectedArray.length, returnList.size());
    
    }

}
