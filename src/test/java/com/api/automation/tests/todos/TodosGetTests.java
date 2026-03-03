package com.api.automation.tests.todos;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Todos GET endpoints
 * Tests GET operations on JSONPlaceholder Todos API
 */
@Feature("API Testing")
@Story("Todos GET Tests")
@DisplayName("Todos GET API Tests")
public class TodosGetTests extends BaseTest {

    // HTTP Request Methods
    
    private Response getAllTodos() {
        return get(Endpoints.TODOS);
    }
    
    private Response getTodoById(int todoId) {
        return get(Endpoints.TODOS + "/" + todoId);
    }
    
    private Response getTodosByUserId(int userId) {
        return get(Endpoints.TODOS, "userId", userId);
    }
    
    private Response getTodosByCompletionStatus(boolean completed) {
        return get(Endpoints.TODOS, "completed", completed);
    }

    // Test Methods

    /**
     * Test GET /todos endpoint
     * Validates that the API returns a list of todos
     */
    @Test
    @DisplayName("Should retrieve all todos")
    public void testGetAllTodos() {
        getAllTodos()
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].userId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].completed", notNullValue());
    }

    /**
     * Test GET /todos/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve todo with id 1")
    public void testGetTodoById() {
        getTodoById(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("userId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("completed", isA(Boolean.class));
    }

    /**
     * Test GET /todos?userId=1 endpoint
     * Validates filtering todos by user ID
     */
    @Test
    @DisplayName("Should retrieve todos by user ID")
    public void testGetTodosByUserId() {
        getTodosByUserId(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)));
    }

    /**
     * Test GET /todos?completed=true endpoint
     * Validates filtering todos by completion status
     */
    @Test
    @DisplayName("Should retrieve completed todos")
    public void testGetCompletedTodos() {
        getTodosByCompletionStatus(true)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("completed", everyItem(equalTo(true)));
    }
}
