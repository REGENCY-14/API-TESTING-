package com.api.automation.pages;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Endpoints;

/**
 * Page Object for Todos API endpoints
 * Contains all todo-related API interactions and assertions
 */
public class TodosPage extends BasePage {
    
    public TodosPage(RequestSpecification requestSpec) {
        super(requestSpec);
    }
    
    /**
     * Retrieves all todos
     * @return Response object
     */
    public Response getAllTodos() {
        return get(Endpoints.TODOS);
    }
    
    /**
     * Retrieves a todo by ID
     * @param todoId The todo ID
     * @return Response object
     */
    public Response getTodoById(int todoId) {
        return get(Endpoints.TODOS + "/" + todoId);
    }
    
    /**
     * Retrieves todos filtered by user ID
     * @param userId The user ID
     * @return Response object
     */
    public Response getTodosByUserId(int userId) {
        return get(Endpoints.TODOS, "userId", userId);
    }
    
    /**
     * Retrieves todos filtered by completion status
     * @param completed The completion status
     * @return Response object
     */
    public Response getTodosByCompletionStatus(boolean completed) {
        return get(Endpoints.TODOS, "completed", completed);
    }
    
    /**
     * Creates a new todo
     * @param userId The user ID
     * @param title The todo title
     * @param completed The completion status
     * @return Response object
     */
    public Response createTodo(int userId, String title, boolean completed) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"completed\": %b\n" +
                "}", userId, title, completed);
        return post(Endpoints.TODOS, payload);
    }
    
    /**
     * Updates an existing todo
     * @param todoId The todo ID
     * @param userId The user ID
     * @param title The todo title
     * @param completed The completion status
     * @return Response object
     */
    public Response updateTodo(int todoId, int userId, String title, boolean completed) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"completed\": %b\n" +
                "}", userId, todoId, title, completed);
        return put(Endpoints.TODOS + "/" + todoId, payload);
    }
    
    /**
     * Deletes a todo
     * @param todoId The todo ID
     * @return Response object
     */
    public Response deleteTodo(int todoId) {
        return delete(Endpoints.TODOS + "/" + todoId);
    }
    
    /**
     * Verifies that all todos response is valid
     */
    public TodosPage verifyGetAllTodosResponse() {
        getAllTodos()
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].userId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].completed", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get todo by ID response is valid
     * @param todoId The todo ID
     */
    public TodosPage verifyGetTodoByIdResponse(int todoId) {
        getTodoById(todoId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("userId", notNullValue())
                .body("id", equalTo(todoId))
                .body("title", notNullValue())
                .body("completed", isA(Boolean.class));
        return this;
    }
    
    /**
     * Verifies that get todos by user ID response is valid
     * @param userId The user ID
     */
    public TodosPage verifyGetTodosByUserIdResponse(int userId) {
        getTodosByUserId(userId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(userId)));
        return this;
    }
    
    /**
     * Verifies that get completed todos response is valid
     */
    public TodosPage verifyGetCompletedTodosResponse() {
        getTodosByCompletionStatus(true)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("completed", everyItem(equalTo(true)));
        return this;
    }
    
    /**
     * Verifies that create todo response is valid
     * @param userId The user ID
     * @param title The todo title
     * @param completed The completion status
     */
    public TodosPage verifyCreateTodoResponse(int userId, String title, boolean completed) {
        createTodo(userId, title, completed)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(userId))
                .body("title", equalTo(title))
                .body("completed", equalTo(completed));
        return this;
    }
    
    /**
     * Verifies that update todo response is valid
     * @param todoId The todo ID
     * @param title The todo title
     * @param completed The completion status
     */
    public TodosPage verifyUpdateTodoResponse(int todoId, String title, boolean completed) {
        updateTodo(todoId, 1, title, completed)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(todoId))
                .body("title", equalTo(title))
                .body("completed", equalTo(completed));
        return this;
    }
    
    /**
     * Verifies that delete todo response is valid
     * @param todoId The todo ID
     */
    public TodosPage verifyDeleteTodoResponse(int todoId) {
        deleteTodo(todoId)
                .then()
                .statusCode(200);
        return this;
    }
}
