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

import com.fdmgroup.DionMangaReader.exceptions.BadRequestException;
import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.service.UserService;

@Transactional
@ExtendWith(MockitoExtension.class)
class UserControllerTest
{

	@Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFirstEndpoint() {
        // Mock data
        List<User> userList = new ArrayList<>();
        userList.add(new User("newemail1@mail.com", "newusername1", "newpassword1"));
        userList.add(new User("newemail2@mail.com", "newusername2", "newpassword2"));
        when(userServiceMock.findAll()).thenReturn(userList);

        // Call the controller method directly
        List<User> result = userController.firstEndpoint();

        // Verify the result
        assertEquals(userList, result);
    }

    @Test
    void testSearchByUsernsame() {
        // Mock data
        String username = "newusername";
        List<User> userList = new ArrayList<>();
        userList.add(new User("newemail1@mail.com", "newusername1", "newpassword1"));
        userList.add(new User("newemail2@mail.com", "newusername2", "newpassword2"));
        when(userServiceMock.findPartialMatch(username)).thenReturn(userList);

        // Call the controller method directly
        List<User> result = userController.searchByUsername(username);

        // Verify the result
        assertEquals(userList, result);
    }

    @Test
    void testFindById() {
        // Mock data
        int userId = 1;
        User user = new User("newemail1@mail.com", "newusername1", "newpassword1");
        when(userServiceMock.findById(userId)).thenReturn(user);

        // Call the controller method directly
        User result = userController.findById(userId);

        // Verify the result
        assertEquals(user, result);
    }

    @Test
    void testCreateNew() {
        // Mock data
        User newUser = new User("newemail1@mail.com", "newusername1", "newpassword1");

        // Call the controller method directly
        userController.createNew(newUser);

        // Verify that the service method is called
        verify(userServiceMock, times(1)).register(newUser);
    }

    @Test
    void testUpdateUser() {
        // Mock data
        User updatedUser = new User("newemail1@mail.com", "newusername1", "newpassword1");

        // Call the controller method directly
        userController.updateUser(updatedUser);

        // Verify that the service method is called
        verify(userServiceMock, times(1)).update(updatedUser);
    }

    @Test
    void testDeleteUser() {
        // Mock data
        int userId = 1;

        // Call the controller method directly
        userController.deleteUser(userId);

        // Verify that the service method is called
        verify(userServiceMock, times(1)).deleteById(userId);
    }

}
