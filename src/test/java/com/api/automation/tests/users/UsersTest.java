package com.api.automation.tests.users;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.api.automation.tests.users.UsersDataProvider.CREATED_STATUS_CODE;
import static com.api.automation.tests.users.UsersDataProvider.TEST_EMAIL;
import static com.api.automation.tests.users.UsersDataProvider.TEST_USERNAME;
import static com.api.automation.tests.users.UsersDataProvider.TEST_USER_NAME;
import static com.api.automation.tests.users.UsersDataProvider.WRONG_EMAIL;
import static com.api.automation.tests.users.UsersDataProvider.WRONG_USERNAME;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test class for Users API
 * Comprehensive tests for all Users CRUD operations
 */
@Feature("API Testing")
@Story("Users API Tests")
@DisplayName("Users API Tests")
public class UsersTest {

    private UsersEndpoint usersEndpoint;

    @BeforeEach
    public void setUp() {
        usersEndpoint = new UsersEndpoint();
    }

    // GET Tests

    /**
     * Test GET /users endpoint
     * Validates that the API returns a list of users
     */
    @Test
    @DisplayName("Should retrieve all users")
    public void testGetAllUsers() {
        usersEndpoint.getAllUsers()
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
        usersEndpoint.getUserById(1)
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
        usersEndpoint.getUserByUsername("Bret")
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
        usersEndpoint.getUserByEmail("Sincere@april.biz")
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
        usersEndpoint.getUserPosts(1)
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
        usersEndpoint.getUserAlbums(1)
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
        usersEndpoint.getUserById(1)
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
        usersEndpoint.getUserById(10)
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
        usersEndpoint.getUserById(0)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: Negative ID (invalid)
     */
    @Test
    @DisplayName("Should return 404 for negative user ID")
    public void testNegativeIdBoundary() {
        usersEndpoint.getUserById(-1)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: ID beyond maximum (ID = 11)
     */
    @Test
    @DisplayName("Should return 404 for user ID beyond maximum (11)")
    public void testBeyondMaxIdBoundary() {
        usersEndpoint.getUserById(11)
                .then()
                .statusCode(404);
    }

    // POST Tests

    /**
     * Test POST /users endpoint
     * Tests creating a new user
     */
    @Test
    @DisplayName("Should create a new user successfully")
    public void testCreateUser() {
        usersEndpoint.createUser(TEST_USER_NAME, TEST_USERNAME, TEST_EMAIL)
                .then()
                .statusCode(CREATED_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("name", equalTo(TEST_USER_NAME))
                .body("username", equalTo(TEST_USERNAME))
                .body("email", equalTo(TEST_EMAIL));
    }
    
    // INTENTIONAL FAILURE TESTS - Expected to fail
    
    /**
     * Intentional failure test: Wrong email expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong email in user response")
    public void testIntentionalFailureWrongEmail() {
        usersEndpoint.getUserById(1)
                .then()
                .statusCode(200)
                .body("email", equalTo(WRONG_EMAIL)); // This will fail
    }
    
    /**
     * Intentional failure test: Wrong username expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong username in user response")
    public void testIntentionalFailureWrongUsername() {
        usersEndpoint.getUserById(1)
                .then()
                .statusCode(200)
                .body("username", equalTo(WRONG_USERNAME)); // This will fail
    }
}
