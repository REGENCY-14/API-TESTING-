package com.api.automation.tests.users;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Users POST endpoints
 * Tests POST operations on JSONPlaceholder Users API
 */
@Feature("API Testing")
@Story("Users POST Tests")
@DisplayName("Users POST API Tests")
public class UsersPostTests extends BaseTest {

    // HTTP Request Methods
    
    private Response createUser(String name, String username, String email) {
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

    // Test Methods

    /**
     * Test POST /users endpoint
     * Tests creating a new user
     */
    @Test
    @DisplayName("Should create a new user successfully")
    public void testCreateUser() {
        createUser("Test User", "testuser", "testuser@example.com")
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("name", equalTo("Test User"))
                .body("username", equalTo("testuser"))
                .body("email", equalTo("testuser@example.com"));
    }
}
