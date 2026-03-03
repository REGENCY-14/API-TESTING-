package com.api.automation.tests.todos;

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
 * Test class for Todos POST endpoints
 * Tests POST operations on JSONPlaceholder Todos API
 */
@Feature("API Testing")
@Story("Todos POST Tests")
@DisplayName("Todos POST API Tests")
public class TodosPostTests extends BaseTest {

    // HTTP Request Methods
    
    private Response createTodo(int userId, String title, boolean completed) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"completed\": %b\n" +
                "}", userId, title, completed);
        return post(Endpoints.TODOS, payload);
    }

    // Test Methods

    /**
     * Test POST /todos endpoint
     * Tests creating a new todo
     */
    @Test
    @DisplayName("Should create a new todo successfully")
    public void testCreateTodo() {
        createTodo(1, "New Test Todo", false)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(1))
                .body("title", equalTo("New Test Todo"))
                .body("completed", equalTo(false));
    }
}
