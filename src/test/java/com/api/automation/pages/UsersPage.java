package com.api.automation.pages;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Endpoints;

/**
 * Page Object for Users API endpoints
 * Contains all user-related API interactions and assertions
 */
public class UsersPage extends BasePage {
    
    public UsersPage(RequestSpecification requestSpec) {
        super(requestSpec);
    }
    
    /**
     * Retrieves all users
     * @return Response object
     */
    public Response getAllUsers() {
        return get(Endpoints.USERS);
    }
    
    /**
     * Retrieves a user by ID
     * @param userId The user ID
     * @return Response object
     */
    public Response getUserById(int userId) {
        return get(Endpoints.USERS + "/" + userId);
    }
    
    /**
     * Retrieves a user by username
     * @param username The username
     * @return Response object
     */
    public Response getUserByUsername(String username) {
        return get(Endpoints.USERS, "username", username);
    }
    
    /**
     * Retrieves a user by email
     * @param email The email address
     * @return Response object
     */
    public Response getUserByEmail(String email) {
        return get(Endpoints.USERS, "email", email);
    }
    
    /**
     * Retrieves all posts for a specific user
     * @param userId The user ID
     * @return Response object
     */
    public Response getUserPosts(int userId) {
        return get(Endpoints.USERS + "/" + userId + "/posts");
    }
    
    /**
     * Retrieves all albums for a specific user
     * @param userId The user ID
     * @return Response object
     */
    public Response getUserAlbums(int userId) {
        return get(Endpoints.USERS + "/" + userId + "/albums");
    }
    
    /**
     * Creates a new user
     * @param name The user name
     * @param username The username
     * @param email The email address
     * @return Response object
     */
    public Response createUser(String name, String username, String email) {
        String payload = String.format("{\n" +
                "  \"name\": \"%s\",\n" +
                "  \"username\": \"%s\",\n" +
                "  \"email\": \"%s\",\n" +
                "  \"address\": {\n" +
                "    \"street\": \"Test Street\",\n" +
                "    \"city\": \"Test City\",\n" +
                "    \"zipcode\": \"12345\"\n" +
                "  },\n" +
                "  \"phone\": \"123-456-7890\",\n" +
                "  \"website\": \"testuser.com\",\n" +
                "  \"company\": {\n" +
                "    \"name\": \"Test Company\",\n" +
                "    \"catchPhrase\": \"Test Catchphrase\",\n" +
                "    \"bs\": \"Test BS\"\n" +
                "  }\n" +
                "}", name, username, email);
        return post(Endpoints.USERS, payload);
    }
    
    /**
     * Verifies that all users response is valid
     */
    public UsersPage verifyGetAllUsersResponse() {
        getAllUsers()
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].username", notNullValue())
                .body("[0].email", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get user by ID response is valid
     * @param userId The user ID
     */
    public UsersPage verifyGetUserByIdResponse(int userId) {
        getUserById(userId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(userId))
                .body("name", notNullValue())
                .body("username", notNullValue())
                .body("email", notNullValue())
                .body("address", notNullValue())
                .body("phone", notNullValue())
                .body("website", notNullValue())
                .body("company", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get user by username response is valid
     * @param username The username
     */
    public UsersPage verifyGetUserByUsernameResponse(String username) {
        getUserByUsername(username)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(1))
                .body("[0].username", equalTo(username));
        return this;
    }
    
    /**
     * Verifies that get user by email response is valid
     * @param email The email address
     */
    public UsersPage verifyGetUserByEmailResponse(String email) {
        getUserByEmail(email)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(1))
                .body("[0].email", equalTo(email));
        return this;
    }
    
    /**
     * Verifies that get user posts response is valid
     * @param userId The user ID
     */
    public UsersPage verifyGetUserPostsResponse(int userId) {
        getUserPosts(userId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(userId)))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].body", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get user albums response is valid
     * @param userId The user ID
     */
    public UsersPage verifyGetUserAlbumsResponse(int userId) {
        getUserAlbums(userId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(userId)))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue());
        return this;
    }
    
    /**
     * Verifies that create user response is valid
     * @param name The user name
     * @param username The username
     * @param email The email address
     */
    public UsersPage verifyCreateUserResponse(String name, String username, String email) {
        createUser(name, username, email)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("name", equalTo(name))
                .body("username", equalTo(username))
                .body("email", equalTo(email));
        return this;
    }
}
