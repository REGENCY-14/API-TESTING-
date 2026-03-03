package com.api.automation.tests.todos;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Todos PUT endpoints
 * Tests PUT operations on JSONPlaceholder Todos API
 */
@Feature("API Testing")
@Story("Todos PUT Tests")
@DisplayName("Todos PUT API Tests")
public class TodosPutTests extends BaseTest {

    // HTTP Request Methods
    
    private Response updateTodo(int todoId, int userId, String title, boolean completed) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"completed\": %b\n" +
                "}", userId, todoId, title, completed);
        return put(Endpoints.TODOS + "/" + todoId, payload);
    }

    // Test Methods

    /**
     * Test PUT /todos/1 endpoint
     * Tests updating an existing todo
     */
    @Test
    @DisplayName("Should update todo successfully")
    public void testUpdateTodo() {
        updateTodo(1, 1, "Updated Todo Title", true)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("title", equalTo("Updated Todo Title"))
                .body("completed", equalTo(true));
    }
}
