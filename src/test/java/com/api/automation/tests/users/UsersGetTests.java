package com.api.automation.tests.users;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Users GET endpoints
 * Tests GET operations on JSONPlaceholder Users API
 */
@Feature("API Testing")
@Story("Users GET Tests")
@DisplayName("Users GET API Tests")
public class UsersGetTests extends BaseTest {

    // HTTP Request Methods
    
    private Response getAllUsers() {
        return get(Endpoints.USERS);
    }
    
    private Response getUserById(int userId) {
        return get(Endpoints.USERS + "/" + userId);
    }
    
    private Response getUserByUsername(String username) {
        return get(Endpoints.USERS, "username", username);
    }
    
    private Response getUserByEmail(String email) {
        return get(Endpoints.USERS, "email", email);
    }
    
    private Response getUserPosts(int userId) {
        return get(Endpoints.USERS + "/" + userId + "/posts");
    }
    
    private Response getUserAlbums(int userId) {
        return get(Endpoints.USERS + "/" + userId + "/albums");
    }

    // Test Methods

    /**
     * Test GET /users endpoint
     * Validates that the API returns a list of users
     */
    @Test
    @DisplayName("Should retrieve all users")
    public void testGetAllUsers() {
        getAllUsers()
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].username", notNullValue())
                .body("[0].email", notNullValue());
    }

    /**
     * Test GET /users/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve user with id 1")
    public void testGetUserById() {
        getUserById(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("name", notNullValue())
                .body("username", notNullValue())
                .body("email", notNullValue())
                .body("address", notNullValue())
                .body("phone", notNullValue())
                .body("website", notNullValue())
                .body("company", notNullValue());
    }

    /**
     * Test GET /users?username=Bret endpoint
     * Validates filtering users by username
     */
    @Test
    @DisplayName("Should retrieve user by username")
    public void testGetUserByUsername() {
        getUserByUsername("Bret")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(1))
                .body("[0].username", equalTo("Bret"));
    }

    /**
     * Test GET /users?email=Sincere@april.biz endpoint
     * Validates filtering users by email
     */
    @Test
    @DisplayName("Should retrieve user by email")
    public void testGetUserByEmail() {
        getUserByEmail("Sincere@april.biz")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(1))
                .body("[0].email", equalTo("Sincere@april.biz"));
    }

    /**
     * Test GET /users/1/posts endpoint
     * Validates retrieving posts for a specific user
     */
    @Test
    @DisplayName("Should retrieve posts for user")
    public void testGetUserPosts() {
        getUserPosts(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].body", notNullValue());
    }

    /**
     * Test GET /users/1/albums endpoint
     * Validates retrieving albums for a specific user
     */
    @Test
    @DisplayName("Should retrieve albums for user")
    public void testGetUserAlbums() {
        getUserAlbums(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue());
    }
    
    /**
     * Boundary test: First valid user ID (ID = 1)
     */
    @Test
    @DisplayName("Should retrieve user with first valid boundary ID (1)")
    public void testFirstValidBoundary() {
        getUserById(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("name", notNullValue())
                .body("username", notNullValue())
                .body("email", notNullValue());
    }
    
    /**
     * Boundary test: Last valid user ID (ID = 10)
     */
    @Test
    @DisplayName("Should retrieve user with last valid boundary ID (10)")
    public void testLastValidBoundary() {
        getUserById(10)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(10))
                .body("name", notNullValue())
                .body("username", notNullValue())
                .body("email", notNullValue());
    }
    
    /**
     * Boundary test: Invalid ID = 0 (below minimum)
     */
    @Test
    @DisplayName("Should return 404 for user ID 0")
    public void testZeroIdBoundary() {
        getUserById(0)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: Negative ID (invalid)
     */
    @Test
    @DisplayName("Should return 404 for negative user ID")
    public void testNegativeIdBoundary() {
        getUserById(-1)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: ID beyond maximum (ID = 11)
     */
    @Test
    @DisplayName("Should return 404 for user ID beyond maximum (11)")
    public void testBeyondMaxIdBoundary() {
        getUserById(11)
                .then()
                .statusCode(404);
    }
    
    // INTENTIONAL FAILURE TESTS - Expected to fail
    
    /**
     * Intentional failure test: Wrong email expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong email in user response")
    public void testIntentionalFailureWrongEmail() {
        getUserById(1)
                .then()
                .statusCode(200)
                .body("email", equalTo("wrong@email.com")); // This will fail
    }
    
    /**
     * Intentional failure test: Wrong username expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong username in user response")
    public void testIntentionalFailureWrongUsername() {
        getUserById(1)
                .then()
                .statusCode(200)
                .body("username", equalTo("WrongUsername")); // This will fail
    }
}
