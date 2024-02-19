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

import com.fdmgroup.DionMangaReader.model.Favourite;
import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.service.FavouriteService;
import com.fdmgroup.DionMangaReader.service.UserService;

@Transactional
@ExtendWith(MockitoExtension.class)
class FavouriteControllerTest {

    @Mock
    private FavouriteService favouriteServiceMock;
    
    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private FavouriteController favouriteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFirstEndpoint() {
        // Mock data
        List<Favourite> favouriteList = new ArrayList<>();
        favouriteList.add(new Favourite(1, 1));
        favouriteList.add(new Favourite(2, 2));
        when(favouriteServiceMock.findAll()).thenReturn(favouriteList);

        // Call the controller method directly
        List<Favourite> result = favouriteController.firstEndpoint();

        // Verify the result
        assertEquals(favouriteList, result);
    }

    @Test
    void testFindById() {
        // Mock data
        int favId = 1;
        Favourite favourite = new Favourite(1, 1);
        when(favouriteServiceMock.findById(favId)).thenReturn(favourite);

        // Call the controller method directly
        Favourite result = favouriteController.findById(favId);

        // Verify the result
        assertEquals(favourite, result);
    }
    
    @Test
    void testFavouriteByUserId() {
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
    	
    	for(Favourite favourite : favouriteArray)
    	{
    		favouriteList.add(favourite);
    	}
    	
    	when(favouriteServiceMock.findAll()).thenReturn(favouriteList);
    	
    	int[] expectedArray = {1,6,7};
    	
    	List<Integer> resultList = favouriteController.favouriteByUserId(1);
    	
    	for(int i = 0; i < resultList.size(); i++) {
    		assertEquals(expectedArray[i], resultList.get(i));
    	}
    	assertEquals(expectedArray.length, resultList.size());
    }
    @Test
    void testFindTop5() {

    	List<Integer> returnList = new ArrayList<>();
    	
    	int[] expectedArray = {7,6,5,4,3,1,2};
    	
    	for(int i : expectedArray)
    	{
    		returnList.add(i);
    	}
    	
    	when(favouriteServiceMock.getByFavouriteCount()).thenReturn(returnList);
    	
    	List<Integer> results = favouriteController.findTop5();
    	for(int i = 0; i < results.size();i++) {
    		assertEquals(results.get(i), returnList.get(i));
    	}
    	assertEquals(5, results.size());
    	
    	
    	
    }
    
    @Test
    void testOrderByDesc() {
    	
    	List<Integer> returnList = new ArrayList<>();
    	
    	int[] expectedArray = {7,6,5,4,3,1,2};
    	
    	for(int i : expectedArray)
    	{
    		returnList.add(i);
    	}
    	
    	when(favouriteServiceMock.getByFavouriteCount()).thenReturn(returnList);
    	
    	List<Integer> results = favouriteController.orderByDesc();
    	for(int i = 0; i < results.size();i++) {
    		assertEquals(results.get(i), returnList.get(i));
    	}
    	assertEquals(expectedArray.length, results.size());
    	
    	
    	
    }
    
    

    @Test
    void testCreateNew() {
        // Mock data
        Favourite newFavourite = new Favourite(1, 1);
        when(userServiceMock.findById(1)).thenReturn(new User("username","email","password"));
        when(favouriteServiceMock.findExactFavourite(1, 1)).thenReturn(newFavourite);
        // Call the controller method directly
        favouriteController.createNew(newFavourite);
        
        
        
        // Verify that the service method is called
        verify(favouriteServiceMock, times(1)).save(newFavourite);
    }

    @Test
    void testUpdateFavourite() {
        // Mock data
        Favourite updatedFavourite = new Favourite(1, 1);
        updatedFavourite.setFavId(1);

        // Call the controller method directly
        favouriteController.updateFavourite(updatedFavourite);

        // Verify that the service method is called
        verify(favouriteServiceMock, times(1)).update(updatedFavourite);
    }

    @Test
    void testDeleteFavouriteByIds() {
        // Mock data
        int bookId = 1;
        int userId = 1;

        // Call the controller method directly
        favouriteController.deleteFavouriteByIds(bookId, userId);

        // Verify that the service method is called
        verify(favouriteServiceMock, times(1)).deleteByBookIdUserId(bookId, userId);
    }

    @Test
    void testDeleteFavourite() {
        // Mock data
        int favId = 1;

        // Call the controller method directly
        favouriteController.deleteFavourite(favId);


        // Verify that the service method is called
        verify(favouriteServiceMock, times(1)).deleteById(favId);
    }
}
