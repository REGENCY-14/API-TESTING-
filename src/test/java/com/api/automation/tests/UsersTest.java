package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Endpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class for Users API endpoints
 * Tests CRUD operations on JSONPlaceholder Users API
 */
@Feature("API Testing")
@Story("Users Endpoint Tests")
@DisplayName("Users API Tests")
public class UsersTest extends BaseTest {

    private static final String USER_BY_ID_1 = Endpoints.USERS + "/1";

    /**
     * Test GET /users endpoint
     * Validates that the API returns a list of users
     */
    @Test
    @DisplayName("Should retrieve all users")
    public void testGetAllUsers() {
        given(requestSpec)
                .when()
                .get(Endpoints.USERS)
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
        given(requestSpec)
                .when()
                .get(USER_BY_ID_1)
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
     * Test GET /users/1 endpoint with response validation
     * Demonstrates extracting and validating specific fields including nested objects
     */
    @Test
    @DisplayName("Should validate user response fields")
    public void testGetUserWithResponseValidation() {
        Response response = given(requestSpec)
                .when()
                .get(USER_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .extract()
                .response();

        // Validate that required fields are present
        assert response.jsonPath().getInt("id") == 1 : "id should be 1";
        assert response.jsonPath().getString("name") != null : "name should not be null";
        assert response.jsonPath().getString("username") != null : "username should not be null";
        assert response.jsonPath().getString("email") != null : "email should not be null";
        assert response.jsonPath().getString("email").contains("@") : "email should be valid";
        
        // Validate nested address object
        assert response.jsonPath().getString("address.street") != null : "address.street should not be null";
        assert response.jsonPath().getString("address.city") != null : "address.city should not be null";
        assert response.jsonPath().getString("address.zipcode") != null : "address.zipcode should not be null";
        
        // Validate nested company object
        assert response.jsonPath().getString("company.name") != null : "company.name should not be null";
    }

    /**
     * Test GET /users/1 endpoint with nested field validation
     * Validates nested address and company objects
     */
    @Test
    @DisplayName("Should validate nested user objects")
    public void testGetUserNestedObjects() {
        given(requestSpec)
                .when()
                .get(USER_BY_ID_1)
                .then()
                .statusCode(200)
                .body("address.street", notNullValue())
                .body("address.suite", notNullValue())
                .body("address.city", notNullValue())
                .body("address.zipcode", notNullValue())
                .body("address.geo.lat", notNullValue())
                .body("address.geo.lng", notNullValue())
                .body("company.name", notNullValue())
                .body("company.catchPhrase", notNullValue())
                .body("company.bs", notNullValue());
    }

    /**
     * Test GET /users?username=Bret endpoint
     * Validates filtering users by username
     */
    @Test
    @DisplayName("Should retrieve user by username")
    public void testGetUserByUsername() {
        given(requestSpec)
                .queryParam("username", "Bret")
                .when()
                .get(Endpoints.USERS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(1))
                .body("[0].username", equalTo("Bret"))
                .body("[0].id", equalTo(1));
    }

    /**
     * Test GET /users?email=Sincere@april.biz endpoint
     * Validates filtering users by email
     */
    @Test
    @DisplayName("Should retrieve user by email")
    public void testGetUserByEmail() {
        given(requestSpec)
                .queryParam("email", "Sincere@april.biz")
                .when()
                .get(Endpoints.USERS)
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
        given(requestSpec)
                .when()
                .get(USER_BY_ID_1 + "/posts")
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
        given(requestSpec)
                .when()
                .get(USER_BY_ID_1 + "/albums")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue());
    }

    /**
     * Test GET /users/1/todos endpoint
     * Validates retrieving todos for a specific user
     */
    @Test
    @DisplayName("Should retrieve todos for user")
    public void testGetUserTodos() {
        given(requestSpec)
                .when()
                .get(USER_BY_ID_1 + "/todos")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].completed", notNullValue());
    }

    /**
     * Test GET /users with invalid ID
     * Validates error handling for non-existent user
     */
    @Test
    @DisplayName("Should return empty response for invalid user ID")
    public void testGetUserWithInvalidId() {
        given(requestSpec)
                .when()
                .get(Endpoints.USERS + "/99999")
                .then()
                .statusCode(404);
    }

    /**
     * Test POST /users endpoint
     * Tests creating a new user
     */
    @Test
    @DisplayName("Should create a new user successfully")
    public void testCreateUser() {
        String userPayload = "{\n" +
                "  \"name\": \"Test User\",\n" +
                "  \"username\": \"testuser\",\n" +
                "  \"email\": \"testuser@example.com\",\n" +
                "  \"address\": {\n" +
                "    \"street\": \"Test Street\",\n" +
                "    \"city\": \"Test City\",\n" +
                "    \"zipcode\": \"12345\"\n" +
                "  },\n" +
                "  \"phone\": \"123-456-7890\",\n" +
                "  \"website\": \"testuser.com\",\n" +
                "  \"company\": {\n" +
                "    \"name\": \"Test Company\"\n" +
                "  }\n" +
                "}";

        given(requestSpec)
                .body(userPayload)
                .when()
                .post(Endpoints.USERS)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("name", equalTo("Test User"))
                .body("username", equalTo("testuser"))
                .body("email", equalTo("testuser@example.com"));
    }

    /**
     * Test PUT /users/1 endpoint
     * Tests updating an existing user
     */
    @Test
    @DisplayName("Should update user successfully")
    public void testUpdateUser() {
        String updatePayload = "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Updated User Name\",\n" +
                "  \"username\": \"updatedusername\",\n" +
                "  \"email\": \"updated@example.com\",\n" +
                "  \"address\": {\n" +
                "    \"street\": \"Updated Street\",\n" +
                "    \"city\": \"Updated City\",\n" +
                "    \"zipcode\": \"54321\"\n" +
                "  },\n" +
                "  \"phone\": \"098-765-4321\",\n" +
                "  \"website\": \"updated.com\",\n" +
                "  \"company\": {\n" +
                "    \"name\": \"Updated Company\"\n" +
                "  }\n" +
                "}";

        given(requestSpec)
                .body(updatePayload)
                .when()
                .put(USER_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("name", equalTo("Updated User Name"))
                .body("email", equalTo("updated@example.com"));
    }

    /**
     * Test PATCH /users/1 endpoint
     * Tests partial update of a user
     */
    @Test
    @DisplayName("Should patch user email successfully")
    public void testPatchUser() {
        String patchPayload = "{\n" +
                "  \"email\": \"patched@example.com\"\n" +
                "}";

        given(requestSpec)
                .body(patchPayload)
                .when()
                .patch(USER_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("email", equalTo("patched@example.com"));
    }

    /**
     * Test DELETE /users/1 endpoint
     * Tests deleting a user
     */
    @Test
    @DisplayName("Should delete user successfully")
    public void testDeleteUser() {
        given(requestSpec)
                .when()
                .delete(USER_BY_ID_1)
                .then()
                .statusCode(200);
    }

    /**
     * Test email format validation
     * Validates that user emails follow proper format
     */
    @Test
    @DisplayName("Should validate user email format")
    public void testUserEmailFormat() {
        given(requestSpec)
                .when()
                .get(USER_BY_ID_1)
                .then()
                .statusCode(200)
                .body("email", matchesRegex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"));
    }
}
