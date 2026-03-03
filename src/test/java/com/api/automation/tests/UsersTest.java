package com.api.automation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;
import com.api.automation.pages.UsersPage;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test class for Users API endpoints
 * Tests CRUD operations on JSONPlaceholder Users API
 * Uses Page Object Model for maintainability
 */
@Feature("API Testing")
@Story("Users Endpoint Tests")
@DisplayName("Users API Tests")
public class UsersTest extends BaseTest {

    private UsersPage usersPage;

    /**
     * Initializes the Users page object before each test
     */
    @BeforeEach
    public void initializePageObjects() {
        usersPage = new UsersPage(requestSpec);
    }

    /**
     * Test GET /users endpoint
     * Validates that the API returns a list of users
     */
    @Test
    @DisplayName("Should retrieve all users")
    public void testGetAllUsers() {
        usersPage.verifyGetAllUsersResponse();
    }

    /**
     * Test GET /users/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve user with id 1")
    public void testGetUserById() {
        usersPage.verifyGetUserByIdResponse(1);
    }

    /**
     * Test GET /users?username=Bret endpoint
     * Validates filtering users by username
     */
    @Test
    @DisplayName("Should retrieve user by username")
    public void testGetUserByUsername() {
        usersPage.verifyGetUserByUsernameResponse("Bret");
    }

    /**
     * Test GET /users?email=Sincere@april.biz endpoint
     * Validates filtering users by email
     */
    @Test
    @DisplayName("Should retrieve user by email")
    public void testGetUserByEmail() {
        usersPage.verifyGetUserByEmailResponse("Sincere@april.biz");
    }

    /**
     * Test GET /users/1/posts endpoint
     * Validates retrieving posts for a specific user
     */
    @Test
    @DisplayName("Should retrieve posts for user")
    public void testGetUserPosts() {
        usersPage.verifyGetUserPostsResponse(1);
    }

    /**
     * Test GET /users/1/albums endpoint
     * Validates retrieving albums for a specific user
     */
    @Test
    @DisplayName("Should retrieve albums for user")
    public void testGetUserAlbums() {
        usersPage.verifyGetUserAlbumsResponse(1);
    }

    /**
     * Test POST /users endpoint
     * Tests creating a new user
     */
    @Test
    @DisplayName("Should create a new user successfully")
    public void testCreateUser() {
        usersPage.verifyCreateUserResponse("Test User", "testuser", "testuser@example.com");
    }
}
