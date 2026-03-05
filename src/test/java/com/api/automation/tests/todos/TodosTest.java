package com.api.automation.tests.todos;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.api.automation.tests.todos.TodosDataProvider.CREATED_STATUS_CODE;
import static com.api.automation.tests.todos.TodosDataProvider.SUCCESS_STATUS_CODE;
import static com.api.automation.tests.todos.TodosDataProvider.TEST_TODO_COMPLETED;
import static com.api.automation.tests.todos.TodosDataProvider.TEST_TODO_TITLE;
import static com.api.automation.tests.todos.TodosDataProvider.UPDATED_TODO_COMPLETED;
import static com.api.automation.tests.todos.TodosDataProvider.UPDATED_TODO_TITLE;
import static com.api.automation.tests.todos.TodosDataProvider.VALID_TODO_ID;
import static com.api.automation.tests.todos.TodosDataProvider.VALID_USER_ID;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test class for Todos API
 * Comprehensive tests for all Todos CRUD operations
 */
@Feature("API Testing")
@Story("Todos API Tests")
@DisplayName("Todos API Tests")
public class TodosTest {

    private TodosEndpoint todosEndpoint;

    @BeforeEach
    public void setUp() {
        todosEndpoint = new TodosEndpoint();
    }

    // GET Tests

    /**
     * Test GET /todos endpoint
     * Validates that the API returns a list of todos
     */
    @Test
    @DisplayName("Should retrieve all todos")
    public void testGetAllTodos() {
        todosEndpoint.getAllTodos()
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
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
        todosEndpoint.getTodoById(VALID_TODO_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("userId", notNullValue())
                .body("id", equalTo(VALID_TODO_ID))
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
        todosEndpoint.getTodosByUserId(VALID_USER_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(VALID_USER_ID)));
    }

    /**
     * Test GET /todos?completed=true endpoint
     * Validates filtering todos by completion status
     */
    @Test
    @DisplayName("Should retrieve completed todos")
    public void testGetCompletedTodos() {
        todosEndpoint.getTodosByCompletionStatus(true)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("completed", everyItem(equalTo(true)));
    }

    // POST Tests

    /**
     * Test POST /todos endpoint
     * Tests creating a new todo
     */
    @Test
    @DisplayName("Should create a new todo successfully")
    public void testCreateTodo() {
        todosEndpoint.createTodo(VALID_USER_ID, TEST_TODO_TITLE, TEST_TODO_COMPLETED)
                .then()
                .statusCode(CREATED_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(VALID_USER_ID))
                .body("title", equalTo(TEST_TODO_TITLE))
                .body("completed", equalTo(TEST_TODO_COMPLETED));
    }

    // PUT Tests

    /**
     * Test PUT /todos/1 endpoint
     * Tests updating an existing todo
     */
    @Test
    @DisplayName("Should update todo successfully")
    public void testUpdateTodo() {
        todosEndpoint.updateTodo(VALID_TODO_ID, VALID_USER_ID, UPDATED_TODO_TITLE, UPDATED_TODO_COMPLETED)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", equalTo(VALID_TODO_ID))
                .body("title", equalTo(UPDATED_TODO_TITLE))
                .body("completed", equalTo(UPDATED_TODO_COMPLETED));
    }

    // DELETE Tests

    /**
     * Test DELETE /todos/1 endpoint
     * Tests deleting a todo
     */
    @Test
    @DisplayName("Should delete todo successfully")
    public void testDeleteTodo() {
        todosEndpoint.deleteTodo(VALID_TODO_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE);
    }
}
