package com.api.automation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;
import com.api.automation.pages.TodosPage;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test class for Todos API endpoints
 * Tests CRUD operations on JSONPlaceholder Todos API
 * Uses Page Object Model for maintainability
 */
@Feature("API Testing")
@Story("Todos Endpoint Tests")
@DisplayName("Todos API Tests")
public class TodosTest extends BaseTest {

    private TodosPage todosPage;

    /**
     * Initializes the Todos page object before each test
     */
    @BeforeEach
    public void initializePageObjects() {
        todosPage = new TodosPage(requestSpec);
    }

    /**
     * Test GET /todos endpoint
     * Validates that the API returns a list of todos
     */
    @Test
    @DisplayName("Should retrieve all todos")
    public void testGetAllTodos() {
        todosPage.verifyGetAllTodosResponse();
    }

    /**
     * Test GET /todos/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve todo with id 1")
    public void testGetTodoById() {
        todosPage.verifyGetTodoByIdResponse(1);
    }

    /**
     * Test GET /todos?userId=1 endpoint
     * Validates filtering todos by user ID
     */
    @Test
    @DisplayName("Should retrieve todos by user ID")
    public void testGetTodosByUserId() {
        todosPage.verifyGetTodosByUserIdResponse(1);
    }

    /**
     * Test GET /todos?completed=true endpoint
     * Validates filtering todos by completion status
     */
    @Test
    @DisplayName("Should retrieve completed todos")
    public void testGetCompletedTodos() {
        todosPage.verifyGetCompletedTodosResponse();
    }

    /**
     * Test POST /todos endpoint
     * Tests creating a new todo
     */
    @Test
    @DisplayName("Should create a new todo successfully")
    public void testCreateTodo() {
        todosPage.verifyCreateTodoResponse(1, "New Test Todo", false);
    }

    /**
     * Test PUT /todos/1 endpoint
     * Tests updating an existing todo
     */
    @Test
    @DisplayName("Should update todo successfully")
    public void testUpdateTodo() {
        todosPage.verifyUpdateTodoResponse(1, "Updated Todo Title", true);
    }

    /**
     * Test DELETE /todos/1 endpoint
     * Tests deleting a todo
     */
    @Test
    @DisplayName("Should delete todo successfully")
    public void testDeleteTodo() {
        todosPage.verifyDeleteTodoResponse(1);
    }
}

