package com.api.automation.tests.users;

import io.restassured.response.Response;
import utils.Endpoints;
import com.api.automation.base.BaseTest;

/**
 * Endpoint class for Users API
 * Encapsulates all HTTP operations related to Users
 */
public class UsersEndpoint {

    private BaseTest baseTest = new BaseTest();

    public UsersEndpoint() {
        baseTest.setup();
    }

    // GET Operations
    
    public Response getAllUsers() {
        return baseTest.get(Endpoints.USERS);
    }
    
    public Response getUserById(int userId) {
        return baseTest.get(Endpoints.USERS + "/" + userId);
    }
    
    public Response getUserByUsername(String username) {
        return baseTest.get(Endpoints.USERS, "username", username);
    }
    
    public Response getUserByEmail(String email) {
        return baseTest.get(Endpoints.USERS, "email", email);
    }
    
    public Response getUserPosts(int userId) {
        return baseTest.get(Endpoints.USERS + "/" + userId + "/posts");
    }
    
    public Response getUserAlbums(int userId) {
        return baseTest.get(Endpoints.USERS + "/" + userId + "/albums");
    }

    // POST Operations
    
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
        return baseTest.post(Endpoints.USERS, payload);
    }
}
