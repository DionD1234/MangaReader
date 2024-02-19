package com.fdmgroup.DionMangaReader.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.DionMangaReader.dal.BookRepository;
import com.fdmgroup.DionMangaReader.dal.UserRepository;
import com.fdmgroup.DionMangaReader.exceptions.BadRequestException;
import com.fdmgroup.DionMangaReader.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
@ExtendWith(MockitoExtension.class)
class UserServiceTest
{

		@Mock
		private PasswordEncoder passwordEncoderMock;
	
		@Mock
	    private BookRepository bookRepositoryMock;

	    @Mock
	    private UserRepository userRepositoryMock;
	    
	    @InjectMocks
	    private UserService userService;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testFindAll() {
	    	User u1 = new User("fakeemail1@mail.com","username1","password1");
	    	User u2 = new User("fakeemail2@mail.com","username2","password2");
	    	List<User> userList = new ArrayList<>();
	    	userList.add(u1);
	    	userList.add(u2);
	    	when(userRepositoryMock.findAll()).thenReturn(userList);
	    	List<User> resultList = userService.findAll();
	    	assertTrue(userList.equals(resultList));
	    }
	    @Test
	    void testUpdate() {
	        // Mock data
	    	User foundUser = new User("fakeemail1@mail.com","username1","password1");
	    	foundUser.setUserId(1);
	    	User newUser = new User("fakeemail1@mail.com","username1","password1");
	    	newUser.setUsername("user1");
	    	newUser.setUserId(1);
	    	
	    	when(userRepositoryMock.findById(newUser.getUserId())).thenReturn(Optional.of(foundUser));
	    	when(userRepositoryMock.existsById(newUser.getUserId())).thenReturn(true);
	    	when(userRepositoryMock.findByEmail(newUser.getEmail())).thenReturn(Optional.of(foundUser));
	    	when(userRepositoryMock.findByUsername(newUser.getUsername())).thenReturn(Optional.empty());
	    	
	    	// Test update method
	        userService.update(newUser);
	        foundUser.setUsername(newUser.getUsername());
	        // Verify that save method of repository is called
	        verify(userRepositoryMock, times(1)).save(foundUser);
	    }

	    @Test
	    void testDeleteById() {
	        // Mock data
	    	int bookId = 1;
	        User user = new User("fakeemail1@mail.com","username1","password1");
	        
	        
	        // Mock behavior of methods
	        when(userRepositoryMock.findById(bookId)).thenReturn(Optional.of(user));
	        when(userRepositoryMock.existsById(user.getUserId())).thenReturn(true);
	        when(userRepositoryMock.findById(user.getUserId())).thenReturn(Optional.of(user));
	        // Test deleteById method
	        userService.deleteById(bookId);

	     // Verify that delete method of repository is called
	        verify(userRepositoryMock, times(1)).delete(user);
	    }

	    @Test
	    void testDelete() {
	        // Mock data
	        User user = new User("fakeemail1@mail.com","username1","password1");
	        when(userRepositoryMock.existsById(user.getUserId())).thenReturn(true);
	        when(userRepositoryMock.findById(user.getUserId())).thenReturn(Optional.of(user));
	        // Test delete method
	        userService.delete(user);

	        // Verify that delete method of repository is called
	        verify(userRepositoryMock, times(1)).delete(user);
	    }

	    @Test
	    void testFindById() {
	        // Mock data
	        int userId = 1;

	        User user = new User("fakeemail1@mail.com","username1","password1");
	        
	        // Mock behavior of findById method
	        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));

	        // Test findById method
	        User result = userService.findById(userId);

	        // Verify the result
	        assertNotNull(result);
	    }
	    
	    @Test
	    void testRegisterThrowBadRequestEmail() {
	        // Mock data
	        User newUser = new User("fakemail1@mail.com", "newusername1", "newpassword1");
	        newUser.setUserId(1);
	        User existingUser = new User("fakemail1@mail.com", "newusername2", "newpassword1");
	        existingUser.setUserId(2);
	        when(userRepositoryMock.findByEmail("fakemail1@mail.com")).thenReturn(Optional.of(existingUser));

	        // Verify that the service method is called
	        assertThrows(BadRequestException.class, () -> userService.register(newUser));
	    }
	    
	    @Test
	    void testRegisterThrowBadRequestUsername() {
	        // Mock data
	        User newUser = new User("fakemail1@mail.com", "newusername1", "newpassword1");
	        newUser.setUserId(1);
	        User existingUser = new User("fakemail2@mail.com", "newusername1", "newpassword1");
	        existingUser.setUserId(2);
	        when(userRepositoryMock.findByEmail("fakemail1@mail.com")).thenReturn(Optional.of(newUser));

	        // Verify that the service method is called
	        assertThrows(BadRequestException.class, () -> userService.register(newUser));
	    }
	    
	    @Test
	    void testCreateNew() {
	        // Mock data
	        User newUser = new User("fakemail1@mail.com", "newusername1", "newpassword1");
	        when(userRepositoryMock.findByEmail("fakemail1@mail.com")).thenReturn(Optional.empty());
	        when(passwordEncoderMock.encode(newUser.getPassword())).thenReturn("Hashed Password");
	        userService.register(newUser);
	        
	        
	        // Verify that the service method is called
	        verify(userRepositoryMock, times(1)).save(newUser);
	    }

}
